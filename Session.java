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

    private Session() {
        //Session.LogInfo("Initializing objects");
        board = new Board();
        gui = new BoardGUI();
        validator = new Validator();
        users = new User[4];
        playedMoves = new ArrayList();
        dbQueries = new QueryClass();
    }

    public String playWord(int startX, int startY, boolean horizontal, String word, User user) {
        if (playedMoves.isEmpty() && startY != GameConstants.BOARD_WIDTH / 2) {
            return "Please start in the center of the board.";
        }

        Object[] result = validator.isValidPlay(new Move(startX, startY, horizontal, word, user));

        if ((int) result[0] == 1) {
            board.placeWord(startX, startY, horizontal, word);
            gui.updateBoard(board.getBoard());
            playedMoves.add((Move) result[1]);
            return "success";
        } else if ((int) result[0] == 2) {
            board.placeWord(startX, startY, horizontal, word);
            gui.updateBoard(board.getBoard());
            playedMoves.add((Move) result[1]);
            return "success, bonus";
        } else if ((int) result[0] == -1) {
            return "profane word";
        }
        return "invalid";

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

    public String addPlayer(String username, String macAddress, String team) {
        Player newPlayer = null;

        //validating the team name
        if (team.toUpperCase().compareTo("GREEN") != 0 && team.toUpperCase().compareTo("GOLD") != 0
                && team != "" && team != null)
            return "Invalid team name.";

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

        /*checking for mac address in the user database to see if user already has a user profile. If a profile exists then userInfo will have the
        username stored in index 0 and team stored in index 1*/
        String[] userInfo = dbQueries.findUser(macAddress);

        if (userInfo != null) {
            newPlayer = new Player(userInfo[0], macAddress, userInfo[1]);

        }

        //creating a new user profile in the database if the mac address is not already registered
        else {

            if (!dbQueries.userAlreadyExists(username)) {
                if(username.compareTo("") != 0)
                    dbQueries.addNewUser(username, macAddress, team);
                else
                    return "empty user name";
            } else {
                return "The username chosen is already in use.";
            }

            newPlayer = new Player(username, macAddress, team);
        }

        //check if any users are not initialized yet
        for (int i = 0; i < users.length; i++) {
            if (users[i] == null) {

                users[i] = newPlayer;
                return "JOINED";
            }
        }

        return "Could not join game.";
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
                    return "Exchanged: " + count + " tiles";
                }
            }
        }
        return "Username not found";
    }

    public String getBoardJSON(){
        Gson gson = new Gson();
        String result = gson.toJson(board);
        return result;
    }
}
