package se.kth.sda.othello;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import android.support.v7.app.AppCompatActivity;
import se.kth.sda.othello.MainActivity;
import se.kth.sda.othello.R;

public class MenuActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        ImageButton btn = (ImageButton) findViewById(R.id.help);

    }

    /** Called when the user clicks the button */
     public void startHumanGame(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(MainActivity.GAME_TYPE, MainActivity.GAME_HUMAN);
       startActivityForResult(intent, 0);
    }


     public void help(View v) {

         Intent itn = new Intent(this,HelpActivity.class);
         startActivity(itn);
     }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Toast t = Toast.makeText(this, data.getExtras().getString(MainActivity.GAME_RESULT), Toast.LENGTH_SHORT);
        t.show();
    }

}
