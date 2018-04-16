/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

import Components.QueryClass;
import Models.*;
import static Models.GameConstants.*;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

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
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable jTable4;
    private javax.swing.JTable jTable5;

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

    public BoardGUI() {
        gui();
        addRowToTable1();
        addRowToTable2();
        addRowToTable3();
        addRowToTable4();
        addRowToTable5();
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
        for (int i = 0; i < userColumn13.length; ++i) // *** For loop to make the table column 50px
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
        for (int i = 0; i < userColumn13.length; ++i) // *** For loop to make the table column 50px
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
        label2.setFont(new Font("Serif", Font.BOLD, 17));
        label2.setForeground(new Color(47, 86, 63));
        lc.gridx = 0;
        lc.gridy = 0;
        lc.insets = new Insets(5, 20, 5, 20);
        label.add(label2, lc);

        JLabel label3 = new JLabel("Team Gold");
        label3.setFont(new Font("Serif", Font.BOLD, 17));
        label3.setForeground(new Color(191, 156, 70));
        lc.gridx = 2;
        lc.gridy = 0;
        label.add(label3, lc);

        player1Label = new JLabel("Player 1");
        player1Label.setFont(new Font("Serif", Font.BOLD, 17));
        lc.gridx = 0;
        lc.gridy = 2;
        label.add(player1Label, lc);

        player2Label = new JLabel("Player 2");
        player2Label.setFont(new Font("Serif", Font.BOLD, 17));
        lc.gridx = 2;
        lc.gridy = 2;
        label.add(player2Label, lc);

        player3Label = new JLabel("Player 3");
        player3Label.setFont(new Font("Serif", Font.BOLD, 17));
        lc.gridx = 0;
        lc.gridy = 4;
        label.add(player3Label, lc);

        player4Label = new JLabel("Player 4");
        player4Label.setFont(new Font("Serif", Font.BOLD, 17));
        lc.gridx = 2;
        lc.gridy = 4;
        label.add(player4Label, lc);

        label.setBackground(Color.WHITE);
        label.setBorder(BorderFactory.createLineBorder(Color.black));
        label.setPreferredSize(new Dimension(350, 420));
        c.gridx = 50;
        c.gridy = 5;
        panel.add(label, c);

        frame.setSize(1500, 1076);
        frame.setVisible(true);

        // *********************************This is the start of Stats frame*********************************** //
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable5 = new javax.swing.JTable();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        frame2.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTable2.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jTable2.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {

                },
                new String [] {
                        "Name", "Score"
                }
        ));
        jTable2.setRowHeight(35);
        jScrollPane2.setViewportView(jTable2);

        jTable3.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jTable3.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {

                },
                new String [] {
                        "Rank", "Player", "Score"
                }
        ));
        jTable3.setRowHeight(35);
        jScrollPane3.setViewportView(jTable3);

        jTable5.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jTable5.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {

                },
                new String [] {
                        "Team", "Stats"
                }
        ));
        jTable5.setRowHeight(35);
        jScrollPane4.setViewportView(jTable5);

        jTable4.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jTable4.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {

                },
                new String [] {
                        "Team", "Stats"
                }
        ));
        jTable4.setRowHeight(35);
        jScrollPane5.setViewportView(jTable4);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel1.setText("Green");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel3.setText("Top Ten Players");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel5.setText("Average Score");

        jTable1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {

                },
                new String [] {
                        "Name", "Score"
                }
        ));
        jTable1.setRequestFocusEnabled(false);
        jTable1.setRowHeight(35);
        jScrollPane6.setViewportView(jTable1);

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel4.setText("T Test");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel2.setText("Gold");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(frame2.getContentPane());
        frame2.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(53, 53, 53)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 553, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel3))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel4)
                                .addGap(210, 210, 210))
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(741, 741, 741)
                                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 553, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                        .addGap(836, 836, 836)
                                                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 361, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGroup(layout.createSequentialGroup()
                                                        .addGap(74, 74, 74)
                                                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 762, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                .addGroup(layout.createSequentialGroup()
                                                                        .addGap(139, 139, 139)
                                                                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 361, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addGroup(layout.createSequentialGroup()
                                                                        .addGap(208, 208, 208)
                                                                        .addComponent(jLabel5))))))
                                .addContainerGap(70, Short.MAX_VALUE))
                        .addGroup(layout.createSequentialGroup()
                                .addGap(275, 275, 275)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel2)
                                .addGap(348, 348, 348))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap(24, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 222, Short.MAX_VALUE)
                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                                .addGap(45, 45, 45)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel3)
                                        .addComponent(jLabel4))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(63, 63, 63)
                                                .addComponent(jLabel5)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 397, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(36, Short.MAX_VALUE))
        );

        frame2.pack();
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
                        model.setValueAt(new ImageIcon(classloader.getResource("letter/" + letter + ".png")), y, x);
                    } catch (Exception e) {
                    }
                }
            }
        }
        jt.repaint();
    }

    public void setTurn(int id) {
        player1Label.setForeground(Color.BLACK);
        player2Label.setForeground(Color.BLACK);
        player3Label.setForeground(Color.BLACK);
        player4Label.setForeground(Color.BLACK);

        if (id == 0) {
            player1Label.setForeground(new Color(47, 86, 63));
        } else if (id == 1) {
            player2Label.setForeground(new Color(244, 212, 108));
        } else if (id == 2) {
            player3Label.setForeground(new Color(53, 226, 40));
        } else if (id == 3) {
            player4Label.setForeground(new Color(191, 156, 70));
        }

    }

    public void updateUsers(User[] users) {
        for (int i = 0; i < users.length; i++) {
            if (users[i] != null) {
                if (i == 0) {
                    player1Label.setText(users[i].getUsername() + "      [ " + users[i].getScore() + " ]");
                } else if (i == 1) {
                    player2Label.setText(users[i].getUsername() + "      [ " + users[i].getScore() + " ]");
                } else if (i == 2) {
                    player3Label.setText(users[i].getUsername() + "      [ " + users[i].getScore() + " ]");
                } else if (i == 3) {
                    player4Label.setText(users[i].getUsername() + "      [ " + users[i].getScore() + " ]");
                }
            }
        }
    }


    public class statUser {

        String name;
        String score;
        String team;
        int rank;
        String stat;

        public statUser(String n, String s) {
            name = n;
            score = s;
        }

        public statUser(int r, String n, String s) {
            rank = r;
            name = n;
            score = s;
        }

    }

    public ArrayList ListUser(String teamName) {
        ArrayList<statUser> list = new ArrayList<>();
        QueryClass dbCall = new QueryClass();
        String[][] temp;
        temp = dbCall.getTopPlayersByTeam(5, teamName);
        statUser[] statList = new statUser[temp.length];
        for(int i = 0; i < temp.length; i++) {
            statUser t = new statUser(temp[i][0], temp[i][2]);
            statList[i] = t;
        }

        for(int i = 0; i < statList.length; i++) {
            list.add(statList[i]);
        }

        return list;

    }

    public ArrayList TopPlayer() {
        ArrayList<statUser> list = new ArrayList<>();
        QueryClass dbCall = new QueryClass();
        String[][] temp;
        temp = dbCall.getTopPlayers(10);
        statUser[] statList = new statUser[temp.length];
        for(int i = 0; i < temp.length; i++) {
            statUser t = new statUser(i + 1, temp[i][0], temp[i][2]);
            statList[i] = t;
        }

        for(int i = 0; i < statList.length; i++) {
            list.add(statList[i]);
        }

        return list;
    }

    public ArrayList AvrAndTTest() {
        ArrayList<statUser> list = new ArrayList<>();
        QueryClass dbCall = new QueryClass();
        String temp = Double.toString(dbCall.getTotalGameScoreAverageForTeam("gold"));
        String temp2 = Double.toString(dbCall.getTotalGameScoreAverageForTeam("green"));
        statUser t1 = new statUser("Gold", temp);
        statUser t2 = new statUser("Green", temp2);

        list.add(t1);
        list.add(t2);
        return list;
    }

    //*******************This are the methods to edit each table*********************//


    public void addRowToTable1() {
        DefaultTableModel modelTest = (DefaultTableModel) jTable1.getModel();
        ArrayList<statUser> list = ListUser("green");
        Object rowData[] = new Object[2];
        for (int i = 0; i < list.size(); i++) {
            //**rowData[0] = list.get(i).methodName;
            rowData[0] = list.get(i).name;
            rowData[1] = list.get(i).score;
            modelTest.addRow(rowData);
        }
    }

    public void addRowToTable2() {
        DefaultTableModel modelTest = (DefaultTableModel) jTable2.getModel();
        ArrayList<statUser> list = ListUser("gold");
        Object rowData[] = new Object[2];
        for (int i = 0; i < list.size(); i++) {
            rowData[0] = list.get(i).name;
            rowData[1] = list.get(i).score;
            modelTest.addRow(rowData);
        }
    }

    public void addRowToTable3() {
        DefaultTableModel modelTest = (DefaultTableModel) jTable3.getModel();
        ArrayList<statUser> list = TopPlayer();
        Object rowData[] = new Object[3];
        for (int i = 0; i < list.size(); i++) {
            rowData[0] = list.get(i).rank;
            rowData[1] = list.get(i).name;
            rowData[2] = list.get(i).score;
            modelTest.addRow(rowData);
        }
    }

    public void addRowToTable4() {
        DefaultTableModel modelTest = (DefaultTableModel) jTable4.getModel();
        ArrayList<statUser> list = AvrAndTTest();
        Object rowData[] = new Object[2];
        for (int i = 0; i < list.size(); i++) {
            rowData[0] = list.get(i).name;
            rowData[1] = list.get(i).score;
            modelTest.addRow(rowData);
        }
    }

    public void addRowToTable5() {
        DefaultTableModel modelTest = (DefaultTableModel) jTable5.getModel();
        ArrayList<statUser> list = AvrAndTTest();
        Object rowData[] = new Object[2];
        for (int i = 0; i < list.size(); i++) {
            rowData[0] = list.get(i).name;
            rowData[1] = list.get(i).score;
            modelTest.addRow(rowData);
        }
    }
}