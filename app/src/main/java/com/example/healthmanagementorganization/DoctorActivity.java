package com.example.healthmanagementorganization;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import com.example.healthmanagementorganization.Fragments.Doctor.DoctorInfoFragment;
import com.example.healthmanagementorganization.Fragments.Doctor.DoctorInfoFragment_Callback;
import com.example.healthmanagementorganization.Fragments.Doctor.DoctorMainFragment;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;

public class DoctorActivity extends AppCompatActivity {
    //Firebase
    FirebaseAuth mAuth;

    //Design
    private AppCompatTextView doc_ACTV_title;

    private NavigationBarView doctor_bottom_navigation;

    //Fragments
    private DoctorMainFragment doctorMainFragment;
    private DoctorInfoFragment doctorInfoFragment;

    DoctorInfoFragment_Callback doctorInfoFragment_Callback = new DoctorInfoFragment_Callback() {
        @Override
        public void logOut() {
            signOut();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);
        mAuth = FirebaseAuth.getInstance();
        findViews();
        doc_ACTV_title.setText("Info");
        getSupportFragmentManager().beginTransaction().replace(R.id.doc_FRCV_fragment, doctorInfoFragment).commit();


        initViews();
    }

    private void initViews() {
        doctor_bottom_navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.doc_info:
                        doc_ACTV_title.setText("Info");
                        getSupportFragmentManager().beginTransaction().replace(R.id.doc_FRCV_fragment, doctorInfoFragment).commit();
                        break;
                    case R.id.doc_home:
                        doc_ACTV_title.setText("Home");
                        getSupportFragmentManager().beginTransaction().replace(R.id.doc_FRCV_fragment, doctorMainFragment).commit();
                        break;
                }
                return true;
            }
        });


    }

    private void findViews() {
        doctor_bottom_navigation = findViewById(R.id.doctor_bottom_navigation);
        doc_ACTV_title = findViewById(R.id.doc_ACTV_title);

        doctorInfoFragment = new DoctorInfoFragment();
        doctorInfoFragment.setCallback(doctorInfoFragment_Callback);
        doctorMainFragment = new DoctorMainFragment();
    }

    public void signOut() {

        mAuth.signOut();
        // return to login screen
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}