package se.kth.sda.othello;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import static android.app.Activity.RESULT_OK;
import static se.kth.sda.othello.MainActivity.GAME_RESULT;


public class MessageDialog extends Dialog {

    public MessageDialog(Context context, String msg,String score) {

        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.activity_message_dialog, null);
        TextView textView = (TextView) view.findViewById(R.id.msg);
        textView.setText(msg);
        TextView resultScore = (TextView) view.findViewById(R.id.score);
        resultScore.setText(score);
        Button ok = (Button) view.findViewById(R.id.ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        Button tryAgain = (Button) view.findViewById(R.id.TryAgain);
        tryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                Intent it = new Intent(v.getContext(),MenuActivity.class);
                v.getContext().startActivity(it);



            }
        });



        super.setContentView(view);


    }
}