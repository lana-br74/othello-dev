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
	get("/login/:login_name", "application/json", new Route() {
		public Object handle(Request req, Response res) throws Exception {
		    req.session(true);
		    if (!req.headers("pwd").equals("123456"))
			halt(401, "Not authorized");

		    User roberto = new User("rob", "Roberto", "Guanciale", 42, 10);
		    return roberto;
		}
	    }, new JsonTransformer());

	post("/report/:login_name", new Route() {
		public Object handle(Request req, Response res) throws Exception {
		    if (!req.headers("pwd").equals("123456"))
			halt(401, "Not authorized");

		    InputStream is = req.raw().getInputStream();

		    Gson gb = new GsonBuilder().create();
		    MatchResult input = gb.fromJson(new InputStreamReader(is), MatchResult.class);
		    System.out.println(input.duration);
		    User roberto = new User("rob", "Roberto", "Guanciale", 55, 10);
		    return roberto;
		}
	    }, new JsonTransformer());
    }
}
