package se.kth.sda.othello;

/**
 * Created by Shuai on 4/7/17.
 */

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;


/** The dialog of user information
 * @author lana
 * passong the user information from the server
 *
 */
public class UserDialog extends Dialog {

    public UserDialog(Context context, String userName1,String email1,String name1,int coins1,int wins1,int loses1)
    {

        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.activity_user_dialog, null);

        TextView userName = (TextView) view.findViewById(R.id.userName);
        userName.setText(userName1);

        TextView email = (TextView) view.findViewById(R.id.email);
        email.setText(email1);

        TextView name = (TextView) view.findViewById(R.id.name);
        name.setText(name1);

        TextView coins = (TextView) view.findViewById(R.id.coins);
        coins.setText(""+coins1);

        TextView wins = (TextView) view.findViewById(R.id.wins);
        wins.setText(""+wins1);

        TextView loses = (TextView) view.findViewById(R.id.loses);
        loses.setText(""+loses1);

        Button ok = (Button) view.findViewById(R.id.ok1);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        super.setContentView(view);


    }
}