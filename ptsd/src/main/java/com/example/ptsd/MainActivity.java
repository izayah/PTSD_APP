package com.example.ptsd;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity{//WearableActivity implements SensorEventListener{

    private TextView mTextView;
    private final String TAG = "Ambient Mode";
    /*Button testButton;
    private Sensor mHeartRateSensor;
    private SensorManager mSensorManager;*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //testButton = (Button) findViewById(R.id.button1);
        /*monitorHeart();
        setAmbientEnabled();
        monitorHeart();*/

    }

   /* public void OpenAssessment(View view){
            Intent startAssessment = new Intent(MainActivity.this, AssessmentActivity.class);
            startActivity(startAssessment);
        }
    public void OpenStressTools(View view){
        Intent startStressTools = new Intent(MainActivity.this, StressToolsActivity.class);
        startActivity(startStressTools);
    }
    @Override
    public void onEnterAmbient(Bundle ambientDetails) {
        super.onEnterAmbient(ambientDetails);
        *//*we start getting heart rate info here*//*
        monitorHeart();
        updateDisplay();
    }

    @Override
    public void onUpdateAmbient() {
        super.onUpdateAmbient();
        updateDisplay();
    }

    @Override
    public void onExitAmbient() {
        updateDisplay();
        super.onExitAmbient();
    }

    private void updateDisplay() {
        if (isAmbient()) {
            //testButton.setTextColor(getResources().getColor(android.R.color.darker_gray));
        } else {
            //testButton.setTextColor(getResources().getColor(android.R.color.black));
        }
    }

    public void monitorHeart(){
        mSensorManager = ((SensorManager)getSystemService(SENSOR_SERVICE));
        mHeartRateSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE);
        mSensorManager.registerListener(this, mHeartRateSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }*/

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        Log.d(TAG, "onAccuracyChanged - accuracy: " + accuracy);
    }

    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_HEART_RATE) {
           // String msg = "" + (int)event.values[0];
            //testButton.setText(msg);
            Log.d(TAG, "Last detected heart rate is: " + (int)event.values[0]);
        }
    }

    }

