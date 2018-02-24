package main.java.Session;

import main.java.Models.*;
import main.java.Views.*;
import main.java.Components.*;
import main.java.Components.Log;

import java.util.Scanner;
import java.util.logging.Level;


/**
 *
 * @author Bohdan Yevdokymov
 */
public class Session {
    private static Session session;
    private Board board;
    private BoardGUI gui;
    private Validator validator;
    public Log log;

    private Session()
    {
        board = new Board();
        gui = new BoardGUI();
        validator = new Validator();
        //populateBoard();

        // initialize logger
        try
        {
            log = new Log();
            log.logger.setLevel(Level.WARNING);
        }
        catch(Exception e){}
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
            board.placeWord(startX,startY,horizontal, word);
            gui.updateBoard(board.getBoard());
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

    public static void LogInfo(String msg)
    {
        session.log.logger.info(msg);
    }
    public static void LogWarning(String msg)
    {
        session.log.logger.warning(msg);
    }
}
