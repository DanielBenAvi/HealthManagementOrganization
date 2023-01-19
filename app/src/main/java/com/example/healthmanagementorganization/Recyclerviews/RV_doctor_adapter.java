package com.example.healthmanagementorganization.Recyclerviews;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthmanagementorganization.Model.Person.Doctor;
import com.example.healthmanagementorganization.R;

import java.util.ArrayList;

public class RV_doctor_adapter extends RecyclerView.Adapter<RV_doctor_adapter.RV_DoctorViewHolder> {

    private Context context;
    private ArrayList<Doctor> doctors;
    private RV_doctor_callback rv_doctor_callback;


    public RV_doctor_adapter(Context context, ArrayList<Doctor> doctors) {
        this.context = context;
        this.doctors = doctors;
    }

    public RV_doctor_adapter setAdapter(RV_doctor_callback rv_doctor_callback) {
        this.rv_doctor_callback = rv_doctor_callback;
        return this;
    }


    @NonNull
    @Override
    public RV_doctor_adapter.RV_DoctorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.doctor_item, parent, false);
        RV_DoctorViewHolder rv_doctorViewHolder = new RV_DoctorViewHolder(view);
        return rv_doctorViewHolder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RV_doctor_adapter.RV_DoctorViewHolder holder, int position) {
        Doctor doctor = getItem(position);

        holder.item_doctor_ACTV_name.setText(doctor.getFirstName() + " " + doctor.getLastName());
        holder.item_doctor_ACTV_specialty.setText("" + doctor.getSpecialty());
    }

    @Override
    public int getItemCount() {
        return doctors == null ? 0 : doctors.size();
    }

    private Doctor getItem(int pos) {
        return doctors.get(pos);
    }

    public class RV_DoctorViewHolder extends RecyclerView.ViewHolder {

        private AppCompatTextView item_doctor_ACTV_name;
        private AppCompatTextView item_doctor_ACTV_specialty;


        public RV_DoctorViewHolder(@NonNull View itemView) {
            super(itemView);
            item_doctor_ACTV_name = itemView.findViewById(R.id.item_doctor_ACTV_name);
            item_doctor_ACTV_specialty = itemView.findViewById(R.id.item_doctor_ACTV_specialty);
            itemView.setOnClickListener(v -> rv_doctor_callback.itemClicked(getItem(getAdapterPosition()), getAdapterPosition()));
        }
    }
}
