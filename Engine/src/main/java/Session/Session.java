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

    public void InitializeObjects()
    {
        Session.LogInfo("Initializing objects");
        board = new Board();
        gui = new BoardGUI();
        validator = new Validator();
        //populateBoard();
    }

    private void populateBoard(){
        Scanner sc = new Scanner(System.in);
        while(true) {
            System.out.print("Enter the word: ");
            String word = sc.next();

            System.out.print("Enter X: ");
            int startX = sc.nextInt();

            System.out.print("Enter Y: ");
            int startY = sc.nextInt();

            System.out.print("Horizontal?(y/n): ");
            String hor = sc.next();
            boolean horizontal;
            if (hor.equals("y")) {
                horizontal = true;
            } else {
                horizontal = false;
            }
            if (validator.isValidPlay(startX, startY, horizontal, word) == 1)
            {
                board.placeWord(startX,startY,horizontal, word);
                gui.updateBoard(board.getBoard());
            }
        }
    }

    public boolean playWord(int startX, int startY, boolean horizontal, String word ){
        if(validator.isValidPlay(startX, startY, horizontal, word) == 1){
            board.placeWord(startX,startY,horizontal, word);
            gui.updateBoard(board.getBoard());
            return true;
        }
        return false;
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
}
