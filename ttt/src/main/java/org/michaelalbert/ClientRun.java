package org.michaelalbert;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import org.michaelalbert.gamemanager.JoinGame;

import javax.swing.*;

public class ClientRun {
    public static void main(String[] args) {
        //set look and feel to the system look and feel
        try {
            UIManager.setLookAndFeel(FlatMacDarkLaf.class.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }


        SwingUtilities.invokeLater(() -> {
            JoinGame joinGame = new JoinGame();
            joinGame.setVisible(true);
        });
    }
}
