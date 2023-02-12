package com.example.healthmanagementorganization.Recyclerviews;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthmanagementorganization.General.General;
import com.example.healthmanagementorganization.Model.MedicineRequest;
import com.example.healthmanagementorganization.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class RV_tasks_adapter extends RecyclerView.Adapter<RV_tasks_adapter.RV_TaskViewHolder> {
    private Context context;
    private ArrayList<MedicineRequest> requests;
    private RV_tasks_callback rv_tasks_callback;


    public RV_tasks_adapter(Context context, ArrayList<MedicineRequest> requests) {
        this.context = context;
        this.requests = requests;
    }

    public void setAdapter(RV_tasks_callback rv_tasks_callback) {
        this.rv_tasks_callback = rv_tasks_callback;
    }

    @NonNull
    @Override
    public RV_TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.medicine_item, parent, false);
        return new RV_TaskViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RV_TaskViewHolder holder, int position) {
        MedicineRequest m = getItem(position);
        holder.med_item_ACTV_med_name.setText(m.getMedicine().getName());
        holder.med_item_ACTV_pat_name.setText(m.getUid());

        // update request status locally
        holder.med_item_ACB_approve.setOnClickListener(v -> {
            m.setStatus(General.MedicineRequestStatus.APPROVE.value);
            loadToDB(m);
        });
        holder.med_item_ACB_decline.setOnClickListener(v -> {
            m.setStatus(General.MedicineRequestStatus.DECLINE.value);
            loadToDB(m);
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    private void loadToDB(MedicineRequest m) {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference docRef = db.getReference(General.FB_Doctors).child(m.getDocID()).child(General.FB_requests).child(m.getRequestID());
        docRef.setValue(m);
        DatabaseReference patRef = db.getReference(General.FB_Patients).child(m.getUid()).child(General.FB_requests).child(m.getRequestID());
        patRef.setValue(m);
        requests.remove(m);
        notifyDataSetChanged();
    }


    private MedicineRequest getItem(int position) {
        return requests.get(position);
    }


    @Override
    public int getItemCount() {
        return requests == null ? 0 : requests.size();
    }

    public class RV_TaskViewHolder extends RecyclerView.ViewHolder {

        AppCompatTextView med_item_ACTV_med_name, med_item_ACTV_pat_name;
        AppCompatButton med_item_ACB_approve, med_item_ACB_decline;


        public RV_TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            med_item_ACTV_med_name = itemView.findViewById(R.id.med_item_ACTV_med_name);
            med_item_ACTV_pat_name = itemView.findViewById(R.id.med_item_ACTV_pat_name);
            med_item_ACB_approve = itemView.findViewById(R.id.med_item_ACB_approve);
            med_item_ACB_decline = itemView.findViewById(R.id.med_item_ACB_decline);
            itemView.setOnClickListener(v -> rv_tasks_callback.itemClicked(getItem(getAdapterPosition()), getAdapterPosition()));
        }
    }
}
