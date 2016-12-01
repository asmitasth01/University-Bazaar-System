package example.com.bazaar;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import example.com.bazaar.bean.ItemInfo;
import example.com.bazaar.bean.UserInfo;

public class ExchangeBuyerSideActivity extends Home {

    public android.content.Context context;
    DatabaseReference bazaar;
    private EditText itemDescription;
    private Spinner itemQuantity;

    private StorageReference mStorage;
    private ImageView myImageView;

    private Firebase mFire;
    private Button mButton;
    private Button mSelectImage;

    private Uri downloadUri;
    private String imagePath;

    public static String senderUserName;
    public static String receiverUserName;
    private static String itemDes;
    public static String itemQuan;

    private static int i = 0;

    SignInActivity signInActivity;
    ItemInfo itemInfo;

    private static int RESULT_LOAD_IMG = 1;
    private static final int GALLERY_INTENT = 2;
    String imgDecodableString;

    public ExchangeBuyerSideActivity(){
        signInActivity = new SignInActivity();
        itemInfo = new ItemInfo();
        i++;

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //inflate your activity layout here!
        View contentView = inflater.inflate(R.layout.activity_exchange_buyer_side, null, false);
        drawer.addView(contentView, 0);
        fab.setVisibility(View.INVISIBLE);
        //Firebase.setAndroidContext(this);

        itemDescription = (EditText)findViewById(R.id.exchange_buyer_itemDes);
        itemQuantity = (Spinner) findViewById(R.id.spinExchangeBuyer);
        senderUserName = signInActivity.getUsername();
        receiverUserName = new Exchange_item_details().getUserName();
        System.out.println(receiverUserName+"This is the userName\n\n\n\n\n");


        mFire = new Firebase("https://bazaar-7ee62.firebaseio.com/Bazaar").child("Exchange_Offers");
        mButton = (Button)findViewById(R.id.submitExchangeOffer);

        mStorage = FirebaseStorage.getInstance().getReference();

        mSelectImage = (Button)findViewById(R.id.buttonSelectImage);

        //final StorageReference mountainsRef = mStorage.child("images");
        mSelectImage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, GALLERY_INTENT);

            }

        });

        myImageView = (ImageView) findViewById(R.id.imageViewExchangeBuyer);

    }

    public void PostExchange(View view) {


        //  System.out.println("This is the downolad URL"+ downloadUri.toString());
        // if (downloadUri!= null) {

        //   }
        itemDes = itemDescription.getText().toString();
        itemQuan = itemQuantity.getSelectedItem().toString();


        imagePath = downloadUri.toString();

        Firebase mRefChild = mFire.child(" "+i);
        mRefChild.push();

        Firebase newChild = mRefChild.child("itemDescription");
        newChild.push();
        newChild.setValue(itemDes);
        newChild = mRefChild.child("itemQuantity");
        newChild.setValue(itemQuan);
        newChild = mRefChild.child("exchange_ItemURL");
        newChild.setValue(imagePath);
        newChild = mRefChild.child("senderUserName");
        newChild.setValue(senderUserName);
        newChild = mRefChild.child("receiveUserName");
        newChild.setValue(receiverUserName);


        Intent intent = new Intent(ExchangeBuyerSideActivity.this, ExchangeOfferSubmittedActivity.class);
        startActivity(intent);
    }


//
//    public void openGetBankInformation(View view)
//    {
//        Intent intent = new Intent(this, BankInfoActivity.class);
//        startActivity(intent);
//    }

//    public void loadImagefromGallery(View view) {
//        // Create intent to Open Image applications like Gallery, Google Photos
//        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
//                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        // Start the Intent
//        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
//    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        final String userName = new SignInActivity().getUsername();

        StorageReference sellItemPicture = mStorage.child(userName);
        StorageReference itemPicture = sellItemPicture.child("Exchange Items");
        super.onActivityResult(requestCode, resultCode, data);
        // When an Image is picked from Gallery
        if (requestCode == GALLERY_INTENT && resultCode == RESULT_OK
                && null != data) {
            // Get the Image from data

            Uri selectedImage = data.getData();

            //Set the image into imageView
            myImageView.setImageURI(selectedImage);

            //UPLOAD IMAGE TO FIREBASE
            StorageReference filePath = itemPicture.child("SellItem_" + selectedImage.getLastPathSegment());
//                String[] filePathColumn = {MediaStore.Images.Media.DATA};
            filePath.putFile(selectedImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    downloadUri = taskSnapshot.getDownloadUrl();
                   // ItemInfo item = new ItemInfo(iDescription,iDes, iQuantity, iPrice, sType,imagePath, userName);
                    Picasso.with(ExchangeBuyerSideActivity.this).load(downloadUri).fit().centerCrop().into(myImageView);
                    Toast.makeText(ExchangeBuyerSideActivity.this, "Upload Done.", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    // Handle unsuccessful uploads
                    Toast.makeText(ExchangeBuyerSideActivity.this, "Upload Unsuccessful.", Toast.LENGTH_SHORT).show();
                }
            });

        }

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
//                ImageView imageViewExchangeBuyer = (ImageView) findViewById(R.id.imageViewExchangeBuyer);
//                // Set the Image in ImageView after decoding the String
//                imageViewExchangeBuyer.setImageBitmap(BitmapFactory
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
}
