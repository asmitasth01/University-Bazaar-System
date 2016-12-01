package example.com.bazaar;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

import example.com.bazaar.Home;
import example.com.bazaar.bean.ClubInfo;
import example.com.bazaar.bean.UserInfo;


@SuppressWarnings({ "deprecation" })
public class ClubActivity extends Home {

    private Firebase mRef;
    private ListView lv;

    // defining class attributes
    ArrayAdapter<String> adapter;
    DatabaseReference bazaar;
    private ArrayList<ClubInfo> users;
    //final ArrayAdapter<UserInfo> adapter;

    ArrayList<String> arrayNames;
    private static String selected;
    private static String club_name;
    private static String club_admin;
    private static String club_desc;
    private static String club_category;
    private int position;

    // Constructor where local variables are initialized
    public ClubActivity(){
        arrayNames = new ArrayList<>();
        users = new ArrayList<>();
        position = 0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //inflate your activity layout here!
        View contentView = inflater.inflate(R.layout.activity_club, null, false);
        drawer.addView(contentView, 0);

        mRef = new Firebase("https://bazaar-7ee62.firebaseio.com/Bazaar/Clubs");
        lv = (ListView) findViewById(R.id.listView_club);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayNames);
        lv.setAdapter(adapter);

        mRef.addChildEventListener(new com.firebase.client.ChildEventListener() {
            @Override
            public void onChildAdded(com.firebase.client.DataSnapshot dataSnapshot, String s) {
                ClubInfo info = dataSnapshot.getValue(ClubInfo.class);
                users.add(info);
                arrayNames.add(users.get(position).getClubName());
                position++;
                adapter.notifyDataSetChanged();
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

        Button myClub = (Button)findViewById(R.id.myClubs);
        Button createClub = (Button)findViewById(R.id.createClub);

        myClub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ClubActivity.this, MyClubActivity.class));
            }
        });

        createClub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ClubActivity.this, FormClubActivity.class));
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                setSelected(adapterView.getItemAtPosition(i).toString());
                for (int j=0;j<users.size();j++){
                    ClubInfo tempUser = users.get(j);
                    System.out.println("here are: "+users.get(j).getClubName());;
                    if(tempUser.getClubName().compareTo(selected)==0){
                        setClub_name(selected);
                        setClub_admin(tempUser.getClubAdmin());
                        setClub_desc(tempUser.getClubDescription());
                        setClub_category(tempUser.getClubCategory());
                        Intent intent = new Intent(ClubActivity.this, AllClubActivity.class);
                        startActivity(intent);
                    }
                }

            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    public static String getSelected() {
        return selected;
    }

    public static void setSelected(String selected) {
        ClubActivity.selected = selected;
    }

    public static String getClub_name() {
        return club_name;
    }

    public static void setClub_name(String club_name) {
        ClubActivity.club_name = club_name;
    }

    public static String getClub_admin() {
        return club_admin;
    }

    public static void setClub_admin(String club_admin) {
        ClubActivity.club_admin = club_admin;
    }

    public static String getClub_desc() {
        return club_desc;
    }

    public static void setClub_desc(String club_desc) {
        ClubActivity.club_desc = club_desc;
    }

    public static String getClub_category() {
        return club_category;
    }

    public static void setClub_category(String club_category) {
        ClubActivity.club_category = club_category;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
