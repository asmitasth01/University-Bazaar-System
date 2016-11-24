package example.com.bazaar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class SignInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
    }

    public void attemptLogin(View view){
        EditText getUser = (EditText) findViewById(R.id.username);
        EditText getPass = (EditText) findViewById(R.id.password);

        String username = getUser.getText().toString();
        String password = getPass.getText().toString();

        Intent loggedIn = new Intent(this, Home.class);
        startActivity(loggedIn);
//        if(username.compareToIgnoreCase("admin")==1 && password.compareToIgnoreCase("admin")==1){
//            Intent loggedIn = new Intent(this, Home.class);
//            startActivity(loggedIn);
//        }
    }

    public void retrievePassword(View view)
    {
        Intent intent = new Intent(this, PasswordRetrivalActivity.class);
        startActivity(intent);
    }
}
