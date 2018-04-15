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
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;


/**
 * @author mide_
 */
public class BoardGUI implements Runnable{
    public final ClassLoader classloader = Thread.currentThread().getContextClassLoader();

    int length13 = 7;
    int length24 = 1;

    JFrame frame;
    JFrame frame2;

    DefaultTableModel model; // *** Takes the column and row. Then passes it to the JTable
    DefaultTableModel user1_model;
    DefaultTableModel user2_model;
    DefaultTableModel user3_model;
    DefaultTableModel user4_model;

    JTable jt;
    JTable user1;
    JTable user2;
    JTable user3;
    JTable user4;

    JLabel player1Label;
    JLabel player2Label;
    JLabel player3Label;
    JLabel player4Label;

    String column[] = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
    Object[][] data = new Object[BOARD_WIDTH][BOARD_WIDTH];

    String userColumn13[] = {"0", "1", "2", "3", "4", "5", "6"};
    Object[][] user1_1 = new Object[length24][length13];
    Object[][] user3_3 = new Object[length24][length13];

    String userColumn24[] = {"0"};
    Object[][] user2_2 = new Object[length13][length24];
    Object[][] user4_4 = new Object[length13][length24];

    // This is the Table for Stats
    JTable statTable1;
    JTable statTable2;
    JTable statTable3;
    JTable statTable4;
    JTable statTable5;


    String column1[] = {"Name", "Score"};
    Object[][] row1 = new Object[5][5];

    String column2[] = {"Name", "Score"};
    Object[][] row2 = new Object[5][5];

    String column3[] = {"Rank", "Player", "Word", "Score"};
    Object[][] row3 = new Object[10][10];

    String column4[] = {"0", "1"};
    Object[][] row4 = new Object[2][2];

    String column5[] = {"0", "1"};
    Object[][] row5 = new Object[2][2];

    public BoardGUI() {
        frame = new JFrame("OSWEBBLE");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        for (int r = 0; r < BOARD_WIDTH; r++) {
            for (int c = 0; c < BOARD_WIDTH; c++) {
                data[r][c] = new ImageIcon();
            }
        }

        for (int i = 0; i < length24; i++) {
            for (int j = 0; j < length13; j++) {

                user1_1[i][j] = new ImageIcon();
            }
        }

        for (int i = 0; i < length24; i++) {
            for (int j = 0; j < length13; j++) {

                user3_3[i][j] = new ImageIcon();
            }
        }

        for (int k = 0; k < length13; k++) {
            for (int l = 0; l < length24; l++) {
                user2_2[k][l] = new ImageIcon();
            }
        }

        for (int k = 0; k < length13; k++) {
            for (int l = 0; l < length24; l++) {
                user4_4[k][l] = new ImageIcon();
            }
        }

        frame2 = new JFrame("Leaderboard & Statistics");
        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        for (int r = 0; r < 5; r++) {
            for (int c = 0; c < 5; c++) {
                row1[r][c] = " ";
            }
        }

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                row2[i][j] = " ";
            }
        }

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                row3[i][j] = " ";
            }
        }

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                row4[i][j] = " ";
            }
        }

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                row5[i][j] = " ";
            }
        }

        final ImageIcon icon_main = new ImageIcon(classloader.getResource("background.jpg"));
        JPanel background_main = new JPanel(new BorderLayout()) {
            protected void paintComponent(Graphics g) {
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
        JPanel background5 = new JPanel(new BorderLayout()) {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                g.drawImage(icon5.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };

        // ***
        user2_model = new DefaultTableModel(user2_2, userColumn24) {
            //  Returning the Class of each column will allow different
            //  Renderer to be used based on Class
            public Class getColumnClass(int column) {

                return getValueAt(0, column).getClass();
            }
        };

        user2 = new JTable(user2_model);
        user2.setDefaultEditor(Object.class, null);
        user2.setRowHeight(50);
        user2.setGridColor(Color.BLACK);
        user2.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        user2.setBackground(Color.WHITE);
        user2.setFont(new Font("Serif", Font.BOLD, 15));
        user2.setBackground(new Color(244, 212, 108));
        user2.getColumnModel().getColumn(0).setPreferredWidth(50);
        DefaultTableCellRenderer renderer5 = (DefaultTableCellRenderer) user2.getDefaultRenderer(Object.class);
        renderer5.setOpaque(false);

        JPanel panel_2 = new JPanel();
        panel_2.setBounds(621, 53, 27, 351);
        panel_2.setOpaque(false);
        panel_2.add(user2);
        background5.add(panel_2);
        c.gridx = 0;
        c.gridy = 5;
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(10, 10, 10, 10);
        panel.add(background5, c);

        final ImageIcon icon4 = new ImageIcon(classloader.getResource("Rack3.png"));
        JPanel background4 = new JPanel(new BorderLayout()) {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                g.drawImage(icon4.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };

        user3_model = new DefaultTableModel(user3_3, userColumn13) {
            //  Returning the Class of each column will allow different
            //  Renderer to be used based on Class
            public Class getColumnClass(int column) {

                return getValueAt(0, column).getClass();
            }
        };

        user3 = new JTable(user3_model);
        user3.setDefaultEditor(Object.class, null);
        user3.setRowHeight(50);
        for(int i = 0; i < userColumn13.length; ++i) // *** For loop to make the table column 50px
        {
            user3.getColumnModel().getColumn(i).setPreferredWidth(50);
            user3.getColumnModel().getColumn(i).setCellRenderer(new BoardCellRenderer());
        }
        user3.setGridColor(Color.BLACK);
        user3.setBackground(new Color(53, 226, 40));
        user3.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        user3.setFont(new Font("Serif", Font.BOLD, 15));
        DefaultTableCellRenderer renderer4 = (DefaultTableCellRenderer) user3.getDefaultRenderer(Object.class);
        renderer4.setOpaque(false);

        JPanel panel_3 = new JPanel();
        panel_3.setBounds(621, 53, 27, 351);
        panel_3.setOpaque(false);
        panel_3.add(user3);
        background4.add(panel_3);
        c.gridx = 2;
        c.gridy = 0;
        c.anchor = GridBagConstraints.PAGE_START;
        panel.add(background4, c);

        final ImageIcon icon3 = new ImageIcon(classloader.getResource("Rack.png"));
        JPanel background3 = new JPanel(new BorderLayout()) {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                g.drawImage(icon3.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };

        user1_model = new DefaultTableModel(user1_1, userColumn13) {
            //  Returning the Class of each column will allow different
            //  Renderer to be used based on Class
            public Class getColumnClass(int column) {

                return getValueAt(0, column).getClass();
            }
        };

        user1 = new JTable(user1_model);
        user1.setDefaultEditor(Object.class, null);
        user1.setRowHeight(50);
        for(int i = 0; i < userColumn13.length; ++i) // *** For loop to make the table column 50px
        {
            user1.getColumnModel().getColumn(i).setPreferredWidth(50);
            user1.getColumnModel().getColumn(i).setCellRenderer(new BoardCellRenderer());
        }
        user1.setGridColor(Color.BLACK);
        user1.setBackground(new Color(47, 86, 63));
        user1.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        user1.setFont(new Font("Serif", Font.BOLD, 15));
        DefaultTableCellRenderer renderer3 = (DefaultTableCellRenderer) user1.getDefaultRenderer(Object.class);
        renderer3.setOpaque(false);

        JPanel panel_1 = new JPanel();
        panel_1.setBounds(621, 53, 27, 351);
        panel_1.setOpaque(false);
        panel_1.add(user1);
        background3.add(panel_1);
        c.gridx = 2;
        c.gridy = 10;
        c.anchor = GridBagConstraints.PAGE_END;
        panel.add(background3, c);


        final ImageIcon icon = new ImageIcon(classloader.getResource("gameboard.png"));
        JPanel background = new JPanel(new BorderLayout()) {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                g.drawImage(icon.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };

        // ***
        model = new DefaultTableModel(data, column) {
            //  Returning the Class of each column will allow different
            //  Renderer to be used based on Class
            public Class getColumnClass(int column) {

                return getValueAt(0, column).getClass();
            }
        };

        jt = new JTable(model); // *** Pass the Model to the JTable
        jt.setOpaque(false);
        jt.setDefaultEditor(Object.class, null);
        jt.setRowHeight(50);
        for(int i = 0; i < column.length; ++i) // *** For loop to make the table column 50px
        {
            jt.getColumnModel().getColumn(i).setPreferredWidth(50);
            jt.getColumnModel().getColumn(i).setCellRenderer(new BoardCellRenderer());
        }
        jt.setGridColor(Color.BLACK);
        jt.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        jt.setBackground(Color.WHITE);
        jt.setFont(new Font("Serif", Font.BOLD, 25));
        jt.setForeground(Color.BLACK);
        DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) jt.getDefaultRenderer(Object.class);
        renderer.setOpaque(false);

        JPanel panel_j = new JPanel();
        panel_j.setBounds(621, 53, 27, 351);
        panel_j.setOpaque(false);
        panel_j.add(jt);
        background.add(panel_j);
        c.gridx = 2;
        c.gridy = 5;
        c.anchor = GridBagConstraints.CENTER;
        panel.add(background, c);

        final ImageIcon icon2 = new ImageIcon(classloader.getResource("Rack4.png"));
        JPanel background2 = new JPanel(new BorderLayout()) {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                g.drawImage(icon2.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };

        user4_model = new DefaultTableModel(user4_4, userColumn24) {
            //  Returning the Class of each column will allow different
            //  Renderer to be used based on Class
            public Class getColumnClass(int column) {

                return getValueAt(0, column).getClass();
            }
        };

        user4 = new JTable(user4_model);
        user4.setDefaultEditor(Object.class, null);
        user4.setRowHeight((int) 50);
        user4.setGridColor(Color.BLACK);
        user4.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        user4.setBackground(Color.WHITE);
        user4.setFont(new Font("Serif", Font.BOLD, 15));
        user4.setBackground(new Color(191, 156, 70));
        user4.getColumnModel().getColumn(0).setPreferredWidth(50);
        DefaultTableCellRenderer renderer2 = (DefaultTableCellRenderer) user4.getDefaultRenderer(Object.class);
        renderer2.setOpaque(false);

        JPanel panel_4 = new JPanel();
        panel_4.setBounds(621, 53, 27, 351);
        panel_4.setOpaque(false);
        panel_4.add(user4);
        background2.add(panel_4);
        c.gridx = 20;
        c.gridy = 5;
        c.anchor = GridBagConstraints.LINE_END;
        panel.add(background2, c);


        JPanel label = new JPanel(new GridBagLayout());
        GridBagConstraints lc = new GridBagConstraints();

        JLabel label2 = new JLabel("Team Green");
        label2.setFont (new Font("Serif", Font.BOLD, 17));
        label2.setForeground(new Color(47, 86, 63));
        lc.gridx = 0;
        lc.gridy = 0;
        lc.insets = new Insets(5, 20, 5, 20);
        label.add(label2, lc);

        JLabel label3 = new JLabel("Team Gold");
        label3.setFont (new Font("Serif", Font.BOLD, 17));
        label3.setForeground(new Color(191, 156, 70));
        lc.gridx = 2;
        lc.gridy = 0;
        label.add(label3, lc);

        player1Label = new JLabel("Player 1");
        player1Label.setFont (new Font("Serif", Font.BOLD, 17));
        lc.gridx = 0;
        lc.gridy = 2;
        label.add(player1Label, lc);

        player2Label = new JLabel("Player 2");
        player2Label.setFont (new Font("Serif", Font.BOLD, 17));
        lc.gridx = 2;
        lc.gridy = 2;
        label.add(player2Label, lc);

        player3Label = new JLabel("Player 3");
        player3Label.setFont (new Font("Serif", Font.BOLD, 17));
        lc.gridx = 0;
        lc.gridy = 4;
        label.add(player3Label, lc);

        player4Label = new JLabel("Player 4");
        player4Label.setFont (new Font("Serif", Font.BOLD, 17));
        lc.gridx = 2;
        lc.gridy = 4;
        label.add(player4Label, lc);


        label.setBackground(Color.WHITE);
        label.setBorder(BorderFactory.createLineBorder(Color.black));
        label.setPreferredSize(new Dimension(350, 420));
        c.gridx = 50;
        c.gridy = 5;
        panel.add(label, c);

        frame.setSize(2000, 1076);
        frame.setVisible(true);

        // *********************************This is the start of Stats frame*********************************** //

        JPanel statsPanel = new JPanel(new GridBagLayout());
        frame2.getContentPane().add(statsPanel, BorderLayout.LINE_START);
        GridBagConstraints statsC = new GridBagConstraints();


        // // All Time scores label for team Green start
        JLabel statLabel1 = new JLabel("Green");
        statLabel1.setFont (new Font("Serif", Font.BOLD, 20));
        statLabel1.setForeground(Color.BLACK);
        statsC.gridx = 0;
        statsC.gridy = 0;
        statsC.anchor = GridBagConstraints.CENTER;
        statsC.insets = new Insets(10, 10, 10, 10);
        statsPanel.add(statLabel1, statsC);
        // All Time scores label for team Green start

        //table 1 start
        statTable1 = new JTable(row1, column1);
        statTable1.setDefaultEditor(Object.class, null);
        statTable1.setRowHeight(40);
        statTable1.getColumnModel().getColumn(0).setPreferredWidth(100);
        statTable1.getColumnModel().getColumn(1).setPreferredWidth(100);
        statTable1.setGridColor(Color.BLACK);
        statTable1.setFont(new Font("Serif", Font.BOLD, 20));

        DefaultTableCellRenderer statsRenderer1 = new DefaultTableCellRenderer();
        statTable1.getTableHeader().setDefaultRenderer(statsRenderer1);

        for(int x = 0; x < 2; x++){
            statTable1.getColumnModel().getColumn(x).setCellRenderer(statsRenderer1);
        }

        //dummy data
        String a[][] = {{ "r0c0", "r0c1" },
                { "r1c0", "r1c1" },
                { "r2c0", "r2c1" },
                { "r3c0", "r3c1" },
                { "r4c0", "r4c1" }
        };

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 2; j++) {
                row1[i][j] = a[i][j];
            }
        }


        statsC.gridx = 0;
        statsC.gridy = 1;
        statsC.anchor = GridBagConstraints.CENTER;

//        JScrollPane statSp1 = new JScrollPane(statTable1,
//                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
//                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        statsPanel.add(statTable1, statsC);
        // table 1 end

        // All Time scores label for team Gold start
        JLabel statLabel2 = new JLabel("Gold");
        statLabel2.setFont (new Font("Serif", Font.BOLD, 20));
        statLabel2.setForeground(Color.BLACK);
        statsC.gridx = 1;
        statsC.gridy = 0;
        statsC.anchor = GridBagConstraints.CENTER;
        statsPanel.add(statLabel2, statsC);
        // All Time scores label for team Gold end

        //table 2 start
        statTable2 = new JTable(row2, column2);
        statTable2.setDefaultEditor(Object.class, null);
        statTable2.setRowHeight(40);
        statTable2.getColumnModel().getColumn(0).setPreferredWidth(100);
        statTable2.getColumnModel().getColumn(1).setPreferredWidth(100);
        statTable2.setGridColor(Color.BLACK);
        statTable2.setFont(new Font("Serif", Font.BOLD, 20));

        DefaultTableCellRenderer statsRenderer2 = new DefaultTableCellRenderer();
        statTable2.getTableHeader().setDefaultRenderer(statsRenderer2);

        for(int x = 0; x < 2; x++){
            statTable2.getColumnModel().getColumn(x).setCellRenderer(statsRenderer2);
        }

        //dummy data
        String b[][] = {{ "r0c0", "r0c1" },
                { "r1c0", "r1c1" },
                { "r2c0", "r2c1" },
                { "r3c0", "r3c1" },
                { "r4c0", "r4c1" }
        };

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 2; j++) {
                row2[i][j] = a[i][j];
            }
        }

        statsC.gridx = 1;
        statsC.gridy = 1;
        statsC.anchor = GridBagConstraints.CENTER;

//        JScrollPane statSp2 = new JScrollPane(statTable2,
//                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
//                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        statsPanel.add(statTable2, statsC);
        //table 2 end

        // T Test label start
        JLabel statLabel4 = new JLabel("T Test");
        statLabel4.setFont (new Font("Serif", Font.BOLD, 20));
        statLabel4.setForeground(Color.BLACK);

        statsC.gridx = 2;
        statsC.gridy = 0;
        statsC.anchor = GridBagConstraints.FIRST_LINE_END;
        statsPanel.add(statLabel4, statsC);
        // T Test label end

        //table 4 start
        statTable4 = new JTable(row4, column4);
        statTable4.setDefaultEditor(Object.class, null);
        statTable4.setRowHeight(40);
        statTable4.getColumnModel().getColumn(0).setPreferredWidth(100);
        statTable4.getColumnModel().getColumn(1).setPreferredWidth(100);
        statTable4.setGridColor(Color.BLACK);
        statTable4.setFont(new Font("Serif", Font.BOLD, 20));

        DefaultTableCellRenderer statsRenderer4 = new DefaultTableCellRenderer();
        statTable4.getTableHeader().setDefaultRenderer(statsRenderer4);

        for(int x = 0; x < 2; x++){
            statTable4.getColumnModel().getColumn(x).setCellRenderer(statsRenderer4);
        }

        //dummy data
        String tTest[][] = {{ "Green", "tVal" },
                { "Gold", "tVal" }
        };

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                row4[i][j] = tTest[i][j];
            }
        }


        statsC.gridx = 2;
        statsC.gridy = 1;
        statsC.anchor = GridBagConstraints.FIRST_LINE_END;

        statsPanel.add(statTable4, statsC);
        //table 4 end

        // Average Score label start
        JLabel statLabel5 = new JLabel("Average Score");
        statLabel5.setFont (new Font("Serif", Font.BOLD, 20));
        statLabel5.setForeground(Color.BLACK);
        statsC.gridx = 2;
        statsC.gridy = 2;
        statsC.anchor = GridBagConstraints.FIRST_LINE_END;
        statsPanel.add(statLabel5, statsC);
        // Average Score label end

        //table 5 start
        statTable5 = new JTable(row5, column5);
        statTable5.setDefaultEditor(Object.class, null);
        statTable5.setRowHeight(40);
        statTable5.getColumnModel().getColumn(0).setPreferredWidth(100);
        statTable5.getColumnModel().getColumn(1).setPreferredWidth(100);
        statTable5.setGridColor(Color.BLACK);
        statTable5.setFont(new Font("Serif", Font.BOLD, 20));

        DefaultTableCellRenderer statsRenderer5 = new DefaultTableCellRenderer();
        statTable5.getTableHeader().setDefaultRenderer(statsRenderer5);

        for(int x = 0; x < 2; x++){
            statTable5.getColumnModel().getColumn(x).setCellRenderer(statsRenderer5);
        }

        //dummy data
        String avg[][] = {{ "Green", "avgVal" },
                { "Gold", "avgVal" }
        };

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                row5[i][j] = avg[i][j];
            }
        }

        statsC.gridx = 2;
        statsC.gridy = 3;
        statsC.anchor = GridBagConstraints.FIRST_LINE_END;

        statsPanel.add(statTable5, statsC);
        //table 4 end

        // Top 10 Word Played label start
        JLabel statLabel3 = new JLabel("Top 10 Words Played");
        statLabel3.setFont (new Font("Serif", Font.BOLD, 20));
        statLabel3.setForeground(Color.BLACK);
        statsC.gridx = 1;
        statsC.gridy = 3;
        statsC.anchor = GridBagConstraints.FIRST_LINE_START;
        statsPanel.add(statLabel3, statsC);
        // Top 10 Word Played label end

        //table 3 start
        statTable3 = new JTable(row3, column3);
        statTable3.setDefaultEditor(Object.class, null);
        statTable3.setRowHeight(40);
        statTable3.getColumnModel().getColumn(0).setPreferredWidth(100);
        statTable3.getColumnModel().getColumn(1).setPreferredWidth(100);
        statTable3.getColumnModel().getColumn(2).setPreferredWidth(100);
        statTable3.getColumnModel().getColumn(3).setPreferredWidth(100);
        statTable3.setGridColor(Color.BLACK);
        statTable3.setFont(new Font("Serif", Font.BOLD, 20));

        DefaultTableCellRenderer statsRenderer3 = new DefaultTableCellRenderer();
        statTable3.getTableHeader().setDefaultRenderer(statsRenderer3);

        for(int x = 0; x < 4; x++){
            statTable3.getColumnModel().getColumn(x).setCellRenderer(statsRenderer3);
        }

        //dummy data
        String wordscores[][] ={{ "r0", "p0", "w0", "score0" },
                { "r1", "p1", "w1", "score1" },
                { "r2", "p2", "w2", "score2" },
                { "r3", "p3", "w3", "score3" },
                { "r4", "p4", "w4", "score4" },
                { "r5", "p5", "w5", "score5" },
                { "r6", "p6", "w6", "score6" },
                { "r7", "p7", "w7", "score7" },
                { "r8", "p8", "w8", "score8" },
                { "r9", "p9", "w9", "score9" },
        };

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 4; j++) {
                row3[i][j] = wordscores[i][j];
            }
        }

        statsC.gridx = 1;
        statsC.gridy = 4;
        statsC.anchor = GridBagConstraints.FIRST_LINE_START;

        JScrollPane statSp3 = new JScrollPane(statTable3,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        statsPanel.add(statSp3, statsC);
        //table 3 end

        frame2.setSize(2000, 1076);
        frame2.setVisible(true);

    }

    @Override
    public void run() {
        new JFrame("frame2").setVisible(true);
        new JFrame("frame").setVisible(true);
    }

    public void updateBoard(Space[][] board) {
        for (int x = 0; x < BOARD_WIDTH; x++) {
            for (int y = 0; y < BOARD_WIDTH; y++) {
                if (board[x][y].getTile() != null) {
                    char letter = board[x][y].getTile().getLetter();
                    try {
                        // *** Gets the tile equivalence of the letter
                        model.setValueAt(new ImageIcon(classloader.getResource("letters/" + letter + ".png")), y, x);
                    } catch (Exception e) {}
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
            player1Label.setForeground( new Color(47, 86, 63));
        } else if (id == 1){
            player2Label.setForeground(new Color(244, 212, 108));
        } else if (id == 2){
            player3Label.setForeground(new Color(53, 226, 40));
        } else if (id == 3){
            player4Label.setForeground(new Color(191, 156, 70));
        }

    }

    public void updateUsers(User[] users){
        for(int i =0; i < users.length; i++){
            if(users[i] != null){
                if(i == 0){
                    player1Label.setText(users[i].getUsername() + "      [ " + users[i].getScore() + " ]");
                } else if (i == 1){
                    player2Label.setText(users[i].getUsername() + "      [ " + users[i].getScore() + " ]");
                } else if (i == 2){
                    player3Label.setText(users[i].getUsername() + "      [ " + users[i].getScore() + " ]");
                } else if (i == 3){
                    player4Label.setText(users[i].getUsername() + "      [ " + users[i].getScore() + " ]");
                }
            }
        }
    }

    public void updateHand(User[] users){
        for (int x = 0; x < length24; x++) {
            for (int y = 0; y < length13; y++) {
                String letters= " ";
                TileGenerator tg = TileGenerator.getInstance();
                ArrayList<Character> userhand = new ArrayList<>();
                for (int j = 0; j < letters.length(); j++) {
                    userhand.add(letters.charAt(j));
                }
                for (int i = 0; i < users.length; i++) {
                    if (users[i] != null) {
                        if (i == 0) {
                            Tile[] hand = users[i].getHand();
                            users[i].setHand(hand);
                            for (int k =0; k < hand.length; ++k)
                                if (userhand.contains(hand[k].getLetter())) {
                                    //generate new tile instead
                                    userhand.remove((Character) hand[k].getLetter());
                                    hand[k] = tg.getRandTile();
                                }
                            try {
                                // *** Gets the tile equivalence of the letter
                                user1_model.setValueAt(new ImageIcon(classloader.getResource("tiles/" + hand + ".png")), y, x);
                            } catch (Exception e) {}
                        }
                    }
                }
            }
        }
    }



}