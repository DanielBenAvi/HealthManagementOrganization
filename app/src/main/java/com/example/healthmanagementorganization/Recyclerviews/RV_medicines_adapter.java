package com.example.healthmanagementorganization.Recyclerviews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthmanagementorganization.Model.Medicine;
import com.example.healthmanagementorganization.R;

import java.util.List;

public class RV_medicines_adapter extends RecyclerView.Adapter<RV_medicines_viewHolder> {


    Context context;
    List<Medicine> items;

    public RV_medicines_adapter(Context context, List<Medicine> items) {
        this.context = context;
        this.items = items;
    }


    @NonNull
    @Override
    public RV_medicines_viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RV_medicines_viewHolder(LayoutInflater.from(context).inflate(R.layout.medicine_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RV_medicines_viewHolder holder, int position) {
        holder.med_item_ACTV_name.setText(getItem(position).getName());
    }

    private Medicine getItem(int position) {
        return items.get(position);
    }


    @Override
    public int getItemCount() {
        return items.size();
    }
}
