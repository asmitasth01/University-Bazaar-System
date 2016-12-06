package example.com.bazaar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class BankInfoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_info);
    }
    public void openBankInfoSuccess(View view) {
        Intent intent = new Intent(this, BankInfoAddedSuccessActivity.class);
        startActivity(intent);
    }
}
