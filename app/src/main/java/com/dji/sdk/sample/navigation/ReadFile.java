package com.dji.sdk.sample.navigation;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ReadFile extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

    }
        private String readFile() {
        //change this to file path of your phone.
            File fileEvents = new File(ReadFile.this.getFilesDir() + "/files/mission1");
            StringBuilder text = new StringBuilder();
            try {
                BufferedReader br = new BufferedReader(new FileReader(fileEvents));
                String line;
                while ((line = br.readLine()) != null){
                    text.append(line);
                    text.append('\n');
                }
                br.close();
            } catch (IOException e) {
            }

            String result = text.toString();
            return result;
        }
}

