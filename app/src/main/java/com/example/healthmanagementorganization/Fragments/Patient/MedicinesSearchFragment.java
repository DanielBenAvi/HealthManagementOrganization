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
import com.example.healthmanagementorganization.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class MedicinesSearchFragment extends Fragment {

    private AppCompatAutoCompleteTextView search_med_ACACTV_search_bar;
    private AppCompatTextView search_med_ACTV_med_name, search_med_ACTV_med_price;
    private AppCompatButton search_med_ACB_req_med;
    private DatabaseReference databaseReference;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_medicines_search, container, false);
        findViews(view);
        initViews();

        loadData();


        return view;
    }

    private void loadData() {
        databaseReference = FirebaseDatabase.getInstance().getReference(General.FB_Medicine);


        ValueEventListener event = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                populateSearch(snapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        databaseReference.addListenerForSingleValueEvent(event);
    }

    private void populateSearch(DataSnapshot snapshot) {
        ArrayList<String> names = new ArrayList<>();
        if (snapshot.exists()) {
            for (DataSnapshot d : snapshot.getChildren()) {
                Medicine m = d.getValue(Medicine.class);
                assert m != null;
                names.add(m.getName());
            }

            ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, names);
            search_med_ACACTV_search_bar.setAdapter(adapter);
            search_med_ACACTV_search_bar.setOnItemClickListener((parent, view, position, id) -> {
                String name = search_med_ACACTV_search_bar.getText().toString();

                searchUser(name);
            });


        } else {
            Log.d("DUFAM", "No Data Found");
        }
    }

    private void searchUser(String name) {
        databaseReference.orderByChild("name").equalTo(name).addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Medicine m = ds.getValue(Medicine.class);
                        assert m != null;
                        search_med_ACTV_med_name.setText("" + m.getName());
                        search_med_ACTV_med_price.setText("" + m.getPrice());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void initViews() {
        search_med_ACB_req_med.setOnClickListener(v -> {
            Toast.makeText(getContext(), "TODO - request med", Toast.LENGTH_SHORT).show();
        });
    }

    private void findViews(View view) {
        search_med_ACACTV_search_bar = view.findViewById(R.id.search_med_ACACTV_search_bar);
        search_med_ACTV_med_name = view.findViewById(R.id.search_med_ACTV_med_name);
        search_med_ACTV_med_price = view.findViewById(R.id.search_med_ACTV_med_price);
        search_med_ACB_req_med = view.findViewById(R.id.search_med_ACB_req_med);
    }
}