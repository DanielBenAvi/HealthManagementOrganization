package com.example.healthmanagementorganization.Recyclerviews;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthmanagementorganization.Database.Database;
import com.example.healthmanagementorganization.Model.Appointment;
import com.example.healthmanagementorganization.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;

public class RV_appointment_adapter extends RecyclerView.Adapter<RV_appointment_adapter.RV_AppointmentViewHolder> {
    private final FirebaseDatabase db;
    private final ArrayList<Appointment> appointments;

    private RV_appointment_callback rv_appointment_callback;

    public RV_appointment_adapter(Context context, ArrayList<Appointment> appointments) {
        this.appointments = appointments;
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
    }


    public RV_appointment_adapter setAdapter(RV_appointment_callback rv_appointment_callback) {
        this.rv_appointment_callback = rv_appointment_callback;
        return this;
    }

    @NonNull
    @Override
    public RV_AppointmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.appointment_item, parent, false);
        return new RV_AppointmentViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RV_AppointmentViewHolder holder, int position) {
        Appointment appointment = getItem(position);


        // set date
        LocalDate date = Instant.ofEpochMilli(appointment.getDate()).atZone(ZoneId.systemDefault()).toLocalDate();

        // change View
        holder.appItem_date.setText("" + date);
        holder.appItem_time.setText("" + appointment.getHour() + ":00");
        holder.appItem_pat.setText("" + Database.getInstance().getPatNameFormFB(appointment.getPatientID()));
        holder.appItem_doc.setText("" + Database.getInstance().getDocNameFormFB(appointment.getDoctorID()));
    }

    
    @Override
    public int getItemCount() {
        return appointments == null ? 0 : appointments.size();
    }

    public Appointment getItem(int pos) {
        return appointments.get(pos);
    }

    public static class RV_AppointmentViewHolder extends RecyclerView.ViewHolder {

        private final AppCompatTextView appItem_date;
        private final AppCompatTextView appItem_time;
        private final AppCompatTextView appItem_doc;
        private final AppCompatTextView appItem_pat;

        public RV_AppointmentViewHolder(@NonNull View itemView) {
            super(itemView);

            appItem_date = itemView.findViewById(R.id.appItem_date);
            appItem_time = itemView.findViewById(R.id.appItem_time);
            appItem_doc = itemView.findViewById(R.id.appItem_doc);
            appItem_pat = itemView.findViewById(R.id.appItem_pat);
        }
    }
}
