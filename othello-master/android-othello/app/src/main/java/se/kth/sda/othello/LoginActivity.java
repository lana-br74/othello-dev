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

    public void Login(View view){
        Intent itn1 = new Intent(this, RegActivity.class);
        startActivity(itn1);
    }
}
