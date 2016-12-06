package example.com.bazaar;


import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import example.com.bazaar.bean.UserInfo;


public class RegisterActivity extends AppCompatActivity {

    public android.content.Context context;
    private EditText username;
    private EditText password;
    private EditText rpassword;
    private EditText Name;
    private EditText address;
    private EditText phoneNumber;
    private EditText Email;
    private ArrayList<UserInfo> users;
    private Firebase mFire;
    private Button mButton;
    private boolean goAhead;
    private Button mSelectImage;
    private StorageReference mStorage;
    Uri downloadUri;
    String imagePath;

    //For Image retrival from database (For Profile Picture)
    private Button myUploadbutton;
    // private StorageReference mCamStorage;
    private ImageView myImageView;
    private ProgressDialog progress;
    public static String user;
    private static String pass;
    private static String rpass;
    private static String addr;
    private static String pNumber;
    private static String name;
    private static String email;
    private static final int GALLERY_INTENT = 2;
    private static final int CAMERA_REQUEST_CODE = 1;

    private Firebase mRef;


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
        rpassword = (EditText) findViewById(R.id.input_reEnterPassword);
        Name = (EditText) findViewById(R.id.input_name);
        address = (EditText) findViewById(R.id.input_address);
        phoneNumber = (EditText) findViewById(R.id.input_mobile);
        Email = (EditText) findViewById(R.id.input_email);
        TextView alreadyReg = (TextView) findViewById(R.id.link_login);
        alreadyReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, SignInActivity.class));
            }
        });

        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable mEdit) {
                user = mEdit.toString();
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable mEdit) {
                pass = mEdit.toString();
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        address.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable mEdit) {
                addr = mEdit.toString();
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        phoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable mEdit) {
                pNumber = mEdit.toString();
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        Email.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable mEdit) {
                email = mEdit.toString();
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        Name.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable mEdit) {
                name = mEdit.toString();
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

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

        mFire = new Firebase("https://bazaar-7ee62.firebaseio.com/Bazaar/User");
        mButton = (Button) findViewById(R.id.btn_signup);

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

        myUploadbutton = (Button) findViewById(R.id.buttonUploadFromCamera);
        myUploadbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, CAMERA_REQUEST_CODE);
                }
            }

        });

        //For profile picture retrival from the firebase
        myImageView = (ImageView) findViewById(R.id.profilePicImageView);
    }
    public void registerUser(View view) {
        goAhead = true;
        try {
            user = username.getText().toString();
            pass = password.getText().toString();
            rpass = rpassword.getText().toString();
            addr = address.getText().toString();
            email = Email.getText().toString();
            pNumber = phoneNumber.getText().toString();
            name = Name.getText().toString();
            imagePath = downloadUri.toString();
            if (pass.compareTo(rpass) != 0) {
                Toast.makeText(RegisterActivity.this, "Password and Re-enter Password not matching", Toast.LENGTH_LONG).show();
                goAhead = false;
            }
        } catch (NullPointerException e) {
            Toast.makeText(RegisterActivity.this, "Complete all the fields above", Toast.LENGTH_LONG).show();
            goAhead = false;
        } catch (Exception e) {
            Toast.makeText(RegisterActivity.this, "Please wait until Upload done notification before proceeding.", Toast.LENGTH_LONG).show();
            goAhead = false;
        }


        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getEmail().compareTo(email) == 0) {
                goAhead = false;
                Toast.makeText(RegisterActivity.this, "Account already created using this email. Try Login", Toast.LENGTH_LONG).show();
            }
            if (users.get(i).getUserName().compareTo(user) == 0) {
                goAhead = false;
                Toast.makeText(RegisterActivity.this, "Username already exists. Choose another Username", Toast.LENGTH_LONG).show();

            }
        }

        if (goAhead == true) {
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
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        StorageReference userProfilePicture;
        if (user == null || name == null || addr == null || email == null || pNumber == null || password == null) {
            Toast.makeText(RegisterActivity.this, "Please complete the fields above.", Toast.LENGTH_SHORT).show();
        } else {
            StorageReference userPicture = mStorage.child(user);
            userProfilePicture = userPicture.child("Profile Picture");
            super.onActivityResult(requestCode, resultCode, data);
            // When an Image is picked from Gallery
            if (requestCode == GALLERY_INTENT && resultCode == RESULT_OK
                    && null != data) {
                progress = new ProgressDialog(this);
                progress.setMessage("Uploading....");
                progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progress.setIndeterminate(true);
                progress.setProgress(0);
                progress.show();
                // Get the Image from data
                Uri selectedImage = data.getData();
                //Set the image into imageView
                myImageView.setImageURI(selectedImage);
                //UPLOAD IMAGE TO FIREBASE
                StorageReference filePath = userProfilePicture.child("ProfilePic_" + user);
                filePath.putFile(selectedImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        downloadUri = taskSnapshot.getDownloadUrl();
                        Picasso.with(RegisterActivity.this).load(downloadUri).into(myImageView);
                        Toast.makeText(RegisterActivity.this, "Upload Done.", Toast.LENGTH_SHORT).show();
                        progress.dismiss();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle unsuccessful uploads
                        Toast.makeText(RegisterActivity.this, "Upload Unsuccessful.", Toast.LENGTH_SHORT).show();
                        progress.dismiss();
                    }
                });

            }
            //When image is picked from camera
            else if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK
                    && null != data) {
                progress = new ProgressDialog(this);
                progress.setMessage("Uploading....");
                progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progress.setIndeterminate(true);
                progress.setProgress(0);
                progress.show();

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
                        Picasso.with(RegisterActivity.this).load(downloadUri).into(myImageView);
                        Toast.makeText(RegisterActivity.this, "Upload Done.", Toast.LENGTH_SHORT).show();
                        progress.dismiss();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle unsuccessful uploads
                        Toast.makeText(RegisterActivity.this, "Upload Unsuccessful.", Toast.LENGTH_SHORT).show();
                        progress.dismiss();
                    }
                });

            }
        }

    }
}


