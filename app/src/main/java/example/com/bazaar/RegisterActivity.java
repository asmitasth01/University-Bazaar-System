package example.com.bazaar;



import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class RegisterActivity extends AppCompatActivity {

    public android.content.Context context;
    private DatabaseReference bazaar;
    private EditText username;
    private EditText password;
    private EditText Name;
    private EditText address;
    private EditText phoneNumber;
    private EditText Email;
    private ArrayList<UserInfo> users;
    private Firebase mFire;
    private Button mButton;

    //For Image Storage to database (For Profile Picture)
    private Button mSelectImage;
    private StorageReference mStorage;

    Uri downloadUri;

    String imagePath;


    //For Image retrival from database (For Profile Picture)
    private Button myUploadbutton;
   // private StorageReference mCamStorage;
    private ImageView myImageView;

    public static String user;
    private static String pass;
    private static String addr;
    private static String pNumber;
    private static String name;
    private static String email;



    private static final int GALLERY_INTENT = 2;
    private static final int CAMERA_REQUEST_CODE = 1;

    public RegisterActivity() {
        users = new ArrayList<>();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Firebase.setAndroidContext(this);


        username = (EditText) findViewById(R.id.input_username);
        password = (EditText) findViewById(R.id.input_password);
        Name = (EditText) findViewById(R.id.input_name);
        address = (EditText) findViewById(R.id.input_address);
        phoneNumber = (EditText) findViewById(R.id.input_mobile);
        Email = (EditText) findViewById(R.id.input_email);


        username.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void afterTextChanged(Editable mEdit)
            {
                user = mEdit.toString();
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after){}

            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });

        password.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void afterTextChanged(Editable mEdit)
            {
                pass = mEdit.toString();
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after){}

            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });

        address.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void afterTextChanged(Editable mEdit)
            {
                addr = mEdit.toString();
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after){}

            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });

        phoneNumber.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void afterTextChanged(Editable mEdit)
            {
                pNumber = mEdit.toString();
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after){}

            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });

        Email.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void afterTextChanged(Editable mEdit)
            {
                email = mEdit.toString();
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after){}

            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });
        Name.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void afterTextChanged(Editable mEdit)
            {
                name = mEdit.toString();
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after){}

            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });




        mFire = new Firebase("https://bazaar-7ee62.firebaseio.com/Bazaar/User");

        mButton = (Button)findViewById(R.id.btn_signup);
//        mStorage = FirebaseStorage.getInstance().getReference("Profile Pictures");
//        bazaar.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                Iterable<DataSnapshot> snapshotIterator = dataSnapshot.getChildren();
//                Iterator<DataSnapshot> iterator = snapshotIterator.iterator();
//                System.out.println(dataSnapshot.getChildren().getClass().toString());
//                while (iterator.hasNext()) {
//                    GenericTypeIndicator<ArrayList<UserInfo>> t = new GenericTypeIndicator<ArrayList<UserInfo>>() {
//                    };
//                    users = iterator.next().getValue(t);
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });






        //For profile Picture upload to the firebase from gallery

        mStorage = FirebaseStorage.getInstance().getReference();

        mSelectImage = (Button) findViewById(R.id.buttonSelectImage);

        //final StorageReference mountainsRef = mStorage.child("images");
        mSelectImage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, GALLERY_INTENT);

            }

        });

        //For profile Picture upload to the firebase from camera

     //   mCamStorage = FirebaseStorage.getInstance().getReference();

        myUploadbutton = (Button) findViewById(R.id.buttonUploadFromCamera);

        myUploadbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (intent.resolveActivity(getPackageManager())!= null) {
                    startActivityForResult(intent, CAMERA_REQUEST_CODE);
                }

            }

        });

        //For profile picture retrival from the firebase


        myImageView = (ImageView) findViewById(R.id.profilePicImageView);

    }

    public void registerUser(View view) {


        user = username.getText().toString();
        pass = password.getText().toString();
        addr = address.getText().toString();
        email = Email.getText().toString();
        pNumber = phoneNumber.getText().toString();
        name = Name.getText().toString();

        imagePath = downloadUri.toString();


        Firebase mRefChild = mFire.child(user);
        mRefChild.push();

        Firebase newChild = mRefChild.child("name");
        newChild.push();
        newChild.setValue(name);
        newChild = mRefChild.child("userName");
        newChild.setValue(user);
        newChild = mRefChild.child("address");
        newChild.setValue(addr);
        newChild = mRefChild.child("email");
        newChild.setValue(email);
        newChild = mRefChild.child("phoneNumber");
        newChild.setValue(pNumber);
        newChild = mRefChild.child("password");
        newChild.setValue(pass);
        newChild = mRefChild.child("profilePic_imageURL");
        newChild.setValue(imagePath);

        Intent intent = new Intent(RegisterActivity.this, SignInActivity.class);
        startActivity(intent);
//
//        DatabaseReference usersData = bazaar.child("User");
//
//        DatabaseReference childName = usersData.child("Name");
//        childName.setValue(name);
//        DatabaseReference childUserName = usersData.child("UserName");
//        childUserName.setValue(user);
//        DatabaseReference childPassword = usersData.child("Password");
//        childUserName.setValue(pass);
//        DatabaseReference childAddress = usersData.child("Address");
//        childUserName.setValue(addr);
//        DatabaseReference chilPhone = usersData.child("Phone Number");
//        childUserName.setValue(pNumber);
//        DatabaseReference childEmail = usersData.child("Email");
//        childUserName.setValue(email);
//
////
//
//
//        UserInfo temp = new UserInfo();
//        temp.setUserName(user);
//        temp.setPassword(pass);
//        temp.setProfilePic_imageURL(imagePath);
//        users.add(temp);
//
//        usersData.setValue(users);
//
//        usersData.push();

    }


//    public void loadImagefromGallery(View view) {
//        // Create intent to Open Image applications like Gallery, Google Photos
//        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
//                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        // Start the Intent
//        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        StorageReference userPicture = mStorage.child(user);
        StorageReference userProfilePicture = userPicture.child("Profile Picture");
        super.onActivityResult(requestCode, resultCode, data);
        // When an Image is picked from Gallery
        if (requestCode == GALLERY_INTENT && resultCode == RESULT_OK
                && null != data) {
            // Get the Image from data

            Uri selectedImage = data.getData();

            //Set the image into imageView
            myImageView.setImageURI(selectedImage);

            //UPLOAD IMAGE TO FIREBASE
            StorageReference filePath = userProfilePicture.child("ProfilePic_" + user);
//                String[] filePathColumn = {MediaStore.Images.Media.DATA};
            filePath.putFile(selectedImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    downloadUri = taskSnapshot.getDownloadUrl();
                    Picasso.with(RegisterActivity.this).load(downloadUri).fit().centerCrop().into(myImageView);
                    Toast.makeText(RegisterActivity.this, "Upload Done.", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    // Handle unsuccessful uploads
                    Toast.makeText(RegisterActivity.this, "Upload Unsuccessful.", Toast.LENGTH_SHORT).show();
                }
            });

        }
        //When image is picked from camera
        else if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK
                && null != data) {
            // Get the Image from Camera

            Uri cameraImage = data.getData();

            //Set the image into imageView
            myImageView.setImageURI(cameraImage);


            //UPLOAD IMAGE TO FIREBASE
            StorageReference filePathCamera = userProfilePicture.child("ProfilePic_" + user);

            filePathCamera.putFile(cameraImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    downloadUri = taskSnapshot.getDownloadUrl();
                    Picasso.with(RegisterActivity.this).load(downloadUri).fit().centerCrop().into(myImageView);
                    Toast.makeText(RegisterActivity.this, "Upload Done.", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    // Handle unsuccessful uploads
                    Toast.makeText(RegisterActivity.this, "Upload Unsuccessful.", Toast.LENGTH_SHORT).show();
                }
            });

        }


    }

    }


