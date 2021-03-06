package example.com.bazaar;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import example.com.bazaar.bean.ExchangeInfo;

public class ExchangeSellerSideActivity extends Home {
    private Firebase mRef;
    private static ArrayList<ExchangeInfo> itemInfoArrayList;
    private String uName = new SignInActivity().getUsername();
    TextView desc;
    TextView quan;
    ImageView imageView;
    public ExchangeSellerSideActivity() {
        itemInfoArrayList = new ArrayList<>();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //inflate your activity layout here!
        View contentView = inflater.inflate(R.layout.activity_exchange_seller_side, null, false);
        drawer.addView(contentView, 0);
        fab.setVisibility(View.INVISIBLE);
        desc = (TextView) findViewById(R.id.exchange_desTextView);
        quan = (TextView) findViewById(R.id.exchange_quantity);
        imageView = (ImageView) findViewById(R.id.profile_pic);
        mRef = new Firebase("https://bazaar-7ee62.firebaseio.com/Bazaar").child("Exchange_Offers");
        mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                grabData(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });
    }

    public void grabData(DataSnapshot dataSnapshot) {
        ExchangeInfo info = dataSnapshot.getValue(ExchangeInfo.class);
        itemInfoArrayList.add(info);
        for (int i = 0; i < itemInfoArrayList.size(); i++) {
            if (uName.compareTo(itemInfoArrayList.get(i).getReceiveUserName()) == 0) {
                desc.setText(itemInfoArrayList.get(i).getItemDescription());
                quan.setText(itemInfoArrayList.get(i).getItemQuantity());
                String imageUrl = itemInfoArrayList.get(i).getExchange_ItemURL();
                Uri myImageUri = Uri.parse(imageUrl);
                Picasso.with(ExchangeSellerSideActivity.this).load(myImageUri).fit().centerCrop().into(imageView);

            }
        }
    }

    public void openExchangeSellerConformation(View view) {
        Intent intent = new Intent(this, ExchangeSellerConformation.class);
        startActivity(intent);
    }
}
