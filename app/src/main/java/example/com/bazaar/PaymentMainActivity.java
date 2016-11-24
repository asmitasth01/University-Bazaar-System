package example.com.bazaar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class PaymentMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_main);
    }

    public void goCash(View view){
        Intent cash = new Intent(this, Cash.class);
        startActivity(cash);
    }

    public void goCredit(View view)
    {
        Intent credit =new Intent(this,Credit.class);
        startActivity(credit);
    }



}
