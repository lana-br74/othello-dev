package se.kth.sda.othello;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);


    }

 public void logIn(View view){
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
    }

    public void registerUser(View view) {

        EditText firstnameView = (EditText) findViewById(R.id.edtfirstname);
        String firstName = firstnameView.getText().toString();

        EditText usernameView = (EditText) findViewById(R.id.edtUsername);
        String userName = usernameView.getText().toString();

        EditText inputPwd = (EditText) findViewById(R.id.edtPass);
        String password = inputPwd.getText().toString();

        EditText inputPwd1 = (EditText) findViewById(R.id.edtConfirmPass);
        String password1 = inputPwd1.getText().toString();

        EditText emailView = (EditText) findViewById(R.id.edtEmail);
        String email = emailView.getText().toString();

        // Validation.
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if(firstName.isEmpty()| userName.isEmpty()|password.isEmpty()|password1.isEmpty()|email.isEmpty()){
            Toast.makeText(getBaseContext(), "Please fill up all the fields", Toast.LENGTH_LONG).show();}
        else if (!password.equals(password1)) {
            Toast.makeText(getBaseContext(), "Not identical Password", Toast.LENGTH_LONG).show();}
        else if (!email.matches(emailPattern)){
            Toast.makeText(getBaseContext(), "Invalid Email", Toast.LENGTH_LONG).show();}
        else {
        //
            RequestQueue queue = Volley.newRequestQueue(this);
            String url = "http://10.0.2.2:4567/register/" + userName;

            final Map<String, String> mHeaders = new ArrayMap<String, String>();

            mHeaders.put("name", firstName);
            mHeaders.put("userName", userName);
            mHeaders.put("password", password);
            mHeaders.put("email", email);

            // Request a string response from the provided URL.
            JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                Toast.makeText(getBaseContext(), "Welcome .." + response.getString("name"), Toast.LENGTH_LONG).show();
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

                    Toast.makeText(getBaseContext(), "Registration  failed! " + error.networkResponse.statusCode, Toast.LENGTH_LONG).show();


                }
            }) {
                public Map<String, String> getHeaders() {

                    return mHeaders;
                }
            };

        queue.add(stringRequest);
    }


    }
}


