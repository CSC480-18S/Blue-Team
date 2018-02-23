package main.java.Session;
import main.java.Models.*;
import main.java.Views.*;

import java.util.Scanner;

/**
 *
 * @author Bohdan Yevdokymov
 */
public class Session {
    private static Session session;
    private Board board;
    private BoardGUI gui;
    private Validator validator;

    private Session(){
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
}
