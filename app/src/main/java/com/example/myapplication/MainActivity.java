package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.NotDirectoryException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;

import android.Manifest;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {
    private final String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private String[] data;
    RecyclerViewAdapter adapter;
    PopupWindow popupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        creerDossier();
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerViewAdapter(data);
        recyclerView.setAdapter(adapter);
    }

    public void creerDossier(){
        File file=new File(this.getFilesDir().getAbsolutePath()+"/note");
        checkStoragePermission();
        if (!file.exists()){
            if (file.mkdir()){
                Toast.makeText(this,"ça marche",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this,"ça marche pas",Toast.LENGTH_SHORT).show();
            }
        }
        else{
            afficherLstNotes(file);
        }
    }
    public boolean isExternalStorageWritable() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            return true;
        }
        return false;
    }
    public boolean isExternalStorageReadable() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) ||

                Environment.MEDIA_MOUNTED_READ_ONLY.equals(Environment.getExternalStorageState())) {
            return true;
        }
        return false;
    }
    public void checkStoragePermission() {
        int permission = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE,101);
        }
    }
    public void afficherLstNotes(File file){
        File[] files=file.listFiles();
        ArrayList<String> strings=new ArrayList<>();
        for (File tittle:
             files) {
            strings.add(tittle.getName());
        }
        data=strings.toArray(new String[0]);
        System.out.println(Arrays.toString(data));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_activity, menu);
        return true;
    }

    public void setMenuSupprimer(){

    }

    public void menucreer(MenuItem menuItem){
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_create_layout, null);
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true;
        popupWindow = new PopupWindow(popupView, width, height, focusable);
        View rootView = getWindow().getDecorView().getRootView();
        popupWindow.showAtLocation(rootView, Gravity.CENTER, 0, 0);

    }

    public void Creer(View view) throws IOException {
        EditText textInputEditText = popupWindow.getContentView().findViewById(R.id.titreDocInput);
        textInputEditText.requestFocus();
        File file = new File(this.getFilesDir().getAbsolutePath()+"/note/"+textInputEditText.getText().toString());
        if(file.createNewFile()){
            System.out.println("ça marche ");
        } else {
            System.out.println("marche pas");
        }
        Intent intent = new Intent(this, EditNote.class);
        intent.putExtra("note", new Note(file));
        startActivity(intent);
    }
    public void lancer(View view) throws IOException {
        Button button=(Button) view;
        Note note=new Note(new File(this.getFilesDir().getAbsolutePath()+"/note/"+button.getText()));
        Intent intent = new Intent(this, EditNote.class);
        intent.putExtra("note", note);
        startActivity(intent);
    }


    public void supprimerToutesLesNotes(MenuItem item) {
        File notesDirectory = new File(this.getFilesDir().getAbsolutePath() + "/note/");
        if (notesDirectory.exists() && notesDirectory.isDirectory()) {
            File[] files = notesDirectory.listFiles();

            if (files != null) {
                for (File file : files) {
                    if (file.delete()) {
                        System.out.println("Note supprimée avec succès: " + file.getName());
                    } else {
                        System.out.println("Erreur lors de la suppression de la note: " + file.getName());
                    }
                }
            }
        }
    }
}