package se.kth.sda.othello;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
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
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;


public class RegistrationActivity extends AppCompatActivity {

    LoginButton loginButton;
    CallbackManager callbackManager;


    private void initailizeControls (){
        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.login_button);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
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

    public void logIn(View view){
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
    }

    public void registerUser(View view){

        EditText firstnameView = (EditText) findViewById(R.id.edtfirstname);
        String firstName = firstnameView.getText().toString();

        EditText usernameView = (EditText) findViewById(R.id.edtUsername);
        String userName = usernameView.getText().toString();

        EditText inputPwd = (EditText) findViewById(R.id.edtPass);
        String password = inputPwd.getText().toString();


        EditText emailView = (EditText) findViewById(R.id.edtEmail);
        String email = emailView.getText().toString();

        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://10.0.2.2:4567/register/"+ userName;

        final Map<String, String> mHeaders = new ArrayMap<String, String>();

        mHeaders.put("name",firstName);
        mHeaders.put("userName",userName);
        mHeaders.put("password", password);
        mHeaders.put("email",email);

        // Request a string response from the provided URL.
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Toast.makeText(getBaseContext(), "Welcome .."+ response.getString("name"), Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getBaseContext(), MenuActivity.class);
                            startActivityForResult(intent, 0);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getBaseContext(), "Registration  failed! "+ error.networkResponse.statusCode, Toast.LENGTH_LONG).show();


            }
        }) {
            public Map<String, String> getHeaders(){

                return mHeaders;
            }
        };

        queue.add(stringRequest);
    }


    }


