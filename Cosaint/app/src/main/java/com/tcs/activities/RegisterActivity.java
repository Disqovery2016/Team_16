package com.tcs.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tcs.R;
import com.tcs.models.Users;
import com.tcs.ui.CustomTitle;
import com.tcs.ui.Snackbar;
import com.tcs.ui.button.ButtonPlus;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Harsh on 11/5/2016.
 */
public class RegisterActivity extends AppCompatActivity {

    @Bind(R.id.etName)
    EditText etName;
    @Bind(R.id.etMobile)
    EditText etMobile;
    @Bind(R.id.etEmail)
    EditText etEmail;
    @Bind(R.id.etPassword)
    EditText etPassword;
    @Bind(R.id.btnRegister)
    ButtonPlus btnRegister;
    @Bind(R.id.etConfirmPassword)
    EditText etConfirmPassword;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    private FirebaseAuth auth;
    private DatabaseReference databaseReference;
    private FirebaseDatabase database;
    private String TAG = RegisterActivity.class.getSimpleName();
    private String userId;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        populate();
    }

    private void populate() {
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(CustomTitle.getTitle(this, "Register"));
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("users");

        database.getReference("app_title").setValue("Cosaint");
        database.getReference("app_title").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e(TAG, "App Title Changed");
                String appTitle = dataSnapshot.getValue(String .class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "Failed to read app title value.", databaseError.toException());
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            btnRegister.setBackgroundResource(R.drawable.ripple);
        }
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (etEmail.getText().toString().equals("")) {
                    Snackbar.show(RegisterActivity.this, getString(R.string.no_text));
                    return;
                }
                if (etName.getText().toString().equals("")) {
                    Snackbar.show(RegisterActivity.this, getString(R.string.no_text));
                    return;
                }
                if (etMobile.getText().toString().equals("")) {
                    Snackbar.show(RegisterActivity.this, getString(R.string.no_text));
                    return;
                }
                if (etPassword.getText().toString().equals("")) {
                    Snackbar.show(RegisterActivity.this, getString(R.string.no_text));
                    return;
                }
                if (etPassword.length() < 6) {
                    Snackbar.show(RegisterActivity.this, getString(R.string.too_short));
                    return;
                }
                if (!etPassword.getText().toString().equals(etConfirmPassword.getText().toString())){
                    Snackbar.show(RegisterActivity.this, getString(R.string.no_match));
                }
                progressBar.setVisibility(View.VISIBLE);
                auth.createUserWithEmailAndPassword(etEmail.getText().toString(), etPassword.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (TextUtils.isEmpty(userId)){
                                    createUser(etName.getText().toString(), etEmail.getText().toString()
                                            , etMobile.getText().toString());
                                }else {
                                    Snackbar.show(RegisterActivity.this, getString(R.string.already_registered));
                                    return;
                                }
                                if (!task.isSuccessful()) {
                                    Snackbar.show(RegisterActivity.this, getString(R.string.failed));
                                } else {
                                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                    finish();
                                }
                            }
                        });
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }

    private void createUser(String name, String mobile, String email){
        if (TextUtils.isEmpty(userId)){
            userId = databaseReference.push().getKey();
        }
        Users user = new Users(name, mobile, email, null, null);
        databaseReference.child(userId).setValue(user);
        addUserChangeListener();
    }

    private void addUserChangeListener(){
        databaseReference.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Users user = dataSnapshot.getValue(Users.class);

                if (user == null){
                    Log.e(TAG, "User Data is Null");
                }

                Log.e(TAG, "User data is changed" + user.name + "," + user.email);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "Failed to read user", databaseError.toException());
            }
        });
    }


    private void updateUser(String name, String mobile, String email){
        if(!TextUtils.isEmpty(name)){
            databaseReference.child(userId).setValue(name);
        }
        if (!TextUtils.isEmpty(email)){
            databaseReference.child(userId).setValue(email);
        }
    }
}
