package ib.scoliowarner;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RotationMeasure extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    Sensor accelerometer;
    Sensor magnetometer;
    private static final String TAG = "EDUIB";
    private long startTime;

    boolean started;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rotation_measure);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        started = false;
    }

    float[] mGravity;
    float[] mGeomagnetic;
    float azimut;
    float roll;
    float pitch;


    @Override
    public void onSensorChanged(SensorEvent event) {
        if (started) {

            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
                mGravity = event.values;
            if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
                mGeomagnetic = event.values;
            if (mGravity != null && mGeomagnetic != null) {
                float[] R = new float[9];
                float[] I = new float[9];
                boolean success = SensorManager.getRotationMatrix(R, I, mGravity, mGeomagnetic);
                if (success) {
                    float[] orientation = new float[3];
                    SensorManager.getOrientation(R, orientation);
                    float toDegsRatio = (float) (180/Math.PI);
                    azimut = orientation[0]*toDegsRatio;
                    roll = orientation[1]*toDegsRatio;
                    pitch = orientation[2]*toDegsRatio;

                }

            }
                TextView angleTV = findViewById(R.id.angleTV);
                angleTV.setText( (int) azimut + "|" + (int) roll + "|" + (int) pitch);



        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
        sensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_UI);
        startTime = System.currentTimeMillis();

    }


    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void onStartBtnClick(View view) {
        started = true;
    }

    public void onFinishBtnClick(View view) {
//        MainActivity.Constant.cobb_angle=MainActivity.Constant.max_angle-MainActivity.Constant.min_angle;
//        MainActivity.Constant.finished =true;
//        String toast = getResources().getString(R.string.measured);
//        Toast.makeText(this, toast, Toast.LENGTH_LONG).show();
//        Intent intent = new Intent(this, MeasureMenager.class);
//        startActivity(intent);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}