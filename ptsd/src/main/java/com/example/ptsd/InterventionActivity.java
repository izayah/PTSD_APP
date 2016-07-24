package com.example.ptsd;

import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class InterventionActivity extends AppCompatActivity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intervention);
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        //vibrator.vibrate(5000);
    }
}
