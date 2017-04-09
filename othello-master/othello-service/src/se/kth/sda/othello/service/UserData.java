package se.kth.sda.othello.service;

import java.util.HashMap;

/**
 * A class to deal with the users data , the class simulates the data base and the required functions.
 * This class is responsible of reading and writing the data from the database, 
 * methods like create new user and check if a user is authorized are included as well. 
 * @author lana
 *
 */
public class UserData {

	// temporary hash map to hold the data in the data base.
	HashMap<String,User> data = new HashMap<>();


	public UserData(){
		addSomeData();
	}


	/**
	 * A method to register new user
	 * @param email
	 * @param userName
	 * @param password
	 * @param name
	 */
	public void registerUser(String email,String userName, String password,String name){
		User userAccount = new User(email,userName,password,name);
		//connect the data base and enter the new user data.
		data.put(userName, userAccount);
	}

	public boolean userNameExisted(String userName){
		//check if the user name is existed

		for(String username:data.keySet()){
			if(username.equals(userName))
				return true;
		}
		return false;
	}

	/**
	 * A method to check if the user existed in the data base
	 * @param userName
	 * @param passWord
	 * @return
	 */
	public boolean UserAccountAuthorized(String userName,String passWord){

		User userAccount;
		for(String user:data.keySet()){

			if(user.equals(userName)){
				userAccount = data.get(user);
				if (userAccount.getPassword().equals(passWord)){
					return true;
				}

			}
		}

		return false;
	}
	
	public User getUser(String username){
       User user = data.get(username);
		return user;		
	}

	public void addSomeData(){
		registerUser("lana@hotmail.com","lana","1234","lanaBar");
		registerUser("maryam@hotmail.com","maryam","1234","maryam");
		registerUser("payal@hotmail.com","payal","1234","payal");
	}

}
