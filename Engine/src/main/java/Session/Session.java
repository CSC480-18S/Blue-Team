package Session;

import Models.*;
import Views.*;
import Components.*;
import Components.Log;
import com.google.gson.Gson;
import sun.jvm.hotspot.opto.HaltNode;

import java.util.ArrayList;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;

import static Models.GameConstants.BOARD_WIDTH;

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
    private int greenScore;
    private int goldScore;
    private Timer timer;
    private static final boolean waitForPlayer = true;
    private static final int turnTimeSec = 60;

    private Session() {
        //Session.LogInfo("Initializing objects");
        board = new Board();
        gui = new BoardGUI();
        validator = new Validator();
        users = new User[4];
        playedMoves = new ArrayList();
        dbQueries = new QueryClass();
        currentTurn = 0;
        timer = new Timer();
    }

    public static Session getSession() {
        if (session == null) {
            session = new Session();
            //adding SkyCats initially
            for(int i =0; i < session.users.length; i++){
                session.users[i] = new SkyCat(session);
            }
            session.gui.updateUsers(session.users);
            session.gui.setTurn(session.currentTurn);
            //play first move to start the game
            if(!waitForPlayer) {
                SkyCat skyCat1 = (SkyCat) session.users[0];
                Move move = skyCat1.chooseMove();
                if (move != null) {
                    System.out.println("x: " + move.getStartX());
                    System.out.println("y: " + move.getStartY());
                    System.out.println("horizontal: " + move.isHorizontal());
                    System.out.println("word: " + move.getWordString());
                    session.playWord(move.getStartX(), move.getStartY(), move.isHorizontal(), move.getWordString(), skyCat1);
                } else {
                    session.exchangeAllTiles(skyCat1);
                }
            }
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
                e.printStackTrace();
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

        // Use entered username
        if (userInfo != null) {
            newPlayer = new Player(username, macAddress, userInfo[1]);

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
            if (users[i].getClass() == SkyCat.class) {
                //return generated tiles back in bag
                TileGenerator.getInstance().putInBag(newPlayer.getHand());
                Tile[] hand = users[i].getHand();
                newPlayer.setHand(hand);
                users[i] = newPlayer;
                gui.updateUsers(users);
                if (currentTurn == -1) {
                    nextTurn();
                }
                return "JOINED";
            }
        }

        return "Could not join game.";
    }

    // Check if this is the first move
    public boolean firstMove() {
        return playedMoves.isEmpty();
    }

    // Validate word and place on board
    public String playWord(int startX, int startY, boolean horizontal, String word, User user) {
        if(users[currentTurn].getClass() == Player.class) {
            setTimer();
            users[currentTurn].setSkipped(0);
        }
        StringBuilder wordForScoring = new StringBuilder("");
        StringBuilder wordForValidating = new StringBuilder("");
        boolean wildCard = false;
        for(char c : word.toCharArray()){
            if(c == '*') {
                wordForScoring.append("-");
                wildCard = true;
            }
            else if(wildCard){
                wildCard = false;
                wordForValidating.append(c);
            }
            else{
                wordForValidating.append(c);
                wordForScoring.append(c);
            }
        }
        word = wordForValidating.toString();
        String initialLetters = word;
        TileGenerator tg = TileGenerator.getInstance();
        // Check if word length is less than 11,
        // it will cause errors if too big.
        // This should never happen but just in case..
        if (word.length() > 11) {
            return "Please play a shorter word?...";
        }

        if (word.length() == 1) {
            if ((startX + 1 < BOARD_WIDTH && board.getBoard()[startX + 1][startY].getTile() != null) || (startX > 0  && board.getBoard()[startX - 1][startY].getTile() != null)) {
                horizontal = true;
            }
        }

        // If first move check
        if (firstMove()) {
            int boardCenter = BOARD_WIDTH / 2;
            if ((horizontal ? startX : startY) > boardCenter
                    || ((horizontal ? startX : startY) + word.length() - 1) < boardCenter
                    || (horizontal ? startY : startX) != boardCenter) {
                return "Please start in the center of the board.";
            }

        }

        //this part appends middle part of the word, ai gives full word, so only for players
        if(user.getClass() == Player.class) {
            ArrayList<Character> chars = new ArrayList<>();
            char[] arr = word.toCharArray();
            for (int i = 0; i < arr.length; i++) {
                chars.add(arr[i]);
            }

            word = "";

            int count = 0;
            StringBuilder wordBuilder = new StringBuilder(word);
            //walk through the board and if tile is already placed - append it
            while (!chars.isEmpty()) {
                if (horizontal) {
                    if ((startX + count) < BOARD_WIDTH && board.getBoard()[startX + count][startY].getTile() != null) {
                        wordBuilder.append(board.getBoard()[startX + count][startY].getTile().getLetter());
                    } else {
                        wordBuilder.append(chars.get(0));
                        chars.remove(0);
                    }
                } else {
                    if ((startY + count) < BOARD_WIDTH && board.getBoard()[startX][startY + count].getTile() != null) {
                        wordBuilder.append(board.getBoard()[startX][startY + count].getTile().getLetter());
                    } else {
                        wordBuilder.append(chars.get(0));
                        chars.remove(0);
                    }
                }

                count++;
            }
            word = wordBuilder.toString();
        }
        Tile[] wordTiles = new Tile[word.length()];

        for (int i = 0; i < word.length(); i++) {

            wordTiles[i] = tg.getTile(word.charAt(i));
        }

        Object[] result = validator.isValidPlay(new Move(startX, startY, horizontal, wordTiles, user));

        System.out.println("----playWord()----");
        System.out.println("valid: " + result[0]);
        System.out.println("word: " + word);
        System.out.println("------------------");

        if ((int) result[0] == 1) {
            board.placeWord(startX, startY, horizontal, word);
            String scoringWord = wordForScoring.toString();
            Tile[] tilesForScoring = new Tile[scoringWord.length()];
            for(int i = 0; i < scoringWord.length(); i++){
                tilesForScoring[i] = tg.getTile(scoringWord.charAt(i));
            }
            Move scoringMove = (Move) result[1];
            scoringMove.setWord(tilesForScoring);
            int score = calculateMovePoints(scoringMove);
            user.setScore(user.getScore() + score);
            if (user instanceof Player) {
                Player temp = (Player) user;
                updateTeamScore(score, temp.getTeam());
            }
            replaceTiles(user, initialLetters);
            gui.updateBoard(board.getBoard());
            playedMoves.add((Move) result[1]);
            nextTurn();
            return "VALID";
        } else if ((int) result[0] == 2) {
            board.placeWord(startX, startY, horizontal, word);
            String scoringWord = wordForScoring.toString();
            Tile[] tilesForScoring = new Tile[scoringWord.length()];
            for(int i = 0; i < scoringWord.length(); i++){
                tilesForScoring[i] = tg.getTile(scoringWord.charAt(i));
            }
            Move scoringMove = (Move) result[1];
            scoringMove.setWord(tilesForScoring);
            int score = calculateMovePoints(scoringMove) * 2;
            user.setScore(user.getScore() + score);
            if (user instanceof Player) {
                Player temp = (Player) user;
                updateTeamScore(score, temp.getTeam());
            }
            replaceTiles(user, initialLetters);
            gui.updateBoard(board.getBoard());
            playedMoves.add((Move) result[1]);
            nextTurn();
            return "bonus";
        } else if ((int) result[0] == -1) {
            return "profane";
        }
        return "Invalid";

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
                    Tile[] hand = users[i].getHand();
                    //replace player with skycat
                    SkyCat skyCat = new SkyCat(Session.getSession());
                    //put generated tiles back
                    TileGenerator.getInstance().putInBag(skyCat.getHand());
                    skyCat.setHand(hand);
                    users[i] = skyCat;
                    gui.setTurn(currentTurn);
                    gui.updateUsers(users);
                    //play instead of player
                    Move move = skyCat.chooseMove();
                    if(move != null) {
                        playWord(move.getStartX(), move.getStartY(), move.isHorizontal(), move.getWordString(), skyCat);
                    } else {
                        exchangeAllTiles(skyCat);
                    }
                    return "removed";
                }
            }
        }
        return "user not found";
    }

    public String exchange(String mac, String letters) {
        //reset skip count
        users[currentTurn].setSkipped(0);
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
                            Tile tileToExchange = tg.getTile(hand[k].getLetter());
                            //generate new tile instead
                            replace.remove((Character) hand[k].getLetter());

                            hand[k] = tg.exchangeTile(tileToExchange);
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

    private void exchangeAllTiles(User user){
        TileGenerator tg = TileGenerator.getInstance();
        Tile[] hand = user.getHand();
        for (int i=0; i < hand.length; i++){
            hand[i] = tg.exchangeTile(hand[i]);
        }
        user.setHand(hand);
        nextTurn();
    }

    public String getBoardJSON() {
        Gson gson = new Gson();
        String result = gson.toJson(board);
        return result;
    }

    public int calculateMovePoints(Move move) {
        Space[][] boardLocal = board.getBoard();
        boolean horizontal = move.isHorizontal();
        ArrayList<Space> usedSpaces = new ArrayList();
        int points = 0;
        int wordMult = 1;
        int letterMult = 1;
        Multiplier mult = Multiplier.NONE;
        for (int i = 0; i < move.getWordString().length(); i++) {
            Space current;
            if (horizontal) {
                mult = boardLocal[move.getStartX() + i][move.getStartY()].
                        getMultiplier();
                current = boardLocal[move.getStartX() + i][move.getStartY()];
                usedSpaces.add(current);
            } else {
                mult = boardLocal[move.getStartX()][move.getStartY() + i].
                        getMultiplier();
                current = boardLocal[move.getStartX()][move.getStartY() + i];
                usedSpaces.add(current);
            }
            if (current.getUsed() == false) {
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
            }
            points += letterMult * move.getWord()[i].getValue();
        }
        points *= wordMult;
        //calculating the total number of points from offshoot moves
        ArrayList<Move> offshootMoves = move.getOffshootMoves();
        if (offshootMoves != null && !offshootMoves.isEmpty()) {
            for (Move aMove : offshootMoves) {
                if (aMove != null)
                    points += calculateMovePoints(aMove);
            }
            for (Space space : usedSpaces) {
                space.setUsed();
            }

        }
        System.out.println("points: " + points);
        return points;
    }

    /**
     * Set's next player's turn
     */
    private void nextTurn() {

        boolean done = false;
        while (!done) {
            //increment player number
            if (currentTurn == 3) {
                currentTurn = 0;
            } else {
                currentTurn++;
            }
            if (users[currentTurn] != null) {
                gui.setTurn(currentTurn);
                gui.updateUsers(users);
                done = true;
            }
        }
        if(users[currentTurn].getClass() == SkyCat.class){
        SkyCat skyCat = (SkyCat) session.users[currentTurn];
        Move move = skyCat.chooseMove();
        if(move != null) {
            System.out.println(skyCat.getUsername());
            System.out.println("x: " + move.getStartX());
            System.out.println("y: " + move.getStartY());
            System.out.println("horizontal: " + move.isHorizontal());
            System.out.println("word: " + move.getWordString());
            Validator val = new Validator();
            System.out.println("Is Valid: " + val.isValidPlay(move)[0]);
            //reset skip counter
            skyCat.setSkipped(0);
            session.playWord(move.getStartX(), move.getStartY(), move.isHorizontal(), move.getWordString(), skyCat);
        } else {
            if(skyCat.getSkipped() < 2) {
                skyCat.setSkipped(skyCat.getSkipped() + 1);
                exchangeAllTiles(users[currentTurn]);
            } else {//probably no moves left
                System.out.println("GAME OVER !");
            }
        }
    } else { //next user is real player
            setTimer();
        }

    }

    private void setTimer(){
        if(users[currentTurn].getClass() == Player.class) {
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    //check if player skipped 3 times
                    if (users[currentTurn].getSkipped() == 2) {
                        //replace player with skycat
                        Tile[] hand = users[currentTurn].getHand();
                        SkyCat skyCat = new SkyCat(Session.getSession());
                        //put generated tiles back in bag
                        TileGenerator.getInstance().putInBag(skyCat.getHand());
                        skyCat.setHand(hand);
                        users[currentTurn] = skyCat;
                    } else {
                        users[currentTurn].setSkipped(users[currentTurn].getSkipped() + 1);
                        nextTurn();
                    }
                }
            }, turnTimeSec * 1000);
        }
    }

    private void replaceTiles(User user, String letters) {
        TileGenerator tg = TileGenerator.getInstance();
        Tile[] hand = user.getHand();
        ArrayList<Character> replace = new ArrayList<>();
        for (int i = 0; i < letters.length(); i++) {
            replace.add(letters.charAt(i));
        }

        for (int i = 0; i < users.length; i++) {
            if (users[i] == user) {
                for (int k = 0; k < hand.length; k++) {
                    if (replace.contains(hand[k].getLetter())) {
                        //generate new tile instead
                        replace.remove((Character) hand[k].getLetter());
                        hand[k] = tg.getRandTile();
                    }
                }
                users[i].setHand(hand);
                break;
            }
        }
    }

    public int getCurrentTurn() {
        return currentTurn;
    }

    // Check whos turn it is and return their username
    public String isMyTurn(String mac)
    {
        try
        {
            // make sure users length lines up with current turn
            int turn = getCurrentTurn();
            if (users.length >= turn)
            {
                // Check if real live player
                if (users[turn].getClass() == Player.class)
                {
                    // Check if its this players turn
                    if (((Player)users[turn]).getMacAddress().equals(mac)) {
                        return "You!";
                    }
                    // Must be another player, give their username
                    return users[turn].getUsername();

                }
                // Its Skycat's turn
                else
                {
                    return "Skycat";
                }
            }
        } catch (Exception e) { e.printStackTrace();}
        // Default to AI's turn
        return "Skycat";
    }

    private void updateTeamScore(int score, String team) {
        switch (team) {
            case "GREEN":
                greenScore += score;
                break;
            case "GOLD":
                goldScore += score;
                break;
            default:
                break;
        }
    }

    public int[] getTeamScores() {
        int[] teamScores = {greenScore, goldScore};
        return teamScores;
    }
}
