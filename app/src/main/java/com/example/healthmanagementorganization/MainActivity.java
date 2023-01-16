package com.example.healthmanagementorganization;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.icu.text.CaseMap;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private AppCompatButton main_ACBTN_logout;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();

        findViews();
        initViews();

        this.setTitle("" + mAuth.getCurrentUser().getEmail());

    }

    private void initViews() {
        main_ACBTN_logout.setOnClickListener(v -> {
            mAuth.signOut();
            // return to login screen
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void findViews() {
        main_ACBTN_logout = findViewById(R.id.main_ACBTN_logout);
    }
}