package com.example.healthmanagementorganization.Fragments.Doctor;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthmanagementorganization.General.Callback_interface;
import com.example.healthmanagementorganization.General.Fragment_interface;
import com.example.healthmanagementorganization.General.General;
import com.example.healthmanagementorganization.Model.Appointment;
import com.example.healthmanagementorganization.R;
import com.example.healthmanagementorganization.Recyclerviews.RV_appointment_adapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class DoctorMainFragment extends Fragment implements Fragment_interface {


    private RecyclerView docMain_RV_appointments;
    private ArrayList<Appointment> appointments;
    private FirebaseAuth mAuth;
    private FirebaseDatabase db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_doctor_main, container, false);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        findViews(view);
        initViews();
        appointments = new ArrayList<>();
        getAllappointmentsFromFB();
        return view;
    }


    private void getAllappointmentsFromFB() {
        DatabaseReference mDatabase = db.getReference().child(General.FB_Doctors).child("" + mAuth.getCurrentUser().getUid()).child(General.FB_appointmets);


        mDatabase.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (DataSnapshot ds : task.getResult().getChildren()) {
                    Appointment a = ds.getValue(Appointment.class);
                    appointments.add(a);
                }
                setAllAppointments();
            }
        });
    }

    private void setAllAppointments() {
        // todo: get list of appointments of current user


        RV_appointment_adapter rv_appointment_adapter = new RV_appointment_adapter(getContext(), appointments);
        docMain_RV_appointments.setLayoutManager(new LinearLayoutManager(getContext()));
        docMain_RV_appointments.setAdapter(rv_appointment_adapter);

        rv_appointment_adapter.setAdapter((appointment, pos) -> {
            // todo: do something
        });


    }

    @Override
    public void initViews() {
    }

    @Override
    public void setCallback(Callback_interface callback) {

    }

    @Override
    public void findViews(View view) {
        docMain_RV_appointments = view.findViewById(R.id.docMain_RV_appointments);
    }
}