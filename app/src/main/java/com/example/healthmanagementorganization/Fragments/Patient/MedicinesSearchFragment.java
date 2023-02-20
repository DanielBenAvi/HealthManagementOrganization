package com.example.healthmanagementorganization.Fragments.Patient;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import com.example.healthmanagementorganization.General.General;
import com.example.healthmanagementorganization.Model.Medicine;
import com.example.healthmanagementorganization.Model.MedicineRequest;
import com.example.healthmanagementorganization.Model.Person.Doctor;
import com.example.healthmanagementorganization.Model.Person.Patient;
import com.example.healthmanagementorganization.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;


public class MedicinesSearchFragment extends Fragment {

    private AppCompatAutoCompleteTextView search_med_ACACTV_search_bar, search_med_ACACTV_search_doc;
    private AppCompatTextView search_med_ACTV_med_price;
    private AppCompatButton search_med_ACB_req_med;
    private DatabaseReference medRef, docRef;

    private Medicine medicine;
    private Doctor doctor;
    private Patient patient;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_medicines_search, container, false);
        findViews(view);
        initViews();

        loadData();


        return view;
    }

    /**
     * load Data from DB
     */
    private void loadData() {
        // load drugs for search
        medRef = FirebaseDatabase.getInstance().getReference(General.FB_Medicine);
        ValueEventListener medSearch = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                medSearch(snapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        };
        medRef.addListenerForSingleValueEvent(medSearch);

        // load docs for search
        docRef = FirebaseDatabase.getInstance().getReference(General.FB_Doctors);
        ValueEventListener docSearch = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                docSearch(snapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        docRef.addListenerForSingleValueEvent(docSearch);
    }

    /**
     * search doctors from DB
     *
     * @param snapshot doctors form DB
     */
    private void docSearch(DataSnapshot snapshot) {
        ArrayList<String> names = new ArrayList<>();
        if (snapshot.exists()) {
            for (DataSnapshot d : snapshot.getChildren()) {
                Doctor doc = d.getValue(Doctor.class);
                assert doc != null;
                names.add("" + doc.getFirstName());
            }
            // adapter for searchbar
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, names);
            search_med_ACACTV_search_doc.setAdapter(adapter);
            search_med_ACACTV_search_doc.setOnItemClickListener((parent, view, position, id) -> {
                String name = search_med_ACACTV_search_doc.getText().toString();
                searchDoc(name);
            });


        } else {
            Log.d("DUFAM", "No Doc Found");
        }
    }

    /**
     * search medicine from DB
     *
     * @param snapshot medicines form DB
     */
    private void medSearch(DataSnapshot snapshot) {
        ArrayList<String> names = new ArrayList<>();
        if (snapshot.exists()) {
            for (DataSnapshot d : snapshot.getChildren()) {
                Medicine m = d.getValue(Medicine.class);
                assert m != null;
                names.add(m.getName());
            }
            // adapter for searchbar
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, names);
            search_med_ACACTV_search_bar.setAdapter(adapter);
            search_med_ACACTV_search_bar.setOnItemClickListener((parent, view, position, id) -> {
                String name = search_med_ACACTV_search_bar.getText().toString();
                searchDrug(name);
            });


        } else {
            Log.d("DUFAM", "No Drug Found");
        }
    }

    private void searchDrug(String name) {
        medRef.orderByChild("name").equalTo(name).addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        medicine = ds.getValue(Medicine.class);
                        assert medicine != null;
                        search_med_ACTV_med_price.setText("" + medicine.getPrice());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void searchDoc(String name) {
        docRef.orderByChild("firstName").equalTo(name).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    doctor = ds.getValue(Doctor.class);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void initViews() {
        search_med_ACB_req_med.setOnClickListener(v -> {
            // current user =>
            String uid = "" + Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference(General.FB_Patients);
            ref.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    patient = snapshot.getValue(Patient.class);
                    requestMedicine();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        });
    }

    private void requestMedicine() {
        if (medicine == null) {
            Toast.makeText(getContext(), "Choose Medicine", Toast.LENGTH_SHORT).show();
            return;
        }
        if (doctor == null) {
            Toast.makeText(getContext(), "Choose Doctor", Toast.LENGTH_SHORT).show();
            return;
        }
        // create new med request
        MedicineRequest medicineRequest = new MedicineRequest();
        medicineRequest.setMedicine(medicine);
        medicineRequest.setDocID(doctor.getUid());
        medicineRequest.setUid(patient.getUid());
        medicineRequest.setStatus(General.MedicineRequestStatus.NEW.value);

        // add to doc and patient
        patient.getRequests().put(medicineRequest.getRequestID(), medicineRequest);
        doctor.getRequests().put(medicineRequest.getRequestID(), medicineRequest);
        // load to DB
        patient.loadToDataBase();
        doctor.loadToDataBase();

        search_med_ACACTV_search_bar.setText("");
        search_med_ACACTV_search_doc.setText("");
        search_med_ACTV_med_price.setText("");
        doctor = null;
        medicine = null;

    }

    private void findViews(View view) {
        search_med_ACACTV_search_bar = view.findViewById(R.id.search_med_ACACTV_search_bar);
        search_med_ACACTV_search_doc = view.findViewById(R.id.search_med_ACACTV_search_doc);
        search_med_ACTV_med_price = view.findViewById(R.id.search_med_ACTV_med_price);
        search_med_ACB_req_med = view.findViewById(R.id.search_med_ACB_req_med);
    }
}