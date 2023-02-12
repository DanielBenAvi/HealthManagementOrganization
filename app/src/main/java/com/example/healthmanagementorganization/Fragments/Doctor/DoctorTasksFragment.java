package com.example.healthmanagementorganization.Fragments.Doctor;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthmanagementorganization.General.General;
import com.example.healthmanagementorganization.Model.MedicineRequest;
import com.example.healthmanagementorganization.R;
import com.example.healthmanagementorganization.Recyclerviews.RV_tasks_adapter;
import com.example.healthmanagementorganization.Recyclerviews.RV_tasks_callback;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Objects;


public class DoctorTasksFragment extends Fragment {

    private RecyclerView tasks_RV_tasks;
    private ArrayList<MedicineRequest> requests;
    private FirebaseAuth mAuth;
    private FirebaseDatabase db;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tasks, container, false);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        findViews(view);
        requests = new ArrayList<>();
        getAllTasksFromFB();
        return view;

    }

    private void getAllTasksFromFB() {
        DatabaseReference mDatabase = db.getReference().child(General.FB_Doctors).child("" + Objects.requireNonNull(mAuth.getCurrentUser()).getUid()).child(General.FB_requests);

        mDatabase.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (DataSnapshot ds : task.getResult().getChildren()) {
                    MedicineRequest a = ds.getValue(MedicineRequest.class);
                    requests.add(a);
                }
                requests.removeIf(r -> r.getStatus() != 0);
                setAllTasks(requests);
            }
        });
    }

    private void setAllTasks(ArrayList<MedicineRequest> requests) {
        RV_tasks_adapter rv_tasks_adapter = new RV_tasks_adapter(getContext(), requests);
        tasks_RV_tasks.setLayoutManager(new LinearLayoutManager(getContext()));
        tasks_RV_tasks.setAdapter(rv_tasks_adapter);

        rv_tasks_adapter.setAdapter((request, pos) -> {
        });
    }


    private void findViews(View view) {
        tasks_RV_tasks = view.findViewById(R.id.tasks_RV_tasks);
    }
}