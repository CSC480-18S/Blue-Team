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
import java.awt.Color;
import java.io.File;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;


/**
 * @author mide_
 */
public class BoardGUI{
    private static final Image[] tiles = new Image[28];
    public final ClassLoader classloader = Thread.currentThread().getContextClassLoader();

    int length13 = 7;
    int length24 = 1;

    JFrame frame;

    JTable jt;
    JTable user1;
    JTable user2;
    JTable user3;
    JTable user4;

    JLabel player1Label;
    JLabel player2Label;
    JLabel player3Label;
    JLabel player4Label;


    String column[]={"0","1","2","3","4","5","6","7","8","9","10"};
    String[][] data = new String[BOARD_WIDTH][BOARD_WIDTH];

    String usercolumn13[]= {"0","1","2","3","4","5","6"};
    String[][] user13 = new String[length24][length13];

    String usercolumn24[]= {"0"};
    String[][] user24 = new String[length13][length24];

    public BoardGUI(){
        frame=new JFrame("Oswebble");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        for (int r = 0; r < BOARD_WIDTH; r++) {
            for (int c = 0; c < BOARD_WIDTH; c++) {
                data[r][c] = " ";
            }
        }

        for (int i = 0; i < length24; i++) {
            for (int j = 0; j < length13; j++) {

                user13[i][j] = " ";
            }
        }

        for (int k = 0; k < length13; k++) {
            for (int l = 0; l < length24; l++) {
                user24[k][l] = " ";
            }
        }

        final ImageIcon icon_main = new ImageIcon(classloader.getResource("background.jpg"));
        JPanel background_main = new JPanel( new BorderLayout() )
        {
            protected void paintComponent(Graphics g)
            {
                super.paintComponent(g);

                g.drawImage(icon_main.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        background_main.add(panel);
        frame.getContentPane().add(background_main, BorderLayout.CENTER);
        GridBagConstraints c = new GridBagConstraints();

        final ImageIcon icon5 = new ImageIcon(classloader.getResource("Rack2.png"));
        JPanel background5 = new JPanel( new BorderLayout() )
        {
            protected void paintComponent(Graphics g)
            {
                super.paintComponent(g);

                g.drawImage(icon5.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };

        user2 = new JTable(user24, usercolumn24);
        user2.setDefaultEditor(Object.class, null);
        user2.setRowHeight(50);
        user2.setGridColor(Color.BLACK);
        user2.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        user2.setBackground(Color.WHITE);
        user2.setFont(new Font("Serif", Font.BOLD, 15));
        user2.setBackground(new Color(244, 212, 108));
        user2.getColumnModel().getColumn(0).setPreferredWidth(50);
        DefaultTableCellRenderer renderer5 = (DefaultTableCellRenderer)user2.getDefaultRenderer(Object.class);
        renderer5.setOpaque(false);

        JPanel panel_2 = new JPanel();
        panel_2.setBounds(621, 53, 27, 351);
        panel_2.setOpaque( false );
        panel_2.add(user2);
        background5.add(panel_2);
        c.gridx = 0;
        c.gridy = 5;
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(10, 10, 10, 10);
        panel.add(background5, c);

        final ImageIcon icon4 = new ImageIcon(classloader.getResource("Rack3.png"));
        JPanel background4 = new JPanel( new BorderLayout() )
        {
            protected void paintComponent(Graphics g)
            {
                super.paintComponent(g);

                g.drawImage(icon4.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };

        user3 = new JTable(user13, usercolumn13);
        user3.setDefaultEditor(Object.class, null);
        user3.setRowHeight(50);
        user3.getColumnModel().getColumn(0).setPreferredWidth(50);
        user3.getColumnModel().getColumn(1).setPreferredWidth(50);
        user3.getColumnModel().getColumn(2).setPreferredWidth(50);
        user3.getColumnModel().getColumn(3).setPreferredWidth(50);
        user3.getColumnModel().getColumn(4).setPreferredWidth(50);
        user3.getColumnModel().getColumn(5).setPreferredWidth(50);
        user3.getColumnModel().getColumn(6).setPreferredWidth(50);
        user3.setGridColor(Color.BLACK);
        user3.setBackground(new Color(53, 226, 40));
        user3.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        user3.setFont(new Font("Serif", Font.BOLD, 15));
        DefaultTableCellRenderer renderer4 = (DefaultTableCellRenderer)user3.getDefaultRenderer(Object.class);
        renderer4.setOpaque(false);

        JPanel panel_3 = new JPanel();
        panel_3.setBounds(621, 53, 27, 351);
        panel_3.setOpaque( false );
        panel_3.add(user3);
        background4.add(panel_3);
        c.gridx = 2;
        c.gridy = 0;
        c.anchor = GridBagConstraints.PAGE_START;
        panel.add(background4, c);

        final ImageIcon icon3 = new ImageIcon(classloader.getResource("Rack.png"));
        JPanel background3 = new JPanel( new BorderLayout() )
        {
            protected void paintComponent(Graphics g)
            {
                super.paintComponent(g);

                g.drawImage(icon3.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };

        user1 = new JTable(user13, usercolumn13);
        user1.setDefaultEditor(Object.class, null);
        user1.setRowHeight((int) 50);
        user1.getColumnModel().getColumn(0).setPreferredWidth(50);
        user1.getColumnModel().getColumn(1).setPreferredWidth(50);
        user1.getColumnModel().getColumn(2).setPreferredWidth(50);
        user1.getColumnModel().getColumn(3).setPreferredWidth(50);
        user1.getColumnModel().getColumn(4).setPreferredWidth(50);
        user1.getColumnModel().getColumn(5).setPreferredWidth(50);
        user1.getColumnModel().getColumn(6).setPreferredWidth(50);
        user1.setGridColor(Color.BLACK);
        user1.setBackground(new Color(47, 86, 63));
        user1.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        user1.setFont(new Font("Serif", Font.BOLD, 15));
        DefaultTableCellRenderer renderer3 = (DefaultTableCellRenderer)user1.getDefaultRenderer(Object.class);
        renderer3.setOpaque(false);

        JPanel panel_1 = new JPanel();
        panel_1.setBounds(621, 53, 27, 351);
        panel_1.setOpaque( false );
        panel_1.add(user1);
        background3.add(panel_1);
        c.gridx = 2;
        c.gridy = 10;
        c.anchor = GridBagConstraints.PAGE_END;
        panel.add(background3, c);


        final ImageIcon icon = new ImageIcon(classloader.getResource("gameboard.png"));
        JPanel background = new JPanel( new BorderLayout() )
        {
            protected void paintComponent(Graphics g)
            {
                super.paintComponent(g);

                g.drawImage(icon.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };

        jt = new JTable(data, column);
        jt.setOpaque(false);
        jt.setDefaultEditor(Object.class, null);
        jt.setRowHeight(50);
        jt.getColumnModel().getColumn(0).setPreferredWidth(50);
        jt.getColumnModel().getColumn(1).setPreferredWidth(50);
        jt.getColumnModel().getColumn(2).setPreferredWidth(50);
        jt.getColumnModel().getColumn(3).setPreferredWidth(50);
        jt.getColumnModel().getColumn(4).setPreferredWidth(50);
        jt.getColumnModel().getColumn(5).setPreferredWidth(50);
        jt.getColumnModel().getColumn(6).setPreferredWidth(50);
        jt.getColumnModel().getColumn(7).setPreferredWidth(50);
        jt.getColumnModel().getColumn(8).setPreferredWidth(50);
        jt.getColumnModel().getColumn(9).setPreferredWidth(50);
        jt.getColumnModel().getColumn(10).setPreferredWidth(50);
        jt.setGridColor(Color.BLACK);
        jt.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        jt.setBackground(Color.WHITE);
        jt.setFont(new Font("Serif", Font.BOLD, 25));
        jt.setForeground(Color.BLACK);
        DefaultTableCellRenderer renderer = (DefaultTableCellRenderer)jt.getDefaultRenderer(Object.class);
        renderer.setOpaque(false);

        JPanel panel_j = new JPanel();
        panel_j.setBounds(621, 53, 27, 351);
        panel_j.setOpaque( false );
        panel_j.add(jt);
        background.add(panel_j);
        c.gridx = 2;
        c.gridy = 5;
        c.anchor = GridBagConstraints.CENTER;
        panel.add(background, c);

        final ImageIcon icon2 = new ImageIcon(classloader.getResource("Rack4.png"));
        JPanel background2 = new JPanel( new BorderLayout() )
        {
            protected void paintComponent(Graphics g)
            {
                super.paintComponent(g);

                g.drawImage(icon2.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };

        user4 = new JTable(user24, usercolumn24);
        user4.setDefaultEditor(Object.class, null);
        user4.setRowHeight((int) 50);
        user4.setGridColor(Color.BLACK);
        user4.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        user4.setBackground(Color.WHITE);
        user4.setFont(new Font("Serif", Font.BOLD, 15));
        user4.setBackground(new Color(191, 156, 70));
        user4.getColumnModel().getColumn(0).setPreferredWidth(50);
        DefaultTableCellRenderer renderer2 = (DefaultTableCellRenderer)user4.getDefaultRenderer(Object.class);
        renderer2.setOpaque(false);

        JPanel panel_4 = new JPanel();
        panel_4.setBounds(621, 53, 27, 351);
        panel_4.setOpaque( false );
        panel_4.add(user4);
        background2.add(panel_4);
        c.gridx = 20;
        c.gridy = 5;
        c.anchor = GridBagConstraints.LINE_END;
        panel.add(background2, c);

        String teamGreen = " ";
        String teamGold = " ";
        String player1 = " ";
        String player2 = " ";
        String player3 = " ";
        String player4 = " ";



        JPanel label = new JPanel(new GridBagLayout());
        GridBagConstraints lc = new GridBagConstraints();
        JLabel label2 = new JLabel("Team Green:");
        label2.setFont (label.getFont ().deriveFont (17.0f));
        label2.setForeground(new Color(47, 86, 63));
        lc.gridx = 0;
        lc.gridy = 0;
        lc.insets = new Insets(5, 20, 5, 20);
        label.add(label2, lc);
        JTextField text = new JTextField(teamGreen, 10);
        lc.gridx = 1;
        lc.gridy = 0;
        label.add(text, lc);
        JLabel label3 = new JLabel("Team Gold:");
        label3.setForeground(new Color(191, 156, 70));
        label3.setFont (label.getFont ().deriveFont (17.0f));
        lc.gridx = 2;
        lc.gridy = 0;
        label.add(label3, lc);
        JTextField text2 = new JTextField(teamGold, 20);
        lc.gridx = 3;
        lc.gridy = 0;
        label.add(text2, lc);
        player1Label = new JLabel("Player 1:");
        player1Label.setFont (label.getFont ().deriveFont (17.0f));

        lc.gridx = 0;
        lc.gridy = 2;
        label.add(player1Label, lc);
        JTextField text3 = new JTextField(player1, 10);
        lc.gridx = 1;
        lc.gridy = 2;
        label.add(text3, lc);
        player2Label = new JLabel("Player 2:");
        player2Label.setFont (label.getFont ().deriveFont (17.0f));
        lc.gridx = 2;
        lc.gridy = 2;
        label.add(player2Label, lc);
        JTextField text4 = new JTextField(player2, 10);
        lc.gridx = 3;
        lc.gridy = 2;
        label.add(text4, lc);
        player3Label = new JLabel("Player 3:");
        player3Label.setFont (label.getFont ().deriveFont (17.0f));
        lc.gridx = 0;
        lc.gridy = 4;
        label.add(player3Label, lc);
        JTextField text5 = new JTextField(player3, 10);
        lc.gridx = 1;
        lc.gridy = 4;
        label.add(text5, lc);
        player4Label = new JLabel("Player 4:");
        player4Label.setFont (label.getFont ().deriveFont (17.0f));
        lc.gridx = 2;
        lc.gridy = 4;
        label.add(player4Label, lc);
        JTextField text6 = new JTextField(player4, 10);
        lc.gridx = 3;
        lc.gridy = 4;
        label.add(text6, lc);


        label.setBackground(Color.WHITE);
        label.setBorder(BorderFactory.createLineBorder(Color.black));
        label.setPreferredSize(new Dimension(350, 420));
        c.gridx = 50;
        c.gridy = 5;
        panel.add(label, c);
        frame.setSize(1500, 1076);
        frame.setVisible(true);
    }

    public void updateBoard (Space[][] board){
        for (int x = 0; x < BOARD_WIDTH; x++) {
            for (int y = 0; y < BOARD_WIDTH; y++) {
                if(board[x][y].getTile() != null){
                    char letter = board[x][y].getTile().getLetter();
                    data[y][x] = Character.toString(letter);
                }
            }
        }
        jt.repaint();
    }
    

    public void setTurn(int id){
        player1Label.setForeground(Color.BLACK);
        player2Label.setForeground(Color.BLACK);
        player3Label.setForeground(Color.BLACK);
        player4Label.setForeground(Color.BLACK);

        if(id == 0){
            player1Label.setForeground(Color.BLUE);
        } else if (id == 1){
            player2Label.setForeground(Color.BLUE);
        } else if (id == 2){
            player3Label.setForeground(Color.BLUE);
        } else if (id == 3){
            player4Label.setForeground(Color.BLUE);
        }

    }

    public void updateUsers(User[] users){
        for(int i =0; i < users.length; i++){
            if(users[i] != null){
                if(i == 0){
                    player1Label.setText(users[i].getUsername() + "(" + users[i].getScore() + ")");
                } else if (i == 1){
                    player2Label.setText(users[i].getUsername() + "(" + users[i].getScore() + ")");
                } else if (i == 2){
                    player3Label.setText(users[i].getUsername() + "(" + users[i].getScore() + ")");
                } else if (i == 3){
                    player4Label.setText(users[i].getUsername() + "(" + users[i].getScore() + ")");
                }
            }
        }
    }

//    public void updateBoard (Space[][] board){
//        for (int x = 0; x < BOARD_WIDTH; x++) {
//            for (int y = 0; y < BOARD_WIDTH; y++) {
//                if(board[x][y].getTile() != null){
//                    char letter = board[x][y].getTile().getLetter();
//                    for (int i = 0; i < tiles.length; i++) {
//                        tiles[i] = new ImageIcon(classloader.getResource("tiles/" + i + ".png")).getImage();
//                        Graphics g = tiles[letter].getGraphics();
//                        g.drawImage(tiles[letter], 0, 0, 50, 50, null);
//                        data[y][x] = Character.toString(letter);
//                    }
//                }
//            }
//        }
//        jt.repaint();
//    }



//    public void updateBoard (Space[][] board){
//        for (int x = 0; x < BOARD_WIDTH; x++) {
//            for (int y = 0; y < BOARD_WIDTH; y++) {
//                if(board[x][y].getTile() != null){
//                    char letter = board[x][y].getTile().getLetter();
////                    data[y][x] = Character.toString(letter);
//                    File[] files = new File("tiles/").listFiles();
//                    for (File thisfile : files) {
//                        System.out.println(thisfile.getName());
//                        if ((thisfile.getName().contains(Character.toString(letter)))) {
//                            ImageIcon imageicon = new ImageIcon(classloader.getResource(thisfile.getName()));
//                                    data[x][y] = imageicon.toString();
//                        }
//                    }
//
//                }
//            }
//        }
//        jt.repaint();
//    }


}