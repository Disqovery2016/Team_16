package com.tcs.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import com.tcs.R;
import com.tcs.ui.button.ButtonPlus;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Harsh on 11/5/2016.
 */
public class ConfirmOTPActivity extends AppCompatActivity{

    @Bind(R.id.etOtp)
    EditText etOTP;
    @Bind(R.id.btnConfirmOTP)
    ButtonPlus btnConfirmOTP;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_otp);
        populate();
    }

    private void populate() {
        ButterKnife.bind(this);
    }
}
