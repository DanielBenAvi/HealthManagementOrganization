package com.example.healthmanagementorganization.Fragments.Login;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.healthmanagementorganization.R;
import com.google.firebase.auth.FirebaseAuth;


public class LoginFragment extends Fragment {

    private AppCompatButton login_ACBTN_login;
    private AppCompatEditText login_ACET_password;
    private AppCompatEditText login_ACET_email;
    private LoginFragment_Callback loginFragment_callback;

    private FirebaseAuth mAuth;

    public void setFragmentCallback(LoginFragment_Callback loginFragment_callback) {
        this.loginFragment_callback = loginFragment_callback;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        mAuth = FirebaseAuth.getInstance();

        findViews(view);
        initViews();

        return view;
    }

    private void initViews() {
        login_ACBTN_login.setOnClickListener(v -> {
            boolean flag = true;
            String email = login_ACET_email.getText() + "";
            flag = checkIfEmpty(email, "Email");
            String password = login_ACET_password.getText() + "";
            flag = checkIfEmpty(password, "Password");

            if (!flag) {
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        loginFragment_callback.onLoginSuccess();
                    } else {
                        Toast.makeText(getContext(), "Login Failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }


        });
    }

    private void findViews(View view) {
        login_ACBTN_login = view.findViewById(R.id.login_ACBTN_login);
        login_ACET_password = view.findViewById(R.id.login_ACET_password);
        login_ACET_email = view.findViewById(R.id.login_ACET_email);
    }

    private boolean checkIfEmpty(String obj, String msg) {
        if (obj.isEmpty()) {
            Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
            return true;
        }

        return false;
    }


}