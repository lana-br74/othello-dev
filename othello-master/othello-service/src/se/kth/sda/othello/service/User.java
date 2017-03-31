package se.kth.sda.othello.service;

/**
 * This class represent a user of the game with all relating information. in addition to 
 * setter and getter methods , there are methods to change the user coins and number of his loses and wins.
 * @author lana
 *
 */
public class User {
	private final String userName;
	private final String password;
	private final String email;
	private String name;
	private int coins;
	private int wins;
	private int loses;

	public User(String email,String userName, String password,String name){
		this.userName = userName;
		this.password = password;
		this.email = email;
		this.name = name;
		this.coins = 100;
		this.wins = 0;
		this.loses = 0;
	}

	public String getUserName(){
		return this.userName;
	}

	public String getPassword(){
		return this.password;
	}

	public String getEmail(){
		return this.email;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return this.name;
	}

	public int getCoins(){
		return this.coins;
	}

	public void addCoins(int coinsToAdd){
		this.coins += coinsToAdd;
	}

	public void subtractCoins(int coinsToAbstract){
		this.coins -= coinsToAbstract;
	}

	public int getWins(){
		return this.wins;
	}

	public void addWins(){
		this.wins +=1;
	}

	public int getLoses(){
		return this.loses;
	}

	public void addLoses(){
		this.loses+=1;
	}
}
