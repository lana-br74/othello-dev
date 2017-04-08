package se.kth.sda.othello;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.app.AppCompatActivity;
import se.kth.sda.othello.MainActivity;
import se.kth.sda.othello.R;
import org.json.JSONException;
import org.json.JSONObject;

public class MenuActivity extends AppCompatActivity {
  //  User currentPlayer = null;
  //  Intent currentintent = getIntent();
    TextView user ;
    TextView coins ;
    JSONObject jsonPlayer = null;
    boolean userIsLoggedIn = false;
    private UserDialog userDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Intent currentintent = getIntent();

        Bundle extras = currentintent.getExtras();
        if (extras != null) {
            if (extras.containsKey("player")) {
                userIsLoggedIn = true;
                String jsonString = currentintent.getStringExtra("player");
                try {
                    jsonPlayer = new JSONObject(jsonString);
                }catch(Exception e){

                }
            }
        }
        user = (TextView)findViewById(R.id.Username);
        coins = (TextView)findViewById(R.id.Coins);
        String name ="";
        int coinNumber = 0;
        if (userIsLoggedIn ) {
            try {
                name = jsonPlayer.getString("name");
                user.setText("" +name);
                coinNumber = jsonPlayer.getInt("coins");
                coins.setText("" + coinNumber);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    /** Called when the user clicks the button */
     public void startHumanGame(View view) {


         String name ="";
         if(userIsLoggedIn) {
             try {
                 name = jsonPlayer.getString("coins");
             } catch (Exception e) {

             }
         }

         Toast t = Toast.makeText(this,""+name, Toast.LENGTH_SHORT);
         t.show();

         Intent intent = new Intent(this, MainActivity.class);
         if(userIsLoggedIn) intent.putExtra("player",jsonPlayer.toString());



        intent.putExtra(MainActivity.GAME_TYPE, MainActivity.GAME_HUMAN);startActivityForResult(intent, 0);

    }
    public void Login(View view){

        // if user is loged in display the user data on the server.
        if(userIsLoggedIn){
            try {
                String name = jsonPlayer.getString("name");
                String username = jsonPlayer.getString("userName");
                String email = jsonPlayer.getString("email");
                int coinNumber = jsonPlayer.getInt("coins");
                int wins = jsonPlayer.getInt("wins");
                int loses = jsonPlayer.getInt("loses");
                userDialog = new UserDialog(MenuActivity.this,username,email,name,coinNumber,wins,loses);
                userDialog.show();
            } catch (JSONException e) {
               //some error handling
                e.printStackTrace();
            }



        }else {
            //If the user play with no log in
            Intent intent = new Intent(this, RegActivity.class);
            startActivity(intent);
        }

    }

     public void help(View v) {

         Intent itn = new Intent(this,HelpActivity.class);
         startActivity(itn);
     }
    public void setting(View v) {

        Intent itn = new Intent(this,SettingActivity.class);
        startActivity(itn);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Toast t = Toast.makeText(this, data.getExtras().getString(MainActivity.GAME_RESULT), Toast.LENGTH_SHORT);
        t.show();
    }

}
