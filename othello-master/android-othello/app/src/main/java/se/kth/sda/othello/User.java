package se.kth.sda.othello;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by tmpuser-10234 on 4/4/17.
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
            this.coins = 100;
            this.wins = 0;
            this.loses = 0;
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
}
