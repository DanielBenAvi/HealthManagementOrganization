package com.example.healthmanagementorganization.Fragments.Doctor;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import com.example.healthmanagementorganization.Model.Person.Doctor;
import com.example.healthmanagementorganization.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class DoctorInfoFragment extends Fragment {


    private DoctorInfoFragment_Callback doctorInfoFragment_Callback;
    private AppCompatButton doctor_ACBTN_logout;

    private AppCompatTextView doc_info_ACTV_mail, doc_info_ACTV_name, doc_info_ACTV_phone, doc_info_ACTV_specialty;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_doctor_info, container, false);

        findViews(view);
        initViews();

        getUserData();

        return view;
    }

    private void initViews() {
        doctor_ACBTN_logout.setOnClickListener(v -> doctorInfoFragment_Callback.logOut());
    }

    private void findViews(View view) {
        doctor_ACBTN_logout = view.findViewById(R.id.doctor_ACBTN_logout);
        doc_info_ACTV_mail = view.findViewById(R.id.doc_info_ACTV_mail);
        doc_info_ACTV_name = view.findViewById(R.id.doc_info_ACTV_name);
        doc_info_ACTV_phone = view.findViewById(R.id.doc_info_ACTV_phone);
        doc_info_ACTV_specialty = view.findViewById(R.id.doc_info_ACTV_specialty);

    }

    public void setFragmentCallback(DoctorInfoFragment_Callback doctorInfoFragment_Callback) {
        this.doctorInfoFragment_Callback = doctorInfoFragment_Callback;
    }

    private void getUserData() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference mDatabase = db.getReference();

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("Doctors").child(mAuth.getCurrentUser().getUid()).exists()) {
                    mDatabase.child("Doctors").child(mAuth.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            if (task.isSuccessful()) {
                                Doctor d = task.getResult().getValue(Doctor.class);

                                doc_info_ACTV_name.setText("Name: \n\t\t" + d.getFirstName() + " " + d.getLastName());
                                doc_info_ACTV_mail.setText("Email: \n\t\t" + d.getEmail());
                                doc_info_ACTV_phone.setText("Phone: \n\t\t" + d.getPhone());
                                doc_info_ACTV_specialty.setText("Specialty: \n\t\t" + d.getSpecialty());
                            }
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });


    }
}