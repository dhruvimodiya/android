package com.example.pr3_3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    Button btnsave,btnload;
    EditText input;
    TextView tvload;
    String filename="";
    String filepath="";
    String fileContent="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnsave=findViewById(R.id.btnsave);
        btnload=findViewById(R.id.btnload);
        input=findViewById(R.id.input);
        tvload=findViewById(R.id.tvload);
        filename="myFile.txt";
        filepath="MyFileDir";
        if(!isExternalStorageAvailableForRW())
        {
            btnsave.setEnabled(false);
        }
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvload.setText("");
                fileContent = input.getText().toString().trim();
                if(!fileContent.equals("")){
                    File myExternalFile = new File(getExternalFilesDir(filepath),filename);
                    FileOutputStream fos = null;
                    try {
                        fos = new FileOutputStream(myExternalFile);
                        fos.write(fileContent.getBytes());
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    input.setText("");
                    Toast.makeText(MainActivity.this, "Information saved in SD card", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this, "Text  field can not  be empty", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileReader fr = null;
                File myExternalFile = new File(getExternalFilesDir(filepath),filename);
                StringBuilder stringBuilder = new StringBuilder();
                try {
                    fr = new FileReader(myExternalFile);
                    BufferedReader br = new BufferedReader(fr);
                    String line = br.readLine();
                    while (line != null){
                        stringBuilder.append(line).append("\n");
                        line = br.readLine();
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    String fileContents = "File contents\n" + stringBuilder.toString();
                    tvload.setText(fileContents);
                }
            }
        });
    }
    private boolean isExternalStorageAvailableForRW(){
        String extStorageState = Environment.getExternalStorageState();
        {
            if(extStorageState.equals(Environment.MEDIA_MOUNTED)){
                return true;
            }
            return false;
        }
    }
}