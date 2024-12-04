package org.michaelalbert;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import org.michaelalbert.game.TTTWindow;

import javax.swing.*;

public class GUIClientRun {

    public static void main(String[] args) {
        //expect args[0] to be the host and args[1] to be the port
        if (args.length != 3) {
            System.out.println("Usage: java -jar <jarfile> <host> <port> <gameId>");
            System.exit(1);
        }

        String host = args[0];
        int port = Integer.parseInt(args[1]);
        String gameId = args[2];

        //set look and feel to the system look and feel
        try {
            UIManager.setLookAndFeel(FlatMacDarkLaf.class.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            TTTWindow window = new TTTWindow();
            window.connect(host, port);
            window.client.joinGame(gameId);
            window.setVisible(true);
        });
    }
}
