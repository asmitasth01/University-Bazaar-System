package example.com.bazaar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class BankInfoAddedSuccessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_info_added_success);
    }
    protected void goHome(View view)
    {
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }
}
