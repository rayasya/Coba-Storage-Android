package com.example.latihanstorage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //inisialisasi awal
        String fileName = "Example.txt";
        String data = "Hello World";

        //inisialisasi button
        Button btn_buat = findViewById(R.id.btn_create);
        Button btn_baca = findViewById(R.id.btn_read);
        Button btn_edit = findViewById(R.id.btn_update);
        Button btn_hapus = findViewById(R.id.btn_delete);
        TextView preview = findViewById(R.id.preview);

        //function button
        btn_buat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean fileExists = isFileExists(fileName);
                if (fileExists){
                    showToast("File sudah ada");
                }else{
                    createFile(fileName, data);
                    showToast("Berhasil menambahkan file, silahkan baca file");
                }
                
            }
        });

        btn_baca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean fileExists = isFileExists(fileName);
                if (fileExists){
                    String readData = readFile(fileName);
                    preview.setText(readData);
                }else{
                    showToast("File tidak ditemukan");
                }
            }
        });

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean fileExists = isFileExists(fileName);
                if (fileExists){
                    String readData = readFile(fileName);
                    String newData = readData+"\nIni text yang baru ditambahkan";
                    updateData(fileName, newData);
                    preview.setText(null);
                    showToast("File berhasil diubah, silahkan baca file");
                }else{
                    showToast("File tidak ditemukan, gagal update");
                }
            }
        });

        btn_hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean fileExists = isFileExists(fileName);
                if (fileExists){
                    deleteData(fileName);
                    showToast("File berhasil dihapus");
                    preview.setText(null);
                }else{
                    showToast("File tidak ditemukan");
                }
            }
        });
    }

    //ubah posisi toast
    void showToast(String message){
        toast = Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 410);
        toast.show();
    }

    //cek file
    boolean isFileExists(String filename){
        File file = new File(getFilesDir(), filename);
        return  file.exists();
    }

    //create file
    void createFile(String fileName, String data){
        try {
            FileOutputStream fos = openFileOutput(fileName, Context.MODE_PRIVATE);
            fos.write(data.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //read file
    String readFile(String fileName){
        StringBuilder data = new StringBuilder();
        try {
            String line;
            FileInputStream fis = openFileInput(fileName);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            while ((line = br.readLine()) != null){
                data.append(line).append("\n");
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return data.toString();
    }

    //update data
    void updateData(String fileName, String newData){
        try {
            FileOutputStream fos = openFileOutput(fileName, Context.MODE_PRIVATE);
            fos.write(newData.getBytes());
            fos.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    //delete file
    void deleteData(String fileName){
        File file = new File(getFilesDir(), fileName);
        if (file.exists()){
            file.delete();
        }else{
            Toast.makeText(this, "File tidak ditemukan", Toast.LENGTH_SHORT).show();
        }
    }
}