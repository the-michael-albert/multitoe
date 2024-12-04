package org.michaelalbert.game;

import org.michaelalbert.game.reqres.*;
import org.michaelalbert.networking.ClientHandler;
import org.michaelalbert.networking.TCPServer;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class TTTServer extends TCPServer {


    public Map<String, TTTInstance> games = new TreeMap<>();

    public TTTServer() {
        super();
    }


    private void handleJoinRequest(JoinRequest joinRequest, ClientHandler callback) {
        final String gameId = joinRequest.getGameId();
        TTTInstance game = games.get(gameId);
        if (game == null) { // first player in the game
            game = new TTTInstance();
            games.put(joinRequest.getGameId(), game);
            game.setPlayerX(callback);
            callback.sendMessage(SetRoleRequest.builder().role("X").build().toString());
            game.setPlayerCount(1);
            callback.sendMessage(BoardUpdateRequest.builder().board(game.getBoard()).build().toString());
        }  else {
            if (game.getPlayerCount() == 2) {
                callback.sendMessage(GameErrorRequest.builder().message("Game is full").build().toString());
                return;
            }
            game.setPlayerO(callback);
            callback.sendMessage(SetRoleRequest.builder().role("O").build().toString());
            game.setPlayerCount(2);
            broadcastBoardUpdate(game);
        }
    }

    private void broadcastBoardUpdate(TTTInstance game) {
        BoardUpdateRequest boardUpdateRequest = BoardUpdateRequest.builder().board(game.getBoard()).build();
        game.getPlayerX().sendMessage(boardUpdateRequest.toString());
        game.getPlayerO().sendMessage(boardUpdateRequest.toString());
    }

    private void broadcastTurnNotification(TTTInstance game) {
        TurnNotificationRequest turnNotificationRequest = TurnNotificationRequest.builder().player(game.currentPlayer).build();
        game.getPlayerX().sendMessage(turnNotificationRequest.toString());
        game.getPlayerO().sendMessage(turnNotificationRequest.toString());
    }

    private void handleMoveRequest(MoveRequest moveRequest, ClientHandler callback) {
        TTTInstance game = games.get(moveRequest.getGameId());
        if (game == null) {
            return;
        }
        final int x = moveRequest.getX();
        final int y = moveRequest.getY();
        final char player = moveRequest.getPlayer();
        if (game.getPlayerCount() < 2) {
            return;
        }
        boolean success = game.makeMove(player, x, y);
        if (success) {
            broadcastBoardUpdate(game);
            broadcastTurnNotification(game);
            if (endGameChecks(game)){
                games.remove(moveRequest.getGameId());
            }
        } else {
            callback.sendMessage(InvalidMoveRequest.builder().message(player + " attempted tictactoe moving violation").build().toString());
        }
    }

    private boolean endGameChecks(TTTInstance game){
        //check for win
        //check for draw
        char[][] board = game.getBoard();
        if (BoardAnalyzer.isGameOver(board)){
            char winner = BoardAnalyzer.getWinner(board);
            if (BoardAnalyzer.isGameDraw(board)){
                game.getPlayerX().sendMessage(GameFinishedRequest.builder().winner("draw").build().toString());
                game.getPlayerO().sendMessage(GameFinishedRequest.builder().winner("draw").build().toString());
                return true;
            }
            game.getPlayerX().sendMessage(GameFinishedRequest.builder().winner(winner + "").build().toString());
            game.getPlayerO().sendMessage(GameFinishedRequest.builder().winner(winner + "").build().toString());
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        TTTServer server = new TTTServer();

        server.addRequestHandler(JoinRequest.ACTION, new ServerRequestHandler() {
            @Override
            public void onMessage(String action, String data, String rawMessage, ClientHandler callback) {
                JoinRequest joinRequest = JoinRequest.fromString(rawMessage);
                server.handleJoinRequest(joinRequest, callback);
            }
        });

        server.addRequestHandler(MoveRequest.ACTION, new ServerRequestHandler() {
            @Override
            public void onMessage(String action, String data, String rawMessage, ClientHandler callback) {
                MoveRequest moveRequest = MoveRequest.fromString(rawMessage);
                TTTInstance game = server.games.get(moveRequest.getGameId());
                if (game == null) {
                    return;
                }
                server.handleMoveRequest(moveRequest,  callback);
            }
        });

        server.addRequestHandler(GetGameListRequest.ACTION, new ServerRequestHandler() {
            @Override
            public void onMessage(String action, String data, String rawMessage, ClientHandler callback) {
                System.out.println("GetGameListRequest");
                ArrayList<GameTuple> gameList = new ArrayList<>();
                server.games.forEach((gameId, game) -> {
                    gameList.add(new GameTuple(gameId, game.getPlayerCount()));
                });
                callback.sendMessage(GameListInfoRequest.builder().gameList(gameList).build().toString());
            }
        });


        server.start(8888);
    }

    TreeMap<String, ServerRequestHandler> clients = new TreeMap<>();

    public void addRequestHandler(String route, ServerRequestHandler requestHandler) {
        clients.put(route.toLowerCase(), requestHandler);
    }


    @Override
    public void onMessage(byte[] message, ClientHandler clientHandler) {
        // When we receive a message, broadcast it to all clients
        String receivedMessage = new String(message);
        receivedMessage = receivedMessage.trim(); // Remove leading and trailing whitespace
        receivedMessage = receivedMessage.toLowerCase(); // Convert to lowercase

        int index = receivedMessage.indexOf("#"); // Find the index of the delimiter

        if (index == -1) {
            return;
        }

        String action = receivedMessage.substring(0, index); // Extract the action
        String actualMessage = receivedMessage.substring(index + 1); // Extract the message

        if (clients.containsKey(action)) {
            clients.get(action).onMessage(action, actualMessage, receivedMessage, clientHandler);
        } else {
            System.out.println("No handler for action: " + action);
        }
    }

    @Override
    protected void onConnection(ClientHandler clientHandler) {

    }
}