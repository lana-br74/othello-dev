package se.kth.sda.othello;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import se.kth.sda.othello.MainActivity;
import se.kth.sda.othello.R;

public class MenuActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    /** Called when the user clicks the button */
    public void startHumanGame(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(MainActivity.GAME_TYPE, MainActivity.GAME_HUMAN);
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Toast t = Toast.makeText(this, data.getExtras().getString(MainActivity.GAME_RESULT), Toast.LENGTH_SHORT);
        t.show();
    }

}
