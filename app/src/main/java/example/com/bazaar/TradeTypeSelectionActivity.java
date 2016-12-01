package example.com.bazaar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class TradeTypeSelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade_type_selection);
    }


    public void goExSellSide(View view)
    {
        Intent intent = new Intent(this, ExchangeSellerSideActivity.class);
        startActivity(intent);
    }

    /*
        Function: selectTrade
        Input: This method takes View object as input
        Return: none
        Description: This method opens up the corresponding activity depending ont he users selection
                    from (Buy/Sell/Borrow/Exchange)
     */
    public void selectTrade(View view)
    {
        System.out.println("Come here");
        Intent intent;

        switch (view.getId())
        {
            case R.id.buy_button:
                intent = new Intent(this, Buy_items.class);
                startActivity(intent);
                break;
            case R.id.sell_button:
                intent = new Intent(this, sellActivity.class);
                startActivity(intent);
                break;
            case R.id.borrow_button:
                intent = new Intent(this, Borrow_items.class);
                startActivity(intent);
                break;
            case R.id.exchange_button:
                intent = new Intent(this, Exchange_items.class);
                startActivity(intent);
                break;
        }
    }
}
