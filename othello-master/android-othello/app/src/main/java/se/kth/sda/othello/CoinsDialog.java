package se.kth.sda.othello;

/**
 * Created by Shuai on 4/10/17.
 */
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

/**
 * When the player don't have enough coins, The dialog will pop up
 * @author Shuai
 */
public class CoinsDialog extends Dialog {

    public CoinsDialog(Context context) {

        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.activity_coins, null);

        Button shop = (Button) view.findViewById(R.id.shop);
        shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //dismiss();
            }
        });


        Button Add50 = (Button) view.findViewById(R.id.Add50);
        Add50.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //dismiss();
            }
        });

        Button ok1 = (Button) view.findViewById(R.id.ok1);
        ok1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        super.setContentView(view);


    }
}