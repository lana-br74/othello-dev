package se.kth.sda.othello.service;

import static spark.Spark.*;

import java.io.InputStream;
import java.io.InputStreamReader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import spark.Request;
import spark.Response;
import spark.Route;

/**
 * This class provides the services for othello game
 * @author lana
 *
 */

public class OthelloService {
	public static void main(String[] args) {
		//final DataBaseCon dataBaseCon = new DataBaseCon();

		get("/login/:login_name", "application/json", new Route() {
			public Object handle(Request req, Response res) throws Exception {
				DataBaseCon dataBaseCon = new DataBaseCon();
				req.session(true);
				if (!(dataBaseCon.UserAccountAuthorized(req.headers("userName"),req.headers("password"))))
					halt(401, "Not authorized");
				String username = req.headers("username");
				User user = dataBaseCon.getUser(username);
				System.out.println("login");
				System.out.println(username);
				try {
					dataBaseCon.finalize();
				} catch (Throwable e) {
					e.printStackTrace();
				}
				return user;
			}
		}, new JsonTransformer());

		post("/report/:login_name", new Route() {
			public Object handle(Request req, Response res) throws Exception {
				DataBaseCon dataBaseCon = new DataBaseCon();
				if (!(dataBaseCon.UserAccountAuthorized(req.headers("userName"),req.headers("password"))))
						halt(406, "Not authorized");
			//	InputStream is = req.raw().getInputStream();
			//	Gson gb = new GsonBuilder().create();
			//	MatchResult input = gb.fromJson(new InputStreamReader(is), MatchResult.class);
			//	System.out.println(input.duration);
				String username = req.headers("userName");
				String password = req.headers("password");
				String coinsString = req.headers("coins");
				System.out.println("connect serveice");
				System.out.println(username);
				int coins = Integer.valueOf(coinsString);
				System.out.println(coins);
				int wins = Integer.valueOf(req.headers("wins"));
				System.out.println(wins);
				int loses = Integer.valueOf(req.headers("loses"));
				System.out.println(loses);
				boolean isUpdated = dataBaseCon.updateUserCoins(username,coins,wins,loses);
				System.out.println(isUpdated);
				User user = dataBaseCon.getUser(username);
				
				try {
					dataBaseCon.finalize();
				} catch (Throwable e) {
					e.printStackTrace();
				}
				return user;
			}
		}, new JsonTransformer());


		post("/register/:register_name",new Route(){

			@Override
			public Object handle(Request req, Response res) throws Exception {
				DataBaseCon dataBaseCon = new DataBaseCon();
				String username = req.headers("userName");
				String email = req.headers("email");
				String password = req.headers("password");
				String name = req.headers("name");

				boolean userExisted = dataBaseCon.userNameExisted(username);
				if(userExisted) {
					System.out.println("existed");
					halt(401,"username is existed");
				}
				dataBaseCon.registerUser(email,username,password,name);
				User user = dataBaseCon.getUser(username);
				System.out.println("register");
				try {
					dataBaseCon.finalize();
				} catch (Throwable e) {
					e.printStackTrace();
				}
				return user;
			}


		}, new JsonTransformer());
	}
}
