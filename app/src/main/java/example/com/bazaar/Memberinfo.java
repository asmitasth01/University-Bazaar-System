package example.com.bazaar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import example.com.bazaar.bean.UserInfo;

public class Memberinfo extends Home {

    EditText input_name;
    EditText input_email;
    EditText input_address;
    EditText input_phone;
    Button save;
    ImageView profile_image;
    DatabaseReference bazaar;
    ArrayList<UserInfo> users;
    SignInActivity login;
    Firebase usersData;
    private Firebase mRef;

    private UserInfo user;

    public Memberinfo(){
        user = new UserInfo();
        users = new ArrayList<>();
        login = new SignInActivity();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //inflate your activity layout here!
        View contentView = inflater.inflate(R.layout.activity_memberinfo, null, false);
        drawer.addView(contentView, 0);

        bazaar = FirebaseDatabase.getInstance().getReference();

        save = (Button)findViewById(R.id.btn_save);
        input_name = (EditText)findViewById(R.id.display_name);
        input_email = (EditText)findViewById(R.id.display_email);
        input_address = (EditText)findViewById(R.id.display_address);
        input_phone = (EditText)findViewById(R.id.display_phone);
        profile_image = (ImageView)findViewById(R.id.profile_pic);

        mRef = new Firebase("https://bazaar-7ee62.firebaseio.com/Bazaar/User");

        mRef.addChildEventListener(new com.firebase.client.ChildEventListener() {
            @Override
            public void onChildAdded(com.firebase.client.DataSnapshot dataSnapshot, String s) {
                UserInfo info = dataSnapshot.getValue(UserInfo.class);
                if (info.getUserName().compareTo(login.getUsername())==0){
                    input_name.setText(info.getName());
                    input_email.setText(info.getEmail());
                    input_address.setText(info.getAddress());
                    input_phone.setText(info.getPhoneNumber());
                    Picasso.with(getApplicationContext()).load(info.getProfilePic_imageURL()).into(profile_image);
                }
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
    }

    public void onClickName(View view){
        save.setVisibility(view.VISIBLE);
        input_name.setCursorVisible(true);
        input_name.setEnabled(true);
        input_name.setFocusableInTouchMode(true);
        input_name.requestFocus();
    }

    public void onClickEmail(View view){
        save.setVisibility(view.VISIBLE);
        input_email.setCursorVisible(true);
        input_email.setEnabled(true);
        input_email.setFocusableInTouchMode(true);
        input_email.requestFocus();
    }

    public void onClickAddress(View view){
        save.setVisibility(view.VISIBLE);
        input_address.setCursorVisible(true);
        input_address.setEnabled(true);
        input_address.setFocusableInTouchMode(true);
        input_address.requestFocus();
    }

    public void onClickPhone(View view){
        save.setVisibility(view.VISIBLE);
        input_phone.setCursorVisible(true);
        input_phone.setEnabled(true);
        input_phone.setFocusableInTouchMode(true);
        input_phone.requestFocus();
    }

    public void onSave(View view){

        usersData = mRef.child(login.getUsername());
        usersData.push();

        for (int i=0;i<users.size();i++){
            if (users.get(i).getUserName().compareTo(login.getUsername())==0){
                user = users.get(i);
                //Firebase newChild = usersData.child("")
                user.setName(input_name.getText().toString());
                System.out.println("new names is: "+user.getName());
                user.setEmail(input_email.getText().toString());
                user.setAddress(input_address.getText().toString());
                user.setPhoneNumber(input_phone.getText().toString());
                users.set(i,user);
                System.out.println("the user inside loop is: "+user.getUserName());

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
            }
        }

        Intent intent = new Intent(this, Memberinfo.class);
        startActivity(intent);
    }

}
