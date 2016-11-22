package example.com.bazaar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ExchangeSellerSideActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange_seller_side);
    }

    protected void openExchangeSellerConformation(View view)
    {
        Intent intent = new Intent(this, ExchangeSellerConformation.class
        );
        startActivity(intent);
    }
}
