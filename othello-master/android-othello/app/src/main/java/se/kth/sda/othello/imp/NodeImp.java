package se.kth.sda.othello.imp;

import se.kth.sda.othello.board.Node;

/**
 * Created by robertog on 2/7/17.
 */
public class NodeImp implements Node {
    private final int x;
    private final int y;
    private String playerId;

    public NodeImp(int x, int y) {
        this.x = x;
        this.y = y;
        playerId = null;
    }

    public NodeImp(String nodeId, String playerId) {
        this.x = getXCoordinate(nodeId);
        this.y = getYCoordinate(nodeId);
        this.playerId = playerId;
    }

    @Override
    public String getId() {
        return format(x, y);
    }
    
    @Override
    public String getOccupantPlayerId() {
        return this.playerId;
    }

    @Override
    public int getXCoordinate() {
        return x;
    }
    @Override
    public int getYCoordinate() {
        return y;
    }
    @Override
    public boolean isMarked() {
        return (playerId != null);
    }

    private static int getXCoordinate(String nodeId) {
        return Integer.valueOf(nodeId.split(",")[0]);
    }
    private static int getYCoordinate(String nodeId) {
        return Integer.valueOf(nodeId.split(",")[1]);
    }

    public static String format(int x, int y) {
        return String.format("%d,%d", x, y);
    }
}
