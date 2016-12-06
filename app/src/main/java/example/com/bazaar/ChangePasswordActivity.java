package example.com.bazaar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import java.util.ArrayList;
import example.com.bazaar.bean.UserInfo;

public class ChangePasswordActivity extends Home {

    private Firebase mRef;
    ArrayList<UserInfo> users;
    private UserInfo user;

    public ChangePasswordActivity(){
        users = new ArrayList<>();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //inflate your activity layout here!
        View contentView = inflater.inflate(R.layout.activity_change_password, null, false);
        drawer.addView(contentView, 0);
        fab.setVisibility(View.INVISIBLE);

    }

    public void submitPassword(View view){
        EditText old = (EditText)findViewById(R.id.input_oldPassword);
        EditText newPass = (EditText)findViewById(R.id.newPassword);
        EditText confirm = (EditText)findViewById(R.id.confirmPassword);
        TextView error = (TextView)findViewById(R.id.error);
        String oldPass = old.getText().toString();
        String newPassword = newPass.getText().toString();
        String confirmPass = confirm.getText().toString();

        mRef = new Firebase("https://bazaar-7ee62.firebaseio.com/Bazaar/User");

        mRef.addChildEventListener(new com.firebase.client.ChildEventListener() {
            @Override
            public void onChildAdded(com.firebase.client.DataSnapshot dataSnapshot, String s) {
                UserInfo info = dataSnapshot.getValue(UserInfo.class);
                users.add(info);
            }

            @Override
            public void onChildChanged(com.firebase.client.DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(com.firebase.client.DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(com.firebase.client.DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


        SignInActivity login = new SignInActivity();
        for (int i=0;i<users.size();i++){
            if (users.get(i).getUserName().compareTo(login.getUsername())==0){
                if (users.get(i).getPassword().compareTo(oldPass)==0) {
                    if (newPassword.compareTo(confirmPass) == 0) {
                        Firebase usersData = mRef.child(users.get(i).getUserName());
                        usersData.push();
                        user = users.get(i);
                        user.setPassword(newPassword);
                        users.set(i, user);
                        System.out.println("the new password is: " + user.getPassword());
                        Firebase newChild = usersData.child("name");
                        newChild.push();
                        newChild.setValue(user.getName());
                        newChild = usersData.child("userName");
                        newChild.setValue(user.getUserName());
                        newChild = usersData.child("address");
                        newChild.setValue(user.getAddress());
                        newChild = usersData.child("email");
                        newChild.setValue(user.getEmail());
                        newChild = usersData.child("phoneNumber");
                        newChild.setValue(user.getPhoneNumber());
                        newChild = usersData.child("password");
                        newChild.setValue(user.getPassword());
                        newChild = usersData.child("profilePic_imageURL");
                        newChild.setValue(user.getProfilePic_imageURL());
                        Toast.makeText(getApplicationContext(),"Password successfully changed.",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ChangePasswordActivity.this, Memberinfo.class));
                    } else {
                        error.setText("The new passwords entered didn't match");
                    }
                }else{
                    error.setText("Old password didn't match");
                }
            }
        }
    }
}
