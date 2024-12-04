/*
 * Created by JFormDesigner on Mon Dec 02 17:51:45 PST 2024
 */

package org.michaelalbert.game;

import lombok.Setter;
import org.michaelalbert.game.reqres.GameErrorRequest;
import org.michaelalbert.game.reqres.GameFinishedRequest;
import org.michaelalbert.game.reqres.InvalidMoveRequest;
import org.michaelalbert.game.reqres.TurnNotificationRequest;

import java.awt.*;
import javax.swing.*;
import javax.swing.GroupLayout;

/**
 * @author michaelalbert
 */
public class TTTWindow extends JFrame {

    @Setter
    char turn;
    @Setter
    String error = null;
    @Setter
    String success = null;
    @Setter
    String warning = null;

    private void updateScreenError() {
        //update with swinguilities update
        SwingUtilities.invokeLater(() -> {
            updatescreen.setText(compileGameHTML(client.gameId, client.role, turn, error, success, warning));
        });
    }

    private void updateScreenError(String err) {
        error = err;
        this.warning = null;
        this.success = null;
        updateScreenError();
    }

    private void updateScreenSuccess(String success) {
        this.success = success;
        this.error = null;
        this.warning = null;
        updateScreenError();
    }

    private void updateScreenWarning(String warning) {
        this.warning = warning;
        this.error = null;
        this.success = null;
        updateScreenError();
    }


    private static String compileGameHTML(String gameId, Character player, Character turn, String err, String success, String warning) {
        String out = "<html><head><style>body {font-family: Arial, sans-serif; background-color: #444; color: #ddd;} #err {background-color:#400;} #warn {background-color:#440;} #suc {background-color:#040;}</style></head><body>" +
                "<h1>Game ID: " + gameId + "</h1>" +
                "<h2>My Player: " + player + "</h2>";

        if (player == turn) {
            out += "<h2 id='suc'>My Turn!</h2>";
        } else {
            out += "<h2 id='err'>Opponent's Turn!</h2>";

        }

        if (err != null) {
            out += "<p id='err'>Error: " + err + "</p>" +
                    "</body></html>";
        } else if (success != null) {
            out += "<p id='suc'>Success: " + success + "</p>" +
                    "</body></html>";
        } else if (warning != null) {
            out += "<p id='warn'>Warning: " + warning + "</p>" +
                    "</body></html>";
        }else {
            out += "</body></html>";
        }
        return out;
    }


    public TTTClient client = new TTTClient(TTTInstance.X) {
        @Override
        public void log(String message) {
            textArea1.append(message + "\n");
        }

        @Override
        public void updateBoard(char[][] board) {
            JButton[] buttons = {button1, button2, button3, button4, button5, button6, button7, button8, button9};
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[i].length; j++) {
                    char c = board[i][j];
                    int buttonIndex = i * 3 + j;
                    JButton button = buttons[buttonIndex];
                    if (c == TTTInstance.X) {
                        button.setText("X");
                        button.setEnabled(false);
                    } else if (c == TTTInstance.O) {
                        button.setText("O");
                        button.setEnabled(false);
                    } else {
                        button.setText("?");
                        button.setEnabled(true);
                    }
                }
            }
        }

        @Override
        public void TurnNotificationRequestCallback(TurnNotificationRequest turnNotificationRequest) {
            super.TurnNotificationRequestCallback(turnNotificationRequest);
            turn = turnNotificationRequest.getPlayer();
            updateScreenError(null);
        }

        @Override
        public void InvalidMoveRequestCallback(InvalidMoveRequest invalidMoveRequest) {
            super.InvalidMoveRequestCallback(invalidMoveRequest);
            updateScreenError(invalidMoveRequest.getMessage());
        }

        @Override
        public void GameErrorRequestCallback(GameErrorRequest gameErrorRequest) {
            super.GameErrorRequestCallback(gameErrorRequest);
            updateScreenError(gameErrorRequest.getMessage());
            internalFrame1.setVisible(false);
        }

        @Override
        public void GameFinishedRequestCallback(GameFinishedRequest gameFinishedRequest) {
            super.GameFinishedRequestCallback(gameFinishedRequest);
            System.out.println("Game won by: " + gameFinishedRequest.getWinner());
            if (gameFinishedRequest.getWinner().equals("draw")) {
                updateScreenWarning("ITS A DRAW!");
                internalFrame1.setVisible(false);
            } else

            if (gameFinishedRequest.getWinner().charAt(0) == client.role) {
                updateScreenSuccess("YOU WIN!");
                internalFrame1.setVisible(false);
            } else {
                updateScreenError("YOU LOSE!");
                internalFrame1.setVisible(false);
            }
            internalFrame1.setVisible(false);
        }
    };





    public void connect(String host, int port) {
        client.start(host, port);
    }

    public TTTWindow() {
        initComponents();
        updatescreen.setContentType( "text/html" );
        updatescreen.setEditable(false);
        button1.addActionListener(e -> {
            client.makeMove(0, 0);
            updateScreenError(null);

        });
        button2.addActionListener(e -> {
            client.makeMove(0, 1);
            updateScreenError(null);

        });
        button3.addActionListener(e -> {
            client.makeMove(0, 2);
            updateScreenError(null);

        });
        button4.addActionListener(e -> {
            client.makeMove(1, 0);
            updateScreenError(null);

        });
        button5.addActionListener(e -> {
            client.makeMove(1, 1);
            updateScreenError(null);

        });
        button6.addActionListener(e -> {
            client.makeMove(1, 2);
            updateScreenError(null);

        });
        button7.addActionListener(e -> {
            client.makeMove(2, 0);
            updateScreenError(null);


        });
        button8.addActionListener(e -> {
            client.makeMove(2, 1);
            updateScreenError(null);

        });
        button9.addActionListener(e -> {
            client.makeMove(2, 2);
            updateScreenError(null);

        });
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Educational license - Michael Albert
        desktopPane1 = new JDesktopPane();
        internalFrame1 = new JInternalFrame();
        button1 = new JButton();
        button2 = new JButton();
        button3 = new JButton();
        button4 = new JButton();
        button5 = new JButton();
        button6 = new JButton();
        button7 = new JButton();
        button8 = new JButton();
        button9 = new JButton();
        internalFrame2 = new JInternalFrame();
        scrollPane1 = new JScrollPane();
        textArea1 = new JTextArea();
        internalFrame3 = new JInternalFrame();
        scrollPane2 = new JScrollPane();
        updatescreen = new JEditorPane();

        //======== this ========
        var contentPane = getContentPane();

        //======== desktopPane1 ========
        {

            //======== internalFrame1 ========
            {
                internalFrame1.setVisible(true);
                internalFrame1.setMinimumSize(new Dimension(150, 150));
                internalFrame1.setMaximumSize(new Dimension(150, 150));
                internalFrame1.setPreferredSize(new Dimension(150, 150));
                internalFrame1.setTitle("Tic Tac Toe Board");
                var internalFrame1ContentPane = internalFrame1.getContentPane();

                //---- button1 ----
                button1.setText("?");
                button1.setFont(button1.getFont().deriveFont(button1.getFont().getStyle() & ~Font.ITALIC, button1.getFont().getSize() + 6f));

                //---- button2 ----
                button2.setText("?");
                button2.setFont(button2.getFont().deriveFont(button2.getFont().getStyle() & ~Font.ITALIC, button2.getFont().getSize() + 6f));

                //---- button3 ----
                button3.setText("?");
                button3.setFont(button3.getFont().deriveFont(button3.getFont().getStyle() & ~Font.ITALIC, button3.getFont().getSize() + 6f));

                //---- button4 ----
                button4.setText("?");
                button4.setFont(button4.getFont().deriveFont(button4.getFont().getStyle() & ~Font.ITALIC, button4.getFont().getSize() + 6f));

                //---- button5 ----
                button5.setText("?");
                button5.setFont(button5.getFont().deriveFont(button5.getFont().getStyle() & ~Font.ITALIC, button5.getFont().getSize() + 6f));

                //---- button6 ----
                button6.setText("?");
                button6.setFont(button6.getFont().deriveFont(button6.getFont().getStyle() & ~Font.ITALIC, button6.getFont().getSize() + 6f));

                //---- button7 ----
                button7.setText("?");
                button7.setFont(button7.getFont().deriveFont(button7.getFont().getStyle() & ~Font.ITALIC, button7.getFont().getSize() + 6f));

                //---- button8 ----
                button8.setText("?");
                button8.setFont(button8.getFont().deriveFont(button8.getFont().getStyle() & ~Font.ITALIC, button8.getFont().getSize() + 6f));

                //---- button9 ----
                button9.setText("?");
                button9.setFont(button9.getFont().deriveFont(button9.getFont().getStyle() & ~Font.ITALIC, button9.getFont().getSize() + 6f));

                GroupLayout internalFrame1ContentPaneLayout = new GroupLayout(internalFrame1ContentPane);
                internalFrame1ContentPane.setLayout(internalFrame1ContentPaneLayout);
                internalFrame1ContentPaneLayout.setHorizontalGroup(
                    internalFrame1ContentPaneLayout.createParallelGroup()
                        .addGroup(internalFrame1ContentPaneLayout.createSequentialGroup()
                            .addGroup(internalFrame1ContentPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                .addComponent(button9, GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
                                .addComponent(button6, GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
                                .addComponent(button1, GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(internalFrame1ContentPaneLayout.createParallelGroup()
                                .addComponent(button5, GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE)
                                .addComponent(button2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(button8, GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(internalFrame1ContentPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                .addComponent(button3, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 76, Short.MAX_VALUE)
                                .addComponent(button4, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 76, Short.MAX_VALUE)
                                .addComponent(button7, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 76, Short.MAX_VALUE)))
                );
                internalFrame1ContentPaneLayout.setVerticalGroup(
                    internalFrame1ContentPaneLayout.createParallelGroup()
                        .addGroup(internalFrame1ContentPaneLayout.createSequentialGroup()
                            .addGroup(internalFrame1ContentPaneLayout.createParallelGroup()
                                .addComponent(button3, GroupLayout.DEFAULT_SIZE, 64, Short.MAX_VALUE)
                                .addComponent(button2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(button1, GroupLayout.DEFAULT_SIZE, 64, Short.MAX_VALUE))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(internalFrame1ContentPaneLayout.createParallelGroup()
                                .addComponent(button6, GroupLayout.DEFAULT_SIZE, 64, Short.MAX_VALUE)
                                .addComponent(button4, GroupLayout.DEFAULT_SIZE, 64, Short.MAX_VALUE)
                                .addComponent(button5, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(internalFrame1ContentPaneLayout.createParallelGroup()
                                .addComponent(button7, GroupLayout.DEFAULT_SIZE, 64, Short.MAX_VALUE)
                                .addComponent(button9, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 64, Short.MAX_VALUE)
                                .addComponent(button8, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                );
            }
            desktopPane1.add(internalFrame1, JLayeredPane.DEFAULT_LAYER);
            internalFrame1.setBounds(85, 15, 240, 240);

            //======== internalFrame2 ========
            {
                internalFrame2.setVisible(true);
                internalFrame2.setTitle("Logs");
                internalFrame2.setIconifiable(true);
                var internalFrame2ContentPane = internalFrame2.getContentPane();

                //======== scrollPane1 ========
                {
                    scrollPane1.setViewportView(textArea1);
                }

                GroupLayout internalFrame2ContentPaneLayout = new GroupLayout(internalFrame2ContentPane);
                internalFrame2ContentPane.setLayout(internalFrame2ContentPaneLayout);
                internalFrame2ContentPaneLayout.setHorizontalGroup(
                    internalFrame2ContentPaneLayout.createParallelGroup()
                        .addGroup(internalFrame2ContentPaneLayout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 461, Short.MAX_VALUE)
                            .addContainerGap())
                );
                internalFrame2ContentPaneLayout.setVerticalGroup(
                    internalFrame2ContentPaneLayout.createParallelGroup()
                        .addGroup(internalFrame2ContentPaneLayout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)
                            .addContainerGap())
                );
            }
            desktopPane1.add(internalFrame2, JLayeredPane.DEFAULT_LAYER);
            internalFrame2.setBounds(5, 260, 485, 180);

            //======== internalFrame3 ========
            {
                internalFrame3.setVisible(true);
                internalFrame3.setIconifiable(true);
                var internalFrame3ContentPane = internalFrame3.getContentPane();

                //======== scrollPane2 ========
                {
                    scrollPane2.setViewportView(updatescreen);
                }

                GroupLayout internalFrame3ContentPaneLayout = new GroupLayout(internalFrame3ContentPane);
                internalFrame3ContentPane.setLayout(internalFrame3ContentPaneLayout);
                internalFrame3ContentPaneLayout.setHorizontalGroup(
                    internalFrame3ContentPaneLayout.createParallelGroup()
                        .addComponent(scrollPane2, GroupLayout.DEFAULT_SIZE, 283, Short.MAX_VALUE)
                );
                internalFrame3ContentPaneLayout.setVerticalGroup(
                    internalFrame3ContentPaneLayout.createParallelGroup()
                        .addComponent(scrollPane2, GroupLayout.DEFAULT_SIZE, 394, Short.MAX_VALUE)
                );
            }
            desktopPane1.add(internalFrame3, JLayeredPane.DEFAULT_LAYER);
            internalFrame3.setBounds(495, 5, 295, 430);
        }

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addComponent(desktopPane1, GroupLayout.DEFAULT_SIZE, 793, Short.MAX_VALUE)
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addComponent(desktopPane1, GroupLayout.DEFAULT_SIZE, 444, Short.MAX_VALUE)
        );
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Educational license - Michael Albert
    private JDesktopPane desktopPane1;
    private JInternalFrame internalFrame1;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    private JButton button5;
    private JButton button6;
    private JButton button7;
    private JButton button8;
    private JButton button9;
    private JInternalFrame internalFrame2;
    private JScrollPane scrollPane1;
    private JTextArea textArea1;
    private JInternalFrame internalFrame3;
    private JScrollPane scrollPane2;
    private JEditorPane updatescreen;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on


    public static void main(String[] args) {
        //expect args[0] to be the host and args[1] to be the port
        if (args.length != 3) {
            System.out.println("Usage: java -jar <jarfile> <host> <port> <gameId>");
            System.exit(1);
        }

        String host = args[0];
        int port = Integer.parseInt(args[1]);
        String gameId = args[2];

        SwingUtilities.invokeLater(() -> {
            TTTWindow window = new TTTWindow();
            window.connect(host, port);
            window.client.joinGame(gameId);
            window.setVisible(true);
        });
    }






}
