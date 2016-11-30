package example.com.bazaar;


import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import static example.com.bazaar.Buy_items.itemList;




public class Buy_item_details extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_item_details);

        TextView desc = (TextView) findViewById(R.id.desTextView);
        TextView price = (TextView) findViewById(R.id.priceTextView);
        TextView name = (TextView) findViewById(R.id.nameTextView);

        int position = getIntent().getExtras().getInt("id");
        ImageView imageView = (ImageView) findViewById(R.id.full_image);


        //imageView.setImageResource(Buy_items.images[position]);
        String imageUrl =   itemList.get(position).getSellItem_imageURL();
        Uri myImageUri = Uri.parse(imageUrl);

        Picasso.with(Buy_item_details.this).load(myImageUri).fit().centerCrop().into(imageView);
       // Picasso.with(getApplicationContext()).load(itemList.get[position]).into(imageView);


      //  System.out.println("This is the item Description: "+itemList.get(position).getItemDes());
        desc.setText(itemList.get(position).getItemDes());

        price.setText(itemList.get(position).getItemPrice());
        name.setText(itemList.get(position).getItemDescription());


    }

    public void shippingInfo(View view)
    {
        Intent intent = new Intent(this,BuyActivity.class);
        startActivity(intent);
    }



}
