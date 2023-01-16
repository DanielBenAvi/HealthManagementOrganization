package com.example.healthmanagementorganization.Fragments.Login;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.healthmanagementorganization.CallBacks.RegisterFragment_Callback;
import com.example.healthmanagementorganization.Model.Person.Doctor;
import com.example.healthmanagementorganization.Model.Person.Patient;
import com.example.healthmanagementorganization.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class RegisterFragment extends Fragment {
    private FirebaseAuth mAuth;
    private RegisterFragment_Callback registerFragment_Callback;
    private AppCompatEditText register_ACET_firstname;
    private AppCompatEditText register_ACET_lastname;
    private AppCompatEditText register_ACET_email;
    private AppCompatEditText register_ACET_password;
    private AppCompatRadioButton register_APRB_doctor;
    private AppCompatRadioButton register_APRB_patient;
    private AppCompatEditText register_ACET_specialization;
    private AppCompatButton register_ACBTN_register;
    private RadioGroup register_RG_type;
    private ContentLoadingProgressBar register_CLPB_progress;

    public void setFragmentCallback(RegisterFragment_Callback registerFragment_callback) {
        this.registerFragment_Callback = registerFragment_callback;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        mAuth = FirebaseAuth.getInstance();
        findViews(view);
        initViews(view);

        return view;
    }

    private void findViews(View view) {
        register_ACET_firstname = view.findViewById(R.id.register_ACET_firstname);
        register_ACET_lastname = view.findViewById(R.id.register_ACET_lastname);
        register_ACET_email = view.findViewById(R.id.register_ACET_email);
        register_ACET_password = view.findViewById(R.id.register_ACET_password);
        register_APRB_doctor = view.findViewById(R.id.register_APRB_doctor);
        register_APRB_patient = view.findViewById(R.id.register_APRB_patient);
        register_ACET_specialization = view.findViewById(R.id.register_ACET_specialization);
        register_ACBTN_register = view.findViewById(R.id.register_ACBTN_register);
        register_CLPB_progress = view.findViewById(R.id.register_CLPB_progress);
        register_RG_type = view.findViewById(R.id.register_RG_type);
    }

    private void initViews(View view) {

        register_RG_type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.register_APRB_doctor) {
                    register_ACET_specialization.setVisibility(View.VISIBLE);
                } else {
                    register_ACET_specialization.setVisibility(View.INVISIBLE);
                }
            }
        });

        register_ACBTN_register.setOnClickListener(v -> {
            boolean flag = true;
            register_CLPB_progress.setVisibility(View.VISIBLE);
            String firstname = register_ACET_firstname.getText() + "";
            flag = checkIfEmpty(firstname, "add firstname");
            String lastname = register_ACET_lastname.getText() + "";
            flag = checkIfEmpty(lastname, "add lastname");
            String email = register_ACET_email.getText() + "";
            flag = checkIfEmpty(email, "add email");
            String password = register_ACET_password.getText() + "";
            flag = checkIfEmpty(password, "add password");
            String specialization = register_ACET_specialization.getText() + "";
            if (register_APRB_doctor.isChecked()) {
                flag = checkIfEmpty(specialization, "add specialization");
            }

            if (!flag) {
                // register in firebase
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        register_CLPB_progress.setVisibility(View.INVISIBLE);
                        if (task.isSuccessful()) {
                            String uid = "" + mAuth.getCurrentUser();

                            // Check if the user is a doctor or a patient
                            if (register_APRB_doctor.isChecked()) {
                                Doctor doctor = new Doctor();
                                doctor.setUid(uid);
                                doctor.setFirstName(firstname);
                                doctor.setLastName(lastname);
                                doctor.setEmail(email);
                                doctor.setSpecialty(specialization);

                            } else {
                                Patient patient = new Patient();
                                patient.setUid(uid);
                                patient.setFirstName(firstname);
                                patient.setLastName(lastname);
                                patient.setEmail(email);

                            }
                            // go to login fragment
                            registerFragment_Callback.onRegisterSuccess();


                        } else {
                            Log.d("TAG", "onComplete: " + task.getException());
                            Toast.makeText(getContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }


        });

    }

    private boolean checkIfEmpty(String obj, String msg) {
        if (obj.isEmpty()) {
            Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
            return true;
        }

        return false;
    }


}