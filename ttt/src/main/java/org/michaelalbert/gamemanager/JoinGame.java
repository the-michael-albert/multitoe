/*
 * Created by JFormDesigner on Tue Dec 03 16:21:42 PST 2024
 */

package org.michaelalbert.gamemanager;

import org.michaelalbert.GUIClientRun;
import org.michaelalbert.game.TTTClient;
import org.michaelalbert.game.TTTInstance;
import org.michaelalbert.game.reqres.GameListInfoRequest;
import org.michaelalbert.game.reqres.GameTuple;
import org.michaelalbert.game.reqres.GetGameListRequest;
import org.michaelalbert.utils.GameConfig;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.GroupLayout;
import javax.swing.border.*;
import javax.swing.table.AbstractTableModel;

/**
 * @author malbert
 */
public class JoinGame extends JFrame {
    final static String serverHost = GameConfig.getGameHost();
    final static String serverPort = GameConfig.getGamePort() + "";


    public static void runGame(String gameId) {
        //in new thread run GuiClientRun.main with args
        new Thread(() -> GUIClientRun.main(new String[]{serverHost, serverPort, gameId})).start();
    }

    TTTClient client = new TTTClient(TTTInstance.X) {
        @Override
        public void log(String message) {
            System.err.println(message);
        }

        @Override
        public void GameListInfoRequestCallback(GameListInfoRequest gameListInfoRequest) {
            //update table
            table.setModel(new GameListTableModel(gameListInfoRequest.getGameList()));
        }

        @Override
        public void GameListInfoRequestCallback(boolean found) {
            //update table
            if (!found) {
                JOptionPane.showMessageDialog(JoinGame.this, "No games found; create a new game");
            }
        }

    };

    public JoinGame() {
        client.start(serverHost, Integer.parseInt(serverPort));
        initComponents();
        refreshbtn.addActionListener(e -> {
            client.queryGameList();
        });
        
        //textF

        okButton.addActionListener(e -> {
            int row = table.getSelectedRow();
            String gameId = null;
            if (!textField1.getText().isEmpty()) {
                boolean containsIllegal = textField1.getText().chars().anyMatch(c -> c == '&' || c == ',');

                if (containsIllegal) {
                    JOptionPane.showMessageDialog(this, "Game name cannot contain '&' or ','");
                } else {
                    gameId = textField1.getText();
                }
            }

            if (row != -1) {
                var curConnection = ((GameListTableModel) table.getModel()).games.get(row);
                if (curConnection.userCount == 2) {
                    JOptionPane.showMessageDialog(this, "Game is full");
                    return;
                } else {
                    gameId = curConnection.name;
                }
            }
            if (gameId != null) {
                runGame(gameId);
            } else {
                JOptionPane.showMessageDialog(this, "Please select a game or enter a game name");
            }
        });
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Educational license - Michael Albert
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        scrollPane1 = new JScrollPane();
        table = new JTable();
        label = new JLabel();
        refreshbtn = new JButton();
        label1 = new JLabel();
        textField1 = new JTextField();
        label2 = new JLabel();
        buttonBar = new JPanel();
        okButton = new JButton();

        //======== this ========
        var contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== dialogPane ========
        {
            dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
            dialogPane.setLayout(new BorderLayout());

            //======== contentPanel ========
            {

                //======== scrollPane1 ========
                {
                    scrollPane1.setViewportView(table);
                }

                //---- label ----
                label.setText("Active Games:");

                //---- refreshbtn ----
                refreshbtn.setText("refresh");
                refreshbtn.setIcon(UIManager.getIcon("FileChooser.upFolderIcon"));

                //---- label1 ----
                label1.setText("----or----");
                label1.setHorizontalAlignment(SwingConstants.CENTER);

                //---- label2 ----
                label2.setText("Create Game:");

                GroupLayout contentPanelLayout = new GroupLayout(contentPanel);
                contentPanel.setLayout(contentPanelLayout);
                contentPanelLayout.setHorizontalGroup(
                    contentPanelLayout.createParallelGroup()
                        .addGroup(contentPanelLayout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(contentPanelLayout.createParallelGroup()
                                .addComponent(label1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(scrollPane1, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 363, GroupLayout.PREFERRED_SIZE)
                                .addGroup(GroupLayout.Alignment.TRAILING, contentPanelLayout.createSequentialGroup()
                                    .addComponent(label, GroupLayout.PREFERRED_SIZE, 267, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(refreshbtn))
                                .addGroup(contentPanelLayout.createSequentialGroup()
                                    .addComponent(label2)
                                    .addGap(0, 0, Short.MAX_VALUE))
                                .addComponent(textField1, GroupLayout.Alignment.TRAILING))
                            .addContainerGap())
                );
                contentPanelLayout.setVerticalGroup(
                    contentPanelLayout.createParallelGroup()
                        .addGroup(GroupLayout.Alignment.TRAILING, contentPanelLayout.createSequentialGroup()
                            .addGap(0, 0, Short.MAX_VALUE)
                            .addGroup(contentPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(refreshbtn)
                                .addComponent(label))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 92, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(label1)
                            .addGap(2, 2, 2)
                            .addComponent(label2)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(textField1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addGap(11, 11, 11))
                );
            }
            dialogPane.add(contentPanel, BorderLayout.CENTER);

            //======== buttonBar ========
            {
                buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
                buttonBar.setLayout(new GridBagLayout());
                ((GridBagLayout)buttonBar.getLayout()).columnWidths = new int[] {0, 80};
                ((GridBagLayout)buttonBar.getLayout()).columnWeights = new double[] {1.0, 0.0};

                //---- okButton ----
                okButton.setText("Join");
                buttonBar.add(okButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            dialogPane.add(buttonBar, BorderLayout.SOUTH);
        }
        contentPane.add(dialogPane, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Educational license - Michael Albert
    private JPanel dialogPane;
    private JPanel contentPanel;
    private JScrollPane scrollPane1;
    private JTable table;
    private JLabel label;
    private JButton refreshbtn;
    private JLabel label1;
    private JTextField textField1;
    private JLabel label2;
    private JPanel buttonBar;
    private JButton okButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
class GameListTableModel extends AbstractTableModel {
    //implement from loading an arraylist of gametuples
    ArrayList<GameTuple> games = new ArrayList<>();

    public GameListTableModel(ArrayList<GameTuple> games) {
        super();
        this.games = games;
    }


    @Override
    public int getRowCount() {
        return games.size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        GameTuple game = games.get(rowIndex);
        if (columnIndex == 0) {
            return game.name;
        } else {
            return game.userCount + " users";
        }
    }
}