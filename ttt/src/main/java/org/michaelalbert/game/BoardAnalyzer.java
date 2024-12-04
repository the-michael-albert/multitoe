package org.michaelalbert.game;

public class BoardAnalyzer {
    public static boolean isBoardFull(char[][] board) {
        for (char[] row : board) {
            for (char cell : row) {
                if (cell == ' ' || cell == TTTInstance.EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean isGameWon(char[][] board, char player) {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == player && board[i][1] == player && board[i][2] == player) {
                return true;
            }
            if (board[0][i] == player && board[1][i] == player && board[2][i] == player) {
                return true;
            }
        }
        if (board[0][0] == player && board[1][1] == player && board[2][2] == player) {
            return true;
        }
        if (board[0][2] == player && board[1][1] == player && board[2][0] == player) {
            return true;
        }
        return false;
    }

    public static boolean isGameDraw(char[][] board) {
        return isBoardFull(board) && !isGameWon(board, TTTInstance.X) && !isGameWon(board, TTTInstance.O);
    }

    public static boolean isGameOver(char[][] board) {
        return isGameWon(board, TTTInstance.X) || isGameWon(board, TTTInstance.O) || isGameDraw(board);
    }

    public static char getWinner(char[][] board) {
        if (isGameWon(board, TTTInstance.X)) {
            return TTTInstance.X;
        } else if (isGameWon(board, TTTInstance.O)) {
            return TTTInstance.O;
        } else {
            return TTTInstance.EMPTY;
        }
    }

}
