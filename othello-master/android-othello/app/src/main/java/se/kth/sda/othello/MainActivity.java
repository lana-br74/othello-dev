package se.kth.sda.othello;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.ArrayMap;
import android.view.View;
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

public class MainActivity extends Activity {
    public static final String GAME_TYPE = "GAME_TYPE";
    public static final String GAME_HUMAN = "HUMAN";
    public static final String GAME_RESULT = "GAME_RESULT";

    OthelloFactory gameFactory = new OthelloFactoryImp();
    Othello game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final BoardView boardView = (BoardView) findViewById(R.id.boardView);

        if (this.getIntent().getExtras().getString(GAME_TYPE).equals(GAME_HUMAN)) {
            game = gameFactory.createHumanGame();
        }

        game.start();

        boardView.setModel(game.getBoard());
        boardView.setEventListener(new BoardView.BoardViewListener() {
            @Override
            public void onClick(int x, int y) {
                String nodeId = NodeImp.format(x,y);
                game.move(game.getPlayerInTurn().getId(), nodeId);
                boardView.invalidate();
            }
        });
    }

    public void quitGame(View view) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://130.237.224.92:4567/report/" + "roberto";

        final Map<String, String> mHeaders = new ArrayMap<String, String>();
        mHeaders.put("pwd", "123456");

        final JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("winner", "P1");
            jsonBody.put("duration", "12");
            jsonBody.put("moves", "108");
        } catch (JSONException e) {
            e.printStackTrace();
        }


        // Request a string response from the provided URL.
        JsonObjectRequest stringRequest = new JsonObjectRequest(
                Request.Method.POST, url,
                jsonBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String str = response.toString();

                        try {
                            Toast.makeText(getBaseContext(), response.getString("wins"), Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Intent intent = new Intent(getBaseContext(), MenuActivity.class);
                        startActivityForResult(intent, 0);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getBaseContext(), "login failed!", Toast.LENGTH_LONG).show();
            }
        }) {
            public Map<String, String> getHeaders(){
                return mHeaders;
            }
        };

        queue.add(stringRequest);

        Intent intent = new Intent(this, MenuActivity.class);
        intent.putExtra(GAME_RESULT, "P1");
        setResult(RESULT_OK, intent);
        super.finish();
    }
}
