package se.kth.sda.othello.imp;

import java.util.List;
import java.util.Vector;

import se.kth.sda.othello.Othello;
import se.kth.sda.othello.board.Board;
import se.kth.sda.othello.board.Node;
import se.kth.sda.othello.player.Player;

/**
 * Created by robertog on 2/7/17.
 */
public class OthelloImp implements Othello {
    private final Player playerOne;
    private final Player playerTwo;
    private final BoardImp board;
    private Player currentPlayer;

    public OthelloImp(Player one, Player two) {
        this.playerOne = one;
        this.playerTwo = two;
        this.board = new BoardImp();
    }

    @Override
    public Board getBoard() {
        return board;
    }

    @Override
    public List<Node> getNodesToSwap(String playerId, String nodeId) {
        return new Vector<Node>();
    }

    @Override
    public Player getPlayerInTurn() {
        return this.currentPlayer;
    }

    @Override
    public List<Player> getPlayers() {
        List<Player> res = new Vector<Player>();
        res.add(playerOne);
        res.add(playerTwo);
        return res;
    }

    @Override
    public boolean hasValidMove(String playerId) {
        return true;
    }

    @Override
    public boolean isActive() {
        return true;
    }

    @Override
    public boolean isMoveValid(String playerId, String nodeId) {
        return true;
    }

    private void swapPlayer() {
        if (currentPlayer == playerOne)
            currentPlayer = playerTwo;
        else
            currentPlayer = playerOne;
    }

    @Override
    public List<Node> move() {
        if (currentPlayer.getType() != Player.Type.COMPUTER)
            throw new IllegalStateException("Current player is not a computer");

        swapPlayer();

        return new Vector<Node>();
    }

    @Override
    public List<Node> move(String playerId, String nodeId) throws IllegalStateException {
        if (currentPlayer.getType() != Player.Type.HUMAN)
            throw new IllegalStateException("Current player is not a human");
        if (! isMoveValid(playerId, nodeId))
            throw new IllegalStateException("Invalid move");
        if (playerId != currentPlayer.getId())
            throw new IllegalStateException("Invalid player ID");

        List<Node> res = new Vector<Node>();
        Node newNode = new NodeImp(nodeId, playerId);
        board.setNode(newNode);
        res.add(newNode);

        swapPlayer();

        return res;
    }

    @Override
    public void start() {
        // TODO: choose a random player
        currentPlayer = playerOne;
    }

    @Override
    public void start(String playerId) {
        // TODO: choose the player with the right ID
        currentPlayer = playerOne;
    }
}
