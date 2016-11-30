package example.com.bazaar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.Iterator;

import example.com.bazaar.bean.ItemInfo;
import example.com.bazaar.bean.UserInfo;

public class SignInActivity extends AppCompatActivity {

    DatabaseReference bazaar;
    EditText user;
    EditText pass;
    private Button login;
    TextView error;
    private static String username;
    String password;
    ArrayList<UserInfo> userList;
    Intent intent;
    DataSnapshot dataSnapshot;

    public SignInActivity(){
        userList = new ArrayList<>();
    }

    private Firebase mRef;




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




//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_sign_in);
//        bazaar = FirebaseDatabase.getInstance().getReference("Bazaar");
//
//

//
//
//        bazaar = FirebaseDatabase.getInstance().getReference("Bazaar");
//        bazaar.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                Iterable<DataSnapshot> snapshotIterator = dataSnapshot.getChildren();
//                Iterator<DataSnapshot> iterator = snapshotIterator.iterator();
//                System.out.println(dataSnapshot.getChildren().getClass().toString());
//                while (iterator.hasNext()) {
//                    GenericTypeIndicator<ArrayList<UserInfo>> t = new GenericTypeIndicator<ArrayList<UserInfo>>() {
//                    };
//                    userList =(ArrayList<UserInfo>) iterator.next().getValue(t);
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//    }

//
//    private void getData(DataSnapshot dataSnapshot)
//    {
//        String Key = dataSnapshot.getKey();
//
//        UserInfo userInfo = (UserInfo) dataSnapshot.getValue(UserInfo.class);
//
//
//    }

    public void attemptLogin(View view){
        setUsername(user.getText().toString());
        password = pass.getText().toString();

        if(username.isEmpty() || password.isEmpty()){
            error.setText("Input fields cannot be empty.");
        }else{
            System.out.println("password is: "+password);
            for (int i = 0; i<userList.size(); i++){

                if(userList.get(i).getUserName().contains(username)){
                    System.out.println("username is: "+userList.get(i).getUserName());
                    if(userList.get(i).getPassword().compareTo(password)==0){
                        System.out.println(userList.get(i).getPassword());
                        Toast.makeText(getApplicationContext(), "Logging in...",
                                Toast.LENGTH_SHORT).show();
                        intent = new Intent(this, EventPage.class);
                        startActivity(intent);
                    }else{
                        error.setText("Username or Password Invalid!!");
                    }
                }
            }
        }

    }

    public void retrievePassword(View view)
    {
        Intent intent = new Intent(this, PasswordRetrivalActivity.class);
        startActivity(intent);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}