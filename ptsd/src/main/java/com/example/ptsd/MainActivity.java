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

      /*  senSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
       senHeartRate = senSensorManager.getDefaultSensor(21);
       senSensorManager.registerListener(this, senHeartRate , SensorManager.SENSOR_DELAY_NORMAL);
        /*senHeartRate = senSensorManager.getDefaultSensor(21);
        //senHeartBeat = senSensorManager.getDefaultSensor(Sensor.TYPE_HEART_BEAT);
        senHeartBeat = senSensorManager.getDefaultSensor(31);
+
        //senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        senSensorManager.registerListener(this, senAccelerometer , SensorManager.SENSOR_DELAY_NORMAL);

        senSensorManager.registerListener(this, senHeartBeat, SensorManager.SENSOR_DELAY_NORMAL);

        List<Sensor> sensors = senSensorManager.getSensorList(Sensor.TYPE_ALL);
        for(Sensor sensor: sensors){
            //Log.i("Sensor", sensor.getName()+" "+sensor.);
        }
        /* Metronome uses 92 bpm*/
        ResponseReceiver mResponseReceiver = new ResponseReceiver();
       //
        beginMonitor();
        LocalBroadcastManager.getInstance(this).registerReceiver(mResponseReceiver, mStatusIntentFilter);

        //The following lines are used to upstart the AmbientMonitor service periodically
        //First, a new intent is created and used to create a Pending Intent -
        Calendar cal = Calendar.getInstance();
        Intent intent = new Intent(this, AmbientMonitor.class);
        PendingIntent pintent = PendingIntent.getService(this, 0, intent, 0);
        AlarmManager alarm = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarm.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), 10*1000, pintent);
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
                   Log.i("Speed Threshold", "Speed threshold has been reached");
                }
            }
        }

        if(mySensor.getType() == Sensor.TYPE_HEART_BEAT){
            Log.i("Heart Beat", ""+sensorEvent.values[0]);
        }

        if (mySensor.getType() == Sensor.TYPE_HEART_RATE) {
            Log.i("Heart Rate", ""+sensorEvent.values[0]);
        }
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        Log.d("Accuracy", i+"");
    }

    protected void onPause() {
        super.onPause();
        //senSensorManager.unregisterListener(this);
    }

    protected void onResume() {
        super.onResume();
        /*senSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        senSensorManager.registerListener(this, senHeartRate, SensorManager.SENSOR_DELAY_NORMAL);
        senSensorManager.registerListener(this, senHeartBeat, SensorManager.SENSOR_DELAY_NORMAL);*/
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
        public ResponseReceiver() {}

        public void onReceive(Context context, Intent intent) {
            //testButton = (Button) findViewById(R.id.button1);
            Log.i("Main:onReceive", intent.getAction());
            //testButton.setText(intent.getAction());
        }
    }
}

