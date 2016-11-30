package example.com.bazaar;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;

import example.com.bazaar.bean.ItemInfo;
import example.com.bazaar.bean.UserInfo;



public class Buy_items extends AppCompatActivity {

    private Firebase mRef;
    public static ArrayList<ItemInfo> itemList;

    public Buy_items()
    {
        itemList = new ArrayList<>();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_items);


         mRef = new Firebase("https://bazaar-7ee62.firebaseio.com/Bazaar/Sell_Items");

         mRef.addChildEventListener(new ChildEventListener() {
             @Override
             public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                 ItemInfo info = dataSnapshot.getValue(ItemInfo.class);
                 itemList.add(info);
                 System.out.println("The length of the itemList is: "+itemList.size());

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
         })  ;


        GridView gridView = (GridView) findViewById(R.id.gridView);
        gridView.setAdapter(new ImageAdapter(this));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> parent,
                                    View v, int position, long id)
            {
                Toast.makeText(getBaseContext(),
                        "Grid" + (position + 1) + " selected",
                        Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(), Buy_item_details.class);
                i.putExtra("id",position);
                startActivity(i);
            }
        });
    }

    public class ImageAdapter extends BaseAdapter
    {
        private Context context;

        public ImageAdapter(Context c)
        {
            context = c;
        }

        //---returns the number of images---
        public int getCount() {
            return itemList.size();
        }

        //---returns the ID of an item---
        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return 0;
        }

        //---returns an ImageView view---
        public View getView(int position, View convertView, ViewGroup parent)
        {
            ImageView imageView;
            if (convertView == null) {
                imageView = new ImageView(context);
                imageView.setLayoutParams(new GridView.LayoutParams(400, 400));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(10, 10, 10, 10);
            } else {
                imageView = (ImageView) convertView;
            }
            String imageUrl =   itemList.get(position).getSellItem_imageURL();
            Uri myImageUri = Uri.parse(imageUrl);


           if (myImageUri != null) {

                Picasso.with(Buy_items.this).load(myImageUri).fit().centerCrop().into(imageView);
               // imageView.setImageURI(myImageUri);

            }
            return imageView;
        }
    }



}



