package com.example.ptsd;

import android.app.IntentService;
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

    private Sensor mHeartRateSensor;
    private Sensor mAccelerometer;
    private SensorManager mSensorManager;
    boolean status;
    String testString = "Now returning from AmbientMonitor service.";
    IntentFilter mStatusIntentFilter = new IntentFilter("AmbientMonitor");

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
        Log.d("AmbientMonitor", "onHandleIntent");
        monitorHeart();
        Log.d("AmbientMonitor", "Operations done in ambient. Now broadcasting");
        //send broadcast back to main
        Intent tempIntent = new Intent("AmbientMonitor");

        LocalBroadcastManager.getInstance(this).sendBroadcast(tempIntent);
        Log.d("AmbientMonitor", "signal sent");
    }

    private static final SimpleDateFormat AMBIENT_DATE_FORMAT =
            new SimpleDateFormat("HH:mm", Locale.US);

    protected void onCreate(Bundle savedInstanceState) {
        //super.onCreate(savedInstanceState)
    }

    public void monitorHeart(){
        /*SensorManager mSensorManager = ((SensorManager)getSystemService(SENSOR_SERVICE));
        Sensor mHeartRateSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE);
        if(mSensorManager.registerListener(this, mHeartRateSensor, SensorManager.SENSOR_DELAY_NORMAL)){
            status = true;
        }*/

      /*  status = true;
        if(status==true){*/

        Log.d("AmbientMonitor", "monitorHeart()");
        //Sends broadcast signalling end of operation
        //}
        Intent tempIntent = new Intent(testString);
        LocalBroadcastManager.getInstance(this).sendBroadcast(tempIntent);
        Log.d("AmbientMonitor", "signal2()");
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //Log.d(TAG, "onAccuracyChanged - accuracy: " + accuracy);
    }

    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_HEART_RATE) {
            String msg = "" + (int)event.values[0];
            //Log.d(TAG, msg);
        }
    }

}
