package example.com.bazaar;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class PaymentMainActivity extends Home {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //inflate your activity layout here!
        View contentView = inflater.inflate(R.layout.activity_payment_main, null, false);
        drawer.addView(contentView, 0);
        fab.setVisibility(View.INVISIBLE);
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
