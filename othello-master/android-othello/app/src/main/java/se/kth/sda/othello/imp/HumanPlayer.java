package se.kth.sda.othello.imp;

import se.kth.sda.othello.player.Player;

/**
 * Created by robertog on 2/7/17.
 */
public class HumanPlayer implements Player {
    private final String id;
    private final String name;

    public HumanPlayer(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Type getType() {
        return Type.HUMAN;
    }
}
