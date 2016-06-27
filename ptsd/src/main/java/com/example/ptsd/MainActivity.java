package com.example.ptsd;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String hello_world = "Hello World!";
    }

            public void sendMessage(View view){
            Intent startAssessment = new Intent(MainActivity.this, AssessmentActivity.class);
            startActivity(startAssessment);
        }

    }

