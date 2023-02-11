package com.example.healthmanagementorganization.Fragments.Doctor;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.healthmanagementorganization.R;


public class DoctorTasksFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_doctor_main, container, false);
        findViews(view);
        initViews();
        loadData();
        return view;

    }

    private void loadData() {
    }

    private void initViews() {
    }

    private void findViews(View view) {
    }
}