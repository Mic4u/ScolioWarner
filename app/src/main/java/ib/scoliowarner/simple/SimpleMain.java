package ib.scoliowarner.simple;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;

import java.io.File;

import ib.scoliowarner.R;

public class SimpleMain extends AppCompatActivity {





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_main);

        Button results = findViewById(R.id.view);

        SimpleMain.Constant.calibrated=false;
        SimpleMain.Constant.finished=false;
        SimpleMain.Constant.cobb_angle = 720;
        SimpleMain.Constant.max_angle = 0;
        SimpleMain.Constant.min_angle = 0;
        SimpleMain.Constant.calibration = 0;
        File[] directories = new File(Environment.getExternalStorageDirectory().getPath() + "/Android/data/ib.scoliowarner/files").listFiles();
        if(directories.length==0) results.setEnabled(false);
    }

    public void onInstructionBtnClick(View view) {

        Intent intent = new Intent(this, Instruction.class);
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

    public static class Constant {

        public static int calibration;
        public static int cobb_angle;
        public static int max_angle;
        public static int min_angle;

        public static boolean calibrated;
        public static boolean finished;
    }

}