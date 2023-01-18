package com.example.healthmanagementorganization.Model.Person;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.healthmanagementorganization.MainActivity;
import com.example.healthmanagementorganization.Model.DataBase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Patient extends Person implements DataBase {


    public Patient() {
        super();
    }


    @Override
    public void loadToDataBase() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference("Patients");
        String id = this.getUid();
        Log.d("DUFAM", "loadToDataBase: " + id);
        ref.child(id).setValue(this).addOnFailureListener(e -> {
            Log.d("DUFAM", "loadToDataBase: " + e.getMessage());
        });
    }



}
