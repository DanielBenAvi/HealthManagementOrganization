package com.example.healthmanagementorganization.Recyclerviews;

import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthmanagementorganization.R;

public class RV_medicines_viewHolder extends RecyclerView.ViewHolder {

    AppCompatTextView med_item_ACTV_name;

    public RV_medicines_viewHolder(@NonNull View itemView) {
        super(itemView);
        med_item_ACTV_name = itemView.findViewById(R.id.med_item_ACTV_name);

        itemView.setOnClickListener(v -> {
//            Toast.makeText(v.getContext(), med_item_ACTV_name.getText(), Toast.LENGTH_SHORT).show();
        });
    }
}
