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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class CreateEventActivity extends Home {
    private static int RESULT_LOAD_IMG = 1;
    String imgDecodableString;
    DatabaseReference bazaar;
    private Firebase mFire;
    private Button mButton;
    private String input_eventName;
    private String input_eventDate;
    private String input_eventTime;
    private String input_eventDescription;
    private String input_location;
    private String imageUrl;

    Uri downloadUri;
    //For Image Storage to database (For Profile Picture)
    private Button mSelectImage;
    private StorageReference mStorage;
    private ImageView myImageView;

    private static final int GALLERY_INTENT = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //inflate your activity layout here!
        View contentView = inflater.inflate(R.layout.activity_create_event, null, false);
        drawer.addView(contentView, 0);
//        fab.setVisibility(View.INVISIBLE);
//        listView.setVisibility(View.INVISIBLE);
        Firebase.setAndroidContext(this);

        EditText eName = (EditText)findViewById(R.id.input_event_name);
        EditText eDate = (EditText)findViewById(R.id.input_event_date);
        EditText eTime = (EditText)findViewById(R.id.input_event_time);
        EditText eDesc = (EditText)findViewById(R.id.input_event_description);
        EditText eLocation = (EditText)findViewById(R.id.input_location);

//        input_eventName = eName.getText().toString();
//        input_eventDate = eDate.getText().toString();
//        input_eventTime = eTime.getText().toString();
//        input_eventDescription = eDesc.getText().toString();

        eName.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void afterTextChanged(Editable mEdit)
            {
                input_eventName = mEdit.toString();
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after){}

            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });

        eDate.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void afterTextChanged(Editable mEdit)
            {
                input_eventDate = mEdit.toString();
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after){}

            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });

        eTime.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void afterTextChanged(Editable mEdit)
            {
                input_eventTime = mEdit.toString();
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after){}

            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });

        eDesc.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void afterTextChanged(Editable mEdit)
            {
                input_eventDescription = mEdit.toString();
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after){}

            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });

        eLocation.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void afterTextChanged(Editable mEdit)
            {
                 input_location= mEdit.toString();
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after){}

            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });

        mFire = new Firebase("https://bazaar-7ee62.firebaseio.com/Bazaar/Events");
        mButton = (Button)findViewById(R.id.create_event_button);
        System.out.println("Event name : "+input_eventName);

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

        myImageView = (ImageView) findViewById(R.id.imgView);


        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                mFire.push();

                Firebase mRefChild = mFire.child(input_eventName);
                mRefChild.push();

                Firebase newChild = mRefChild.child("eventName");
                newChild.setValue(input_eventName);
                newChild = mRefChild.child("eventDate");
                newChild.setValue(input_eventDate);
                newChild = mRefChild.child("eventTime");
                newChild.setValue(input_eventTime);
                newChild = mRefChild.child("eventDescription");
                newChild.setValue(input_eventDescription);
                newChild = mRefChild.child("eventImageUrl");
                newChild.setValue(imageUrl);
                newChild = mRefChild.child("eventLocation");
                newChild.setValue(input_location);

                startActivity(new Intent(CreateEventActivity.this, EventPage.class));
            }
        });
    }

    public void loadImagefromGallery(View view) {
        // Create intent to Open Image applications like Gallery, Google Photos
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Start the Intent
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        try {
//            // When an Image is picked
//            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
//                    && null != data) {
//                // Get the Image from data
//
//                Uri selectedImage = data.getData();
//                String[] filePathColumn = { MediaStore.Images.Media.DATA };
//
//                // Get the cursor
//                Cursor cursor = getContentResolver().query(selectedImage,
//                        filePathColumn, null, null, null);
//                // Move to first row
//                cursor.moveToFirst();
//
//                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//                imgDecodableString = cursor.getString(columnIndex);
//                cursor.close();
//                ImageView imgView = (ImageView) findViewById(R.id.imgView);
//                // Set the Image in ImageView after decoding the String
//                imgView.setImageBitmap(BitmapFactory
//                        .decodeFile(imgDecodableString));
//
//            } else {
//                Toast.makeText(this, "You haven't picked Image",
//                        Toast.LENGTH_LONG).show();
//            }
//        } catch (Exception e) {
//            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
//                    .show();
//        }
//
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
            StorageReference filePath = userProfilePicture.child("EventPic_" + input_eventName);
//                String[] filePathColumn = {MediaStore.Images.Media.DATA};
            filePath.putFile(selectedImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    downloadUri = taskSnapshot.getDownloadUrl();
                    imageUrl = downloadUri.toString();
                    Picasso.with(CreateEventActivity.this).load(downloadUri).fit().centerCrop().into(myImageView);
                    Toast.makeText(CreateEventActivity.this, "Upload Done.", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    // Handle unsuccessful uploads
                    Toast.makeText(CreateEventActivity.this, "Upload Unsuccessful.", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

}