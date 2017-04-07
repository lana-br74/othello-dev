package se.kth.sda.othello;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.ArrayMap;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.Map;

import se.kth.sda.othello.imp.OthelloFactoryImp;

/**
 * Created by Samer Obid on 2017-03-24.
 */

public class RegActivity extends AppCompatActivity {
    public static final String GAME_TYPE = "GAME_TYPE";
    public static final String GAME_HUMAN = "HUMAN";
    public static final String GAME_RESULT = "GAME_RESULT";



    LoginButton loginButton;
    CallbackManager callbackManager;


    private void initailizeControls (){
        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.login_button);

    }


    OthelloFactory gameFactory = new OthelloFactoryImp();
    Othello game;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        initailizeControls();
        loginWithFB();
    }
    private void loginWithFB (){
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }



    public void reg (View v) {

        Intent itn = new Intent(this,RegistrationActivity.class);
        startActivity(itn);


    }

    public void login(View view) {
        EditText inputLogin = (EditText) findViewById(R.id.edit_login);
        String login = inputLogin.getText().toString();
        EditText inputPwd = (EditText) findViewById(R.id.edit_pwd);
        String pwd = inputPwd.getText().toString();

        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://10.0.2.2:4567/login/" + login;

        final Map<String, String> mHeaders = new ArrayMap<String, String>();
        mHeaders.put("userName",login);
        mHeaders.put("password", pwd);

        // Request a string response from the provided URL.
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Toast.makeText(getBaseContext(), "Welcome "+response.getString("name"), Toast.LENGTH_LONG).show();
                            User user = new User(response);
                            Intent intent = new Intent(getBaseContext(), MenuActivity.class);
                            intent.putExtra("player",response.toString());
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
            public Map<String, String> getHeaders(){

                return mHeaders;
            }
        };

        queue.add(stringRequest);
    }
}
