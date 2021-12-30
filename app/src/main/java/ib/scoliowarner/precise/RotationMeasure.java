package ib.scoliowarner.precise;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import ib.scoliowarner.MainActivity;
import ib.scoliowarner.R;

public class RotationMeasure extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    Sensor accelerometer;
    Sensor magnetometer;
    private static final String TAG = "EDUIB";
    private long startTime;

    boolean started;

    boolean isSecond = false;
    boolean isOnly = false;


    int readCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rotation_measure);

        Intent intent = getIntent();
        isSecond = intent.getBooleanExtra("isSecond", false);
        isOnly = intent.getBooleanExtra("isOnly", false);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        started = false;

        TextView textView = findViewById(R.id.textView);
        if (isSecond) {
            textView.setText("Required Measure Points:" + " " + RotationMain.Constant.results1.size() + "\n");
        }
    }

    float[] mGravity;
    float[] mGeomagnetic;
    float azimut;
    float roll;
    float pitch;
    float azimutCal = 0;
    float rollCal = 0;
    float pitchCal = 0;
    ArrayList<int[]> results = new ArrayList<>();

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (started) {

            TextView angleTV = findViewById(R.id.angleTV);
            angleTV.setText((int) azimut + "|" + (int) roll + "|" + (int) pitch);

        }

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
                float toDegsRatio = (float) (180 / Math.PI);

                azimut = ((orientation[0] * toDegsRatio - azimutCal));
                azimut = calibration(azimut);

                roll = ((orientation[1] * toDegsRatio - rollCal));
                roll = calibration(roll);

                pitch = ((orientation[2] * toDegsRatio - pitchCal));
                pitch = calibration(pitch);

            }

        }

    }

    float calibration(float x) {
        if (x > 180) {
            x = x - 360;
        } else if (x < -180) {
            x = x + 360;
        }
        return x;
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
        if (started) {

            readCount++;

            TextView textView = findViewById(R.id.textView);


            textView.setText(textView.getText() + "" + (int) azimut + "|" + (int) roll + "|" + (int) pitch + "\n");
            int[] sinRes = new int[]{(int) azimut, (int) roll, (int) pitch};
            if (isSecond) {
                RotationMain.Constant.results2.add(sinRes);
            } else {
                RotationMain.Constant.results1.add(sinRes);
            }

        } else {
            started = true;
            Button button = findViewById(R.id.nextBtn);
            button.setText(R.string.next);
            azimutCal = azimut;
            rollCal = roll;
            pitchCal = pitch;

        }
    }

    public void onFinishBtnClick(View view) {

        Intent intent;

        if (isSecond) {
            intent = new Intent(this, RotationResultViewer.class);
        } else if (isOnly) {
            intent = new Intent(this, RotationSave.class);
        } else {
            intent = new Intent(this, RotationMeasure.class);
            intent.putExtra("isSecond", true);
        }
        startActivity(intent);
    }
}