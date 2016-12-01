package example.com.bazaar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.client.Firebase;


public class FormClubActivity extends Home{

    private Firebase mFire;
    private Button mButton;
    private String input_club_name;
    private String input_club_category;
    private String input_club_desc;
    private static int id = 0;

    public FormClubActivity(){
        id++;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //inflate your activity layout here!
        View contentView = inflater.inflate(R.layout.activity_form_club, null, false);
        drawer.addView(contentView, 0);
        fab.setVisibility(View.INVISIBLE);

        EditText cName = (EditText)findViewById(R.id.input_club_name);
        EditText cCategory = (EditText)findViewById(R.id.input_club_category);
        EditText cDesc = (EditText)findViewById(R.id.input_club_description);

        cName.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void afterTextChanged(Editable mEdit)
            {
                input_club_name = mEdit.toString();
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after){}

            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });

        cCategory.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void afterTextChanged(Editable mEdit)
            {
                input_club_category = mEdit.toString();
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after){}

            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });

        cDesc.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void afterTextChanged(Editable mEdit)
            {
                input_club_desc = mEdit.toString();
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after){}

            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });

        System.out.println("Club name is: "+ input_club_category);

        final SignInActivity login = new SignInActivity();

        mFire = new Firebase("https://bazaar-7ee62.firebaseio.com/Bazaar/Clubs");
        mButton = (Button)findViewById(R.id.create_club_button);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                mFire.push();

                Firebase mRefChild = mFire.push();
                mRefChild.push();

                Firebase newChild = mRefChild.child("clubName");
                newChild.setValue(input_club_name);
                newChild = mRefChild.child("clubCategory");
                newChild.setValue(input_club_category);
                newChild = mRefChild.child("clubDescription");
                newChild.setValue(input_club_desc);
                newChild = mRefChild.child("clubAdmin");
                newChild.setValue(login.getUsername());

                startActivity(new Intent(FormClubActivity.this, ClubActivity.class));
            }
        });

    }

}
