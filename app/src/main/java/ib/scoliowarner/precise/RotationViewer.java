package ib.scoliowarner.precise;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import ib.scoliowarner.R;

public class RotationViewer extends AppCompatActivity {

    private ArrayList<String> names_list = new ArrayList<>();

    private ArrayList<String> results_list = new ArrayList<>();

    public Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rotation_viewer);

        Button btnChoose = findViewById(R.id.btnChoose);

        //EXTERNAL READ
        File[] directories = new File(Environment.getExternalStorageDirectory().getPath() + "/Android/data/ib.scoliowarner/files/RESULTSROTATION").listFiles();

        if (directories.length == 0) {
            btnChoose.setEnabled(false);
        } else {


            for (int i = 0; i < directories.length; i++) {
                boolean match = false;

                names_list.add(directories[i].getName());

                try (FileInputStream is = new FileInputStream(directories[i])) {
                    byte[] bytes = new byte[2048];
                    is.read(bytes);
                    String note = new String(bytes);
                    results_list.add(note);
                    btnChoose.setEnabled(true);
                } catch (IOException e) {
                    Log.e("Error", e.toString());
                }

            }


            spinner = (Spinner) findViewById(R.id.spinChoose);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, names_list);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
        }

    }


    public void onChooseClick(View view) {
        String chosen = spinner.getSelectedItem().toString();

        String data = "";
        File[] directories = new File(Environment.getExternalStorageDirectory().getPath() + "/Android/data/ib.scoliowarner/files/RESULTSROTATION").listFiles();
        for (int i = 0; i < directories.length; i++) {


            String noteName = directories[i].getName();

            if (noteName.equals(chosen)) {

                try (FileInputStream is = new FileInputStream(Environment.getExternalStorageDirectory().getPath() + "/Android/data/ib.scoliowarner/files/RESULTSROTATION/" + noteName)) {
                    byte[] bytes = new byte[2048];
                    is.read(bytes);
                    String note = new String(bytes);
                    if (data.equals("")) data = note;
                    else
                        data = data + "\n" + note;

                } catch (IOException e) {
                    Log.e("Error", e.toString());
                }
            }
        }

        TextView scrollView = findViewById(R.id.scroll);
        scrollView.setText(data);
    }

    public void onCompareClick(View view) {
        TextView scrollView = findViewById(R.id.scroll);

        String[] lines = scrollView.getText().toString().split("\n");

        for (int i=1;i<=(lines.length-2);i++){
            String[] res = lines[i].split("\\|");
            int az =Integer.parseInt(res[1]);
            int ro =Integer.parseInt(res[2]);
            int pi =Integer.parseInt(res[3]);
            RotationMain.Constant.results1.add(new int[]{az, ro, pi});
        }

        Intent intent;
        intent = new Intent(this, RotationMeasure.class);
        intent.putExtra("isSecond", true);
        startActivity(intent);
    }
}