package ib.scoliowarner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Calibrate extends AppCompatActivity implements SensorEventListener {



    private SensorManager sensorManager;
    private static  final String TAG="EDUIB";
    private long startTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calibrate);

        sensorManager=(SensorManager) getSystemService(SENSOR_SERVICE);
        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

    }


    @Override
    public void onSensorChanged(SensorEvent event) {

        float [] values=event.values;
        float ax=values[0];
        float ay=values[1];
        float az=values[2];

        double angle = Math.round((Math.atan2(ay, Math.sqrt((ax*ax+az*az)))/(Math.PI/180)));

        Log.d(TAG, String.valueOf(angle));


        MainActivity.Constant.calibration= (int) angle;


    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_GAME);
        startTime=System.currentTimeMillis();

    }



    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void calibrationBtnClick(View view) {
        String toast = getResources().getString(R.string.calibrated);
        MainActivity.Constant.calibrated=true;
        Toast.makeText(this, toast, Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, MeasureMenager.class);
        startActivity(intent);

    }
}