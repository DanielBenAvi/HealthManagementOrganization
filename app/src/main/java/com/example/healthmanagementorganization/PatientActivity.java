package com.example.healthmanagementorganization;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import com.example.healthmanagementorganization.Fragments.Patient.MedicinesSearchFragment;
import com.example.healthmanagementorganization.Fragments.Patient.NewAppointmentFragment;
import com.example.healthmanagementorganization.Fragments.Patient.NewAppointmentFragment_Callback;
import com.example.healthmanagementorganization.Fragments.Patient.PatientInfoFragment;
import com.example.healthmanagementorganization.Fragments.Patient.PatientInfoFragment_Callback;
import com.example.healthmanagementorganization.Fragments.Patient.PatientMainFragment;
import com.example.healthmanagementorganization.Fragments.Patient.PatientPrescriptionFragment;
import com.example.healthmanagementorganization.General.General;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class PatientActivity extends AppCompatActivity {

    // Design
    private AppCompatTextView main_ACTV_test;

    private NavigationBarView patient_bottom_navigation;

    // FireBase
    private FirebaseAuth mAuth;

    // Fragments
    private NewAppointmentFragment newAppointmentFragment;
    private PatientMainFragment patientMainFragment;
    private PatientInfoFragment patientInfoFragment;

    private PatientPrescriptionFragment patientPrescriptionFragment;

    private MedicinesSearchFragment medicinesSearchFragment;

    NewAppointmentFragment_Callback newAppointmentFragment_callback = new NewAppointmentFragment_Callback() {
        @Override
        public void changeFragment() {
            getSupportFragmentManager().beginTransaction().replace(R.id.main_FCV_main, patientMainFragment).commit();
        }
    };


    PatientInfoFragment_Callback patientInfoFragment_callback = this::signOut;

    public void signOut() {
        mAuth.signOut();
        // return to login screen
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);
        mAuth = FirebaseAuth.getInstance();

        findViews();
        initViews();

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference mDatabase = db.getReference();
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(General.FB_Patients).child(mAuth.getCurrentUser().getUid()).exists()) {
                    //do ur stuff
                    main_ACTV_test.setText("Hello " + dataSnapshot.child(General.FB_Patients).child(mAuth.getCurrentUser().getUid()).child(General.FB_firstName).getValue(String.class));
                }  //do something if not exists
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }

    @SuppressLint({"NonConstantResourceId", "SetTextI18n"})
    private void initViews() {
        getSupportFragmentManager().beginTransaction().add(R.id.main_FCV_main, patientInfoFragment).commit();

        patient_bottom_navigation.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.pat_home:
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_FCV_main, patientMainFragment).commit();
                    main_ACTV_test.setText("" + General.Main);
                    break;
                case R.id.pat_info:
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_FCV_main, patientInfoFragment).commit();
                    main_ACTV_test.setText("" + General.Info);
                    break;
                case R.id.pat_add:
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_FCV_main, newAppointmentFragment).commit();
                    main_ACTV_test.setText("" + General.NewAPP);
                    break;
                case R.id.pat_med:
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_FCV_main, medicinesSearchFragment).commit();
                    main_ACTV_test.setText("" + General.Medicines);
                    break;
                case R.id.pat_med_list:
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_FCV_main, patientPrescriptionFragment).commit();
                    main_ACTV_test.setText("" + General.Prescription);
                    break;

            }
            return true;
        });

    }

    private void findViews() {
        // design
        patient_bottom_navigation = findViewById(R.id.patient_bottom_navigation);
        main_ACTV_test = findViewById(R.id.main_ACTV_test);

        medicinesSearchFragment = new MedicinesSearchFragment();
        newAppointmentFragment = new NewAppointmentFragment();
        newAppointmentFragment.setCallback(newAppointmentFragment_callback);
        patientInfoFragment = new PatientInfoFragment();
        patientInfoFragment.setCallback(patientInfoFragment_callback);
        patientMainFragment = new PatientMainFragment();
        patientPrescriptionFragment = new PatientPrescriptionFragment();
    }
}