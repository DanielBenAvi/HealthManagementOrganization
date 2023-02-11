package com.example.healthmanagementorganization;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import com.example.healthmanagementorganization.Fragments.Doctor.DoctorInfoFragment;
import com.example.healthmanagementorganization.Fragments.Doctor.DoctorInfoFragment_Callback;
import com.example.healthmanagementorganization.Fragments.Doctor.DoctorMainFragment;
import com.example.healthmanagementorganization.Fragments.Doctor.DoctorTasksFragment;
import com.example.healthmanagementorganization.General.General;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class DoctorActivity extends AppCompatActivity {
    //Firebase
    FirebaseAuth mAuth;

    //Design
    private AppCompatTextView doc_ACTV_title;

    private NavigationBarView doctor_bottom_navigation;

    //Fragments
    private DoctorMainFragment doctorMainFragment;
    private DoctorInfoFragment doctorInfoFragment;
    private DoctorTasksFragment doctorTasksFragment;

    DoctorInfoFragment_Callback doctorInfoFragment_Callback = this::signOut;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);
        mAuth = FirebaseAuth.getInstance();
        findViews();

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference mDatabase = db.getReference();
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(General.FB_Doctors).child(Objects.requireNonNull(mAuth.getCurrentUser()).getUid()).exists()) {
                    //do ur stuff
                    doc_ACTV_title.setText("Hello " + dataSnapshot.child(General.FB_Doctors).child(mAuth.getCurrentUser().getUid()).child(General.FB_firstName).getValue(String.class));
                }  //do something if not exists
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
        doc_ACTV_title.setText("Info");
        getSupportFragmentManager().beginTransaction().replace(R.id.doc_FRCV_fragment, doctorInfoFragment).commit();


        initViews();
    }

    @SuppressLint("NonConstantResourceId")
    private void initViews() {
        doctor_bottom_navigation.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.doc_info:
                    doc_ACTV_title.setText(General.Info);
                    getSupportFragmentManager().beginTransaction().replace(R.id.doc_FRCV_fragment, doctorInfoFragment).commit();
                    break;
                case R.id.doc_home:
                    doc_ACTV_title.setText(General.Main);
                    getSupportFragmentManager().beginTransaction().replace(R.id.doc_FRCV_fragment, doctorMainFragment).commit();
                    break;
                case R.id.doc_task:
                    doc_ACTV_title.setText(General.Task);
                    getSupportFragmentManager().beginTransaction().replace(R.id.doc_FRCV_fragment, doctorTasksFragment).commit();
                    break;
            }
            return true;
        });


    }

    private void findViews() {
        doctor_bottom_navigation = findViewById(R.id.doctor_bottom_navigation);
        doc_ACTV_title = findViewById(R.id.doc_ACTV_title);

        doctorInfoFragment = new DoctorInfoFragment();
        doctorInfoFragment.setCallback(doctorInfoFragment_Callback);
        doctorMainFragment = new DoctorMainFragment();
        doctorTasksFragment = new DoctorTasksFragment();
    }

    public void signOut() {

        mAuth.signOut();
        // return to login screen
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}