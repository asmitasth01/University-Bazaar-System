package example.com.bazaar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import example.com.bazaar.bean.ClubInfo;

public class AllClubActivity extends Home {

    private Firebase mFire;
    private Button join;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //inflate your activity layout here!
        View contentView = inflater.inflate(R.layout.activity_all_club, null, false);
        drawer.addView(contentView, 0);

        // initializing the TextView attributes in activity_search_results_member xml file
        TextView clubName = (TextView)findViewById(R.id.display_club_name);
        TextView clubCategory = (TextView)findViewById(R.id.display_category);
        TextView clubDesc = (TextView)findViewById(R.id.display_club_desc);
        TextView clubAdmin = (TextView)findViewById(R.id.display_club_admin);

        //setting the member details to display in its corresponding TextView
        clubName.setText(ClubActivity.getClub_name());
        clubCategory.setText(ClubActivity.getClub_category());
        clubDesc.setText(ClubActivity.getClub_desc());
        clubAdmin.setText(ClubActivity.getClub_admin());

    }

    public void joinClub(View view) {
        mFire = new Firebase("https://bazaar-7ee62.firebaseio.com/Bazaar/MyClubs");

        Firebase mRefChild = mFire.push();
        mRefChild.push();

        final SignInActivity sign = new SignInActivity();

        Firebase newChild = mRefChild.child("username");
        newChild.setValue(sign.getUsername());
        newChild = mRefChild.child("clubName");
        newChild.setValue(ClubActivity.getClub_name());
        newChild = mRefChild.child("clubCategory");
        newChild.setValue(ClubActivity.getClub_category());
        newChild = mRefChild.child("clubDescription");
        newChild.setValue(ClubActivity.getClub_desc());
        newChild = mRefChild.child("clubAdmin");
        newChild.setValue(ClubActivity.getClub_admin());

        startActivity(new Intent(AllClubActivity.this, ClubActivity.class));
    }
}