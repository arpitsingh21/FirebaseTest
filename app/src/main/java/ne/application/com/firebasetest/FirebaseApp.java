package ne.application.com.firebasetest;

import android.app.Application;

/**
 * Created by Harikesh on 07/02/2019.
 */
public class FirebaseApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        com.google.firebase.FirebaseApp.initializeApp(getApplicationContext());
    }
}
