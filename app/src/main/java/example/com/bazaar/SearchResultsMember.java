package example.com.bazaar;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

// This extends the appbar header from Home and displays the details of the member looked for
public class SearchResultsMember extends Home {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //inflate your activity layout here!
        View contentView = inflater.inflate(R.layout.activity_search_results_member, null, false);
        drawer.addView(contentView, 0);

        // initializing the TextView attributes in activity_search_results_member xml file
        TextView username = (TextView)findViewById(R.id.display_username);
        TextView name = (TextView)findViewById(R.id.display_name);
        TextView email = (TextView)findViewById(R.id.display_email);
        TextView address = (TextView)findViewById(R.id.display_address);
        TextView phone = (TextView)findViewById(R.id.display_phone);
        ImageView image = (ImageView)findViewById(R.id.profile_pic);

        //setting the member details to display in its corresponding TextView
        username.setText(SearchActivity.getUsername());
        name.setText(SearchActivity.getName());
        email.setText(SearchActivity.getEmail());
        address.setText(SearchActivity.getAddress());
        phone.setText(SearchActivity.getPhone());
        Picasso.with(getApplicationContext()).load(SearchActivity.getProfile_url()).into(image);

    }
}
