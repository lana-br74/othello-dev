package se.kth.sda.othello.player;

/**
 * A representation of player.
 *
 * @author Tomas Ekholm
 */
public interface Player {

	/**
	 * The different type of {@link Player}s
	 */
	public enum Type {
		COMPUTER, HUMAN
	}

	/**
	 * The id is a unique identifier of this player in the context of all players
	 * 
	 * @return the id
	 */
	public String getId();

	/**
	 * The name of the player
	 * 
	 * @return the name
	 */
	public String getName();

	/**
	 * The {@link Type} of the player
	 * 
	 * @return the type
	 */
	public Type getType();

}
