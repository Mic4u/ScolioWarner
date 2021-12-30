package ib.scoliowarner.precise;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ib.scoliowarner.R;

public class RotationResultViewer extends AppCompatActivity {

    ArrayList<int[]> results1 = RotationMain.Constant.results1;
    ArrayList<int[]> results2 = RotationMain.Constant.results2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rotation_result_viewer);

        if(results1.size()==results2.size()){
            TextView textView1 = findViewById(R.id.results1TV);
            TextView textView2 = findViewById(R.id.results2TV);
            TextView textView3 = findViewById(R.id.difTV);
            TextView textView4 = findViewById(R.id.relativeTV1);
            TextView textView5 = findViewById(R.id.relativeTV2);
            TextView textView6 = findViewById(R.id.relativeDifTV);

            textView1.setText("FIRST\nMEASURE"+"\n");
            textView2.setText("SECOND\nMEASURE"+"\n");
            textView3.setText("DIF"+"\n\n");
            textView4.setText("RELAT 1"+"\n\n");
            textView5.setText("RELAT 2"+"\n\n");
            textView6.setText("RELAT\nDIF"+"\n");


            for(int i=0; i<results1.size();i++){
                textView1.setText(textView1.getText() + "" + results1.get(i)[0] + "|" + results1.get(i)[1] + "|" + results1.get(i)[2] + "\n");
                textView2.setText(textView2.getText() + "" + results2.get(i)[0] + "|" + results2.get(i)[1] + "|" + results2.get(i)[2] + "\n");
                textView3.setText(textView3.getText()+"" + (results1.get(i)[0]-results2.get(i)[0]) + "|" + (results1.get(i)[1]-results2.get(i)[1]) + "|" +(results1.get(i)[2]-results2.get(i)[2]) + "\n");


                if(i==0) {
                    textView4.setText(textView4.getText() +" - | - | - \n");
                    textView5.setText(textView5.getText() +" - | - | - \n");
                    textView6.setText(textView6.getText() +" - | - | - \n");
                }else{
                    int aziRelDif1 = (results1.get(i)[0]-results1.get(i-1)[0]);
                    int rollRelDif1 = (results1.get(i)[1]-results1.get(i-1)[1]);
                    int pitchRelDif1 = (results1.get(i)[2]-results1.get(i-1)[2]);
                    textView4.setText(textView4.getText() + "" + aziRelDif1 + "|" + rollRelDif1 + "|" +pitchRelDif1 + "\n");
                    int aziRelDif2 = (results2.get(i)[0]-results2.get(i-1)[0]);
                    int rollRelDif2 = (results2.get(i)[1]-results2.get(i-1)[1]);
                    int pitchRelDif2 = (results2.get(i)[2]-results2.get(i-1)[2]);
                    textView5.setText(textView5.getText() + "" + aziRelDif2 + "|" + rollRelDif2 + "|" +pitchRelDif2 + "\n");
                    textView6.setText(textView6.getText() + "" + (aziRelDif1-aziRelDif2) + "|" + (rollRelDif2-rollRelDif2) + "|" + (pitchRelDif2-pitchRelDif2) + "\n");
                }
            }

        }else{
            Toast.makeText(this, R.string.WrongLength, Toast.LENGTH_LONG).show();
            RotationMain.Constant.results2.clear();
            Intent intent = new Intent(this, RotationMeasure.class);
            intent.putExtra("isSecond", true);
            startActivity(intent);
        }

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, RotationMain.class);
        startActivity(intent);
    }
}