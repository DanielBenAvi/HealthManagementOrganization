package com.example.healthmanagementorganization.Fragments.Patient;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthmanagementorganization.General.General;
import com.example.healthmanagementorganization.Model.Appointment;
import com.example.healthmanagementorganization.Model.Person.Doctor;
import com.example.healthmanagementorganization.R;
import com.example.healthmanagementorganization.Recyclerviews.RV_doctor_adapter;
import com.example.healthmanagementorganization.Recyclerviews.RV_doctor_callback;
import com.example.healthmanagementorganization.Recyclerviews.RV_hours_adapter;
import com.example.healthmanagementorganization.Recyclerviews.RV_hours_callback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;


public class NewAppointmentFragment extends Fragment {
    private AppCompatButton newapp_ACBTN_date;

    private AppCompatTextView newapp_ACTV_date;

    private RecyclerView newapp_RV_doctors, newapp_RV_hours;
    private LinearLayout newapp_LL_date_picker;

    private ArrayList<Integer> hours;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_appointment, container, false);


        findViews(view);
        initViews();
        getAllDoctors();

        return view;
    }

    @SuppressLint("SetTextI18n")
    private void initViews() {
        newapp_ACBTN_date.setOnClickListener(v -> {
            MaterialDatePicker<Long> builder = MaterialDatePicker.Builder.datePicker().setTitleText("Select Date").build();
            builder.show(getParentFragmentManager(), "TAG");

            builder.addOnPositiveButtonClickListener(selection -> newapp_ACTV_date.setText("" + builder.getHeaderText()));
            setHoursRecycleView(hours);
            newapp_RV_hours.setVisibility(View.VISIBLE);

        });
    }

    private void setHoursRecycleView(ArrayList<Integer> hours) {
        RV_hours_adapter rv_hours_adapter = new RV_hours_adapter(getContext(), hours);
        newapp_RV_hours.setLayoutManager(new LinearLayoutManager(getContext()));
        newapp_RV_hours.setAdapter(rv_hours_adapter);


        rv_hours_adapter.setAdapter(new RV_hours_callback() {
            @Override
            public void itemClicked(Integer hour, int position) {
                Log.d("Dufam", "" + hour);
            }
        });

    }


    private void setDoctorRecycleView(ArrayList<Doctor> allDoctors) {

        RV_doctor_adapter rv_doctor_adapter = new RV_doctor_adapter(getContext(), allDoctors);
        newapp_RV_doctors.setLayoutManager(new LinearLayoutManager(getContext()));
        newapp_RV_doctors.setAdapter(rv_doctor_adapter);

        rv_doctor_adapter.setAdapter(new RV_doctor_callback() {
            @Override
            public void itemClicked(Doctor doctor, int position) {
                Toast.makeText(getContext(), doctor.getFirstName(), Toast.LENGTH_SHORT).show();
                // make date picker visible
                newapp_LL_date_picker.setVisibility(View.VISIBLE);
                // TODO add new recycleView with hours each day
                // todo: make doc gone
                hours = new ArrayList<>();
                // add general hours
                for (int i = General.DAY_START; i < General.DAY_END; i++) {
                    hours.add(i);
                }

                for (Appointment app : doctor.getAppointments()) {
                    hours.remove((Integer) app.getCalendar().get(Calendar.HOUR));
                }
            }
        });
    }

    private ArrayList<Doctor> getAllDoctors() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference mDatabase = db.getReference().child("Doctors");
        ArrayList<Doctor> docs = new ArrayList<>();

        mDatabase.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    Log.d("DUFAM", task.getResult().toString());

                    for (DataSnapshot ds : task.getResult().getChildren()) {
                        Doctor d = ds.getValue(Doctor.class);
                        docs.add(d);
                        Log.d("DUFAM", "" + d.toString());
                    }
                    Log.d("DUFAM", "" + docs.toString());
                    setDoctorRecycleView(docs);
                }
            }
        });

        return docs;
    }

    private void findViews(View view) {
        newapp_RV_doctors = view.findViewById(R.id.newapp_RV_doctors);
        newapp_ACBTN_date = view.findViewById(R.id.newapp_ACBTN_date);
        newapp_ACTV_date = view.findViewById(R.id.newapp_ACTV_date);
        newapp_LL_date_picker = view.findViewById(R.id.newapp_LL_date_picker);
        newapp_RV_hours = view.findViewById(R.id.newapp_RV_hours);
    }

}