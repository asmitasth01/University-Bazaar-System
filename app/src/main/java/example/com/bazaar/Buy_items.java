package example.com.bazaar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;




public class Buy_items extends AppCompatActivity {

    public static Integer[] images ={
            R.drawable.club_icon,
            R.drawable.club,
            R.drawable.dollar_icon,
            R.drawable.exchange,
            R.drawable.ic_menu_camera
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_items);

        GridView gridView = (GridView) findViewById(R.id.gridview);
        gridView.setAdapter(new ImageAdapter(this));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> parent,
                                    View v, int position, long id)
            {
                Toast.makeText(getBaseContext(),
                        "pic" + (position + 1) + " selected",
                        Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(), Buy_item_details.class);
                i.putExtra("id",position);
                Log.d("id", position+"");
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
            return images.length;
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
                imageView.setLayoutParams(new GridView.LayoutParams(185, 185));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(5, 5, 5, 5);
            } else {
                imageView = (ImageView) convertView;
            }
            imageView.setImageResource(images[position]);
            return imageView;
        }
    }


//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_buy_items);
//        GridView gridView = (GridView)findViewById(R.id.gridview);
//        gridView.setAdapter(new Image_Adapter((this)));
//        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//                Intent i = new Intent(getApplicationContext(), Buy_item_details.class);
//                i.putExtra("id",position);
//                Log.d("id", position+"");
//                startActivity(i);
//            }
//        });
//
//    }

}



