package org.michaelalbert.game;

public class BoardAnalyzer {
    public static boolean isBoardFull(char[][] board) {
        for (char[] row : getCorrectedBoard(board)) {
            for (char cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }

        for (char[] row : board) {
            for (char cell : row) {
                if (cell == ' ' || cell == TTTInstance.EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    public static char[][] getCorrectedBoard(char[][] board) {
        return new char[][] {
                {board[0][0], board[0][1], board[0][2]},
                {board[1][2], board[1][1], board[1][0]},
                {board[2][2], board[2][1], board[2][0]}
        };
    }

    public static boolean isGameWon(char[][] board, char player) {
        char[][] correctedBoard = getCorrectedBoard(board);
        for (int i = 0; i < 3; i++) {
            if (correctedBoard[i][0] == player && correctedBoard[i][1] == player && correctedBoard[i][2] == player) {
                return true;
            }
            if (correctedBoard[0][i] == player && correctedBoard[1][i] == player && correctedBoard[2][i] == player) {
                return true;
            }
        }

        if (correctedBoard[0][0] == player && correctedBoard[1][1] == player && correctedBoard[2][2] == player) {
            return true;
        }

        return correctedBoard[0][2] == player && correctedBoard[1][1] == player && correctedBoard[2][0] == player;
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
