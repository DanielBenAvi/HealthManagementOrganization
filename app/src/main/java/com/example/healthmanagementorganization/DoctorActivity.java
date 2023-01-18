package com.example.healthmanagementorganization;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatTextView;

import com.example.healthmanagementorganization.Fragments.Doctor.DoctorInfoFragment;
import com.example.healthmanagementorganization.Fragments.Doctor.DoctorMainFragment;
import com.example.healthmanagementorganization.Fragments.Doctor.SetWorkingHoursFragment;
import com.google.firebase.auth.FirebaseAuth;

public class DoctorActivity extends AppCompatActivity {
    //Firebase
    FirebaseAuth mAuth;

    //Design
    private AppCompatButton doctor_ACBTN_logout;
    private AppCompatImageButton doc_ACBTN_info, doc_ACBTN_main, doc_ACBTN_work_hours;
    private AppCompatTextView doc_ACTV_title;

    //Fragments
    private SetWorkingHoursFragment setWorkingHoursFragment;
    private DoctorMainFragment doctorMainFragment;
    private DoctorInfoFragment doctorInfoFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);
        mAuth = FirebaseAuth.getInstance();
        findViews();
        getSupportFragmentManager().beginTransaction().add(R.id.doc_FRCV_fragment, doctorMainFragment).commit();

        initViews();
    }

    private void initViews() {
        doctor_ACBTN_logout.setOnClickListener(v -> signOut());
        doc_ACBTN_info.setOnClickListener(v -> {
            getSupportFragmentManager().beginTransaction().replace(R.id.doc_FRCV_fragment, doctorInfoFragment).commit();
            doc_ACBTN_info.setBackgroundResource(R.drawable.base_button_design_selected);
            doc_ACBTN_main.setBackgroundResource(R.drawable.base_button_design);
            doc_ACBTN_work_hours.setBackgroundResource(R.drawable.base_button_design);

        });

        doc_ACBTN_main.setOnClickListener(v -> {
            getSupportFragmentManager().beginTransaction().replace(R.id.doc_FRCV_fragment, doctorMainFragment).commit();
            doc_ACBTN_info.setBackgroundResource(R.drawable.base_button_design);
            doc_ACBTN_main.setBackgroundResource(R.drawable.base_button_design_selected);
            doc_ACBTN_work_hours.setBackgroundResource(R.drawable.base_button_design);

        });

        doc_ACBTN_work_hours.setOnClickListener(v -> {
            getSupportFragmentManager().beginTransaction().replace(R.id.doc_FRCV_fragment, setWorkingHoursFragment).commit();
            doc_ACBTN_info.setBackgroundResource(R.drawable.base_button_design);
            doc_ACBTN_main.setBackgroundResource(R.drawable.base_button_design);
            doc_ACBTN_work_hours.setBackgroundResource(R.drawable.base_button_design_selected);
        });
    }

    private void findViews() {
        doctor_ACBTN_logout = findViewById(R.id.doctor_ACBTN_logout);
        doc_ACBTN_info = findViewById(R.id.doc_ACBTN_info);
        doc_ACBTN_main = findViewById(R.id.doc_ACBTN_main);
        doc_ACBTN_work_hours = findViewById(R.id.doc_ACBTN_work_hours);
        doc_ACTV_title = findViewById(R.id.doc_ACTV_title);

        doctorInfoFragment = new DoctorInfoFragment();
        doctorMainFragment = new DoctorMainFragment();
        setWorkingHoursFragment = new SetWorkingHoursFragment();
    }

    public void signOut() {

        mAuth.signOut();
        // return to login screen
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}