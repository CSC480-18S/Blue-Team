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
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.text.DecimalFormat;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

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
    private javax.swing.JScrollPane statsScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable jTable4;
    private javax.swing.JTextArea statsTextArea;
    private javax.swing.JTextArea jTextArea2;

    private javax.swing.JTextArea gameLogArea;
    private javax.swing.JLabel timeLabel2;
    private javax.swing.JLabel timeLabel1;
    private javax.swing.JLabel timeLabel3;
    private javax.swing.JLabel timeLabel4;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
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
    private javax.swing.JLabel timer1;
    private javax.swing.JLabel timer2;
    private javax.swing.JLabel timer3;
    private javax.swing.JLabel timer4;

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
        setStatsTextArea();
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
        c.insets = new Insets(15, 15, 15, 15);
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

        Dimension minSize = new Dimension();
        minSize.setSize(550,550);
        background.setMinimumSize(minSize);


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
        jPanel2 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        label2 = new javax.swing.JLabel();
        player1Label = new javax.swing.JLabel();
        timeLabel1 = new javax.swing.JLabel();
        timer1 = new javax.swing.JLabel();
        player3Label = new javax.swing.JLabel();
        timeLabel3 = new javax.swing.JLabel();
        timer3 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        label3 = new javax.swing.JLabel();
        player2Label = new javax.swing.JLabel();
        timeLabel2 = new javax.swing.JLabel();
        timer2 = new javax.swing.JLabel();
        player4Label = new javax.swing.JLabel();
        timer4 = new javax.swing.JLabel();
        timeLabel4 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        logLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        gameLogArea = new javax.swing.JTextArea();
        jPanel4 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        qrCode = new javax.swing.JPanel();

        label.setBackground(new java.awt.Color(243, 243, 243));
        label.setPreferredSize(new Dimension(310, 570));
        label.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jPanel2.setBackground(new java.awt.Color(243, 243, 243));
        jPanel2.setPreferredSize(new Dimension(300, 190));

        jPanel5.setBackground(new java.awt.Color(243, 243, 243));
        jPanel5.setPreferredSize(new Dimension(120, 170));

        label2.setFont(new java.awt.Font("Serif", 1, 17)); // NOI18N
        label2.setForeground(new java.awt.Color(42, 99, 66));
        label2.setText("Team Green");

        player1Label.setFont(new java.awt.Font("Serif", 1, 15)); // NOI18N
        player1Label.setText("Player 1");

        timeLabel1.setFont(new java.awt.Font("Serif", 0, 14)); // NOI18N
        timeLabel1.setText("Time:");

        timer1.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        timer1.setText("00");

        player3Label.setFont(new java.awt.Font("Serif", 1, 15)); // NOI18N
        player3Label.setText("Player 3");

        timeLabel3.setFont(new java.awt.Font("Serif", 0, 14)); // NOI18N
        timeLabel3.setText("Time:");

        timer3.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        timer3.setText("00");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
                jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(label2)
                                        .addComponent(player1Label)
                                        .addGroup(jPanel5Layout.createSequentialGroup()
                                                .addComponent(timeLabel1)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(timer1))
                                        .addComponent(player3Label)
                                        .addGroup(jPanel5Layout.createSequentialGroup()
                                                .addComponent(timeLabel3)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(timer3)))
                                .addGap(0, 14, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
                jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                .addComponent(label2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(player1Label)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(timeLabel1)
                                        .addComponent(timer1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(player3Label)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(timeLabel3)
                                        .addComponent(timer3))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel6.setBackground(new java.awt.Color(243, 243, 243));
        jPanel6.setPreferredSize(new Dimension(120, 170));

        label3.setFont(new java.awt.Font("Serif", 1, 17)); // NOI18N
        label3.setForeground(new java.awt.Color(255, 204, 51));
        label3.setText("Team Gold");

        player2Label.setFont(new java.awt.Font("Serif", 1, 15)); // NOI18N
        player2Label.setText("Player 2");

        timeLabel2.setFont(new java.awt.Font("Serif", 0, 14)); // NOI18N
        timeLabel2.setText("Time:");

        timer2.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        timer2.setText("00");

        player4Label.setFont(new java.awt.Font("Serif", 1, 15)); // NOI18N
        player4Label.setText("Player 4");

        timer4.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        timer4.setText("00");

        timeLabel4.setFont(new java.awt.Font("Serif", 0, 14)); // NOI18N
        timeLabel4.setText("Time:");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
                jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(label3)
                                        .addComponent(player2Label)
                                        .addGroup(jPanel6Layout.createSequentialGroup()
                                                .addComponent(timeLabel2)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(timer2)))
                                .addGap(0, 27, Short.MAX_VALUE))
                        .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel6Layout.createSequentialGroup()
                                                .addComponent(timeLabel4)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(timer4))
                                        .addComponent(player4Label))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
                jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(label3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(player2Label)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(timeLabel2)
                                        .addComponent(timer2))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(player4Label)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(timer4)
                                        .addComponent(timeLabel4))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30))
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(27, 27, 27))
        );

        jPanel3.setBackground(new java.awt.Color(243, 243, 243));
        jPanel3.setPreferredSize(new Dimension(300, 142));

        logLabel.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        logLabel.setForeground(new java.awt.Color(185, 39, 39));
        logLabel.setText("Game Log");

        gameLogArea.setColumns(20);
        gameLogArea.setFont(new java.awt.Font("Comic Sans MS", 1, 11)); // NOI18N
        gameLogArea.setRows(5);
        jScrollPane1.setViewportView(gameLogArea);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel3Layout.createSequentialGroup()
                                                .addComponent(logLabel)
                                                .addGap(0, 0, Short.MAX_VALUE))
                                        .addComponent(jScrollPane1))
                                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(logLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(243, 243, 243));
        jPanel4.setPreferredSize(new Dimension(300, 205));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel9.setText("Scan QR Code To Play");

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
                        .addGap(0, 148, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel4Layout.createSequentialGroup()
                                                .addGap(48, 48, 48)
                                                .addComponent(jLabel9))
                                        .addGroup(jPanel4Layout.createSequentialGroup()
                                                .addGap(72, 72, 72)
                                                .addComponent(qrBackground, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(54, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(qrBackground, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout labelLayout = new javax.swing.GroupLayout(label);
        label.setLayout(labelLayout);
        labelLayout.setHorizontalGroup(
                labelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(labelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        labelLayout.setVerticalGroup(
                labelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(labelLayout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );


        c.gridx = 50;
        c.gridy = 5;
        c.anchor = GridBagConstraints.FIRST_LINE_END;
        panel.add(label, c);



//        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
//        frame.setUndecorated(true);
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
        statsScrollPane1 = new javax.swing.JScrollPane();
        statsTextArea = new javax.swing.JTextArea();

        frame2.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        frame2.getContentPane().setBackground(new java.awt.Color(42, 99, 66));
        frame2.setResizable(true);

        jTable2.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jTable2.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {

                },
                new String [] {
                        "Rank", "Player", "Total Score"
                }
        ));
        jTable2.setRowHeight(35);

        JTableHeader header = jTable2.getTableHeader();
        header.setFont(new java.awt.Font("Tahoma", 0, 16));
        header.setBackground(new java.awt.Color(255, 204, 51));

        jScrollPane2.setViewportView(jTable2);

        jTable3.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jTable3.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {

                },
                new String [] {
                        "Team", "Cumulative", "Longest Word", "Wins", "Tiles", "Losses", "Highest Word Score", "B-Word Count"
                }
        ));
        jTable3.setRowHeight(35);

        JTableHeader header3 = jTable3.getTableHeader();
        header3.setFont(new java.awt.Font("Tahoma", 0, 16));
        header3.setBackground(new java.awt.Color(255, 204, 51));

        jScrollPane3.setViewportView(jTable3);

        jTable4.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jTable4.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {

                },
                new String [] {
                        "Team", "Stats"
                }
        ));
        jTable4.setRowHeight(35);

        JTableHeader header4 = jTable4.getTableHeader();
        header4.setFont(new java.awt.Font("Tahoma", 0, 16));
        header4.setBackground(new java.awt.Color(255, 204, 51));

        jScrollPane5.setViewportView(jTable4);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 30)); // NOI18N
        jLabel1.setText("Green");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel3.setText("Statistics");

        jTable1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {

                },
                new String [] {
                        "Rank", "Player", "Total Score"
                }
        ));
        jTable1.setRequestFocusEnabled(false);
        jTable1.setRowHeight(35);

        JTableHeader header2 = jTable1.getTableHeader();
        header2.setFont(new java.awt.Font("Tahoma", 0, 16));
        header2.setBackground(new java.awt.Color(255, 204, 51));

        jScrollPane6.setViewportView(jTable1);

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 30)); // NOI18N
        jLabel4.setText("T Test:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 30)); // NOI18N
        jLabel2.setText("Gold");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel6.setText("Leaderboard");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 30)); // NOI18N
        jLabel5.setText("Conclusion:");

        jTextArea2.setBackground(new java.awt.Color(255, 204, 51));
        jTextArea2.setColumns(20);
        jTextArea2.setFont(new java.awt.Font("Comic Sans MS", 1, 18)); // NOI18N
        jTextArea2.setRows(5);
        jScrollPane4.setViewportView(jTextArea2);

        statsTextArea.setBackground(new java.awt.Color(255, 204, 51));
        statsTextArea.setColumns(20);
        statsTextArea.setFont(new java.awt.Font("Comic Sans MS", 1, 18)); // NOI18N
        statsTextArea.setRows(5);
        statsScrollPane1.setViewportView(statsTextArea);

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
                                .addComponent(jLabel6)
                                .addGap(602, 602, 602))
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 680, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 680, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
                        .addGroup(layout.createSequentialGroup()
                                .addGap(636, 636, 636)
                                .addComponent(jLabel3)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(210, 210, 210)
                                                .addComponent(jLabel4)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 361, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(44, 44, 44)
                                                .addComponent(jLabel5)
                                                .addGap(18, 18, 18)
                                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 609, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(statsScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 486, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(75, 75, 75))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel6)
                                .addGap(22, 22, 22)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel2)
                                        .addComponent(jLabel1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 203, Short.MAX_VALUE)
                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                                .addGap(38, 38, 38)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGap(60, 60, 60)
                                                                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGap(85, 85, 85)
                                                                .addComponent(jLabel4)))
                                                .addGap(61, 61, 61)
                                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addContainerGap())
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                                .addComponent(jLabel5)
                                                                .addGap(95, 95, 95))
                                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                                .addComponent(statsScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(61, 61, 61))))))
        );

//        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
//        frame.setUndecorated(true);
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
        updateTeamScore(Session.getSession().getUsers());
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
        timer1.setForeground(Color.BLACK);
        player2Label.setForeground(Color.BLACK);
        timer2.setForeground(Color.BLACK);
        player3Label.setForeground(Color.BLACK);
        timer3.setForeground(Color.BLACK);
        player4Label.setForeground(Color.BLACK);
        timer4.setForeground(Color.BLACK);

        if (id == 0) {
            player1Label.setForeground(new Color(42, 99, 66));
            timer1.setForeground(new Color(42, 99, 66));
        } else if (id == 1) {
            player2Label.setForeground(new Color(255, 204, 51));
            timer2.setForeground(new Color(255, 204, 51));
        } else if (id == 2) {
            player3Label.setForeground(new Color(42, 99, 66));
            timer3.setForeground(new Color(42, 99, 66));
        } else if (id == 3) {
            player4Label.setForeground(new Color(255, 204, 51));
            timer4.setForeground(new Color(255, 204, 51));
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

    public void updateTeamScore(User[] users){
        int greenScore = 0;
        int goldScore = 0;
        if (users[0].getClass() == Player.class){
            greenScore += users[0].getScore();
        } if (users[1].getClass() == Player.class) {
            goldScore += users[1].getScore();
        }
        if (users[2].getClass() == Player.class) {
            greenScore += users[2].getScore();
        }
        if (users[3].getClass() == Player.class){
            greenScore += users[3].getScore();
        }
        label2.setText("Team Green: " + greenScore);
        label3.setText("Team Green: " + goldScore);
    }

    public void setTimerText(int turn, int sec) {
        timer1.setVisible(false);
        timer2.setVisible(false);
        timer3.setVisible(false);
        timer4.setVisible(false);
        timeLabel1.setText("      ");
        timeLabel2.setText("      ");
        timeLabel3.setText("      ");
        timeLabel4.setText("      ");
        if(turn == 0){
            timer1.setText(Integer.toString(sec));
            timer1.setVisible(true);
            timeLabel1.setText("Time: ");
        } else if(turn == 1){
            timer2.setText(Integer.toString(sec));
            timer2.setVisible(true);
            timeLabel2.setText("Time: ");
        } else if (turn == 2){
            timer3.setText(Integer.toString(sec));
            timer3.setVisible(true);
            timeLabel3.setText("Time: ");
        } else if(turn == 3){
            timer4.setText(Integer.toString(sec));
            timer4.setVisible(true);
            timeLabel4.setText("Time: ");
        }
    }


    //**************************** This is a test class*************************//
    public class testUser {

        String name;
        String value;
        String longestWord;
        String team;
        String totalScore;
        int teamCmuScore;
        int wins;
        int tiles;
        int losses;
        int highestPerGame;
        int bonusWordCount;
        int rank;

        public testUser(int r, String n, String ts) {
            rank = r;
            name = n;
            totalScore = ts;

        }

        public testUser(String tm, int tcs, String lw, int w, int t, int l, int hpg, int bwc) {
            team = tm;
            teamCmuScore = tcs;
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
            testUser t = new testUser(i + 1, temp[i][0], temp[i][2]);
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
        testUser t1 = new testUser(teamName,dbCall.getTeamCumulative(teamName), dbCall.getTeamLongestWord(teamName),dbCall.getTeamWinCount(teamName), dbCall.getTeamTieCount(teamName), dbCall.getTeamLoseCount(teamName), dbCall.getHighestGameSessionScore(teamName), dbCall.getTeamBonuses(teamName));
        testUser t2 = new testUser(teamName2,dbCall.getTeamCumulative(teamName2), dbCall.getTeamLongestWord(teamName2),dbCall.getTeamWinCount(teamName2), dbCall.getTeamTieCount(teamName2), dbCall.getTeamLoseCount(teamName2), dbCall.getHighestGameSessionScore(teamName2), dbCall.getTeamBonuses(teamName2));

        System.out.println("At Stats Dispaly: Green: " + dbCall.getTeamCumulative("green"));
        System.out.println("At stats display: Gold: " + dbCall.getTeamCumulative("gold"));

        list.add(t1);
        list.add(t2);
        return list;

    }

    public ArrayList TTest() {
        ArrayList<testUser> list = new ArrayList<>();
        QueryClass dbCalls = new QueryClass();
        if(dbCalls.getTotalGameScoreAverageForTeam("gold").isNaN() || dbCalls.getTotalGameScoreAverageForTeam("green").isNaN()) {
            testUser t1 = new testUser("Gold", "0");
            testUser t2 = new testUser("Green", "0");
            list.add(t1);
            list.add(t2);
            return list;
        } else {
            DecimalFormat df = new DecimalFormat("#.##");
            String formatAvg = df.format(dbCalls.getTotalGameScoreAverageForTeam("gold"));
            String formatAvg2 = df.format(dbCalls.getTotalGameScoreAverageForTeam("green"));
            testUser t1 = new testUser("Gold", formatAvg);
            testUser t2 = new testUser("Green", formatAvg2);

            list.add(t1);
            list.add(t2);
            return list;
        }
    }

    public String TestConclusion() {
        QueryClass dbCalls = new QueryClass();
        String team = " ";
        String notSig = "There is not a significant difference between the teams.";
        String sig = "There is a significant difference between the teams.";
        if(dbCalls.tTestResults() != null && dbCalls.tTestResults() >= 0.05) {
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

    public String TextInformationBox() {
        String text;
        text = "Hello";
        return text;
    }


    //*******************This are the methods to edit each table*********************//

    public void addRowToTable1() {
        DefaultTableModel modelTest = (DefaultTableModel) jTable1.getModel();
        ArrayList<testUser> list = TopPlayer("Green");
        Object rowData[] = new Object[5];
        for (int i = 0; i < list.size(); i++) {
            //**rowData[0] = list.get(i).methodName;
            rowData[0] = list.get(i).rank;
            rowData[1] = list.get(i).name;
            rowData[2] = list.get(i).totalScore;
            modelTest.addRow(rowData);
        }
    }

    public void addRowToTable2() {
        DefaultTableModel modelTest = (DefaultTableModel) jTable2.getModel();
        ArrayList<testUser> list = TopPlayer("Gold");
        Object rowData[] = new Object[5];
        for (int i = 0; i < list.size(); i++) {
            rowData[0] = list.get(i).rank;
            rowData[1] = list.get(i).name;
            rowData[2] = list.get(i).totalScore;
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
            rowData[2] = list.get(i).longestWord;
            rowData[3] = list.get(i).wins;
            rowData[4] = list.get(i).tiles;
            rowData[5] = list.get(i).losses;
            rowData[6] = list.get(i).highestPerGame;
            rowData[7] = list.get(i).bonusWordCount;
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

    public void setStatsTextArea( ) {
        String info = TextInformationBox();
        statsTextArea.append(info);
    }

    public void printGameLog(String message){
        gameLogArea.append( message + "\n");
        gameLogArea.setCaretPosition(gameLogArea.getDocument().getLength());
    }

    public void closeFrame(){
        frame.setVisible(false);
        frame2.setVisible(false);
        frame.dispose();
        frame2.dispose();
    }
}