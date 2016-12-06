package example.com.bazaar;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
    private ProgressDialog progress;
    private Uri downloadUri;
    private String imagePath;
    public static String senderUserName;
    public static String receiverUserName;
    private static String itemDes;
    public static String itemQuan;
    private boolean goAhead;
    private static int i = 0;
    SignInActivity signInActivity;
    ItemInfo itemInfo;
    private static final int GALLERY_INTENT = 2;

    public ExchangeBuyerSideActivity() {
        signInActivity = new SignInActivity();
        itemInfo = new ItemInfo();
        i++;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        goAhead = true;
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //inflate your activity layout here!
        View contentView = inflater.inflate(R.layout.activity_exchange_buyer_side, null, false);
        drawer.addView(contentView, 0);
        fab.setVisibility(View.INVISIBLE);
        itemDescription = (EditText) findViewById(R.id.exchange_buyer_itemDes);
        itemQuantity = (Spinner) findViewById(R.id.spinExchangeBuyer);
        senderUserName = signInActivity.getUsername();
        receiverUserName = new Exchange_item_details().getUserName();
        mFire = new Firebase("https://bazaar-7ee62.firebaseio.com/Bazaar").child("Exchange_Offers");
        mButton = (Button) findViewById(R.id.submitExchangeOffer);
        mStorage = FirebaseStorage.getInstance().getReference();
        mSelectImage = (Button) findViewById(R.id.buttonSelectImage);
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

        goAhead = true;

        try {
            itemDes = itemDescription.getText().toString();
            itemQuan = itemQuantity.getSelectedItem().toString();


            imagePath = downloadUri.toString();
        } catch (NullPointerException e) {
            Toast.makeText(ExchangeBuyerSideActivity.this, "Complete all the fields above", Toast.LENGTH_LONG).show();
            goAhead = false;
        } catch (IllegalStateException e) {
            Toast.makeText(ExchangeBuyerSideActivity.this, "Please wait until Upload done notification before proceeding.", Toast.LENGTH_LONG).show();
            goAhead = false;
        }
        if (goAhead == true) {

            Firebase mRefChild = mFire.child(" " + i);
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
    }


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
            progress = new ProgressDialog(this);
            progress.setMessage("Uploading....");
            progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progress.setIndeterminate(true);
            progress.setProgress(0);
            progress.show();

            Uri selectedImage = data.getData();
            //Set the image into imageView
            myImageView.setImageURI(selectedImage);
            //UPLOAD IMAGE TO FIREBASE
            StorageReference filePath = itemPicture.child("SellItem_" + selectedImage.getLastPathSegment());
            filePath.putFile(selectedImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    downloadUri = taskSnapshot.getDownloadUrl();
                    Picasso.with(ExchangeBuyerSideActivity.this).load(downloadUri).fit().centerCrop().into(myImageView);
                    Toast.makeText(ExchangeBuyerSideActivity.this, "Upload Done.", Toast.LENGTH_SHORT).show();
                    progress.dismiss();

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

}
