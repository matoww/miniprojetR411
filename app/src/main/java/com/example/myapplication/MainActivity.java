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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
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

import android.Manifest;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {
    private final String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private String[] data;
    RecyclerViewAdapter adapter;

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
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_activity, menu);
        return true;
    }

    public void setMenuSupprimer(){
        RecyclerView recyclerView=findViewById(R.id.recyclerView);
        for (int i=0;i<recyclerView.getChildCount();i++){
            RecyclerView.ViewHolder viewHolder=recyclerView.getChildViewHolder(recyclerView.getChildAt(i));
            View view=viewHolder.itemView;
            View textView=view.findViewById(R.id.textViewItem);
        }
    }

    public void menucreer(MenuItem menuItem){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.popup_create_layout);
        dialog.show();
    }

    public void Creer (View view) throws IOException {
        View popupView =LayoutInflater.from(this).inflate(R.layout.popup_create_layout,null);
        EditText textInputEditText=popupView.findViewById(R.id.titreDocInput);
        File file =new File(this.getFilesDir().getAbsolutePath()+"/note/"+textInputEditText.getText().toString()+".txt");
        if(file.createNewFile()){
            System.out.println("ça marche ");
        }else{System.out.println("marche pas");}
        Intent intent=new Intent(this,EditNote.class);
        intent.putExtra("note",new Note(file));
        startActivity(intent);
    }
    @SuppressLint("NotifyDataSetChanged")
    public void supprimerNote(ArrayList<Note> notes){
        for (Note note:notes ){
            File file=new File(this.getFilesDir().getAbsolutePath()+"/note/"+note.getTitre()+"txt");
            if(file.delete()){
            }else{
                Toast.makeText(this,"il y a eu une erreur lors de la suppression des fichiers",Toast.LENGTH_LONG).show();
                break;
            }
        }
        afficherLstNotes(new File(this.getFilesDir().getAbsolutePath() + "/note"));
        adapter.notifyDataSetChanged();
    }


}