package example.com.bazaar;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by sumanshrestha on 11/29/16.
 */

public class FireApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Firebase.setAndroidContext(this);
    }
}
