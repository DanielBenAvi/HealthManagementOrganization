package com.example.healthmanagementorganization.Fragments.Doctor;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.healthmanagementorganization.R;


public class SetWorkingHoursFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_set_working_hours, container, false);

        findViews(view);
        initView();

        return view;
    }

    private void initView() {
    }

    private void findViews(View view) {
    }
}