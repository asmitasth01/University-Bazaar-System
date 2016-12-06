package example.com.bazaar;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.client.Firebase;

public class setting extends Home {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //inflate your activity layout here!
        View contentView = inflater.inflate(R.layout.activity_setting, null, false);
        drawer.addView(contentView, 0);
        fab.setVisibility(View.INVISIBLE);
        Firebase.setAndroidContext(this);
        EditText pass1 = (EditText) findViewById(R.id.editText18);
        pass1.getText().toString();
        Button button1 = (Button) findViewById(R.id.button10);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }
}
