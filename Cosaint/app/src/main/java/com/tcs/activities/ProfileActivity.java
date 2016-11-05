package com.tcs.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tcs.R;
import com.tcs.models.Users;
import com.tcs.ui.CustomTitle;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Harsh on 11/5/2016.
 */
public class ProfileActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.txtName)
    TextView txtName;
    @Bind(R.id.txtPhone)
    TextView txtPhone;
    @Bind(R.id.txtEmail)
    TextView txtEmail;
    private String TAG = ProfileActivity.class.getSimpleName();
    private DatabaseReference reference;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        populate();
    }

    private void populate() {
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(CustomTitle.getTitle(ProfileActivity.this, getString(R.string.profile)));
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("users");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Users user = dataSnapshot.getValue(Users.class);

                txtName.setText(user.name);
                txtEmail.setText(user.email);
                txtPhone.setText(user.mobile);

                if (user == null){
                    Log.e(TAG, "User Data is Null");
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
