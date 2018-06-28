package org.bitm.pencilbox.firebaseauthpb5;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

public class MyTodoApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
