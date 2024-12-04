package org.michaelalbert.game.reqres;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import static org.michaelalbert.game.BoardAnalyzer.getCorrectedBoard;

@Builder
@Getter
@Setter
public class BoardUpdateRequest {
    public static final String ACTION = "boardupdate";
    private char[][] board;

    public BoardUpdateRequest(char[][] board) {
        this.board = board;
    }

    public static BoardUpdateRequest fromString(String message) {
        String[] parts = message.split("#");
        String[] rows = parts[1].split(";");
        char[][] board = new char[rows.length][rows[0].length()];
        for (int i = 0; i < rows.length; i++) {
            board[i] = rows[i].toCharArray();
        }
        return new BoardUpdateRequest(board);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(ACTION).append("#");
        for (int i = 0; i < board.length; i++) {
            sb.append(board[i]);
            if (i < board.length - 1) {
                sb.append(";");
            }
        }
        return sb.toString();
    }

    public void printBoard() {
        char[][] board = getCorrectedBoard(this.board);
        String output =
                "╔═══╦═══╦═══╗\n" +
                "║ " + board[0][0] + " ║ " + board[0][1] + " ║ " + board[0][2] + " ║\n" +
                "╠═══╬═══╬═══╣\n" +
                "║ " + board[1][0] + " ║ " + board[1][1] + " ║ " + board[1][2] + " ║\n" +
                "╠═══╬═══╬═══╣\n" +
                "║ " + board[2][0] + " ║ " + board[2][1] + " ║ " + board[2][2] + " ║\n" +
                "╚═══╩═══╩═══╝";

        System.out.println(output);
    }
}
