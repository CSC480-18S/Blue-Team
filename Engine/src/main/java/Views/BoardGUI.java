/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import Models.*;
import static Models.GameConstants.*;

/**
 * @author Bohdan
 */
public class BoardGUI {
    JFrame frame;
    JTable jt;
    String column[]={"0","1","2","3","4","5", "6","7","8","9","10"};
    String[][] data = new String[BOARD_WIDTH][BOARD_WIDTH];

     public BoardGUI(){
        frame=new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        for (int r = 0; r < BOARD_WIDTH; r++) {
            for (int c = 0; c < BOARD_WIDTH; c++) {
                data[r][c] = " ";
            }
        }
        jt=new JTable(data, column);
        jt.setDefaultEditor(Object.class, null);
        //jt.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jt.setRowHeight(40);
        jt.setBounds(30,40,100,400);
        jt.setGridColor(Color.BLUE);
        jt.setFont(new Font("Serif", Font.BOLD, 20));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        jt.getTableHeader().setDefaultRenderer(centerRenderer);
        for(int x=0;x<BOARD_WIDTH;x++){
         jt.getColumnModel().getColumn(x).setCellRenderer( centerRenderer );
        }

        JScrollPane sp=new JScrollPane(jt);
        frame.add(sp);
        frame.setSize(800,500);
        frame.setVisible(true);
    }

    public void updateBoard (Space[][] board){
        for (int r = 0; r < BOARD_WIDTH; r++) {
            for (int c = 0; c < BOARD_WIDTH; c++) {
                if(board[r][c].getTile() != null){
                    char letter = board[r][c].getTile().getLetter();
                    data[r][c] = Character.toString(letter);
                }
            }
        }
        jt.repaint();
    }

}