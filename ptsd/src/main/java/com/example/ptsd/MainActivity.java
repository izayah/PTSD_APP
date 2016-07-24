package com.example.ptsd;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity /*WearableActivity*/ implements SensorEventListener{

    private String dataUrl = "main";
    private Button testButton;
    private long lastUpdate = 0;
    private float last_x, last_y, last_z;
    Intent mServiceIntent;
    private SensorManager senSensorManager;
    private Sensor senAccelerometer, senHeartBeat, senHeartRate;
    IntentFilter mStatusIntentFilter = new IntentFilter("AmbientMonitor");

    /* Metronome uses 92 bpm*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ResponseReceiver mResponseReceiver = new ResponseReceiver();

        beginMonitor();
        LocalBroadcastManager.getInstance(this).registerReceiver(mResponseReceiver, mStatusIntentFilter);

        //The following lines are used to upstart the AmbientMonitor service periodically
        //First, a new intent is created and used to create a Pending Intent -
        Calendar cal = Calendar.getInstance();
        Intent intent = new Intent(this, AmbientMonitor.class);
        PendingIntent pintent = PendingIntent.getService(this, 0, intent, 0);
        AlarmManager alarm = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarm.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), 10*10000, pintent);
    }

    public void OpenAssessment(View view) {
        Intent startAssessment = new Intent(MainActivity.this, AssessmentActivity.class);
        startActivity(startAssessment);
    }

    public void OpenStressTools(View view) {
        Intent startStressTools = new Intent(MainActivity.this, StressToolsActivity.class);
        //startActivity(startStressTools);
        Intent startIntervention = new Intent(MainActivity.this, InterventionActivity.class);
        startActivity(startIntervention);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor mySensor = sensorEvent.sensor;

        /*if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];

            long curTime = System.currentTimeMillis();
*//*increase interval to reduce amount of data*//*
            if ((curTime - lastUpdate) > 100) {
                long diffTime = (curTime - lastUpdate);
                lastUpdate = curTime;

                float speed = Math.abs(x + y + z - last_x - last_y - last_z) / diffTime * 1000;
                last_x = x;
                last_y = y;
                last_z = z;
                //Log.i("Velocity",speed + " m/s" );

                int speedThreshold = 150;
                if(speed >= speedThreshold){
                   //Log.i("Speed Threshold", "Speed threshold has been reached");
                }
            }
        }

        if(mySensor.getType() == Sensor.TYPE_HEART_BEAT){
            //Log.i("Heart Beat", ""+sensorEvent.values[0]);
        }

        if (mySensor.getType() == Sensor.TYPE_HEART_RATE) {
            //Log.i("Heart Rate", ""+sensorEvent.values[0]);
        }*/
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        Log.d("Accuracy", i+"");
    }

    protected void onPause() {
        super.onPause();
    }

    protected void onResume() {
        super.onResume();
    }

    /* Starts the heart rate monitor process
    * Creates a new intent, sets data parameters and starts the service*/
    public void beginMonitor() {
        mServiceIntent = new Intent(this, AmbientMonitor.class);
        mServiceIntent.setData(Uri.parse(dataUrl));
        this.startService(mServiceIntent);
    }

    public class ResponseReceiver extends BroadcastReceiver
    {
        public ResponseReceiver() {}

        public void onReceive(Context context, Intent intent) {
            Log.i("Main:onReceive", ""+intent.getDataString());
            //if AmbientMonitor determines the user is in a critical state,
            //start up the intervention activity
            if(intent.getAction() == "Intervene"){
                Intent startIntervention = new Intent(MainActivity.this, InterventionActivity.class);
                startActivity(startIntervention);
            }
        }
    }
}

