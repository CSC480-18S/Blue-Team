package Session;

import Models.*;
import Views.*;
import Components.*;
import Components.Log;

import java.util.Scanner;
import java.util.logging.Level;


/**
 *
 * @author Bohdan Yevdokymov
 */
public class Session {
    private static Session session;
    private Log log;
    private Board board;
    private BoardGUI gui;
    private Validator validator;
    private User[] users;


    private Session()
    {
        //Session.LogInfo("Initializing objects");
        board = new Board();
        gui = new BoardGUI();
        validator = new Validator();
        users = new User[4];
    }


    public String playWord(int startX, int startY, boolean horizontal, String word, User user){
        int result = validator.isValidPlay(startX, startY, horizontal, word);
        if(result == 1){
            board.placeWord(startX,startY,horizontal, word);
            gui.updateBoard(board.getBoard());
            return "success";
        } else if(result == 2){
            board.placeWord(startX,startY,horizontal, word);
            gui.updateBoard(board.getBoard());
            return "success, bonus";
        } else if(result == -1){
            return "profane word";
        }
        return "invalid";
    }

    public static Session getSession(){
        if(session == null){
            session = new Session();
        }
        return session;
    }

    public Log getLogger()
    {
        // Just incase session hasn't been instantiated yet
        if (session == null) getSession();

        if (session.log == null)
        {
            // Initialize logger
            try {
                session.log = new Log();
                session.log.logger.setLevel(Level.INFO);
            }
            catch(Exception e){System.out.println("Error creating logger: \n" + e);}
        }

        return session.log;
    }

    public String addPlayer(String username, String macAddress){
        //check if username already exists first
        for(int i = 0; i < users.length; i++){
            if(users[i] != null) {
                if(users[i].getUsername().equals(username)){
                    return "already joined";
                }
            }
        }
        //check if any users are not initialized yet
        for(int i = 0; i < users.length; i++) {
            if(users[i] == null){
                users[i] = new Player(username, macAddress);
                return "joined";
            }
        }

        return "not joined";
    }

    public Space[][] getBoardAsSpaces()
    {
        return board.getBoard();
    }

    public static void LogInfo(String msg)
    {
        getSession().getLogger().logger.info(msg);
    }
    public static void LogWarning(String msg)
    {
        getSession().getLogger().logger.warning(msg);
    }

    public User[] getUsers(){
        return users;
    }
}
