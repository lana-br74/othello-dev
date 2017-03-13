package se.kth.sda.othello.board;

/**
 * The responsibility of a node is to keep information of which player is occupying it.
 * 
 * @author Tomas Ekholm
 */
public interface Node {

	/**
	 * The unique identifier of a node. A node should be identified uniquely given the x- and y-coordinate
	 * 
	 * @return the id
	 */
	public String getId();

	/**
	 * To get the player id of the occupant player
	 * 
	 * @return the id of the occupant player or null if the node is not marked
	 */
	public String getOccupantPlayerId();

	/**
	 * The x-coordinate of this node
	 * 
	 * @return the x-coordinate
	 */
	public int getXCoordinate();

	/**
	 * The y-coordinate of this node
	 * 
	 * @return the y-coordinate
	 */
	public int getYCoordinate();

	/**
	 * Determines of the node is occupied by any player
	 * 
	 * @return true if the node is occupied by any player
	 */
	public boolean isMarked();


}
