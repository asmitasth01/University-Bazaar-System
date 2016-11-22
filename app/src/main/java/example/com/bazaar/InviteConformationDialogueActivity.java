package com.shrey.ubs_shrey;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class InviteConformationDialogueActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_conformation_dialogue);
    }
    protected void openSendInvite(View view)
    {
        Intent intent = new Intent(this, SendGroupInvitationActivity.class);
        startActivity(intent);
    }
}
