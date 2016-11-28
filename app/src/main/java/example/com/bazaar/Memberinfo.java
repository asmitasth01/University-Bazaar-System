package example.com.bazaar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

import example.com.bazaar.bean.UserInfo;

public class Memberinfo extends Home {

    EditText input_name;
    EditText input_email;
    EditText input_address;
    EditText input_phone;
    Button save;
    DatabaseReference bazaar;
    ArrayList<UserInfo> users;
    final SignInActivity login;
    
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

        save = (Button)findViewById(R.id.btn_save);
        input_name = (EditText)findViewById(R.id.display_name);
        input_email = (EditText)findViewById(R.id.display_email);
        input_address = (EditText)findViewById(R.id.display_address);
        input_phone = (EditText)findViewById(R.id.display_phone);

        bazaar = FirebaseDatabase.getInstance().getReference("Bazaar");
        bazaar.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> snapshotIterator = dataSnapshot.getChildren();
                Iterator<DataSnapshot> iterator = snapshotIterator.iterator();
                System.out.println(dataSnapshot.getChildren().getClass().toString());
                while (iterator.hasNext()) {
                    GenericTypeIndicator<ArrayList<UserInfo>> t = new GenericTypeIndicator<ArrayList<UserInfo>>() {};
                    users = iterator.next().getValue(t);
                    for (int i=0;i<users.size();i++){
                        if (users.get(i).getUserName().compareTo(login.getUsername())==0){
                            setUser(users.get(i));
                            input_name.setText(user.getName());
                            input_email.setText(user.getEmail());
                            input_address.setText(user.getAddress());
                            input_phone.setText(user.getPhoneNumber());
                            System.out.println("the user inside loop is: "+user.getUserName());
                        }
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        System.out.println("the user outside loop is: "+user.getUserName());




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

        DatabaseReference usersData = bazaar.child("User");

        for (int i=0;i<users.size();i++){
            if (users.get(i).getUserName().compareTo(login.getUsername())==0){
                user.setName(input_name.getText().toString());
                user.setEmail(input_email.getText().toString());
                user.setAddress(input_address.getText().toString());
                user.setPhoneNumber(input_phone.getText().toString());
                users.set(i,user);
                System.out.println("the user inside loop is: "+user.getUserName());
            }
        }

        usersData.setValue(users);

        usersData.push();
        Intent intent = new Intent(this, Memberinfo.class);
        startActivity(intent);
    }

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }
}
