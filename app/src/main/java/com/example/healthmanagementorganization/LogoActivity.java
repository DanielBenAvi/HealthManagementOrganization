package com.example.healthmanagementorganization;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;

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
                        startApp();
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
    }

    private void startApp() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }


    private void findViews() {
        splash_LAV_logo = findViewById(R.id.splash_LAV_logo);
    }
}