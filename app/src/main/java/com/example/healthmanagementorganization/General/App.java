package com.example.healthmanagementorganization.General;

import android.app.Application;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;

public class App extends Application {

    private static FirebaseAuth mAuth;


    @Override
    public void onCreate() {
        super.onCreate();
        // init FireBase
        mAuth = FirebaseAuth.getInstance();

        Log.d("Dufam", "app");
        // Required initialization logic here!
    }


    public static FirebaseAuth getmAuth() {
        return mAuth;
    }

}
