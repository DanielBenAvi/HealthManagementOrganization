package com.example.healthmanagementorganization.Fragments.Patient;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthmanagementorganization.General.General;
import com.example.healthmanagementorganization.Model.Medicine;
import com.example.healthmanagementorganization.R;
import com.example.healthmanagementorganization.Recyclerviews.RV_medicines_adapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class MedicinesSearchFragment extends Fragment {

    private AppCompatAutoCompleteTextView search_med_ACACTV_search_bar;
    private RecyclerView search_med_RV_med_list;


    private DatabaseReference databaseReference;
    private List<Medicine> items;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_medicines_search, container, false);
        findViews(view);
        initViews();
        initData();

        loadData();


        search_med_RV_med_list.setLayoutManager(new LinearLayoutManager(getContext()));


        return view;
    }

    private void loadData() {
        databaseReference = FirebaseDatabase.getInstance().getReference(General.FB_Medicine);
        search_med_RV_med_list.setAdapter(new RV_medicines_adapter(getContext(), items));


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
//                items.add(m);
                names.add(m.getName());
            }

            ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, names);
            search_med_ACACTV_search_bar.setAdapter(adapter);
            search_med_ACACTV_search_bar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String name = search_med_ACACTV_search_bar.getText().toString();
                    searchUser(name);
                }
            });


        } else {
            Log.d("DUFAM", "No Data Found");
        }
    }

    private void searchUser(String name) {
        databaseReference.orderByChild("name").equalTo(name).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    items.clear();
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Medicine m = ds.getValue(Medicine.class);
                        items.add(m);
                    }
                    search_med_RV_med_list.setAdapter(new RV_medicines_adapter(getContext(), items));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void initData() {
        items = new ArrayList<Medicine>();
    }

    private void initViews() {
    }

    private void findViews(View view) {
        search_med_RV_med_list = view.findViewById(R.id.search_med_RV_med_list);
        search_med_ACACTV_search_bar = view.findViewById(R.id.search_med_ACACTV_search_bar);
    }
}