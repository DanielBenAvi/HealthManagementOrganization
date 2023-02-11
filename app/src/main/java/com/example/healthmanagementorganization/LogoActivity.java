package com.example.healthmanagementorganization;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LogoActivity extends AppCompatActivity {

    private LottieAnimationView splash_LAV_logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);


        findViews();
        startAnimation(splash_LAV_logo);
    }


    private void startAnimation(View view) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);


        view.setAlpha(0.0f); // Transparent

        view.animate().alpha(1.0f).setDuration(2000) //in milliseconds
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
//                        login();
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
    }

    private void login() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    private void startDoc() {
        startActivity(new Intent(this, DoctorActivity.class));
        finish();
    }

    private void startPat() {
        startActivity(new Intent(this, PatientActivity.class));
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.child("Patients").child(mAuth.getCurrentUser().getUid()).exists()) {
                        startPat();
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
                        startDoc();
                    } else {
                        //do something if not exists
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }else {
            login();
        }
    }


    private void findViews() {
        splash_LAV_logo = findViewById(R.id.splash_LAV_logo);
    }
}