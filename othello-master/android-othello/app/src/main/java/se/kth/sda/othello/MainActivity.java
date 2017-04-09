package se.kth.sda.othello;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.ArrayMap;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import se.kth.sda.othello.imp.NodeImp;
import se.kth.sda.othello.imp.OthelloFactoryImp;
import se.kth.sda.othello.R;
import se.kth.sda.othello.imp.Statistic;

public class MainActivity extends Activity {
    public static final String GAME_TYPE = "GAME_TYPE";
    public static final String GAME_HUMAN = "HUMAN";
    public static final String GAME_RESULT = "GAME_RESULT";

    User user;
    TextView score1 ;
    TextView score2 ;
    TextView player1;
    TextView player2;
    BoardView boardView;
    Statistic statistic ;

    OthelloFactory gameFactory = new OthelloFactoryImp();
    Othello game;
    private MessageDialog msgDialog;
    boolean userIsLoggedIn = false;
    TextView turn;
    JSONObject jsonPlayer = null;

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // @suthor lana
        // get the json information from the server.and display it.
        Intent currentintent = getIntent();
        Bundle extras = currentintent.getExtras();
        if (extras != null) {
            if (extras.containsKey("player")) {
                userIsLoggedIn = true;
                String jsonString = currentintent.getStringExtra("player");
                try {
                    jsonPlayer = new JSONObject(jsonString);
                    user = new User(jsonPlayer);
                }catch(Exception e){

                }
            }
        }

         boardView = (BoardView) findViewById(R.id.boardView);
        score1 = (TextView)findViewById(R.id.score1);
        score2 = (TextView)findViewById(R.id.score2);
        player1 = (TextView)findViewById(R.id.player1);
        String name ="";
        if (userIsLoggedIn) {
            name = user.getName();
            player1.setText("" + name);
            }
        player2 = (TextView)findViewById(R.id.player2);
        // Font path
        String fontPath = "fonts/CelestiaMedium-v1.51.ttf";
        String fontPath1 = "fonts/Pacifico.ttf";
        String fontPath2 = "fonts/Capture_it.ttf";
        String fontPath3 = "fonts/FFF_Tusj.ttf";
        String fontPath4 = "fonts/SEASRN__.ttf";

        // Loading Font Face
        Typeface tf = Typeface.createFromAsset(getAssets(), fontPath3);
        //Typeface tf2 = Typeface.createFromAsset(getAssets(), fontPath3);

        // Applying font
        player1.setTypeface(tf);
        player2.setTypeface(tf);
        score1.setTypeface(tf);
        score2.setTypeface(tf);


        if (this.getIntent().getExtras().getString(GAME_TYPE).equals(GAME_HUMAN)) {
            game = gameFactory.createHumanGame();
        }

        game.start();

        //Show the initial player's score.
        score1.setText(" "+2);
        score2.setText(" "+2);

        //Show the initial player's turn.
        player1.setTextColor(Color.WHITE);
        player1.setBackgroundColor(Color.BLUE);
        player2.setTextColor(Color.BLACK);


        boardView.setModel(game.getBoard());
        boardView.setEventListener(new BoardView.BoardViewListener() {
            @Override
            public void onClick(int x, int y) {
                String nodeId = NodeImp.format(x, y);
                try { //changes for othello

                        game.move(game.getPlayerInTurn().getId(), nodeId);
                        boardView.invalidate();
                        showScores();
                        showTurn();
                     if(!game.isActive()){
                        statistic = boardView.analyse();
                        gameOver(statistic.getP1Discs() - statistic.getP2Discs());}


                } catch (IllegalStateException e) {
                    System.out.println("Invalid move");
                }
            }
        });
    }


    /** This method is responsible of quiting the game.
     * @author lana
     * @param view
     */
    public void quitGame(View view) {
        if (userIsLoggedIn) {
            updateGameResults();}
        // to be continued
        Intent intent = new Intent(this, MenuActivity.class);
        intent.putExtra(GAME_RESULT, "P1");
        setResult(RESULT_OK, intent);
        super.finish();
    }

    /**
     * @author Shuai
     * Show the scores in the Main GUI
     */
    public void showScores(){
        statistic = boardView.analyse();
        score1.setText(" "+statistic.getP1Discs());
        score2.setText(" "+statistic.getP2Discs());
    }

    /**
     * @author Shuai
     *Show turn of the player
     */
    public void showTurn(){
        String r = game.getPlayerInTurn().getId() ;
        if (r.equals("P1"))
        {   player1.setTextColor(Color.WHITE);
            player1.setBackgroundColor(Color.BLUE);
            player2.setTextColor(Color.BLACK);
            player2.setBackgroundColor(Color.WHITE);
        }
        else
        {    player2.setTextColor(Color.WHITE);
             player2.setBackgroundColor(Color.BLUE);
             player1.setTextColor(Color.BLACK);
             player1.setBackgroundColor(Color.WHITE);

        }

    }

    /**
     * @author Shuai
     * * Show the details of game over
     * @author lana ,
     * changing the display and update the coins in case the user is logged in.
     * @param winOrLoseOrDraw  player1's discs subtract player2's discs
     */
    private void gameOver(int winOrLoseOrDraw){

        String msg = "";
        String score = ""+ statistic.getP1Discs()+ "  VS  " + statistic.getP2Discs();
        if(winOrLoseOrDraw > 0){
            if(userIsLoggedIn) {
                user.addCoins(50);
                user.addWins();
                msg = user.getName() +" win";
                msg += "\n" +"congratulations . You got 50 coins extra!!! ";
            }else {
                msg = "Player1 win";
            }
        }else if(winOrLoseOrDraw == 0){
            if(userIsLoggedIn) {
                user.addCoins(25);
                msg =  " draw";
                msg += "\n" +user.getName()+" congratulations . You got 25 coins extra!!! ";
            }else {
                msg = "draw";
            }
        }else if(winOrLoseOrDraw < 0) {

            if (userIsLoggedIn) {
                user.subtractCoins(50);
                user.addLoses();
                msg += "\n" + user.getName() + " bad luck . You lost 50 coins !!! ";
            } else {
                msg = "Player2 win";
            }
        }
        if (userIsLoggedIn) {
            updateGameResults();}
        msgDialog = new MessageDialog(MainActivity.this, msg,score,userIsLoggedIn,user);
        msgDialog.show();
    }


    /**
     * This method is responsible of sending the results to the server to update the player information.
     * @author lana
     */
    public void updateGameResults(){
        String username = user.getUserName();
        String pwd = user.getPassword();
        int coins = user.getCoins();
        int wins = user.getWins();
        int loses = user.getLoses();
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://10.0.2.2:4567/report/"+username;

        final Map<String, String> mHeaders = new ArrayMap<String, String>();
        mHeaders.put("userName",username);
        mHeaders.put("password", pwd);
        mHeaders.put("coins", ""+coins);
        mHeaders.put("wins", ""+wins);
        mHeaders.put("loses", ""+loses);

        // Request a string response from the provided URL.
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Toast.makeText(getBaseContext(), "Results for "+response.getString("name")+"is upated", Toast.LENGTH_LONG).show();
                             user = new User(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getBaseContext(), "update failed!", Toast.LENGTH_LONG).show();
            }
        }) {
            public Map<String, String> getHeaders(){

                return mHeaders;
            }
        };

        queue.add(stringRequest);
    }
}







