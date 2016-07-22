package com.example.ptsd;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class AmbientMonitor extends IntentService implements SensorEventListener{

    private Sensor senHeartRate,senHeartBeat,senAccelerometer;
    private SensorManager senSensorManager;
    boolean status;
    String testString = "Now returning from AmbientMonitor service.";
    IntentFilter mStatusIntentFilter = new IntentFilter("AmbientMonitor");
    private float last_x, last_y, last_z;

    public AmbientMonitor(String name) {
        super(name);
    }

    public AmbientMonitor(){
        super("AmbientMonitor");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        //Get information from the incoming intent
        String dataString = intent.getDataString();
        monitorHeart();
        //send broadcast back to main
        Intent tempIntent = new Intent("AmbientMonitor");
        LocalBroadcastManager.getInstance(this).sendBroadcast(tempIntent);
    }

    private static final SimpleDateFormat AMBIENT_DATE_FORMAT =
            new SimpleDateFormat("HH:mm", Locale.US);

    protected void onCreate(Bundle savedInstanceState) {
        //super.onCreate(savedInstanceState);
    }

    public void monitorHeart(){
        senSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        senHeartRate = senSensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE);
        //senHeartBeat = senSensorManager.getDefaultSensor(Sensor.TYPE_HEART_BEAT);
        Sensor senAll = senSensorManager.getDefaultSensor(Sensor.TYPE_ALL);
        senSensorManager.registerListener(this, senAccelerometer , SensorManager.SENSOR_DELAY_NORMAL);
        senSensorManager.registerListener(this, senHeartRate , SensorManager.SENSOR_DELAY_NORMAL);
        //senSensorManager.registerListener(this, senHeartBeat, SensorManager.SENSOR_DELAY_NORMAL);
        senSensorManager.registerListener(this, senAll, senSensorManager.SENSOR_DELAY_NORMAL);

        //Sends broadcast signalling end of operation
        //}
        Intent tempIntent = new Intent(testString);
        //LocalBroadcastManager.getInstance(this).sendBroadcast(tempIntent);
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //Log.d(TAG, "onAccuracyChanged - accuracy: " + accuracy);
    }

    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_HEART_RATE) {
            Log.d("Ambient Heart Rate", ""+event.values[0] + " bpm");
        }

        if (event.sensor.getType() == Sensor.TYPE_HEART_BEAT) {
            Log.d("Ambient Heart Beat", ""+event.values[0]);
        }

        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            long lastUpdate = 0;


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

        Log.d("Ambient Accel", ""+event.values[0]);
        }
}
