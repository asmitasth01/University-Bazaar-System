package example.com.bazaar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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

public class RegisterActivity extends AppCompatActivity {

    public android.content.Context context;
    DatabaseReference bazaar;
    private EditText username;
    private EditText password;
    private ArrayList<UserInfo> users;

    String user;
    String pass;

    public RegisterActivity() {
        users = new ArrayList<>();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

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
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        username = (EditText) findViewById(R.id.input_username);
        password = (EditText) findViewById(R.id.input_password);

    }

    public void registerUser(View view) {

        user = username.getText().toString();
        pass = password.getText().toString();

        System.out.println("password is: "+pass);
        DatabaseReference usersData = bazaar.child("User");

        UserInfo temp = new UserInfo();
        temp.setUserName(user);
        temp.setPassword(pass);
        users.add(temp);

        usersData.setValue(users);

        usersData.push();
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
    }
}
