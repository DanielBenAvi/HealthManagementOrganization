package com.example.healthmanagementorganization.Fragments.Patient;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import com.example.healthmanagementorganization.R;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;


public class NewAppointmentFragment extends Fragment {
    private AppCompatButton newapp_ACBTN_date;
    private AppCompatTextView newapp_ACTV_date;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_appointment, container, false);


        findViews(view);
        initViews();

        return view;
    }

    private void initViews() {
        newapp_ACBTN_date.setOnClickListener(v -> {
            MaterialDatePicker builder = MaterialDatePicker.Builder.datePicker().setTitleText("Select Date").build();
            builder.show(getParentFragmentManager(), "TAG");

            builder.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
                @Override
                public void onPositiveButtonClick(Object selection) {
                    newapp_ACTV_date.setText("" + builder.getHeaderText());
                }
            });


        });
    }

    private void findViews(View view) {
        newapp_ACBTN_date = view.findViewById(R.id.newapp_ACBTN_date);
        newapp_ACTV_date = view.findViewById(R.id.newapp_ACTV_date);
    }

}