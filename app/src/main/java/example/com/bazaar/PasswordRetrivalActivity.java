package example.com.bazaar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class PasswordRetrivalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_retrival);
    }

    public void openPasswordResetConformation(View view)
    {
        Intent intent = new Intent(this, PasswordResetLinkSentConformationActivity.class);
        startActivity(intent);
    }
}
