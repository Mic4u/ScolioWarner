package ib.scoliowarner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

public class MeasureMenager extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measure_menager);

        boolean calibrated= MainActivity.Constant.calibrated;
        boolean finished= MainActivity.Constant.finished;

        Button saveBtn=findViewById(R.id.saveBtn);
        if(!finished) saveBtn.setEnabled(false);
        Button measureBtn=findViewById(R.id.thoracicBtn);
        if(!calibrated) measureBtn.setEnabled(false);

    }



    public void onCalibrateBtnClick(View view) {

        Intent intent = new Intent(this, Calibrate.class);
        startActivity(intent);

    }



    public void onThoracicBtnClick(View view) {

        Intent intent = new Intent(this, Measure.class);
        startActivity(intent);

        MainActivity.Constant.finished=true;
        Button saveBtn=findViewById(R.id.saveBtn);
        saveBtn.setEnabled(true);
    }



    public void onSaveBtnClick(View view) {

        EditText name=findViewById(R.id.patient_name);

        Date currentTime = Calendar.getInstance().getTime();

        String cobbanglestring=getResources().getString(R.string.cobbangle);;
        String rightanglestring=getResources().getString(R.string.rightangle);;
        String leftanglestring=getResources().getString(R.string.leftangle);;



        String measureText= currentTime.toString()+": \n"+
                cobbanglestring+": "+(MainActivity.Constant.cobb_angle +"°\n")+
                rightanglestring+": "+(MainActivity.Constant.max_angle +"°\n")+
                leftanglestring+": "+(-MainActivity.Constant.min_angle +"°\n")+
                "\n";

        if(!name.getText().toString().equals("")) {

            if(name.getText().toString().contains(";")){
                String failed=getResources().getString(R.string.containsSemicolon);
                Toast.makeText(this, failed, Toast.LENGTH_LONG).show();
            }else {

                String FILENAME = name.getText().toString() + ";" + currentTime.toString();

                String toast = getResources().getString(R.string.toastSaved);

                File myExternalFile = new File(getExternalFilesDir(FOLDERNAME), FILENAME);

                try (FileOutputStream os = new FileOutputStream(myExternalFile)) {
                    os.write(measureText.getBytes());
                    os.close();
                    Toast.makeText(this, toast, Toast.LENGTH_LONG).show();
                } catch (FileNotFoundException e) {
                    Log.e(measureText, e.toString());
                } catch (IOException e) {
                    Log.e(measureText, e.toString());
                }

                //BACK TO MAIN MENU
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
        }else{

            String failed=getResources().getString(R.string.noName);
            Toast.makeText(this, failed, Toast.LENGTH_LONG).show();

        }
    }



    public static  final String FOLDERNAME="RESULTS";


}