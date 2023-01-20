package com.example.healthmanagementorganization.Fragments.Patient;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthmanagementorganization.General.General;
import com.example.healthmanagementorganization.Model.Appointment;
import com.example.healthmanagementorganization.R;
import com.example.healthmanagementorganization.Recyclerviews.RV_appointment_adapter;
import com.example.healthmanagementorganization.Recyclerviews.RV_appointment_callback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class PatientMainFragment extends Fragment {


    private RecyclerView patientMain_RV_appointments;
    private ArrayList<Appointment> appointments;
    private FirebaseAuth mAuth;
    private FirebaseDatabase db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_patient_main, container, false);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        findViews(view);
        initViews();

        appointments = new ArrayList<>();
        getAllappointmentsFromFB();

        return view;
    }

    private void setAllAppointments() {
        // todo: get list of appointments of current user


        RV_appointment_adapter rv_appointment_adapter = new RV_appointment_adapter(getContext(), appointments);
        patientMain_RV_appointments.setLayoutManager(new LinearLayoutManager(getContext()));
        patientMain_RV_appointments.setAdapter(rv_appointment_adapter);

        rv_appointment_adapter.setAdapter(new RV_appointment_callback() {
            @Override
            public void itemClicked(Appointment appointment, int pos) {
                // todo: do something
            }
        });


    }

    private void getAllappointmentsFromFB() {
        DatabaseReference mDatabase = db.getReference().child(General.FB_Patients).child("" + mAuth.getCurrentUser().getUid()).child(General.FB_appointmets);


        mDatabase.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    for (DataSnapshot ds : task.getResult().getChildren()) {
                        Appointment a = ds.getValue(Appointment.class);
                        appointments.add(a);
                    }
                    setAllAppointments();
                }
            }
        });
    }

    private void initViews() {
    }

    private void findViews(View view) {
        patientMain_RV_appointments = view.findViewById(R.id.patientMain_RV_appointments);
    }

}