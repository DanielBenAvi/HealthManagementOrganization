package com.example.healthmanagementorganization;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.FragmentContainerView;

import com.example.healthmanagementorganization.Fragments.Patient.NewAppointmentFragment;
import com.example.healthmanagementorganization.Fragments.Patient.PatientInfoFragment;
import com.example.healthmanagementorganization.Fragments.Patient.PatientInfoFragment_Callback;
import com.example.healthmanagementorganization.Fragments.Patient.PatientMainFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class PatientActivity extends AppCompatActivity {

    // Design
    private AppCompatImageButton main_ACBTN_main, main_ACBTN_info, main_ACBTN_new;
    private AppCompatTextView main_ACTV_test;

    // FireBase
    private FirebaseAuth mAuth;

    // Fragments
    private NewAppointmentFragment newAppointmentFragment;
    private PatientMainFragment patientMainFragment;
    private PatientInfoFragment patientInfoFragment;


    PatientInfoFragment_Callback patientInfoFragment_callback = new PatientInfoFragment_Callback() {
        @Override
        public void logout() {
            signOut();
        }
    };

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
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("Patients").child(mAuth.getCurrentUser().getUid()).exists()) {
                    //do ur stuff
                    main_ACTV_test.setText("Patients");
                }  //do something if not exists
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }

    private void initViews() {


        getSupportFragmentManager().beginTransaction().add(R.id.main_FCV_main, patientMainFragment).commit();

        main_ACBTN_new.setOnClickListener(v -> {
            getSupportFragmentManager().beginTransaction().replace(R.id.main_FCV_main, newAppointmentFragment).commit();
            main_ACTV_test.setText("Add Appointment");
            main_ACBTN_new.setBackgroundResource(R.drawable.base_button_design_selected);
            main_ACBTN_info.setBackgroundResource(R.drawable.base_button_design);
            main_ACBTN_main.setBackgroundResource(R.drawable.base_button_design);
        });

        main_ACBTN_info.setOnClickListener(v -> {
            main_ACTV_test.setText("Home");
            getSupportFragmentManager().beginTransaction().replace(R.id.main_FCV_main, patientInfoFragment).commit();
            main_ACBTN_new.setBackgroundResource(R.drawable.base_button_design);
            main_ACBTN_info.setBackgroundResource(R.drawable.base_button_design_selected);
            main_ACBTN_main.setBackgroundResource(R.drawable.base_button_design);
        });

        main_ACBTN_main.setOnClickListener(v -> {
            main_ACTV_test.setText("My Profile");
            getSupportFragmentManager().beginTransaction().replace(R.id.main_FCV_main, patientMainFragment).commit();
            main_ACBTN_new.setBackgroundResource(R.drawable.base_button_design);
            main_ACBTN_info.setBackgroundResource(R.drawable.base_button_design);
            main_ACBTN_main.setBackgroundResource(R.drawable.base_button_design_selected);
        });

    }

    private void findViews() {
        // design
        main_ACBTN_main = findViewById(R.id.main_ACBTN_main);
        main_ACBTN_new = findViewById(R.id.main_ACBTN_new);
        main_ACBTN_info = findViewById(R.id.main_ACBTN_info);
        main_ACTV_test = findViewById(R.id.main_ACTV_test);

        FragmentContainerView main_FCV_main = findViewById(R.id.main_FCV_main);


        newAppointmentFragment = new NewAppointmentFragment();
        patientInfoFragment = new PatientInfoFragment();
        patientInfoFragment.setFragmentCallback(patientInfoFragment_callback);
        patientMainFragment = new PatientMainFragment();
    }
}