package com.example.healthmanagementorganization;

import android.app.Application;
import android.util.Log;

import com.example.healthmanagementorganization.Database.Database;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class App extends Application {

    private static FirebaseDatabase firebaseDatabase;
    private static FirebaseAuth mAuth;


    private Database database;

    @Override
    public void onCreate() {
        super.onCreate();
        // init FireBase
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        // init my DB
        database = Database.getInstance();

        Log.d("Dufam", "app");
        // Required initialization logic here!
    }

    public static FirebaseDatabase getFirebaseDatabase() {
        return firebaseDatabase;
    }


    public static FirebaseAuth getmAuth() {
        return mAuth;
    }

}
