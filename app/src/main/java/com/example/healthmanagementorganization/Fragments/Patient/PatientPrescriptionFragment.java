package com.example.healthmanagementorganization.Fragments.Patient;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthmanagementorganization.General.General;
import com.example.healthmanagementorganization.Model.MedicineRequest;
import com.example.healthmanagementorganization.R;
import com.example.healthmanagementorganization.Recyclerviews.RV_prescription_adapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Objects;

public class PatientPrescriptionFragment extends Fragment {
    private RecyclerView prescription_RV_prescriptions;
    private FirebaseAuth mAuth;
    private FirebaseDatabase db;
    private ArrayList<MedicineRequest> requests;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_patient_prescription, container, false);
        initViews(view);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        requests = new ArrayList<>();
        loadData();
        return view;
    }

    private void loadData() {
        DatabaseReference mDatabase = db.getReference().child(General.FB_Patients).child("" + Objects.requireNonNull(mAuth.getCurrentUser()).getUid()).child(General.FB_requests);

        mDatabase.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (DataSnapshot ds : task.getResult().getChildren()) {
                    MedicineRequest a = ds.getValue(MedicineRequest.class);
                    requests.add(a);
                }
                setAllTasks();
            }
        });

    }

    private void setAllTasks() {
        RV_prescription_adapter rv_prescription_adapter = new RV_prescription_adapter(getContext(), requests);
        prescription_RV_prescriptions.setLayoutManager(new LinearLayoutManager(getContext()));
        prescription_RV_prescriptions.setAdapter(rv_prescription_adapter);

        rv_prescription_adapter.setAdapter((request, pos) -> {
        });
    }

    private void initViews(View view) {
        prescription_RV_prescriptions = view.findViewById(R.id.prescription_RV_prescriptions);
    }
}