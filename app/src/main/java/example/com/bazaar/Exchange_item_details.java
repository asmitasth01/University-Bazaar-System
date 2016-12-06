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

import static example.com.bazaar.Exchange_items.itemList;

public class Exchange_item_details extends Home {

    private static String userName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //inflate your activity layout here!
        View contentView = inflater.inflate(R.layout.activity_exchange_item_details, null, false);
        drawer.addView(contentView, 0);
        fab.setVisibility(View.INVISIBLE);
        TextView desc = (TextView) findViewById(R.id.exchange_desTextView);
        TextView price = (TextView) findViewById(R.id.exchange_priceTextView);
        TextView name = (TextView) findViewById(R.id.exchange_nameTextView);
        int position = getIntent().getExtras().getInt("id");
        ImageView imageView = (ImageView) findViewById(R.id.profile_pic);
        String imageUrl = itemList.get(position).getSellItem_imageURL();
        Uri myImageUri = Uri.parse(imageUrl);
        Picasso.with(Exchange_item_details.this).load(myImageUri).fit().centerCrop().into(imageView);
        desc.setText(itemList.get(position).getItemDes());
        price.setText(itemList.get(position).getItemPrice());
        name.setText(itemList.get(position).getItemDescription());
        setUserName(itemList.get(position).getUserName());
    }

    protected void openExchangeActivity(View view) {
        Intent intent = new Intent(this, ExchangeBuyerSideActivity.class);
        startActivity(intent);
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;

    }
}
