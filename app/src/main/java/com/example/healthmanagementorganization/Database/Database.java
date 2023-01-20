package com.example.healthmanagementorganization.Database;


import android.util.Log;

import androidx.annotation.NonNull;

import com.example.healthmanagementorganization.App;
import com.example.healthmanagementorganization.General.General;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Database {
    private FirebaseAuth mAuth;
    private FirebaseDatabase db;
    private static Database single_instance = null;

    private Database() {
        mAuth = App.getmAuth();
        db = App.getFirebaseDatabase();
    }

    public static Database getInstance() {
        if (single_instance == null) single_instance = new Database();

        return single_instance;
    }


    public String getDocNameFormFB(String uid) {
        DatabaseReference mDatabase = db.getReference().child(General.FB_Doctors).child(uid).child(General.FB_firstName);
        final String[] docName = new String[1];
        mDatabase.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                docName[0] = task.getResult().getValue(String.class);
                Log.d("DUFAM", "" + docName[0]);
            }
        });
        Log.d("DUFAM", "" + docName[0]);
        return docName[0];
    }

    public String getPatNameFormFB(String uid) {
        DatabaseReference mDatabase = db.getReference().child(General.FB_Patients).child(uid).child(General.FB_firstName);
        final String[] patName = new String[1];
        mDatabase.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                patName[0] = task.getResult().getValue(String.class);
                Log.d("DUFAM", "" + patName[0]);
            }
        });
        Log.d("DUFAM", "" + patName[0]);
        return patName[0];
    }


}
