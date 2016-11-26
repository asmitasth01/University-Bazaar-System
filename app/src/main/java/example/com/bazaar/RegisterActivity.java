package example.com.bazaar;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.ads.formats.NativeAd;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import example.com.bazaar.bean.UserInfo;

public class RegisterActivity extends AppCompatActivity {

    public android.content.Context context;
    DatabaseReference bazaar;
    private EditText username;
    private EditText password;
    private ArrayList<UserInfo> users;

    //For Image Storage to database (For Profile Picture)
    private Button mSelectImage;
    private StorageReference mStorage;



    //For Image retrival from database (For Profile Picture)
    private Button myUploadbutton;
   // private StorageReference mCamStorage;
    private ImageView myImageView;

    String user;
    String pass;


    private static final int GALLERY_INTENT = 2;
    private static final int CAMERA_REQUEST_CODE = 1;

    public RegisterActivity() {
        users = new ArrayList<>();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        bazaar = FirebaseDatabase.getInstance().getReference("Bazaar");
        mStorage = FirebaseStorage.getInstance().getReference("Profile Pictures");
        bazaar.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> snapshotIterator = dataSnapshot.getChildren();
                Iterator<DataSnapshot> iterator = snapshotIterator.iterator();
                System.out.println(dataSnapshot.getChildren().getClass().toString());
                while (iterator.hasNext()) {
                    GenericTypeIndicator<ArrayList<UserInfo>> t = new GenericTypeIndicator<ArrayList<UserInfo>>() {
                    };
                    users = iterator.next().getValue(t);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        username = (EditText) findViewById(R.id.input_username);
        password = (EditText) findViewById(R.id.input_password);


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


//    public void loadImagefromGallery(View view) {
//        // Create intent to Open Image applications like Gallery, Google Photos
//        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
//                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        // Start the Intent
//        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        StorageReference userProfilePicture = mStorage.child("User Photo");
        super.onActivityResult(requestCode, resultCode, data);
        // When an Image is picked from Gallery
        if (requestCode == GALLERY_INTENT && resultCode == RESULT_OK
                && null != data) {
            // Get the Image from data

            Uri selectedImage = data.getData();

            //Set the image into imageView
            myImageView.setImageURI(selectedImage);
            StorageReference filePath = userProfilePicture.child("ProfilePic_" + user);
//                String[] filePathColumn = {MediaStore.Images.Media.DATA};
            filePath.putFile(selectedImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Uri downloadUri = taskSnapshot.getDownloadUrl();
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

            //
            Bundle extras = data.getExtras();
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] dataBAOS = baos.toByteArray();

            //Set the image into imageView
            myImageView.setImageBitmap(bitmap);

            //Upload the pic to firebase

           // Uri CameraImage = data.getData();

            StorageReference filePathCamera = userProfilePicture.child("ProfilePic_" + user);

//                String[] filePathColumn = {MediaStore.Images.Media.DATA};
            UploadTask uploadTask = filePathCamera.putBytes(dataBAOS);

            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
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


