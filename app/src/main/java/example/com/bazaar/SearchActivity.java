package example.com.bazaar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

import example.com.bazaar.bean.UserInfo;

// This class extends te appbar from Home and filters the search results as the user types
public class SearchActivity extends Home {

    private Firebase mRef;
    private ListView lv;

    // defining class attributes
    ArrayAdapter<String> adapter;
    DatabaseReference bazaar;
    private ArrayList<UserInfo> users;
    //final ArrayAdapter<UserInfo> adapter;

    ArrayList<String> arrayNames;
    private static String selected;
    private static String username;
    private static String name;
    private static String email;
    private static String address;
    private static String phone;
    private static String profile_url;
    private int position;

    // Constructor where local variables are initialized
    public SearchActivity(){
        arrayNames = new ArrayList<>();
        users = new ArrayList<>();
        position = 0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //inflate your activity layout here!
        View contentView = inflater.inflate(R.layout.activity_search, null, false);
        drawer.addView(contentView, 0);

        mRef = new Firebase("https://bazaar-7ee62.firebaseio.com/Bazaar/User");
        lv = (ListView)findViewById(R.id.listView_search);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayNames);
        lv.setAdapter(adapter);

        mRef.addChildEventListener(new com.firebase.client.ChildEventListener() {
            @Override
            public void onChildAdded(com.firebase.client.DataSnapshot dataSnapshot, String s) {
                UserInfo info = dataSnapshot.getValue(UserInfo.class);
                users.add(info);
                arrayNames.add(users.get(position).getUserName());
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


//        bazaar = FirebaseDatabase.getInstance().getReference("Bazaar");
//        bazaar.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                Iterable<DataSnapshot> snapshotIterator = dataSnapshot.getChildren();
//                Iterator<DataSnapshot> iterator = snapshotIterator.iterator();
//                System.out.println(dataSnapshot.getChildren().getClass().toString());
//                while (iterator.hasNext()) {
//                    GenericTypeIndicator<ArrayList<UserInfo>> t = new GenericTypeIndicator<ArrayList<UserInfo>>() {};
//                    users = iterator.next().getValue(t);
//                    for (int i = 0;i<users.size();i++){
//                        arrayNames.add(users.get(i).getUserName());
//                        System.out.println("array names :"+arrayNames.get(i));
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//
//        System.out.println("array size is : "+arrayNames.size());
//
//        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayNames);
//        lv.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem item = menu.findItem(R.id.menuSearch);
        final SearchView searchView = (SearchView)item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                setSelected(adapterView.getItemAtPosition(i).toString());
                System.out.println("selected is: "+selected);
                Toast.makeText(SearchActivity.this, adapterView.getItemAtPosition(i).toString()+" is selected", Toast.LENGTH_SHORT).show();

                System.out.println("this is the array size; "+users.size());
                for (int j=0;j<users.size();j++){
                    UserInfo tempUser = users.get(j);
                    System.out.println("here are: "+users.get(j).getUserName());;
                    if(tempUser.getUserName().compareTo(selected)==0){
                        setUsername(selected);
                        setName(tempUser.getName());
                        setEmail(tempUser.getEmail());
                        setAddress(tempUser.getAddress());
                        setPhone(tempUser.getPhoneNumber());
                        setProfile_url(tempUser.getProfilePic_imageURL());
                        Intent intent = new Intent(SearchActivity.this, SearchResultsMember.class);
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
        SearchActivity.selected = selected;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        SearchActivity.username = username;
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        SearchActivity.name = name;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        SearchActivity.email = email;
    }

    public static String getAddress() {
        return address;
    }

    public static void setAddress(String address) {
        SearchActivity.address = address;
    }

    public static String getPhone() {
        return phone;
    }

    public static void setPhone(String phone) {
        SearchActivity.phone = phone;
    }

    public static String getProfile_url() {
        return profile_url;
    }

    public static void setProfile_url(String profile_url) {
        SearchActivity.profile_url = profile_url;
    }

    public ArrayList<UserInfo> getUsers() {
        return users;
    }
}
