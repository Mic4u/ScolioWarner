package ib.scoliowarner.precise;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

import ib.scoliowarner.R;
import ib.scoliowarner.simple.SimpleMain;

public class RotationMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rotation_main);

        Constant.results1.clear();
        Constant.results2.clear();
    }

    public void onRotationMeasureClick(View view) {
        Intent intent = new Intent(this, MeasureType.class);
        startActivity(intent);
    }

    public void onRotationInstructionClick(View view) {
        //TO_DO
    }

    public void onRotationResultsClick(View view) {
        Intent intent = new Intent(this, RotationViewer.class);
        startActivity(intent);
    }

    public static class Constant {

        public static ArrayList<int[]> results1 = new ArrayList<>();
        public static ArrayList<int[]> results2 = new ArrayList<>();

    }
}