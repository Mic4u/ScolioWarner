package ib.scoliowarner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button results = findViewById(R.id.view);

        Constant.calibrated=false;
        Constant.finished=false;
        Constant.cobb_angle = 720;
        Constant.max_angle = 0;
        Constant.min_angle = 0;
        Constant.calibration = 0;
        File[] directories = new File(Environment.getExternalStorageDirectory().getPath() + "/Android/data/ib.scoliowarner/files").listFiles();
        if(directories.length==0) results.setEnabled(false);
    }

    public void onInstructionBtnClick(View view) {

        Intent intent = new Intent(this,Instruction.class);
        startActivity(intent);

    }

    public void onMenuBtn1click(View view) {

        Intent intent = new Intent(this, MeasureMenager.class);
        startActivity(intent);

    }

    public void onMenuBtn2click(View view) {

        Intent intent = new Intent(this, ResultViewer.class);
        startActivity(intent);

    }

    public void onMenuBtn3click(View view) {

        Intent intent = new Intent(this, RotationMeasure.class);
        startActivity(intent);

    }

    public static class Constant {

        public static int calibration;
        public static int cobb_angle;
        public static int max_angle;
        public static int min_angle;

        public static boolean calibrated;
        public static boolean finished;
    }

}