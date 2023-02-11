package com.example.healthmanagementorganization;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.healthmanagementorganization.Fragments.Login.LoginBaseFragment;
import com.example.healthmanagementorganization.Fragments.Login.LoginBaseFragment_Callback;
import com.example.healthmanagementorganization.Fragments.Login.LoginFragment;
import com.example.healthmanagementorganization.Fragments.Login.LoginFragment_Callback;
import com.example.healthmanagementorganization.Fragments.Login.RegisterFragment;
import com.example.healthmanagementorganization.Fragments.Login.RegisterFragment_Callback;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private LoginBaseFragment loginBaseFragment;
    private LoginFragment loginFragment;
    private RegisterFragment registerFragment;
    FirebaseAuth mAuth;
    FirebaseDatabase db;
    DatabaseReference mDatabase;

    LoginBaseFragment_Callback loginBaseFragment_Callback = new LoginBaseFragment_Callback() {
        @Override
        public void LoginFragment() {
            getSupportFragmentManager().beginTransaction().replace(R.id.login_AFCV_fragmentContainer, loginFragment).commit();
        }

        @Override
        public void RegisterFragment() {
            getSupportFragmentManager().beginTransaction().replace(R.id.login_AFCV_fragmentContainer, registerFragment).commit();
        }
    };

    RegisterFragment_Callback registerFragment_Callback = new RegisterFragment_Callback() {
        @Override
        public void onRegisterSuccess() {
            Toast.makeText(LoginActivity.this, "Register Success", Toast.LENGTH_SHORT).show();
            getSupportFragmentManager().beginTransaction().replace(R.id.login_AFCV_fragmentContainer, loginFragment).commit();
        }
    };

    LoginFragment_Callback loginFragment_Callback = new LoginFragment_Callback() {
        @Override
        public void onLoginSuccess() {
            mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.child("Patients").child(mAuth.getCurrentUser().getUid()).exists()) {
                        changeActivityToPatientActivity();
                        //do ur stuff
                    } else {
                        //do something if not exists
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }

            });

            mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.child("Doctors").child(mAuth.getCurrentUser().getUid()).exists()) {
                        changeActivityToDoctorActivity();
                    } else {
                        //do something if not exists
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }
    };


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.child("Patients").child(mAuth.getCurrentUser().getUid()).exists()) {
                        changeActivityToPatientActivity();
                        //do ur stuff
                    } else {
                        //do something if not exists
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }

            });

            mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.child("Doctors").child(mAuth.getCurrentUser().getUid()).exists()) {
                        changeActivityToDoctorActivity();
                    } else {
                        //do something if not exists
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.login_AFCV_fragmentContainer, loginBaseFragment).commit();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        mDatabase = db.getReference();
        findViews();
    }


    private void findViews() {
        loginBaseFragment = new LoginBaseFragment();
        loginBaseFragment.setFragmentCallback(loginBaseFragment_Callback);
        loginFragment = new LoginFragment();
        loginFragment.setFragmentCallback(loginFragment_Callback);
        registerFragment = new RegisterFragment();
        registerFragment.setFragmentCallback(registerFragment_Callback);


    }


    private void changeActivityToPatientActivity() {
        Intent intent = new Intent(LoginActivity.this, PatientActivity.class);
        startActivity(intent);
        finish();
    }

    private void changeActivityToDoctorActivity() {
        Intent intent = new Intent(LoginActivity.this, DoctorActivity.class);
        startActivity(intent);
        finish();
    }

}
