package ib.scoliowarner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Instruction extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction);

        TextView txtMoves = findViewById(R.id.txtMoves);

        String instruction=getResources().getString(R.string.instructionText);

        txtMoves.setText(instruction);

    }
}