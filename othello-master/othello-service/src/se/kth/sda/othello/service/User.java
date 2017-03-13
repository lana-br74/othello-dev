package se.kth.sda.othello.service;

public class User {
    public String login;
    public String name;
    public String surname;
    public int wins;
    public int loses;

    public User(String login, String name, String surname, int wins, int loses) {
	this.login = login;
	this.name = name;
	this.surname = surname;
	this.wins = wins;
	this.loses = loses;
    }
}
