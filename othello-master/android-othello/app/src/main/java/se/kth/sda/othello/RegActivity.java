package se.kth.sda.othello;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by Samer Obid on 2017-03-24.
 */

public class RegActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
    }
    public void reg (View v) {

        Intent itn = new Intent(this,RegistrationActivity.class);
        startActivity(itn);


    }
}
