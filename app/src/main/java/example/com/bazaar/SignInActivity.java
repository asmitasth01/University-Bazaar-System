package example.com.bazaar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

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
    String username;
    String password;
    ArrayList<UserInfo> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        bazaar = FirebaseDatabase.getInstance().getReference("Bazaar");

        EditText user = (EditText)findViewById(R.id.username);
        username = user.getText().toString();
        EditText pass = (EditText)findViewById(R.id.password);
        password = pass.getText().toString();
    }

    public void attemptLogin(View view){

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

        for (int i = 0; i<userList.size(); i++){
            if(userList.get(i).getUserName().contains(username)){
                if(userList.get(i).getPassword().compareTo(password)==1){

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
