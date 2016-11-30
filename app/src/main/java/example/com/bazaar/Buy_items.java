package example.com.bazaar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;




public class Buy_items extends AppCompatActivity {

    public static Integer[] images ={
            R.drawable.r1,
            R.drawable.r2,
            R.drawable.r3,
            R.drawable.r4,
            R.drawable.r5,
            R.drawable.r6,

    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_items);

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
                imageView.setLayoutParams(new GridView.LayoutParams(400, 400));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(10, 10, 10, 10);
            } else {
                imageView = (ImageView) convertView;
            }
            imageView.setImageResource(images[position]);
            return imageView;
        }
    }



}



