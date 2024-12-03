package org.michaelalbert.game;

import lombok.Getter;
import lombok.Setter;
import org.michaelalbert.game.reqres.*;
import org.michaelalbert.networking.TCPClient;

import java.util.Scanner;

public abstract class TTTClient extends TCPClient {

    public char role;
    public String gameId;

    @Getter
    @Setter
    private char[][] board = new char[3][3];

    public void sendMessage(String message) {
        super.sendMessage(message.getBytes());
    }

    public TTTClient(char role) {
        this.role = role;
    }

    public void joinGame(String game) {
        // Send join request
        this.gameId = game;
        sendMessage(JoinRequest.builder().gameId(game).build().toString());
    }

    public void makeMove(int x, int y) {
        // Send move request
        sendMessage(MoveRequest.builder().gameId(gameId).player(role).x(x).y(y).build().toString());
    }


    public static void main(String[] args) throws InterruptedException {
        // Create a new client
        TTTClient client = new TTTClient(TTTInstance.X) {
            @Override
            public void log(String message) {
                System.out.println(message);
            }
        };
        // Start the client
        client.start("localhost", 8888);

        client.joinGame("game69");

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Role: " + client.role);

            System.out.println("Enter x and y coordinates: ");
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            client.makeMove(x, y);
        }
    }

    public abstract void log(String message);

    public void updateBoard(char[][] board) {
        this.board = board;
    }

    @Override
    public void onMessage(byte[] message) {
        // Print received messages to console
        String receivedMessage = new String(message);
        receivedMessage = receivedMessage.trim(); // Remove leading and trailing whitespace
        receivedMessage = receivedMessage.toLowerCase(); // Convert to lowercase

        log(receivedMessage);
        int index = receivedMessage.indexOf("#"); // Find the index of the delimiter
        if (index == -1) {
            return;
        }
        String action = receivedMessage.substring(0, index); // Extract the action

        if (SetRoleRequest.ACTION.equals(action)) {
            SetRoleRequest setRoleRequest = SetRoleRequest.fromString(receivedMessage);
            SetRoleRequestCallback(setRoleRequest);
        } if (BoardUpdateRequest.ACTION.equals(action)) {
            BoardUpdateRequest boardUpdateRequest = BoardUpdateRequest.fromString(receivedMessage);
            BoardUpdateRequestCallback(boardUpdateRequest);
        } if (InvalidMoveRequest.ACTION.equals(action)) {
            InvalidMoveRequest invalidMoveRequest = InvalidMoveRequest.fromString(receivedMessage);
            InvalidMoveRequestCallback(invalidMoveRequest);
        } if (TurnNotificationRequest.ACTION.equals(action)) {
            TurnNotificationRequest turnNotificationRequest = TurnNotificationRequest.fromString(receivedMessage);
            TurnNotificationRequestCallback(turnNotificationRequest);
        } if (GameErrorRequest.ACTION.equals(action)) {
            GameErrorRequest gameErrorRequest = GameErrorRequest.fromString(receivedMessage);
            GameErrorRequestCallback(gameErrorRequest);
        } if (GameFinishedRequest.ACTION.equals(action)) {
            GameFinishedRequest gameFinishedRequest = GameFinishedRequest.fromString(receivedMessage);
            GameFinishedRequestCallback(gameFinishedRequest);
        }
    }

    public void GameFinishedRequestCallback(GameFinishedRequest gameFinishedRequest) {
        log(gameFinishedRequest.getWinner());
    }

    public void GameErrorRequestCallback(GameErrorRequest gameErrorRequest) {
        log(gameErrorRequest.getMessage());
    }

    public void TurnNotificationRequestCallback(TurnNotificationRequest turnNotificationRequest) {
        log(turnNotificationRequest.getPlayer() + "'s turn");
    }

    public void InvalidMoveRequestCallback(InvalidMoveRequest invalidMoveRequest) {
        log(invalidMoveRequest.getMessage());
    }

    public void BoardUpdateRequestCallback(BoardUpdateRequest boardUpdateRequest) {
        updateBoard(boardUpdateRequest.getBoard());
    }

    public void SetRoleRequestCallback(SetRoleRequest setRoleRequest) {
        acceptRole(setRoleRequest);
    }

    private void acceptRole(SetRoleRequest setRoleRequest) {
        if (setRoleRequest.getRole().equals(TTTInstance.X + "")) {
            role = TTTInstance.X;
        } else {
            role = TTTInstance.O;
        }
    }
}