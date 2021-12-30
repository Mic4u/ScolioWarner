package ib.scoliowarner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;

import java.io.File;

import ib.scoliowarner.precise.RotationMain;
import ib.scoliowarner.precise.RotationMeasure;
import ib.scoliowarner.simple.Instruction;
import ib.scoliowarner.simple.MeasureMenager;
import ib.scoliowarner.simple.ResultViewer;
import ib.scoliowarner.simple.SimpleMain;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }



    public void onMenuBtn3click(View view) {

        Intent intent = new Intent(this, RotationMain.class);
        startActivity(intent);

    }

    public void onSimClick(View view) {
        Intent intent = new Intent(this, SimpleMain.class);
        startActivity(intent);
    }
}