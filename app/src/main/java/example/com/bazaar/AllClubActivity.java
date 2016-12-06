package example.com.bazaar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.client.Firebase;

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
        TextView clubName = (TextView) findViewById(R.id.display_club_name);
        TextView clubCategory = (TextView) findViewById(R.id.display_category);
        TextView clubDesc = (TextView) findViewById(R.id.display_club_desc);
        TextView clubAdmin = (TextView) findViewById(R.id.display_club_admin);

        //setting the member details to display in its corresponding TextView
        clubName.setText(ClubActivity.getClub_name());
        clubCategory.setText(ClubActivity.getClub_category());
        clubDesc.setText(ClubActivity.getClub_desc());
        clubAdmin.setText(ClubActivity.getClub_admin());
    }

    public void joinClub(View view) {
        mFire = new Firebase("https://bazaar-7ee62.firebaseio.com/Bazaar/MyClubs");
        join = (Button) findViewById(R.id.join_club);

        final SignInActivity sign = new SignInActivity();

        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Firebase mRefChild = mFire.push();
                mRefChild.push();
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
        });
    }
    public Button getJoin() {
        return join;
    }
}