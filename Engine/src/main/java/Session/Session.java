package Session;

import Models.*;
import Views.*;
import Components.*;
import Components.Log;
import com.google.gson.Gson;

import java.util.ArrayList;

import java.util.Scanner;
import java.util.logging.Level;

/**
 * @author Bohdan Yevdokymov
 */
public class Session {

    private static Session session;
    private Log log;
    private Board board;
    private BoardGUI gui;
    private Validator validator;
    private User[] users;
    private ArrayList<Move> playedMoves;
    private QueryClass dbQueries;
    private int currentTurn;

    private Session() {
        //Session.LogInfo("Initializing objects");
        board = new Board();
        gui = new BoardGUI();
        validator = new Validator();
        users = new User[4];
        playedMoves = new ArrayList();
        dbQueries = new QueryClass();
        currentTurn = -1;
    }
    public static Session getSession() {
        if (session == null) {
            session = new Session();
        }
        return session;
    }

    public Log getLogger() {
        // Just incase session hasn't been instantiated yet
        if (session == null) {
            getSession();
        }

        if (session.log == null) {
            // Initialize logger
            try {
                session.log = new Log();
                session.log.logger.setLevel(Level.INFO);
            } catch (Exception e) {
                System.out.println("Error creating logger: \n" + e);
            }
        }

        return session.log;
    }


    /// Setup Player object and join the game
    public String addPlayer(String username, String macAddress, String team) {
        Player newPlayer = null;

        //validating the team name
        if (team.toUpperCase().compareTo("GREEN") != 0 && team.toUpperCase().compareTo("GOLD") != 0
                && team != "" && team != null) {
            return "Invalid team name.";
        }

        //check if username already exists in game first
        for (int i = 0; i < users.length; i++) {
            if (users[i] != null) {
                if (users[i].getClass() == Player.class) {
                    Player player = (Player) users[i];
                    if (player.getMacAddress().equals(macAddress)) {
                        return "JOINED";
                    }
                }
            }
        }

        // If user hasn't joined yet make sure they have entered a username
        // This prevents the user from auto joining at page load
        if (username == "")
            return "";

        /*checking for mac address in the user database to see if user already has a user profile. If a profile exists then userInfo will have the
        username stored in index 0 and team stored in index 1*/
        String[] userInfo = dbQueries.findUser(macAddress);

        if (userInfo != null) {
            newPlayer = new Player(userInfo[0], macAddress, userInfo[1]);

        } //creating a new user profile in the database if the mac address is not already registered
        else {

            if (!dbQueries.userAlreadyExists(username)) {
                if (username.compareTo("") != 0) {
                    dbQueries.addNewUser(username, macAddress, team);
                } else {
                    return "empty user name";
                }
            } else {
                return "The username chosen is already in use.";
            }

            newPlayer = new Player(username, macAddress, team);
        }

        //check if any users are not initialized yet
        for (int i = 0; i < users.length; i++) {
            if (users[i] == null) {

                users[i] = newPlayer;
                gui.updateUsers(users);
                if(currentTurn == -1){
                    nextTurn();
                }
                return "JOINED";
            }
        }

        return "Could not join game.";
    }

    // Check if this is the first move
    public boolean firstMove()
    {
        return playedMoves.isEmpty();
    }
  
    // Validate word and place on board
    public String playWord(int startX, int startY, boolean horizontal, String word, User user) {
        // Check if word length is less than 11,
        // it will cause errors if too big.
        // This should never happen but just in case..
        if (word.length() > 11)
        {
            return "Please play a shorter word?...";
        }

        // If first move check
        if (firstMove())
        {
            int boardCenter = GameConstants.BOARD_WIDTH/2;
            if ((horizontal ? startX : startY) > boardCenter
                    || ((horizontal ? startX : startY) + word.length() - 1) < boardCenter
                    || (horizontal ? startY : startX) != boardCenter)
            {
                return "Please start in the center of the board.";
            }

        }

        ArrayList<Character> chars = new ArrayList<>();
        char[] arr = word.toCharArray();
        for(int i =0; i < arr.length;i++){
            chars.add(arr[i]);
        }

        word = "";

        int count = 0;
        StringBuilder wordBuilder = new StringBuilder(word);
        //walk through the board and if tile is already placed - append it
        while(!chars.isEmpty()){
            if(horizontal){
                if(board.getBoard()[startX + count][startY].getTile() != null){
                    wordBuilder.append(board.getBoard()[startX + count][startY].getTile().getLetter());
                } else {
                    wordBuilder.append(chars.get(0));
                    chars.remove(0);
                }
            } else {
                if(board.getBoard()[startX][startY + count].getTile() != null){
                    wordBuilder.append(board.getBoard()[startX][startY + count].getTile().getLetter());
                } else {
                    wordBuilder.append(chars.get(0));
                    chars.remove(0);
                }
            }

            count++;
        }
        word = wordBuilder.toString();
        Tile[] wordTiles = new Tile[word.length()];

        for (int i = 0; i < word.length(); i++) {
            wordTiles[i] = TileGenerator.getTile(word.charAt(i));
        }

        Object[] result = validator.isValidPlay(new Move(startX, startY, horizontal, wordTiles, user));

        if ((int) result[0] == 1) {
            board.placeWord(startX, startY, horizontal, word);
            int score = calculateMovePoints((Move) result[1]);
            user.setScore(user.getScore() + score);
            System.out.println("Played move for " + score + " points");
            gui.updateBoard(board.getBoard());
            playedMoves.add((Move) result[1]);
            nextTurn();
            return "VALID";
        } else if ((int) result[0] == 2) {
            board.placeWord(startX, startY, horizontal, word);
            gui.updateBoard(board.getBoard());
            playedMoves.add((Move) result[1]);
            nextTurn();
            return "bonus";
        } else if ((int) result[0] == -1) {
            return "profane";
        }
        return "invalid";

    }

    public Space[][] getBoardAsSpaces() {
        return board.getBoard();
    }

    public static void LogInfo(String msg) {
        getSession().getLogger().logger.info(msg);
    }

    public static void LogWarning(String msg) {
        getSession().getLogger().logger.warning(msg);
    }

    public User[] getUsers() {
        return users;
    }

    public String removePlayer(String mac) {
        for (int i = 0; i < users.length; i++) {
            if (users[i] != null && users[i].getClass() == Player.class) {
                Player player = (Player) users[i];
                if (player.getMacAddress().equals(mac)) {
                    users[i] = null;
                    return "removed";
                }
            }
        }
        return "user not found";
    }

    public String exchange(String mac, String letters) {
        letters = letters.toUpperCase();
        TileGenerator tg = TileGenerator.getInstance();
        int count = 0;
        //add letter to ArrayList
        ArrayList<Character> replace = new ArrayList<>();
        for (int i = 0; i < letters.length(); i++) {
            replace.add(letters.charAt(i));
        }

        //find corresponding user
        for (int i = 0; i < users.length; i++) {
            if (users[i] != null && users[i].getClass() == Player.class) {
                Player player = (Player) users[i];
                if (player.getMacAddress().equals(mac)) {
                    Tile[] hand = player.getHand();
                    //check each tile in hand for match
                    for (int k = 0; k < hand.length; k++) {
                        if (replace.contains(hand[k].getLetter())) {
                            //generate new tile instead
                            replace.remove((Character) hand[k].getLetter());

                            hand[k] = tg.getRandTile();
                            count++;
                        }
                    }
                    player.setHand(hand);
                    nextTurn();
                    return "Exchanged: " + count + " tiles";
                }
            }
        }
        return "Username not found";
    }

    public String getBoardJSON() {
        Gson gson = new Gson();
        String result = gson.toJson(board);
        return result;
    }

    public int calculateMovePoints(Move move) {
        Space[][] boardLocal = board.getBoard();
        boolean horizontal = move.isHorizontal();
        int points = 0;
        int wordMult = 1;
        int letterMult = 1;
        Multiplier mult = Multiplier.NONE;
        for (int i = 0; i < move.getWordString().length(); i++) {
            if (horizontal) {
                mult = boardLocal[move.getStartX() + i][move.getStartY()].
                        getMultiplier();
            } else {
                mult = boardLocal[move.getStartX()][move.getStartY() + 1].
                        getMultiplier();
            }
                switch (mult) {
                    case NONE:
                        letterMult = 1;
                        break;
                    case DOUBLE_LETTER:
                        letterMult = 2;
                        break;
                    case DOUBLE_WORD:
                        wordMult *= 2;
                        break;
                    case TRIPLE_LETTER:
                        letterMult = 3;
                        break;
                    case TRIPLE_WORD:
                        wordMult *= 3;
                        break;
                }
            points += letterMult * move.getWord()[i].getValue();
        }
        points *= wordMult;
        //calculating the total number of points from offshoot moves
        ArrayList<Move> offshootMoves = move.getOffshootMoves();
        if(offshootMoves != null)
            for(Move aMove : offshootMoves){
                if(aMove != null)
                    points += calculateMovePoints(aMove);
            }
        return points;
    }

    /**
     * Set's next player's turn
     */
    private void nextTurn(){

        boolean done = false;
        while (!done){
            //increment player number
            if(currentTurn == 3){
                currentTurn = 0;
            } else {
                currentTurn++;
            }
            if(users[currentTurn] != null){
                gui.setTurn(currentTurn);
                gui.updateUsers(users);
                done = true;
            }
        }

    }

    public int getCurrentTurn(){
        return currentTurn;
    }
}
