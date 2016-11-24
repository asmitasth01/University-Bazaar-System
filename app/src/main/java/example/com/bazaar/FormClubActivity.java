package example.com.bazaar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


public class FormClubActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_club);

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
