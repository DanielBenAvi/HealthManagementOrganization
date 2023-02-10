package com.example.healthmanagementorganization.Fragments.Patient;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthmanagementorganization.Model.Medicine;
import com.example.healthmanagementorganization.R;
import com.example.healthmanagementorganization.Recyclerviews.RV_medicines_adapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class MedicinesSearchFragment extends Fragment {

    private SearchView search_med_SV_search_bar;
    private RecyclerView search_med_RV_med_list;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_medicines_search, container, false);
        findViews(view);
        initViews();


        List<Medicine> items = new ArrayList<Medicine>();



        search_med_RV_med_list.setLayoutManager(new LinearLayoutManager(getContext()));
        search_med_RV_med_list.setAdapter(new RV_medicines_adapter(getContext(), items));


        return view;
    }

    private void initViews() {
    }

    private void findViews(View view) {
        search_med_RV_med_list = view.findViewById(R.id.search_med_RV_med_list);
        search_med_SV_search_bar = view.findViewById(R.id.search_med_SV_search_bar);
    }
}