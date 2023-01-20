package com.example.healthmanagementorganization.Fragments.Patient;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import com.example.healthmanagementorganization.General.Callback_interface;
import com.example.healthmanagementorganization.General.Fragment_interface;
import com.example.healthmanagementorganization.Model.Person.Patient;
import com.example.healthmanagementorganization.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class PatientInfoFragment extends Fragment implements Fragment_interface {

    private AppCompatTextView info_ACTV_medical_issues, info_ACTV_phone, info_ACTV_mail, info_ACTV_name;
    private AppCompatButton info_ACBTN_logout;

    private PatientInfoFragment_Callback patientInfoFragment_callback;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_patient_info, container, false);

        findViews(view);
        initViews();

        getUserData();

        return view;
    }

    private void getUserData() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference mDatabase = db.getReference();

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("Patients").child(mAuth.getCurrentUser().getUid()).exists()) {
                    mDatabase.child("Patients").child(mAuth.getCurrentUser().getUid()).get().addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Patient p = task.getResult().getValue(Patient.class);

                            info_ACTV_name.setText("Name: \n\t\t" + p.getFirstName() + " " + p.getLastName());
                            info_ACTV_mail.setText("Email: \n\t\t" + p.getEmail());
                            info_ACTV_phone.setText("Phone: \n\t\t" + p.getPhone());
                            info_ACTV_medical_issues.setText("Medical Issues: \n\t\t");
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });


    }

    public void initViews() {
        info_ACBTN_logout.setOnClickListener(v -> {
            patientInfoFragment_callback.logout();
        });

    }

    public void findViews(View view) {
        info_ACTV_medical_issues = view.findViewById(R.id.info_ACTV_medical_issues);
        info_ACTV_phone = view.findViewById(R.id.info_ACTV_phone);
        info_ACTV_mail = view.findViewById(R.id.info_ACTV_mail);
        info_ACTV_name = view.findViewById(R.id.info_ACTV_name);
        info_ACBTN_logout = view.findViewById(R.id.info_ACBTN_logout);

    }


    @Override
    public void setCallback(Callback_interface callback) {
        this.patientInfoFragment_callback = (PatientInfoFragment_Callback) callback;
    }
}