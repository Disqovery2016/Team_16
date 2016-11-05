package com.tcs.activities;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.tcs.R;
import com.tcs.ui.CustomTitle;
import com.tcs.util.SessionManager;

import java.io.File;
import java.util.ArrayList;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Harsh on 11/4/2016.
 */
public class SpeechActivity extends AppCompatActivity {

    @Bind(R.id.txtSpeechInput)
    TextView txtSpeechInput;
    @Bind(R.id.btnSpeak)
    ImageButton btnSpeak;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    private final int REQ_CODE_SPEECH_INPUT = 100;
    File destination;
    Uri selectedImage;
    private static final int PICK_CAMERA_IMAGE = 2;
    private SessionManager sessionManager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speech);
        populate();
    }

    //Inititalising the variables using butterknife

    private void populate() {
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        sessionManager = new SessionManager(getApplicationContext());
        getSupportActionBar().setTitle(CustomTitle.getTitle(getApplicationContext(), "Say Something"));

        btnSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promptSpeechInput();
            }
        });
    }

    /*
    *   opening prompt for speech input method
    */
    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, getString(R.string.speech_prompt));

        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(), getString(R.string.speech_not_supported), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    txtSpeechInput.setText(result.get(0));

                    if (txtSpeechInput.getText().toString().equals("help") || txtSpeechInput.getText().toString().equals("i need help")
                            || txtSpeechInput.getText().toString().equals("help me")
                            || txtSpeechInput.getText().toString().equals("please help")
                            || txtSpeechInput.getText().toString().equals("need help")
                            || txtSpeechInput.getText().toString().equals("somebody help")
                            || txtSpeechInput.getText().toString().equals("somebody please help")
                            || txtSpeechInput.getText().toString().equals("somebody help me")) {
                        startActivity(new Intent(SpeechActivity.this, CameraActivity.class));
                    }
                }
                break;
            }
        }
    }


    //Menu options
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.profile:
                startActivity(new Intent(SpeechActivity.this, ProfileActivity.class));
                break;
            case R.id.logout:
                Intent intent = new Intent(SpeechActivity.this, LoginActivity.class);
                sessionManager.setLogin(false);
                startActivity(intent);
                ActivityCompat.finishAffinity(this);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}

