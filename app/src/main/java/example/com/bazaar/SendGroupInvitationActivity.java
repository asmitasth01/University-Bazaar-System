package example.com.bazaar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SendGroupInvitationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_group_invitation);
    }

    protected void openInviteConformationDialogue(View view)
    {
        Intent intent = new Intent(this, InviteConformationDialogueActivity.class);
        startActivity(intent);
    }
}
