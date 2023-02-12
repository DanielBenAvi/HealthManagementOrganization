package com.example.healthmanagementorganization.Recyclerviews;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthmanagementorganization.General.General;
import com.example.healthmanagementorganization.Model.MedicineRequest;
import com.example.healthmanagementorganization.Model.Person.Doctor;
import com.example.healthmanagementorganization.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RV_prescription_adapter extends RecyclerView.Adapter<RV_prescription_adapter.RV_PrescriptionViewHolder> {
    private final ArrayList<MedicineRequest> requests;


    public RV_prescription_adapter(Context context, ArrayList<MedicineRequest> requests) {
        this.requests = requests;
    }

    public void setAdapter(RV_prescription_callback prescription_callback) {
    }

    @NonNull
    @Override
    public RV_PrescriptionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.prescription_item, parent, false);
        return new RV_PrescriptionViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RV_PrescriptionViewHolder holder, int position) {
        MedicineRequest m = getItem(position);
        holder.prescription_ACTV_drug.setText(m.getMedicine().getName());
        String status = "";
        switch (m.getStatus()) {
            case 0:
                status = "Waiting";
                break;
            case 1:
                status = "Approved";
                break;
            case -1:
                status = "Declined";
                break;
        }
        holder.prescription_ACTV_status.setText("Status: " + status);

        // get pat name
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference mDatabase = db.getReference();

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mDatabase.child(General.FB_Doctors).child(m.getDocID()).get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Doctor d = task.getResult().getValue(Doctor.class);
                        assert d != null;
                        holder.prescription_ACTV_doctor.setText("Doctor Name: " + d.getFirstName() + " " + d.getLastName());
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }

    private MedicineRequest getItem(int position) {
        return requests.get(position);
    }


    @Override
    public int getItemCount() {
        return requests == null ? 0 : requests.size();
    }

    public static class RV_PrescriptionViewHolder extends RecyclerView.ViewHolder {

        AppCompatTextView prescription_ACTV_drug, prescription_ACTV_doctor, prescription_ACTV_status;


        public RV_PrescriptionViewHolder(@NonNull View itemView) {
            super(itemView);
            prescription_ACTV_drug = itemView.findViewById(R.id.prescription_ACTV_drug);
            prescription_ACTV_doctor = itemView.findViewById(R.id.prescription_ACTV_doctor);
            prescription_ACTV_status = itemView.findViewById(R.id.prescription_ACTV_status);
//            itemView.setOnClickListener(v -> rv.itemClicked(getItem(getAdapterPosition()), getAdapterPosition()));
        }
    }
}
