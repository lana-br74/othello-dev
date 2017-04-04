package se.kth.sda.othello.service;

import static spark.Spark.*;

import java.io.InputStream;
import java.io.InputStreamReader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import spark.Request;
import spark.Response;
import spark.Route;

public class OthelloService {
	public static void main(String[] args) {
		final UserData data = new UserData();

		get("/login/:login_name", "application/json", new Route() {
			public Object handle(Request req, Response res) throws Exception {
				req.session(true);
				if (!(data.UserAccountAuthorized(req.headers("userName"),req.headers("password"))))
					halt(401, "Not authorized");

				String username = req.headers("username");
				String email = req.headers("email");
				String password = req.headers("password");
				String name = req.headers("name");
				User user = new User(email,username,password,name);
				data.registerUser(email,username,password,name);
				return user;
			}
		}, new JsonTransformer());

		post("/report/:login_name", new Route() {
			public Object handle(Request req, Response res) throws Exception {
				if (data.UserAccountAuthorized(req.headers("userName"),req.headers("password")))
					halt(406, "Not authorized");
				InputStream is = req.raw().getInputStream();

				Gson gb = new GsonBuilder().create();
				MatchResult input = gb.fromJson(new InputStreamReader(is), MatchResult.class);
				System.out.println(input.duration);
				User roberto = new User("rob", "Roberto", "Guanciale", "roberto");
				return roberto;
			}
		}, new JsonTransformer());


		post("/register/:register_name",new Route(){

			@Override
			public Object handle(Request req, Response res) throws Exception {

				String username = req.headers("username");
				String email = req.headers("email");
				String password = req.headers("password");
				String name = req.headers("name");

				boolean userExisted = data.userNameExisted(username);
				if(userExisted) {
					System.out.println("existed");
					halt(401,"username is existed");
				}

				User user = new User(email,username,password,name);
				data.registerUser(email,username,password,name);

				return user;
			}


		}, new JsonTransformer());
	}
}
