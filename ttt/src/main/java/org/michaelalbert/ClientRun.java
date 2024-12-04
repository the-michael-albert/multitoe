package org.michaelalbert;

import org.michaelalbert.gamemanager.JoinGame;
import com.formdev.flatlaf.FlatDarkLaf;

import javax.swing.*;

public class ClientRun {
    public static void main(String[] args) {
        //set look and feel to the system look and feel
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }


        SwingUtilities.invokeLater(() -> {
            JoinGame joinGame = new JoinGame();
            joinGame.setVisible(true);
        });
    }
}
