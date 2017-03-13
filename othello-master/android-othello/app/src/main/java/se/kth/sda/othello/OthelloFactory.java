package se.kth.sda.othello;

import se.kth.sda.othello.Othello;

/**
 * A factory for producing othello games.
 * 
 * @author Tomas Ekholm
 */
public interface OthelloFactory {

	/**
	 * Creates an Othello game with two computer.
	 * 
	 * @return An Othello game
	 */
	public Othello createComputerGame();

	/**
	 * Creates an Othello game with two humans.
	 * 
	 * @return An Othello game
	 */
	public Othello createHumanGame();

	/**
	 * Creates an Othello game with one computer playing against one human.
	 * 
	 * @return An Othello game
	 */
	public Othello createHumanVersusComputerGame();

}