package example.com.bazaar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Random;

import example.com.bazaar.bean.UserInfo;

public class PasswordRetrivalActivity extends AppCompatActivity {

    private Firebase mRef;
    ArrayList<UserInfo> users;
    private UserInfo user;
    boolean status;

    public PasswordRetrivalActivity(){
        users = new ArrayList<>();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_retrival);

        Button send =(Button)findViewById(R.id.sendLink);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                status = true;
                EditText email = (EditText)findViewById(R.id.input_email);
                final String emailAddress = email.getText().toString();
                String tempPass = generateRandomPassword();
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

                for (int i=0;i<users.size();i++){
                    if (users.get(i).getEmail().compareTo(emailAddress)==0){
                        Firebase usersData = mRef.child(users.get(i).getUserName());
                        usersData.push();
                        user = users.get(i);
                        user.setPassword(tempPass);
                        users.set(i,user);
                        System.out.println("the new password is: "+user.getPassword());
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

                        String subject = "Your Temporary password";
                        String message = "Here is the temporary password for your account.\n" +
                                "Please change the change password by clicking 'change password'\n in you profile page at your next login.\n" +
                                "\n\tTemporary Password: "+tempPass+"\n\n\n\nThank You,\nBazaar Team";

                        SendMail sm = new SendMail(PasswordRetrivalActivity.this, emailAddress,  subject, message);
                        sm.execute();

                        Intent intent = new Intent(PasswordRetrivalActivity.this, SignInActivity.class);
                        startActivity(intent);
                    }else{
                        status = false;
                    }
                }
                if (status == false){
                    Toast.makeText(getApplicationContext(),"Email not valid. Please Enter the email linked to the account.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private static final Random RANDOM = new SecureRandom();
    /** Length of password. @see #generateRandomPassword() */
    public static final int PASSWORD_LENGTH = 8;
    /**
     * Generate a random String suitable for use as a temporary password.
     *
     * @return String suitable for use as a temporary password
     * @since 2.4
     */
    public static String generateRandomPassword()
    {
        // Pick from some letters that won't be easily mistaken for each
        // other. So, for example, omit o O and 0, 1 l and L.
        String letters = "abcdefghjkmnpqrstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ23456789+@";

        String pw = "";
        for (int i=0; i<PASSWORD_LENGTH; i++)
        {
            int index = (int)(RANDOM.nextDouble()*letters.length());
            pw += letters.substring(index, index+1);
        }
        return pw;
    }
}