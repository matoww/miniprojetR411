package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class EditNote extends AppCompatActivity {
    private Note myNote;

    private EditText editTexete;

    private EditText editTitle;

    private String oldTitle ; ;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        myNote = getIntent().getParcelableExtra("note");
        oldTitle = myNote.getTitre();

        if (myNote != null) {
            System.out.println(myNote.getTitre());

            editTitle = findViewById(R.id.editTitle);
            editTexete = findViewById(R.id.editTexete);

            editTitle.setText(myNote.getTitre());
            editTexete.setText(myNote.getTexte());
        } else {
            finish();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_note, menu);
        return true;
    }



    public void onUpdateButtonClick(View view) {

        String newTitle = editTitle.getText().toString();
        String newText = editTexete.getText().toString();

        myNote.setTitre(newTitle);
        myNote.setTexte(newText);
        myNote.setDateDerniereModif(new Date());

        System.out.println(myNote.getTitre());
        File oldFile = new File(this.getFilesDir().getAbsolutePath() + "/note/" );
        System.out.println(supprimerNote(oldFile, oldTitle));

        File newFile = new File(this.getFilesDir().getAbsolutePath() + "/note/" + newTitle);
        try {
            if (newFile.createNewFile()) {
                FileWriter writer = new FileWriter(newFile);
                writer.write(newText);
                writer.flush();
                writer.close();

                System.out.println("Note modifiée et enregistrée avec succès: " + newTitle);
            } else {
                System.out.println("Erreur lors de la création du nouveau fichier de note: " + newTitle);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finish();
    }
    public boolean supprimerNote(File file, String nomNoteASupprimer) {
        File[] files = file.listFiles();
        if (files != null) {
            for (File note : files) {
                if (note.getName().equals(nomNoteASupprimer)) {
                    if (note.delete()) {
                        System.out.println("Note supprimée avec succès: " + nomNoteASupprimer);
                    } else {
                        System.out.println("Erreur lors de la suppression de la note: " + nomNoteASupprimer);
                    }
                    break;
                }
            }
            return true;
        }
        return false;
    }

    public void onSupprimerNoteMenuItemClick(MenuItem item) {
        File oldFile = new File(this.getFilesDir().getAbsolutePath() + "/note/" );
        supprimerNote(oldFile, oldTitle);
        finish();
    }


}
