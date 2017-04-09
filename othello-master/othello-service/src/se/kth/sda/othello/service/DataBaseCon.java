package se.kth.sda.othello.service;

import java.sql.*;


/**
 * This class provides the database connection and some of 
 * the required functions for Othello game, like authentication , login and
 * registration and geting the user information and updating it.
 * @author lana
 *
 */
public class DataBaseCon {

	private Connection conn = null;
	private String othelloUrl = "jdbc:hsqldb:file:/NOBACKUP/tmpuser-10234/hsqldb-2.3.4;shutdown=true";
	//private String othelloUrl = "jdbc:hsqldb:file:D:\\hsqldb-2.3.4;shutdown=true";


	public DataBaseCon(){
		try {
			Class.forName("org.hsqldb.jdbcDriver");  
			conn = DriverManager.getConnection(othelloUrl);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public DataBaseCon(String url, String user_name, String password) {
		try {
			Class.forName("org.hsqldb.jdbcDriver");  
			conn = DriverManager.getConnection(url, user_name, password);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public ResultSet runSql(String sql) throws SQLException {
		Statement sta = conn.createStatement();
		return sta.executeQuery(sql);
	}



	public Connection getCon (){
		return this.conn;
	}

	@Override
	protected void finalize() throws Throwable {
		if (conn != null && !conn.isClosed()) {
			conn.close();
		}
	}

	// This sql instrustion to create the matching table in HSqldb database
	/*
	 * CREATE TABLE PUBLIC.USERS (
	USERNAME VARCHAR(100),
	PASSWORD VARCHAR(100),
	NAME VARCHAR(100),
	EMAIL VARCHAR(100),
	COINS INTEGER,
	WINS INTEGER,
	LOSES INTEGER,
	CONSTRAINT USERS_PK PRIMARY KEY (USERNAME)
    ) ;

	 */
	public boolean userNameExisted(String username){
		//check if the user name is existed

		try {
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM USERS WHERE USERNAME = ?");
			stmt.setString(1, username);
			ResultSet rs = stmt.executeQuery();
				while(rs.next()){
				 if(rs.getString("USERNAME").equals(username))  return true;
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}

		return false;
	}


	public boolean UserAccountAuthorized(String userName,String password){
		
        if(userNameExisted(userName)){
		try {
			PreparedStatement stmt = conn.prepareStatement("SELECT PASSWORD FROM USERS WHERE USERNAME = ? ");
			stmt.setString(1, userName);
			ResultSet rs = stmt.executeQuery();
			if(rs != null) 
			{
				while(rs.next()){
					 if(rs.getString("PASSWORD").equals(password))  return true;
				}
			}
		} catch (SQLException e) {

			e.printStackTrace();
		} 
        }
		return false;
	}

	public User getUser(String username){
		User currentUser = null;
		try {
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM USERS WHERE USERNAME = ?");
			stmt.setString(1, username);

			ResultSet rs = stmt.executeQuery();
			if(rs != null) 
			{
				while(rs.next()){
					currentUser = new User(rs.getString("EMAIL"),rs.getString("USERNAME"),rs.getString("PASSWORD"),rs.getString("NAME"),rs.getInt("COINS"),rs.getInt("WINS"),rs.getInt("LOSES"));	
				}
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return currentUser;		
	}
	
	public boolean registerUser(String email,String userName, String password,String name){
		try {
			PreparedStatement stmt = conn.prepareStatement("INSERT INTO USERS VALUES (?, ?, ?, ?, ?, ?, ?)");
			stmt.setString(1, userName);
			stmt.setString(2, password);
			stmt.setString(3, name);
			stmt.setString(4, email);
			stmt.setInt(5, 100);
			stmt.setInt(6, 0);
			stmt.setInt(7, 0);
			stmt.executeUpdate();
				return true;
			
		} catch (SQLException e) {

			e.printStackTrace();
			return false;
		} 
		
	}
	
	public boolean updateUserCoins(String userName,int coins,int wins,int loses){
		try {
			PreparedStatement stmt = conn.prepareStatement("UPDATE USERS SET COINS = ?, WINS = ?, LOSES = ?  WHERE USERNAME = ?");
			stmt.setInt(1, coins);
			stmt.setInt(2, wins);
			stmt.setInt(3, loses);
			stmt.setString(4, userName);
			stmt.executeUpdate();
			System.out.println("updated");
				return true;
			
		} catch (SQLException e) {
			System.out.println(" not updated");
			e.printStackTrace();
			return false;
		} 
		
	}

}
