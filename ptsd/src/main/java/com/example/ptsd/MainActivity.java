package com.example.ptsd;

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

public class MainActivity extends AppCompatActivity /*WearableActivity*/ implements SensorEventListener{

    private String dataUrl = "main";
    private Button testButton;
    private long lastUpdate = 0;
    private float last_x, last_y, last_z;
    Intent mServiceIntent;
    private SensorManager senSensorManager;
    private Sensor senAccelerometer;
    private Sensor senHeartRate;
    IntentFilter mStatusIntentFilter = new IntentFilter("AmbientMonitor");

    /* MEtronome uses 92 bpm*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

       senSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        senHeartRate = senSensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE);
        senSensorManager.registerListener(this, senAccelerometer , SensorManager.SENSOR_DELAY_NORMAL);
        senSensorManager.registerListener(this, senHeartRate , SensorManager.SENSOR_DELAY_NORMAL);
        /* MEtronome uses 92 bpm*/
        //ResponseReceiver mResponseReceiver = new ResponseReceiver();
        //beginMonitor();
        //LocalBroadcastManager.getInstance(this).registerReceiver(mResponseReceiver, mStatusIntentFilter);

        //The following lines are used to upstart the AmbientMonitor service periodically
        //First, a new intent is created and used to create a Pending Intent -
       /* Calendar cal = Calendar.getInstance();
        Intent intent = new Intent(this, AmbientMonitor.class);
        PendingIntent pintent = PendingIntent.getService(this, 0, intent, 0);
        AlarmManager alarm = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarm.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), 10*1000, pintent);*/
    }

    public void OpenAssessment(View view) {
        Intent startAssessment = new Intent(MainActivity.this, AssessmentActivity.class);
        startActivity(startAssessment);
    }

    public void OpenStressTools(View view) {
        Intent startStressTools = new Intent(MainActivity.this, StressToolsActivity.class);
        startActivity(startStressTools);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor mySensor = sensorEvent.sensor;

        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];

            long curTime = System.currentTimeMillis();
/*increase interval to reduce amount of data*/
            if ((curTime - lastUpdate) > 1000) {
                long diffTime = (curTime - lastUpdate);
                lastUpdate = curTime;

                float speed = Math.abs(x + y + z - last_x - last_y - last_z) / diffTime * 1000;
                last_x = x;
                last_y = y;
                last_z = z;
                Log.d("Velocity",speed + " m/s^2" );

                int speedThreshold = 150;
                if(speed >= speedThreshold){
                   Log.d("Speed Threshold", "Speed threshold has been reached");
                }
            }
        }
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        //Log.d("Accuracy", i+"");
    }

    protected void onPause() {
        super.onPause();
        //senSensorManager.unregisterListener(this);
    }

    protected void onResume() {
        super.onResume();
        //senSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    /* Starts the heart rate monitor process
    * Creates a new intent, sets data parameters and starts the service*/
    public void beginMonitor() {
        mServiceIntent = new Intent(this, AmbientMonitor.class);
        mServiceIntent.setData(Uri.parse(dataUrl));
        this.startService(mServiceIntent);
    }

    public void viewMonitorInfo() {
        ResponseReceiver mResponseReceiver = new ResponseReceiver();
        LocalBroadcastManager.getInstance(this).registerReceiver(mResponseReceiver, mStatusIntentFilter);
    }

    public class ResponseReceiver extends BroadcastReceiver
    {
        // Prevents instantiation
        public ResponseReceiver() {
            //Log.d("Main", "ResponseReceiver Created");
        }

        public void onReceive(Context context, Intent intent) {
            //Log.d("Main", "Received Intent from BroadCast");
            testButton = (Button) findViewById(R.id.button1);
            testButton.setText(intent.getAction());
        }
    }


}

