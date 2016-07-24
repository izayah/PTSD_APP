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
    String status;
    String testString = "Now returning from AmbientMonitor service.";
    IntentFilter mStatusIntentFilter = new IntentFilter("AmbientMonitor");
    private float last_x, last_y, last_z;
    long lastUpdate = 0;
    int heartRateThreshold = 80;

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
        Intent tempIntent = new Intent(status);
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
        senSensorManager.registerListener(this, senAccelerometer , SensorManager.SENSOR_DELAY_NORMAL);
        senSensorManager.registerListener(this, senHeartRate , SensorManager.SENSOR_DELAY_NORMAL);
        //Sends broadcast signalling end of operation
        Intent tempIntent = new Intent(status);
        LocalBroadcastManager.getInstance(this).sendBroadcast(tempIntent);
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //Log.d(TAG, "onAccuracyChanged - accuracy: " + accuracy);
    }

    public void onSensorChanged(SensorEvent event) {

        if (event.sensor.getType() == Sensor.TYPE_HEART_RATE) {
            //Log.i("Ambient Heart Rate", ""+event.values[0] + " bpm");
            //checks if heart beat is over threshold as well as the accuracy
            //low accuracy measures aren't counted
            //if(event.values[0] >= heartRateThreshold /*&& event.accuracy != 0*/){
                status = "Intervene";
                //Log.i("Ambient Heart Rate", "Reached heart rate threshold! Critical Limits");
                //broadcast to the main activity to start intervention and end this service
                this.stopSelf();
                Intent tempIntent = new Intent(status);
                LocalBroadcastManager.getInstance(this).sendBroadcast(tempIntent);

            //}
        }
        if (event.sensor.getType() == Sensor.TYPE_HEART_BEAT) {
            Log.d("Ambient Heart Beat", ""+event.values[0]);
        }
        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            long curTime = System.currentTimeMillis();
/*increase interval to reduce amount of data*/
            if ((curTime - lastUpdate) > 100) {
                long diffTime = (curTime - lastUpdate);
                lastUpdate = curTime;

                float speed = Math.abs(x + y + z - last_x - last_y - last_z) / diffTime * 1000;
                last_x = x;
                last_y = y;
                last_z = z;
                Log.i("Velocity",speed + " m/s" );
                //int speedThreshold = 150;
            }
        }
    }
}
