package com.tcs.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.tcs.R;
import com.tcs.ui.CustomTitle;
import com.tcs.ui.button.ButtonPlus;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Harsh on 11/5/2016.
 */
public class EmergencyContactsActivity extends AppCompatActivity {

    @Bind(R.id.etName1)
    EditText etName1;
    @Bind(R.id.etPhone1)
    EditText etPhone1;
    @Bind(R.id.etName2)
    EditText etName2;
    @Bind(R.id.etPhone2)
    EditText etPhone2;
    @Bind(R.id.btnSave)
    ButtonPlus btnSave;
    @Bind(R.id.toolbar)
    Toolbar toolbar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_contacts);
        populate();
    }

    private void populate() {
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(CustomTitle.getTitle(EmergencyContactsActivity.this, getString(R.string.add_emergency)));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            btnSave.setBackgroundResource(R.drawable.ripple);
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EmergencyContactsActivity.this, SpeechActivity.class));
                finish();
            }
        });
    }
}
