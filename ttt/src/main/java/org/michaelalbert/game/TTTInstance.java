package org.michaelalbert.game;

import lombok.Getter;
import lombok.Setter;
import org.michaelalbert.networking.ClientHandler;

@Getter
@Setter
public class TTTInstance {

    public static final char X = 'x';
    public static final char O = 'o';

    public char[][] board = new char[3][3];
    public char currentPlayer = TTTInstance.X;

    int playerCount = 0;

    public TTTInstance() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = '?';
            }
        }
    }

    public boolean makeMove(char player, int x, int y) {
        if (player != currentPlayer) {
            return false;
        }
        if (board[x][y] == '?') {
            board[x][y] = currentPlayer;
            currentPlayer = currentPlayer == TTTInstance.X ? TTTInstance.O : TTTInstance.X;
            return true;
        }
        return false;
    }

    ClientHandler playerX;
    ClientHandler playerO;
}
