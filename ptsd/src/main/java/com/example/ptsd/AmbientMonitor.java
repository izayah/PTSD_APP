package com.example.ptsd;

import android.app.IntentService;
import android.content.Intent;
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
        Log.d(testString, "onHandleIntent");
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

        status = true;
        if(status==true){
            Intent tempIntent = new Intent(testString);
            Log.d("1", "monitorHeart()");
            LocalBroadcastManager.getInstance(this).sendBroadcast(tempIntent);
            Log.d("2", "monitorheart 2");
        }
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
