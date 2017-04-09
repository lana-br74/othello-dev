package se.kth.sda.othello;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class is responsible for holding the user information that comes from the server in json format, and
 * performing different opeaation like adding or subtracting coins. and viceversa.
 * @author lana.
 */

public class User {
    private  String userName;
    private  String password;
    private  String email;
    private String name;
    private int coins;
    private int wins;
    private int loses;

    public User(JSONObject jsonObject) {
        try {
            this.userName = jsonObject.getString("userName");
            this.password = jsonObject.getString("password");
            this.email = jsonObject.getString("email");
            this.name = jsonObject.getString("name");
            this.coins = jsonObject.getInt("coins");
            this.wins = jsonObject.getInt("wins");
            this.loses = jsonObject.getInt("loses");
        }catch(Exception e){

        }
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

    public JSONObject toJson(){
        JSONObject jsonObj = new JSONObject();
            try {
                jsonObj.put("userName", this.getUserName());
                jsonObj.put("password", this.getPassword());
                jsonObj.put("email", this.getEmail());
                jsonObj.put("name", this.getName());
                jsonObj.put("coins", this.getCoins());
                jsonObj.put("wins", this.getWins());
                jsonObj.put("loses", this.getLoses());
            } catch (JSONException e) {
                e.printStackTrace();
            }

        return jsonObj;
    }
}
