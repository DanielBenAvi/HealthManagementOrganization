package com.example.healthmanagementorganization;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.healthmanagementorganization.CallBacks.LoginBaseFragment_Callback;
import com.example.healthmanagementorganization.CallBacks.LoginFragment_Callback;
import com.example.healthmanagementorganization.CallBacks.RegisterFragment_Callback;
import com.example.healthmanagementorganization.Fragments.Login.LoginFragment;
import com.example.healthmanagementorganization.Fragments.Login.LoginBaseFragment;
import com.example.healthmanagementorganization.Fragments.Login.RegisterFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private LoginBaseFragment loginBaseFragment;
    private LoginFragment loginFragment;
    private RegisterFragment registerFragment;


    LoginBaseFragment_Callback loginBaseFragment_Callback = new LoginBaseFragment_Callback() {
        @Override
        public void LoginFragment() {
            changeTitle("Login");
            getSupportFragmentManager().beginTransaction().replace(R.id.login_AFCV_fragmentContainer, loginFragment).commit();


        }

        @Override
        public void RegisterFragment() {
            changeTitle("Register");
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
            changeActivityToMainActivity();
        }
    };


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            changeActivityToMainActivity();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        changeTitle("Login");

        findViews();
        initViews();

    }

    private void findViews() {
        loginBaseFragment = new LoginBaseFragment();
        loginBaseFragment.setFragmentCallback(loginBaseFragment_Callback);
        loginFragment = new LoginFragment();
        loginFragment.setFragmentCallback(loginFragment_Callback);
        registerFragment = new RegisterFragment();
        registerFragment.setFragmentCallback(registerFragment_Callback);


    }

    private void initViews() {
        getSupportFragmentManager().beginTransaction().replace(R.id.login_AFCV_fragmentContainer, loginBaseFragment).commit();

    }

    private void changeTitle(String t) {
        this.setTitle("" + t);
    }

    private void changeActivityToMainActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}
