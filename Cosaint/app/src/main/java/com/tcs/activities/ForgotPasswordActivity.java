package com.tcs.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

import com.tcs.R;
import com.tcs.ui.CustomTitle;
import com.tcs.ui.button.ButtonPlus;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Harsh on 11/5/2016.
 */
public class ForgotPasswordActivity extends AppCompatActivity {

    @Bind(R.id.etEmail)
    EditText etEmail;
    @Bind(R.id.btnForgotPassword)
    ButtonPlus btnForgotPassword;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        populate();
    }

    private void populate() {
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(CustomTitle.getTitle(ForgotPasswordActivity.this, getString(R.string.reset_password)));
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
