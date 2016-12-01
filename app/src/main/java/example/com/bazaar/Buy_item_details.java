package example.com.bazaar;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import static example.com.bazaar.Buy_items.itemList;




public class Buy_item_details extends Home {

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //inflate your activity layout here!
        View contentView = inflater.inflate(R.layout.activity_buy_item_details, null, false);
        drawer.addView(contentView, 0);
        fab.setVisibility(View.INVISIBLE);

        TextView desc = (TextView) findViewById(R.id.buy_desTextView);
        TextView price = (TextView) findViewById(R.id.buy_priceTextView);
        TextView name = (TextView) findViewById(R.id.buy_nameTextView);

        int position = getIntent().getExtras().getInt("id");
        ImageView imageView = (ImageView) findViewById(R.id.profile_pic);


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
