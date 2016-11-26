package example.com.bazaar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;


public class FormClubActivity extends Home{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //inflate your activity layout here!
        View contentView = inflater.inflate(R.layout.activity_form_club, null, false);
        drawer.addView(contentView, 0);

    }


    public void openSendInvite(View view)
    {
        Intent intent;

        switch(view.getId()){
            case R.id.create_club_button:
                intent = new Intent(this, SendGroupInvitationActivity.class);
                startActivity(intent);
        }

    }
}
