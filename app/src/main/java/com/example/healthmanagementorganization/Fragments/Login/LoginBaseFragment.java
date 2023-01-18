package com.example.healthmanagementorganization.Fragments.Login;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.healthmanagementorganization.R;


public class LoginBaseFragment extends Fragment {
    private AppCompatButton loginF_ACB_login;
    private AppCompatButton loginF_ACB_register;

    private LoginBaseFragment_Callback loginBaseFragment_Callback;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login_base, container, false);

        findViews(view);
        initViews();

        return view;
    }

    private void findViews(View view) {

        loginF_ACB_login = view.findViewById(R.id.loginF_ACB_login);
        loginF_ACB_register = view.findViewById(R.id.loginF_ACB_register);
    }

    private void initViews() {

        loginF_ACB_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginBaseFragment_Callback.LoginFragment();
            }
        });

        loginF_ACB_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginBaseFragment_Callback.RegisterFragment();
            }
        });


    }

    public void setFragmentCallback(LoginBaseFragment_Callback loginBaseFragment_callback) {
        this.loginBaseFragment_Callback = loginBaseFragment_callback;
    }
}