package example.com.bazaar;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
import android.widget.Spinner;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import example.com.bazaar.bean.ItemInfo;

public class sellActivity extends Home {

    public android.content.Context context;
    DatabaseReference bazaar;
    private EditText itemDescription;
    private EditText itemQuantity;
    private EditText itemPrice;
    private EditText itemDes;
    private Spinner sellType;
    private Firebase mFire;
    private Button mButton;
    private ArrayList<ItemInfo> sellItems;
    //For Image Storage to database (For sell items)
    private Button mSelectImage;
    private StorageReference mStorage;
    private ProgressDialog progress;
    private String userName;
    private Uri downloadUri;
    private String imagePath;
    //For Image retrival from database (For sell items)
    private Button myUploadbutton;
    // private StorageReference mCamStorage;
    private ImageView myImageView;
    public static String iDescription;
    public static String iDes;
    private static String iQuantity;
    public static String iPrice;
    private static String sType;
    private static final int GALLERY_INTENT = 2;
    private static final int CAMERA_REQUEST_CODE = 1;

    public sellActivity() {
        sellItems = new ArrayList<>();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //inflate your activity layout here!
        View contentView = inflater.inflate(R.layout.activity_sell, null, false);
        drawer.addView(contentView, 0);
        fab.setVisibility(View.INVISIBLE);
        Firebase.setAndroidContext(this);
        itemDescription = (EditText) findViewById(R.id.itemDescription);
        itemDes = (EditText) findViewById(R.id.itemDes);
        itemPrice = (EditText) findViewById(R.id.itemPrice);
        itemQuantity = (EditText) findViewById(R.id.itemQuantity);
        sellType = (Spinner) findViewById((R.id.TradeType));
        itemDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable mEdit) {
                iDescription = mEdit.toString();
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        itemDes.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable mEdit) {
                iDes = mEdit.toString();
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        itemPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable mEdit) {
                iPrice = mEdit.toString();
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        itemQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable mEdit) {
                iQuantity = mEdit.toString();
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        mFire = new Firebase("https://bazaar-7ee62.firebaseio.com/Bazaar");
        mButton = (Button) findViewById(R.id.SellButton);
        mStorage = FirebaseStorage.getInstance().getReference();
        mSelectImage = (Button) findViewById(R.id.SelectSellItemFromGallery);
        mSelectImage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, GALLERY_INTENT);
            }

        });

        //For profile Picture upload to the firebase from camera
        myUploadbutton = (Button) findViewById(R.id.SelectSellItemFromCamera);
        myUploadbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, CAMERA_REQUEST_CODE);
                }
            }

        });

        //For profile picture retrival from the firebase
        myImageView = (ImageView) findViewById(R.id.SellItemImage);
    }

    public void UploadSellItems(View view) {
        boolean goAhead = true;
        final String userName;
        try {
            iDescription = itemDescription.getText().toString();
            iDes = itemDes.getText().toString();
            iPrice = itemPrice.getText().toString();
            iQuantity = itemQuantity.getText().toString();
            sType = sellType.getSelectedItem().toString();
            imagePath = downloadUri.toString();
        } catch (NullPointerException e) {
            Toast.makeText(sellActivity.this, "Complete all the fields above", Toast.LENGTH_LONG).show();
            goAhead = false;
        } catch (IllegalStateException e) {
            Toast.makeText(sellActivity.this, "Please wait until Upload done notification before proceeding.", Toast.LENGTH_LONG).show();
            goAhead = false;
        }
        userName = new SignInActivity().getUsername();
        if (sType.compareTo("Sell") == 0) {
            mFire = mFire.child("Buy_Items");

        } else if (sType.compareTo("Exchange") == 0) {
            mFire = mFire.child("Exchange_Items");
        } else {
            mFire = mFire.child("Borrow_Items");
        }
        if (goAhead == true) {

            Firebase mRefChild = mFire.child(iDescription);
            mRefChild.push();
            Firebase newChild = mRefChild.child("itemDescription");
            newChild.push();
            newChild.setValue(iDescription);
            newChild = mRefChild.child("itemQuantity");
            newChild.setValue(iQuantity);
            newChild = mRefChild.child("itemPrice");
            newChild.setValue(iPrice);
            newChild = mRefChild.child("sellType");
            newChild.setValue(sType);
            newChild = mRefChild.child("sellItem_imageURL");
            newChild.setValue(imagePath);
            newChild = mRefChild.child("itemDes");
            newChild.setValue(iDes);
            newChild = mRefChild.child("userName");
            newChild.setValue(userName);
            Intent intent = new Intent(sellActivity.this, BankInfoActivity.class);
            startActivity(intent);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (iDescription == null || iQuantity == null || iPrice == null) {
            Toast.makeText(sellActivity.this, "Please complete the fields above.", Toast.LENGTH_SHORT).show();
        } else {
            final String userName = new SignInActivity().getUsername();
            StorageReference sellItemPicture = mStorage.child(userName);
            StorageReference itemPicture = sellItemPicture.child("Sell Items");
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
                StorageReference filePath = itemPicture.child("SellItem_" + selectedImage.getLastPathSegment());
                filePath.putFile(selectedImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        downloadUri = taskSnapshot.getDownloadUrl();
                        ItemInfo item = new ItemInfo(iDescription, iDes, iQuantity, iPrice, sType, imagePath, userName);
                        Picasso.with(sellActivity.this).load(downloadUri).into(myImageView);
                        Toast.makeText(sellActivity.this, "Upload Done.", Toast.LENGTH_SHORT).show();
                        progress.dismiss();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle unsuccessful uploads
                        Toast.makeText(sellActivity.this, "Upload Unsuccessful.", Toast.LENGTH_SHORT).show();
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
                StorageReference filePathCamera = itemPicture.child("SellItem_" + cameraImage.getLastPathSegment());
                filePathCamera.putFile(cameraImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        downloadUri = taskSnapshot.getDownloadUrl();
                        ItemInfo item = new ItemInfo(iDescription, iDes, iQuantity, iPrice, sType, imagePath, userName);
                        Picasso.with(sellActivity.this).load(downloadUri).into(myImageView);
                        Toast.makeText(sellActivity.this, "Upload Done.", Toast.LENGTH_SHORT).show();
                        progress.dismiss();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle unsuccessful uploads
                        Toast.makeText(sellActivity.this, "Upload Unsuccessful.", Toast.LENGTH_SHORT).show();
                        progress.dismiss();

                    }
                });

            }
        }
    }
}


