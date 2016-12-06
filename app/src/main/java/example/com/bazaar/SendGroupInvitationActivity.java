package example.com.bazaar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class SendGroupInvitationActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_group_invitation);
    }
    public void openInviteConformationDialogue(View view) {
        Intent intent = new Intent(this, InviteConformationDialogueActivity.class);
        startActivity(intent);
    }
    public void goHome(View view) {
        Intent intent = new Intent(this, EventPage.class);
        startActivity(intent);
    }
}
