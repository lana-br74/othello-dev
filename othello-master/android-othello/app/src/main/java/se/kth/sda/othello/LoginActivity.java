package se.kth.sda.othello;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.ArrayMap;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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

import se.kth.sda.othello.imp.OthelloFactoryImp;
import se.kth.sda.othello.R;

public class LoginActivity extends Activity {
    public static final String GAME_TYPE = "GAME_TYPE";
    public static final String GAME_HUMAN = "HUMAN";
    public static final String GAME_RESULT = "GAME_RESULT";

    OthelloFactory gameFactory = new OthelloFactoryImp();
    Othello game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
    public void Start(View v) {

        Intent itn = new Intent(this, MenuActivity.class);
        startActivity(itn);
    }

    public void login(View view) {
        EditText inputLogin = (EditText) findViewById(R.id.edit_login);
        String login = inputLogin.getText().toString();
        EditText inputPwd = (EditText) findViewById(R.id.edit_pwd);
        String pwd = inputPwd.getText().toString();

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://10.0.2.2:4567/login/" + login;

        final Map<String, String> mHeaders = new ArrayMap<String, String>();
        mHeaders.put("pwd", pwd);

        // Request a string response from the provided URL.
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Toast.makeText(getBaseContext(), response.getString("wins"), Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getBaseContext(), MenuActivity.class);
                            startActivityForResult(intent, 0);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getBaseContext(), "login failed!", Toast.LENGTH_LONG).show();
            }
        }) {
            public Map<String, String> getHeaders() {
                return mHeaders;
            }
        };

        queue.add(stringRequest);
    }
}

