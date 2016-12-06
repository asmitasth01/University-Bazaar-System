package example.com.bazaar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
public class MyClubIformation extends Home {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //inflate your activity layout here!
        View contentView = inflater.inflate(R.layout.activity_my_club_iformation, null, false);
        drawer.addView(contentView, 0);
        // initializing the TextView attributes in activity_search_results_member xml file
        TextView clubName = (TextView) findViewById(R.id.display_club_name);
        TextView clubCategory = (TextView) findViewById(R.id.display_category);
        TextView clubDesc = (TextView) findViewById(R.id.display_club_desc);
        TextView clubAdmin = (TextView) findViewById(R.id.display_club_admin);
        //setting the member details to display in its corresponding TextView
        clubName.setText(MyClubActivity.getClub_name());
        clubCategory.setText(MyClubActivity.getClub_category());
        clubDesc.setText(MyClubActivity.getClub_desc());
        clubAdmin.setText(MyClubActivity.getClub_admin());
    }

    public void goClubs(View view) {
        startActivity(new Intent(MyClubIformation.this, ClubActivity.class));
    }
}
