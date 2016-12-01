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

import static example.com.bazaar.Borrow_items.itemList;


public class Borrow_item_details extends Home {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //inflate your activity layout here!
        View contentView = inflater.inflate(R.layout.activity_borrow_item_details, null, false);
        drawer.addView(contentView, 0);
        fab.setVisibility(View.INVISIBLE);

        TextView desc = (TextView) findViewById(R.id.borrow_desTextView);
        TextView price = (TextView) findViewById(R.id.borrow_priceTextView);
        TextView name = (TextView) findViewById(R.id.borrow_nameTextView);

        int position = getIntent().getExtras().getInt("id");
        ImageView imageView = (ImageView) findViewById(R.id.profile_pic);

//        System.out.println("The size of itemList is:"+itemList.size()+"\n\n\n");

        String imageUrl =   itemList.get(position).getSellItem_imageURL();
        Uri myImageUri = Uri.parse(imageUrl);

        Picasso.with(Borrow_item_details.this).load(myImageUri).fit().centerCrop().into(imageView);

        desc.setText(itemList.get(position).getItemDes());

//        System.out.println("Item Price: "+ itemList.get(position).getItemPrice());
        price.setText(itemList.get(position).getItemPrice());
        name.setText(itemList.get(position).getItemDescription());

    }

    public void openPayment(View view)
    {
        Intent intent = new Intent(this,BorrowActivity.class);
        startActivity(intent);
    }



}
