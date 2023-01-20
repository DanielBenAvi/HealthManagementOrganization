package com.example.healthmanagementorganization.Database;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;


public class Database {
    private FirebaseAuth mAuth;
    private FirebaseDatabase db;
    private static Database single_instance = null;

    private Database() {
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
    }

    public static Database getInstance() {
        if (single_instance == null) single_instance = new Database();

        return single_instance;
    }


}
