package example.com.bazaar;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.KeyListener;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Memberinfo extends Home {

    EditText input_name;
    EditText input_email;
    EditText input_address;
    EditText input_phone;

    String name;
    String email;
    String phoneNumber;
    String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //inflate your activity layout here!
        View contentView = inflater.inflate(R.layout.activity_memberinfo, null, false);
        drawer.addView(contentView, 0);

        input_name = (EditText)findViewById(R.id.display_name);
        input_email = (EditText)findViewById(R.id.display_email);
        input_address = (EditText)findViewById(R.id.display_address);
        input_phone = (EditText)findViewById(R.id.display_phone);

    }

    public void onClickName(View view){
        input_name.setCursorVisible(true);
        input_name.setEnabled(true);
        input_name.setFocusableInTouchMode(true);
        input_name.requestFocus();
    }

    public void onClickEmail(View view){
        input_email.setCursorVisible(true);
        input_email.setEnabled(true);
        input_email.setFocusableInTouchMode(true);
        input_email.requestFocus();
    }

    public void onClickAddress(View view){
        input_address.setCursorVisible(true);
        input_address.setEnabled(true);
        input_address.setFocusableInTouchMode(true);
        input_address.requestFocus();
    }

    public void onClickPhone(View view){
        input_phone.setCursorVisible(true);
        input_phone.setEnabled(true);
        input_phone.setFocusableInTouchMode(true);
        input_phone.requestFocus();
    }
}
