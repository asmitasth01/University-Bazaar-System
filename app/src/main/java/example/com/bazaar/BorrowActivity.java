package example.com.bazaar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class BorrowActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrow);
    }
    protected void openPayment(View view)
    {
        Intent intent = new Intent(this,PaymentMainActivity.class);
        startActivity(intent);
    }
}
