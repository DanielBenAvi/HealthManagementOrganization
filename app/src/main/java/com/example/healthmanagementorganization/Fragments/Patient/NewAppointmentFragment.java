package com.example.healthmanagementorganization.Fragments.Patient;

import android.annotation.SuppressLint;
import android.os.Bundle;
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

import com.example.healthmanagementorganization.General.Callback_interface;
import com.example.healthmanagementorganization.General.Fragment_interface;
import com.example.healthmanagementorganization.General.General;
import com.example.healthmanagementorganization.Model.Appointment;
import com.example.healthmanagementorganization.Model.Person.Doctor;
import com.example.healthmanagementorganization.Model.Person.Patient;
import com.example.healthmanagementorganization.R;
import com.example.healthmanagementorganization.Recyclerviews.RV_doctor_adapter;
import com.example.healthmanagementorganization.Recyclerviews.RV_hours_adapter;
import com.example.healthmanagementorganization.Recyclerviews.RV_hours_callback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


public class NewAppointmentFragment extends Fragment implements Fragment_interface {
    private FirebaseAuth mAuth;
    private FirebaseDatabase db;
    private AppCompatButton newapp_ACBTN_date, newapp_ACBTN_saveData;

    private AppCompatTextView newapp_ACTV_date;

    private RecyclerView newapp_RV_doctors, newapp_RV_hours;
    private LinearLayout newapp_LL_date_picker;

    private Doctor currentDoctor;
    private Long currentDate;
    private int currentHour;

    private NewAppointmentFragment_Callback newAppointmentFragment_callback;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_appointment, container, false);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();

        findViews(view);
        initViews();
        getAllDoctors();

        return view;
    }

    @SuppressLint("SetTextI18n")
    public void initViews() {
        // date picker
        newapp_ACBTN_date.setOnClickListener(v -> {
            MaterialDatePicker<Long> builder = MaterialDatePicker.Builder.datePicker().setTitleText("Select Date").build();
            builder.show(getParentFragmentManager(), "TAG");

            builder.addOnPositiveButtonClickListener(selection -> {
                newapp_ACTV_date.setText("" + builder.getHeaderText());
                currentDate = selection;
                // check if selection is in the future
                if (selection < System.currentTimeMillis()) {
                    Toast.makeText(getContext(), "Choose Future Date", Toast.LENGTH_SHORT).show();
                } else {
                    Set<Integer> newHours = new HashSet<>();
                    // add general hours
                    for (int i = General.DAY_START; i < General.DAY_END; i++) {
                        newHours.add(i);
                    }

                    for (Appointment appointment : currentDoctor.getAppointments()) {
                        if (Objects.equals(currentDate, appointment.getDate())) {
                            newHours.remove(appointment.getHour());
                        }
                    }

                    ArrayList<Integer> hours = new ArrayList<>(newHours);
                    Collections.sort(hours);

                    setHoursRecycleView(hours);
                    newapp_RV_hours.setVisibility(View.VISIBLE);
                }


            });

        });


        newapp_ACBTN_saveData.setOnClickListener(v -> {
            saveAppointment();
            newAppointmentFragment_callback.changeFragment();
        });
    }

    /**
     * create hours RV
     *
     * @param hours list of available hours
     */
    private void setHoursRecycleView(ArrayList<Integer> hours) {
        RV_hours_adapter rv_hours_adapter = new RV_hours_adapter(getContext(), hours);
        newapp_RV_hours.setLayoutManager(new LinearLayoutManager(getContext()));
        newapp_RV_hours.setAdapter(rv_hours_adapter);


        rv_hours_adapter.setAdapter(new RV_hours_callback() {
            @Override
            public void itemClicked(Integer hour, int position) {
                currentHour = hour;
                newapp_ACBTN_saveData.setVisibility(View.VISIBLE);
            }
        });

    }

    /**
     * Save new Appointment to DB
     */
    private void saveAppointment() {
        // save appointment to dr appointments
        currentDoctor.getAppointments().add(new Appointment().setDoctorID(currentDoctor.getUid()).setPatientID("" + mAuth.getCurrentUser().getUid()).setDate(currentDate).setHour(currentHour));
        currentDoctor.loadToDataBase();

        DatabaseReference mDatabase = db.getReference();
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("Patients").child(mAuth.getCurrentUser().getUid()).exists()) {
                    mDatabase.child("Patients").child(mAuth.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            if (task.isSuccessful()) {
                                Patient p = task.getResult().getValue(Patient.class);
                                assert p != null;
                                p.getAppointments().add(new Appointment().setDoctorID(currentDoctor.getUid()).setPatientID("" + mAuth.getCurrentUser().getUid()).setDate(currentDate).setHour(currentHour));
                                p.loadToDataBase();
                            }
                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }


    private void setDoctorRecycleView(ArrayList<Doctor> allDoctors) {

        RV_doctor_adapter rv_doctor_adapter = new RV_doctor_adapter(getContext(), allDoctors);
        newapp_RV_doctors.setLayoutManager(new LinearLayoutManager(getContext()));
        newapp_RV_doctors.setAdapter(rv_doctor_adapter);

        rv_doctor_adapter.setAdapter((doctor, position) -> {
            newapp_RV_doctors.setVisibility(View.GONE);
            // make date picker visible
            newapp_LL_date_picker.setVisibility(View.VISIBLE);

            currentDoctor = doctor;


        });
    }

    /**
     * get all doctors from FB
     */
    private void getAllDoctors() {
        DatabaseReference mDatabase = db.getReference().child(General.FB_Doctors);
        ArrayList<Doctor> docs = new ArrayList<>();

        mDatabase.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (DataSnapshot ds : task.getResult().getChildren()) {
                    Doctor d = ds.getValue(Doctor.class);
                    docs.add(d);
                }
                setDoctorRecycleView(docs);
            }
        });

    }

    @Override
    public void findViews(View view) {
        newapp_RV_doctors = view.findViewById(R.id.newapp_RV_doctors);
        newapp_ACBTN_date = view.findViewById(R.id.newapp_ACBTN_date);
        newapp_ACTV_date = view.findViewById(R.id.newapp_ACTV_date);
        newapp_LL_date_picker = view.findViewById(R.id.newapp_LL_date_picker);
        newapp_RV_hours = view.findViewById(R.id.newapp_RV_hours);
        newapp_ACBTN_saveData = view.findViewById(R.id.newapp_ACBTN_saveData);
    }

    @Override
    public void setCallback(Callback_interface callback) {
        this.newAppointmentFragment_callback = (NewAppointmentFragment_Callback) callback;
    }
}