package example.com.bazaar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Exchange_item_details extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange_item_details);
    }
    protected void openExchangeActivity(View view)
    {
        Intent intent = new Intent(this, ExchangeBuyerSideActivity.class);
        startActivity(intent);
    }
}
