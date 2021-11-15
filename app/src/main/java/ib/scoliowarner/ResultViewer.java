package ib.scoliowarner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ResultViewer extends AppCompatActivity {

    private ArrayList<String> names_list = new ArrayList<>();
    ;
    private ArrayList<String> results_list = new ArrayList<>();

    public Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_viewer);

        Button btnChoose = findViewById(R.id.btnChoose);

        //EXTERNAL READ
        File[] directories = new File(Environment.getExternalStorageDirectory().getPath() + "/Android/data/ib.scoliowarner/files/RESULTS").listFiles();
        int length = directories.length;
        if (directories.length == 0)
            btnChoose.setEnabled(false);
        else {


            for (int i = 0; i < directories.length; i++) {
                boolean match = false;
                for (int k = 0; k < names_list.size(); k++) {

                    if (directories[i].getName().split(";")[0].equals(names_list.get(k)))
                        match = true;
                }
                if (!match) names_list.add(directories[i].getName().split(";")[0]);

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
        File[] directories = new File(Environment.getExternalStorageDirectory().getPath() + "/Android/data/ib.scoliowarner/files/RESULTS").listFiles();
        for (int i = 0; i < directories.length; i++) {


            String noteName=directories[i].getName();

            if(noteName.split(";")[0].equals(chosen)){

            try (FileInputStream is = new FileInputStream(Environment.getExternalStorageDirectory().getPath() + "/Android/data/ib.scoliowarner/files/RESULTS/" + noteName)) {
                byte[] bytes = new byte[2048];
                is.read(bytes);
                String note = new String(bytes);
                if(data.equals("")) data=note;
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
}