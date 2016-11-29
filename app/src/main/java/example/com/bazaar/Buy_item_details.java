package example.com.bazaar;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;


public class Buy_item_details extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_item_details);

        int position = getIntent().getExtras().getInt("id");
        ImageView imageView = (ImageView) findViewById(R.id.full_image);
        imageView.setImageResource(Buy_items.images[position]);
//
//        GridView grid = new GridView();
//        int imageInt = getIntent().getIntExtra("id", R.drawable.basket_icon);
//        ImageView imageView = (ImageView) findViewById(R.id.full_image);
//        imageView.setImageResource (imageInt);

//        ActionBar actionBar = getSupportActionBar();
//        if (actionBar != null) {
//            actionBar.setDisplayHomeAsUpEnabled(true);
//            getSupportActionBar().setDisplayShowHomeEnabled(true);
//        }
    }



}
