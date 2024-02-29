package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.util.Date;

public class EditNote extends AppCompatActivity {
    private Note myNote;

    private EditText editTexete;

    private EditText editTitle;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        myNote = getIntent().getParcelableExtra("note");

        editTitle = findViewById(R.id.editTitle);
        editTexete = findViewById(R.id.editTexete);

        editTitle.setText(myNote.getTitre());
        editTexete.setText(myNote.getTexte());


    }

    public void onUpdateButtonClick(View view) {
        String newTitle = editTexete.getText().toString();
        String newText = editTitle.getText().toString();

        myNote.setTitre(newTitle);
        myNote.setTexte(newText);
        myNote.setDateDerniereModif(new Date());

        finish();
    }
    public void supprimerNote(File file, String nomNoteASupprimer) {
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
        }
    }


}
