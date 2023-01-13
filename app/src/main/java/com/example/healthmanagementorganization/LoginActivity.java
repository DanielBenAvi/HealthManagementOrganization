package com.example.healthmanagementorganization;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Collections;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    AppCompatButton login_APB_logout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.setTitle(R.string.app_name_2);
        findViews();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) {
            login();
        } else {
            String uid = user.getUid();
            String name = user.getDisplayName();
            String email = user.getEmail();
        }
        initViews();
    }

    private void initViews() {
        login_APB_logout.setOnClickListener(v -> {
            mAuth.signOut();
            login();
        });
    }

    /**
     * Find the Views in the layout
     */
    private void findViews() {
        login_APB_logout = findViewById(R.id.login_APB_logout);
    }


    // See: https://developer.android.com/training/basics/intents/result
    private final ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(new FirebaseAuthUIActivityResultContract(), new ActivityResultCallback<FirebaseAuthUIAuthenticationResult>() {
        @Override
        public void onActivityResult(FirebaseAuthUIAuthenticationResult result) {
            onSignInResult(result);
        }
    });

    /**
     * ????????????
     *
     * @param result ?????
     */
    private void onSignInResult(FirebaseAuthUIAuthenticationResult result) {
    }


    /**
     * Launch the sign-in flow using FirebaseUI, which provides the UI for
     * email and password.
     */
    private void login() {
        // Choose authentication providers
        List<AuthUI.IdpConfig> providers = Collections.singletonList(new AuthUI.IdpConfig.EmailBuilder().build());

        // Create and launch sign-in intent
        Intent signInIntent = AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(providers).build();
        signInLauncher.launch(signInIntent);
    }

    /**
     * Change the activity to the main activity
     */
    private void changeActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


}