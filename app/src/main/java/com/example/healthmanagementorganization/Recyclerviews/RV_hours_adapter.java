package com.example.healthmanagementorganization.Recyclerviews;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthmanagementorganization.R;

import java.util.ArrayList;

public class RV_hours_adapter extends RecyclerView.Adapter<RV_hours_adapter.RV_HourViewHolder> {
    private Context context;
    private ArrayList<Integer> hours;
    private RV_hours_callback rv_hours_callback;


    public RV_hours_adapter(Context context, ArrayList<Integer> hours) {
        this.context = context;
        this.hours = hours;
    }

    public RV_hours_adapter setAdapter(RV_hours_callback rv_hours_callback) {
        this.rv_hours_callback = rv_hours_callback;
        return this;
    }

    @NonNull
    @Override
    public RV_hours_adapter.RV_HourViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hour_item, parent, false);
        RV_HourViewHolder rv_hourViewHolder = new RV_HourViewHolder(view);
        return rv_hourViewHolder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RV_hours_adapter.RV_HourViewHolder holder, int position) {
        int hour = getItem(position);
        holder.hour_ACTV_hour.setText("" + hour);
        holder.hour_ACTV_minutes.setText("00");
    }


    private int getItem(int position) {
        return hours.get(position);
    }


    @Override
    public int getItemCount() {
        return hours == null ? 0 : hours.size();
    }

    public class RV_HourViewHolder extends RecyclerView.ViewHolder {

        private AppCompatTextView hour_ACTV_hour, hour_ACTV_minutes;


        public RV_HourViewHolder(@NonNull View itemView) {
            super(itemView);
            hour_ACTV_hour = itemView.findViewById(R.id.hour_ACTV_hour);
            hour_ACTV_minutes = itemView.findViewById(R.id.hour_ACTV_minutes);
            itemView.setOnClickListener(v -> rv_hours_callback.itemClicked(getItem(getAdapterPosition()), getAdapterPosition()));
        }
    }
}
