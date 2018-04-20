/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

import Components.Log;
import Components.QueryClass;
import Models.*;
import Session.Start;
import Session.Session;
import static Models.GameConstants.*;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.text.DefaultCaret;

/**
 * @author mide_
 */
public class BoardGUI implements Runnable {

    public final ClassLoader classloader = Thread.currentThread().getContextClassLoader();

    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable jTable4;
    private javax.swing.JTextArea jTextArea2;

    private javax.swing.JTextArea gameLogArea;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel label;
    private javax.swing.JLabel label2;
    private javax.swing.JLabel label3;
    private javax.swing.JLabel logLabel;
    private javax.swing.JLabel player1Label;
    private javax.swing.JLabel player2Label;
    private javax.swing.JLabel player3Label;
    private javax.swing.JLabel player4Label;
    private javax.swing.JPanel qrCode;

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

    String column[] = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
    Object[][] data = new Object[BOARD_WIDTH][BOARD_WIDTH];

    String userColumn13[] = {"0", "1", "2", "3", "4", "5", "6"};
    Object[][] user1_1 = new Object[length24][length13];
    Object[][] user3_3 = new Object[length24][length13];

    String userColumn24[] = {"0"};
    Object[][] user2_2 = new Object[length13][length24];
    Object[][] user4_4 = new Object[length13][length24];

    public BoardGUI() {
        gui();
        addRowToTable1();
        addRowToTable2();
        addRowToTable3();
        addRowToTable4();
        setTTestConclusion();
    }

    public void gui() {
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
        user2.setOpaque(false);
        user2.getColumnModel().getColumn(0).setCellRenderer(new BoardCellRenderer());
        user2.setDefaultEditor(Object.class, null);
        user2.setRowHeight(50);
        user2.setGridColor(new Color(242, 208, 176));
        user2.setBorder(BorderFactory.createLineBorder(new Color(242, 208, 176), 1));
        user2.setFont(new Font("Serif", Font.BOLD, 15));
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
        user3.setOpaque(false);
        user3.setDefaultEditor(Object.class, null);
        user3.setRowHeight(50);
        for (int i = 0; i < userColumn13.length; ++i) // *** For loop to make the table column 50px
        {
            user3.getColumnModel().getColumn(i).setPreferredWidth(50);
            user3.getColumnModel().getColumn(i).setCellRenderer(new BoardCellRenderer());
        }
        user3.setGridColor(new Color(242, 208, 176));
        user3.setBorder(BorderFactory.createLineBorder(new Color(242, 208, 176), 1));
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
        user1.setOpaque(false);
        user1.setDefaultEditor(Object.class, null);
        user1.setRowHeight(50);
        for (int i = 0; i < userColumn13.length; ++i) // *** For loop to make the table column 50px
        {
            user1.getColumnModel().getColumn(i).setPreferredWidth(50);
            user1.getColumnModel().getColumn(i).setCellRenderer(new BoardCellRenderer());
        }
        user1.setGridColor(new Color(242, 208, 176));
        user1.setBorder(BorderFactory.createLineBorder(new Color(242, 208, 176), 1));
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
        for (int i = 0; i < column.length; ++i) // *** For loop to make the table column 50px
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
        user4.setOpaque(false);
        user4.getColumnModel().getColumn(0).setCellRenderer(new BoardCellRenderer());
        user4.setDefaultEditor(Object.class, null);
        user4.setRowHeight(50);
        user4.setGridColor(new Color(242, 208, 176));
        user4.setBorder(BorderFactory.createLineBorder(new Color(242, 208, 176), 1));
        user4.setFont(new Font("Serif", Font.BOLD, 15));
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


        //**********************Player names and game log*************************//
        label = new javax.swing.JPanel();
        label2 = new javax.swing.JLabel();
        label3 = new javax.swing.JLabel();
        player1Label = new javax.swing.JLabel();
        player2Label = new javax.swing.JLabel();
        player3Label = new javax.swing.JLabel();
        player4Label = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        gameLogArea = new javax.swing.JTextArea();
        logLabel = new javax.swing.JLabel();
        qrCode = new javax.swing.JPanel();

        label.setBackground(new java.awt.Color(243, 243, 243));
        label.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        label2.setFont(new java.awt.Font("Serif", 1, 17)); // NOI18N
        label2.setForeground(new java.awt.Color(42, 99, 66));
        label2.setText("Team Green");

        label3.setFont(new java.awt.Font("Serif", 1, 17)); // NOI18N
        label3.setForeground(new java.awt.Color(255, 204, 51));
        label3.setText("Team Gold");

        player1Label.setFont(new java.awt.Font("Serif", 1, 17)); // NOI18N
        player1Label.setText("Player 1");

        player2Label.setFont(new java.awt.Font("Serif", 1, 17)); // NOI18N
        player2Label.setText("Player 2");

        player3Label.setFont(new java.awt.Font("Serif", 1, 17)); // NOI18N
        player3Label.setText("Player 3");

        player4Label.setFont(new java.awt.Font("Serif", 1, 17)); // NOI18N
        player4Label.setText("Player 4");

        gameLogArea.setColumns(20);
        gameLogArea.setFont(new java.awt.Font("Comic Sans MS", 1, 16)); // NOI18N
        gameLogArea.setRows(5);

        DefaultCaret caret = (DefaultCaret)gameLogArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        jScrollPane1.setViewportView(gameLogArea);

        logLabel.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        logLabel.setForeground(new java.awt.Color(185, 39, 39));
        logLabel.setText("Game Log");

        final ImageIcon qrIcon = new ImageIcon(Start.qrFile.getPath());
        JPanel qrBackground = new JPanel(new BorderLayout()) {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                g.drawImage(qrIcon.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };

        qrCode.setOpaque(false);
        qrBackground.add(qrCode);

        qrCode.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout qrCodeLayout = new javax.swing.GroupLayout(qrCode);
        qrCode.setLayout(qrCodeLayout);
        qrCodeLayout.setHorizontalGroup(
                qrCodeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 150, Short.MAX_VALUE)
        );
        qrCodeLayout.setVerticalGroup(
                qrCodeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 150, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout labelLayout = new javax.swing.GroupLayout(label);
        label.setLayout(labelLayout);
        labelLayout.setHorizontalGroup(
                labelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, labelLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(qrBackground, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(110, 110, 110))
                        .addGroup(labelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(labelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(labelLayout.createSequentialGroup()
                                                .addGroup(labelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(logLabel)
                                                        .addGroup(labelLayout.createSequentialGroup()
                                                                .addGroup(labelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(player1Label)
                                                                        .addComponent(player3Label))
                                                                .addGap(85, 85, 85)
                                                                .addGroup(labelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(label3)
                                                                        .addGroup(labelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                                                .addComponent(player2Label)
                                                                                .addComponent(player4Label)))))
                                                .addContainerGap(36, Short.MAX_VALUE))
                                        .addGroup(labelLayout.createSequentialGroup()
                                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                                .addContainerGap())
                                        .addGroup(labelLayout.createSequentialGroup()
                                                .addComponent(label2)
                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        labelLayout.setVerticalGroup(
                labelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(labelLayout.createSequentialGroup()
                                .addGap(33, 33, 33)
                                .addGroup(labelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(label2)
                                        .addComponent(label3))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(labelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(player1Label)
                                        .addComponent(player2Label))
                                .addGap(34, 34, 34)
                                .addGroup(labelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(player3Label)
                                        .addComponent(player4Label))
                                .addGap(36, 36, 36)
                                .addComponent(logLabel)
                                .addGap(3, 3, 3)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                                .addComponent(qrBackground, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );

        c.gridx = 50;
        c.gridy = 5;
        c.anchor = GridBagConstraints.FIRST_LINE_END;
        panel.add(label, c);


        frame.setSize(1920, 1080);
        frame.setVisible(true);

        // *********************************This is the start of Stats frame*********************************** //
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();

        frame2.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        frame2.getContentPane().setBackground(new java.awt.Color(42, 99, 66));
        frame2.setResizable(true);

        jTable2.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jTable2.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{

                },
                new String[]{
                        "Rank", "Player", "Total Score", "H-Scoring Word", "B-Word Count"
                }
        ));
        jTable2.setRowHeight(35);

        JTableHeader header = jTable2.getTableHeader();
        header.setFont(new java.awt.Font("Tahoma", 0, 16));
        header.setBackground(new java.awt.Color(255, 204, 51));

        jScrollPane2.setViewportView(jTable2);

        jTable3.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jTable3.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{

                },
                new String[]{
                        "Team", "Cumulative", "Average", "Longest Word", "Wins", "Tiles", "Losses", "Highest Word Score", "B-Word Count"
                }
        ));
        jTable3.setRowHeight(35);

        JTableHeader header3 = jTable3.getTableHeader();
        header3.setFont(new java.awt.Font("Tahoma", 0, 16));
        header3.setBackground(new java.awt.Color(255, 204, 51));

        jScrollPane3.setViewportView(jTable3);

        jTable4.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jTable4.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{

                },
                new String[]{
                        "Team", "Average Score"
                }
        ));
        jTable4.setRowHeight(35);

        JTableHeader header4 = jTable4.getTableHeader();
        header4.setFont(new java.awt.Font("Tahoma", 0, 16));
        header4.setBackground(new java.awt.Color(255, 204, 51));

        jScrollPane5.setViewportView(jTable4);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 30)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 204, 51));
        jLabel1.setText("Green");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 204, 51));
        jLabel3.setText("Statistics");

        jTable1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{

                },
                new String[]{
                        "Rank", "Player", "Total Score", "H-Scoring Word", "B-Word Count"
                }
        ));
        jTable1.setRequestFocusEnabled(false);
        jTable1.setRowHeight(35);

        JTableHeader header2 = jTable1.getTableHeader();
        header2.setFont(new java.awt.Font("Tahoma", 0, 16));
        header2.setBackground(new java.awt.Color(255, 204, 51));

        jScrollPane6.setViewportView(jTable1);

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 30)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 204, 51));
        jLabel4.setText("T Test:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 30)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 204, 51));
        jLabel2.setText("Gold");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 204, 51));
        jLabel6.setText("Leaderboard");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 30)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 204, 51));
        jLabel5.setText("Conclusion:");

        jTextArea2.setBackground(new java.awt.Color(255, 204, 51));
        jTextArea2.setColumns(20);
        jTextArea2.setFont(new java.awt.Font("Comic Sans MS", 1, 24)); // NOI18N
        jTextArea2.setRows(5);
        jScrollPane4.setViewportView(jTextArea2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(frame2.getContentPane());
        frame2.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(314, 314, 314)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel2)
                                .addGap(319, 319, 319))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane3)
                                .addContainerGap())
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addComponent(jLabel6)
                                                .addGap(626, 626, 626))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addComponent(jLabel3)
                                                .addGap(626, 626, 626))))
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(53, 53, 53)
                                                .addComponent(jLabel4)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 361, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(44, 44, 44)
                                                .addComponent(jLabel5)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 609, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 0, Short.MAX_VALUE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 660, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 660, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel6)
                                .addGap(45, 45, 45)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel2)
                                        .addComponent(jLabel1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 203, Short.MAX_VALUE)
                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 84, Short.MAX_VALUE)
                                .addComponent(jLabel3)
                                .addGap(39, 39, 39)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 144, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addComponent(jLabel4)
                                                .addGap(81, 81, 81))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(54, 54, 54))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addComponent(jLabel5)
                                                .addGap(81, 81, 81))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(63, 63, 63))))
        );

        frame2.pack();
        frame2.setSize(1440, 900);
        frame2.setVisible(true);

    }

    @Override
    public void run() {
        new JFrame("frame2").setVisible(true);
        new JFrame("frame").setVisible(true);
    }

    public void updateBoard(Space[][] board) {
        updateHand(Session.getSession().getUsers());
        for (int x = 0; x < BOARD_WIDTH; x++) {
            for (int y = 0; y < BOARD_WIDTH; y++) {
                if (board[x][y].getTile() != null) {
                    char letter = board[x][y].getTile().getLetter();
                    try {
                        // *** Gets the tile equivalence of the letter
                        model.setValueAt(new ImageIcon(classloader.getResource("letters/" + letter + ".png")), y, x);
                    } catch (Exception e) {
                        Log.getLogger().logException(e);
                        e.printStackTrace();
                    }
                }
            }
        }
        jt.repaint();
    }

    public void updateHand(User[] users) {
        for (int i = 0; i < users.length; i++) {
            if (users[i] != null) {
                if (i == 0) {
                    Tile[] hand = users[i].getHand();
                    for (int y = 0; y < length13; y++) {
                        if (hand[y] != null) {
                            char letter = hand[y].getLetter();
                            try {
                                user1_model.setValueAt(new ImageIcon(classloader.getResource("letters/" + letter + ".png")), 0, y);
                            } catch (Exception e) {
                                Log.getLogger().logException(e);
                                e.printStackTrace();
                            }
                        }
                    }
                    user1.repaint();
                } else if (i == 1) {
                    Tile[] hand = users[i].getHand();
                    for (int y = 0; y < length13; y++) {
                        if (hand[y] != null) {
                            char letter = hand[y].getLetter();
                            try {
                                user2_model.setValueAt(new ImageIcon(classloader.getResource("letters/" + letter + ".png")), y, 0);
                            } catch (Exception e) {
                                Log.getLogger().logException(e);
                                e.printStackTrace();
                            }
                        }
                    }
                    user2.repaint();
                } else if (i == 2) {
                    Tile[] hand = users[i].getHand();
                    for (int y = 0; y < length13; y++) {
                        if (hand[y] != null) {
                            char letter = hand[y].getLetter();
                            try {
                                user3_model.setValueAt(new ImageIcon(classloader.getResource("letters/" + letter + ".png")), 0, y);
                            } catch (Exception e) {
                                Log.getLogger().logException(e);
                                e.printStackTrace();
                            }
                        }
                    }
                    user3.repaint();
                } else if (i == 3) {
                    Tile[] hand = users[i].getHand();
                    for (int y = 0; y < length13; y++) {
                        if (hand[y] != null) {
                            char letter = hand[y].getLetter();
                            try {
                                user4_model.setValueAt(new ImageIcon(classloader.getResource("letters/" + letter + ".png")), y, 0);
                            } catch (Exception e) {
                                Log.getLogger().logException(e);
                                e.printStackTrace();
                            }
                        }
                    }
                    user4.repaint();
                }
            }
        }
    }


    public void setTurn(int id) {
        player1Label.setForeground(Color.BLACK);
        player2Label.setForeground(Color.BLACK);
        player3Label.setForeground(Color.BLACK);
        player4Label.setForeground(Color.BLACK);

        if (id == 0) {
            player1Label.setForeground(new Color(26, 63, 40));
        } else if (id == 1) {
            player2Label.setForeground(new Color(255, 204, 51));
        } else if (id == 2) {
            player3Label.setForeground(new Color(42, 99, 66));
        } else if (id == 3) {
            player4Label.setForeground(new Color(193, 154, 36));
        }

    }

    public void updateUsers(User[] users) {
        for (int i = 0; i < users.length; i++) {
            if (users[i] != null) {
                if (i == 0) {
                    player1Label.setText(users[i].getUsername() + "  [ " + users[i].getScore() + " ]");
                } else if (i == 1) {
                    player2Label.setText(users[i].getUsername() + "  [ " + users[i].getScore() + " ]");
                } else if (i == 2) {
                    player3Label.setText(users[i].getUsername() + "  [ " + users[i].getScore() + " ]");
                } else if (i == 3) {
                    player4Label.setText(users[i].getUsername() + "  [ " + users[i].getScore() + " ]");
                }
            }
        }
    }


    //**************************** This is a test class*************************//
    public class testUser {

        String name;
        String longestWord;
        String team;
        String totalScore;
        String plongestWord;
        int pbonusWordCount;
        int teamCmuScore;
        int teamCumAverage;
        int wins;
        int tiles;
        int losses;
        int highestPerGame;
        int bonusWordCount;
        int rank;
        String value;

        public testUser(int r, String n, String ts, String plw, int pbwc) {
            rank = r;
            name = n;
            totalScore = ts;
            plongestWord = plw;
            pbonusWordCount = pbwc;

        }

        public testUser(String tm, int tcs, int tca, String lw, int w, int t, int l, int hpg, int bwc) {
            team = tm;
            teamCmuScore = tcs;
            teamCumAverage = tca;
            longestWord = lw;
            wins = w;
            tiles = t;
            losses = l;
            highestPerGame = hpg;
            bonusWordCount = bwc;
        }

        public testUser(String t, String v) {
            team = t;
            value = v;
        }


    }

    public ArrayList TopPlayer(String teamName) {
        ArrayList<testUser> list = new ArrayList<>();
        QueryClass dbCall = new QueryClass();
        String[][] temp;
        temp = dbCall.getTopPlayersByTeam(5, teamName);
        testUser[] statList = new testUser[temp.length];
        for(int i = 0; i < temp.length; i++) {
            testUser t = new testUser(i, temp[i][0], temp[i][2], "GUI Sucks", 0);
            statList[i] = t;
        }
        for(int i = 0; i < temp.length; i++) {
            list.add(statList[i]);
        }
        return list;
    }

    public ArrayList Statistics() {
        ArrayList<testUser> list = new ArrayList<>();
        QueryClass dbCall = new QueryClass();
        String teamName = "green";
        String teamName2 = "gold";
        testUser t1 = new testUser(teamName,dbCall.getTeamCumulative(teamName),0, dbCall.getTeamLongestWord(teamName),dbCall.getTeamWinCount(teamName), dbCall.getTeamTieCount(teamName), dbCall.getTeamLoseCount(teamName), dbCall.getHighestGameSessionScore(teamName), dbCall.getTeamBonuses(teamName));
        testUser t2 = new testUser(teamName2,dbCall.getTeamCumulative(teamName2),0, dbCall.getTeamLongestWord(teamName2),dbCall.getTeamWinCount(teamName2), dbCall.getTeamTieCount(teamName2), dbCall.getTeamLoseCount(teamName2), dbCall.getHighestGameSessionScore(teamName2), dbCall.getTeamBonuses(teamName2));

        list.add(t1);
        list.add(t2);
        return list;

    }

    public ArrayList TTest() {
        ArrayList<testUser> list = new ArrayList<>();
        QueryClass dbCalls = new QueryClass();
        testUser t1 = new testUser("Gold", Double.toString(dbCalls.getTotalGameScoreAverageForTeam("gold")));
        testUser t2 = new testUser("Green", Double.toString(dbCalls.getTotalGameScoreAverageForTeam("green")));

        list.add(t1);
        list.add(t2);
        return list;
    }

    public String TestConclusion() {
        QueryClass dbCalls = new QueryClass();
        String team = " ";
        String notSig = "There is not a significant difference between the teams.";
        String sig = "There is a significant difference between the teams.";
        if(dbCalls.tTestResults()>= 0.05) {
            double greenAvg;
            double goldAvg;
            greenAvg = dbCalls.getTotalGameScoreAverageForTeam("green");
            goldAvg = dbCalls.getTotalGameScoreAverageForTeam("gold");
            System.out.println(greenAvg + " " + goldAvg);
            if(greenAvg > goldAvg) {
                team = "Green team is statistically better.";
            } else {
                team = "Gold team is statistically better.";
            }
            return sig + "\n" + team;
        } else {
            return notSig;
        }
    }

    public String TestGameLog() {
        String word = "The T-test is good";
        return word;
    }

    //*******************This are the methods to edit each table*********************//

    public void addRowToTable1() {
        DefaultTableModel modelTest = (DefaultTableModel) jTable1.getModel();
        ArrayList<testUser> list = TopPlayer("Gold");
        Object rowData[] = new Object[5];
        for (int i = 0; i < list.size(); i++) {
            //**rowData[0] = list.get(i).methodName;
            rowData[0] = list.get(i).rank;
            rowData[1] = list.get(i).name;
            rowData[2] = list.get(i).totalScore;
            rowData[3] = list.get(i).plongestWord;
            rowData[4] = list.get(i).pbonusWordCount;
            modelTest.addRow(rowData);
        }
    }

    public void addRowToTable2() {
        DefaultTableModel modelTest = (DefaultTableModel) jTable2.getModel();
        ArrayList<testUser> list = TopPlayer("Green");
        Object rowData[] = new Object[5];
        for (int i = 0; i < list.size(); i++) {
            rowData[0] = list.get(i).rank;
            rowData[1] = list.get(i).name;
            rowData[2] = list.get(i).totalScore;
            rowData[3] = list.get(i).plongestWord;
            rowData[4] = list.get(i).pbonusWordCount;
            modelTest.addRow(rowData);
        }
    }

    public void addRowToTable3() {
        DefaultTableModel modelTest = (DefaultTableModel) jTable3.getModel();
        ArrayList<testUser> list = Statistics();
        Object rowData[] = new Object[9];
        for (int i = 0; i < list.size(); i++) {
            rowData[0] = list.get(i).team;
            rowData[1] = list.get(i).teamCmuScore;
            rowData[2] = list.get(i).teamCumAverage;
            rowData[3] = list.get(i).longestWord;
            rowData[4] = list.get(i).wins;
            rowData[5] = list.get(i).tiles;
            rowData[6] = list.get(i).losses;
            rowData[7] = list.get(i).highestPerGame;
            rowData[8] = list.get(i).bonusWordCount;
            modelTest.addRow(rowData);
        }
    }

    public void addRowToTable4() {
        DefaultTableModel modelTest = (DefaultTableModel) jTable4.getModel();
        ArrayList<testUser> list = TTest();
        Object rowData[] = new Object[2];
        for (int i = 0; i < list.size(); i++) {
            rowData[0] = list.get(i).team;
            rowData[1] = list.get(i).value;
            modelTest.addRow(rowData);
        }
    }

    public void setTTestConclusion() {
        String word = TestConclusion();
        jTextArea2.append(word);
    }

    public void printGameLog(String message){
        gameLogArea.append( message + "\n");
        gameLogArea.setCaretPosition(gameLogArea.getDocument().getLength());
    }
}