package com.tcs.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.tcs.R;
import com.tcs.ui.CustomTitle;
import com.tcs.ui.Snackbar;
import com.tcs.ui.button.ButtonPlus;
import com.tcs.util.SessionManager;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Harsh on 11/5/2016.
 */
public class LoginActivity extends AppCompatActivity {

    @Bind(R.id.etPassword)
    EditText etPassword;
    @Bind(R.id.etEmail)
    EditText etEmail;
    @Bind(R.id.btnLogin)
    ButtonPlus btnLogin;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.txtForgotPassword)
    TextView txtForgotPassword;
    @Bind(R.id.txtRegister)
    TextView txtRegister;
    private FirebaseAuth auth;
    private SessionManager sessionManager;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessionManager = new SessionManager(getApplicationContext());
        /*auth = FirebaseAuth.getInstance();
        if(auth != null){
            startActivity(new Intent(LoginActivity.this, EmergencyContactsActivity.class));
            finish();
        }*/
        setContentView(R.layout.activity_login);
        populate();
    }

    private void populate() {
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(CustomTitle.getTitle(this, "Login"));


        auth = FirebaseAuth.getInstance();
        txtForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
            }
        });
        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            btnLogin.setBackgroundResource(R.drawable.ripple);
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etEmail.getText().toString().equals("")){
                    Snackbar.show(LoginActivity.this, getString(R.string.no_text));
                }

                if (etPassword.getText().toString().equals("")){
                    Snackbar.show(LoginActivity.this, getString(R.string.no_text));
                }
                progressBar.setVisibility(View.VISIBLE);
                auth.signInWithEmailAndPassword(etEmail.getText().toString(), etPassword.getText().toString())
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                sessionManager.setLogin(true);
                                if (!task.isSuccessful()){
                                    Snackbar.show(LoginActivity.this, getString(R.string.failed));
                                }
                                else {
                                    startActivity(new Intent(LoginActivity.this, EmergencyContactsActivity.class));
                                    finish();
                                }
                            }
                        });
            }
        });
    }
}
