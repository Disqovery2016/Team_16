package com.tcs.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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
    private DatabaseReference databaseReference;
    private FirebaseDatabase database;
    private String TAG = EmergencyContactsActivity.class.getSimpleName();
    private String userId;
    private String name;
    private String email;
    private String mobile;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_contacts);
        populate();
    }

    private void populate() {
        ButterKnife.bind(this);
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("users");
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(CustomTitle.getTitle(EmergencyContactsActivity.this, getString(R.string.add_emergency)));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            btnSave.setBackgroundResource(R.drawable.ripple);
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //         addUserChangeListener();

                //         updateUser(name, email, mobile, etPhone1.getText().toString(), etPhone2.getText().toString());
                startActivity(new Intent(EmergencyContactsActivity.this, SpeechActivity.class));
                finish();
            }
        });
    }


/*    private void updateUser(String name, String mobile, String email, String emergency_contact1, String emergency_contact2) {
        if (!TextUtils.isEmpty(emergency_contact1)) {
            databaseReference.child(userId).setValue(emergency_contact1);
        }
        if (!TextUtils.isEmpty(emergency_contact2)) {
            databaseReference.child(userId).setValue(emergency_contact2);
        }
    }


    private void addUserChangeListener(){
        userId = databaseReference.push().getKey();
        databaseReference.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Users user = dataSnapshot.getValue(Users.class);
                name = user.name;
                email = user.email;
                mobile = user.mobile;
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
    }*/
}