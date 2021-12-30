package ib.scoliowarner.precise;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import ib.scoliowarner.MainActivity;
import ib.scoliowarner.R;
import ib.scoliowarner.simple.SimpleMain;

public class RotationSave extends AppCompatActivity {

    public static  final String FOLDERNAME="RESULTSROTATION";
    EditText name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rotation_save);

        name=findViewById(R.id.editTextPersonName);
    }

    public void onSaveClick(View view) {
        Date currentTime = Calendar.getInstance().getTime();


        StringBuilder results= new StringBuilder(currentTime.toString() + ": \n");

        for(int i=0; i<RotationMain.Constant.results1.size();i++){
                                 results.append("|")
                                        .append(RotationMain.Constant.results1.get(i)[0])
                                        .append("|")
                                        .append(RotationMain.Constant.results1.get(i)[1])
                                        .append("|")
                                        .append(RotationMain.Constant.results1.get(i)[2])
                                        .append("\n");
        }

        String measureText= results.toString();


        if(!name.getText().toString().equals("")) {

            if(name.getText().toString().contains(";")){
                String failed=getResources().getString(R.string.containsSemicolon);
                Toast.makeText(this, failed, Toast.LENGTH_LONG).show();
            }else {

                String FILENAME = name.getText().toString() + ";" + currentTime.toString();

                String toast = getResources().getString(R.string.toastSaved);

                File myExternalFile = new File(getExternalFilesDir(FOLDERNAME), FILENAME); ///sdcard/Android/data/ib.scoliowarner/files

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
                Intent intent = new Intent(this, RotationMain.class);
                startActivity(intent);
            }
        }else{

            String failed=getResources().getString(R.string.noName);
            Toast.makeText(this, failed, Toast.LENGTH_LONG).show();

        }
    }
}