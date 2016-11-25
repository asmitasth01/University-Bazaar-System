package example.com.bazaar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Iterator;
import example.com.bazaar.bean.UserInfo;

public class SignInActivity extends AppCompatActivity {

    DatabaseReference bazaar;
    EditText user;
    EditText pass;
    TextView error;
    String username;
    String password;
    ArrayList<UserInfo> userList;
    Intent intent;
    DataSnapshot dataSnapshot;

    public SignInActivity(){
        userList = new ArrayList<>();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        bazaar = FirebaseDatabase.getInstance().getReference("Bazaar");

        user = (EditText)findViewById(R.id.username);
        pass = (EditText)findViewById(R.id.password);
        error = (TextView)findViewById(R.id.error_login);

        System.out.println(password);
        bazaar.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Iterable<DataSnapshot> snapshotIterator = dataSnapshot.getChildren();
                Iterator<DataSnapshot> iterator = snapshotIterator.iterator();
                System.out.println(dataSnapshot.getChildren().getClass().toString());
                while(iterator.hasNext()){
                    GenericTypeIndicator<ArrayList<UserInfo>> t = new GenericTypeIndicator<ArrayList<UserInfo>>() {};
                    userList = iterator.next().getValue(t);
                    for (int i=0;i<userList.size();i++){
                        System.out.println(userList.get(i).getUserName());
                    }

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void attemptLogin(View view){
        username = user.getText().toString();
        password = pass.getText().toString();

        System.out.println("password is: "+password);
        for (int i = 0; i<userList.size(); i++){

            if(userList.get(i).getUserName().contains(username)){
                System.out.println("username is: "+userList.get(i).getUserName());
                if(userList.get(i).getPassword().compareTo(password)==0){
                    System.out.println(userList.get(i).getPassword());
                    intent = new Intent(this, Home.class);
                    startActivity(intent);
                }else{
                    error.setText("Username or Password Invalid!!");
                }
            }
        }

//        bazaar.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//
//                System.out.println("Sosal le vanchha vayena re");
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
    }

    public void retrievePassword(View view)
    {
        Intent intent = new Intent(this, PasswordRetrivalActivity.class);
        startActivity(intent);
    }
}
