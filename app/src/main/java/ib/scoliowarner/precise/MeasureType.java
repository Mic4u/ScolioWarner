package ib.scoliowarner.precise;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import ib.scoliowarner.R;

public class MeasureType extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measure_type);
    }

    public void onSingleClick(View view) {
        Intent intent = new Intent(this, RotationMeasure.class);
        intent.putExtra("isOnly", true);
        startActivity(intent);
    }

    public void onDoubleClick(View view) {
        Intent intent = new Intent(this, RotationMeasure.class);
        intent.putExtra("isOnly", false);
        startActivity(intent);
    }
}