package example.com.bazaar;


import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import static example.com.bazaar.Borrow_items.itemList;


public class Borrow_item_details extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrow_item_details);

        TextView desc = (TextView) findViewById(R.id.borrow_desTextView);
        TextView price = (TextView) findViewById(R.id.borrow_priceTextView);
        TextView name = (TextView) findViewById(R.id.borrow_nameTextView);

        int position = getIntent().getExtras().getInt("id");
        ImageView imageView = (ImageView) findViewById(R.id.full_image);

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
