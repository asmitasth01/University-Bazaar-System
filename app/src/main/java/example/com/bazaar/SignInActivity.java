package example.com.bazaar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;

import example.com.bazaar.bean.UserInfo;

public class SignInActivity extends AppCompatActivity {

    private Firebase mRef;
    EditText user;
    EditText pass;
    private Button login;
    TextView error;
    private static String username;
    private static String name;
    private static String email;
    private static String address;
    private static String phone;
    private static String profile_url;
    String password;
    private ArrayList<UserInfo> userList;
    Intent intent;
    public SignInActivity() {
        userList = new ArrayList<>();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        Firebase.setAndroidContext(this);
        user = (EditText) findViewById(R.id.username);
        pass = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.createAccount);
        error = (TextView) findViewById(R.id.error_login);
        mRef = new Firebase("https://bazaar-7ee62.firebaseio.com/Bazaar/User");
        mRef.addChildEventListener(new com.firebase.client.ChildEventListener() {
            @Override
            public void onChildAdded(com.firebase.client.DataSnapshot dataSnapshot, String s) {
                UserInfo info = dataSnapshot.getValue(UserInfo.class);
                userList.add(info);
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

    public void attemptLogin(View view) {
        setUsername(user.getText().toString());
        password = pass.getText().toString();
        if (username.isEmpty() || password.isEmpty()) {
            error.setText("Input fields cannot be empty.");
        } else {
            System.out.println("password is: " + password);
            for (int i = 0; i < userList.size(); i++) {

                if (userList.get(i).getUserName().contains(username)) {
                    System.out.println("username is: " + userList.get(i).getUserName());
                    if (userList.get(i).getPassword().compareTo(password) == 0) {
                        setName(userList.get(i).getName());
                        setEmail(userList.get(i).getEmail());
                        setAddress(userList.get(i).getAddress());
                        setPhone(userList.get(i).getPhoneNumber());
                        setProfile_url(userList.get(i).getProfilePic_imageURL());
                        System.out.println(userList.get(i).getPassword());
                        Toast.makeText(getApplicationContext(), "Logging in...",
                                Toast.LENGTH_SHORT).show();
                        intent = new Intent(this, EventPage.class);
                        startActivity(intent);
                    } else {
                        error.setText("Username or Password Invalid!!");
                    }
                }
            }
        }

    }
    public void retrievePassword(View view) {
        Intent intent = new Intent(this, PasswordRetrivalActivity.class);
        startActivity(intent);
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public ArrayList<UserInfo> getUserList() {
        return userList;
    }
    public static String getName() {
        return name;
    }
    public static void setName(String name) {
        SignInActivity.name = name;
    }
    public static String getEmail() {
        return email;
    }
    public static void setEmail(String email) {
        SignInActivity.email = email;
    }
    public static String getAddress() {
        return address;
    }
    public static void setAddress(String address) {
        SignInActivity.address = address;
    }
    public static String getPhone() {
        return phone;
    }
    public static void setPhone(String phone) {
        SignInActivity.phone = phone;
    }
    public static String getProfile_url() {
        return profile_url;
    }
    public static void setProfile_url(String profile_url) {
        SignInActivity.profile_url = profile_url;
    }
}