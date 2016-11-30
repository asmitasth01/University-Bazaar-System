package example.com.bazaar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goLogin(View view)
    {
        Intent intent = new Intent(this, EventPage.class);
        startActivity(intent);
    }

    public void goRegister(View view){
        Intent register = new Intent(this, RegisterActivity.class);
        startActivity(register);
    }
}
