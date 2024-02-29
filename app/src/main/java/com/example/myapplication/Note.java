package com.example.myapplication;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Instant;
import java.util.Date;

public class Note implements Parcelable {
    private String titre;
    private String texte;
    private Date dateDerniereModif;
    private  Date dateDeCreation;
    public Note(){
        this.dateDeCreation=Date.from(Instant.now());
    }
    public Note(String titre,String texte,Date dateDerniereModif,Date dateDeCreation){
        this.titre=titre;
        this.texte=texte;
        this.dateDerniereModif=dateDerniereModif;
        this.dateDeCreation=dateDeCreation;
    }
    public Note(File file) throws IOException {
        this.dateDerniereModif=new Date(file.lastModified());

        Path path=file.toPath();
        BasicFileAttributes basicFileAttributes= Files.readAttributes(path,BasicFileAttributes.class);
        this.dateDeCreation=new Date(basicFileAttributes.creationTime().toMillis());

        this.titre= file.getName();

        this.texte= Files.readAllLines(path).toString();
    }

    protected Note(Parcel in) {
        titre = in.readString();
        texte = in.readString();
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    public Date getDateDeCreation() {
        return dateDeCreation;
    }

    public Date getDateDerniereModif() {
        return dateDerniereModif;
    }

    public String getTexte() {
        return texte;
    }

    public String getTitre() {
        return titre;
    }

    public void setDateDeCreation(Date dateDeCreation) {
        this.dateDeCreation = dateDeCreation;
    }

    public void setDateDerniereModif(Date dateDerniereModif) {
        this.dateDerniereModif = dateDerniereModif;
    }

    public void setTexte(String texte) {
        this.texte = texte;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(titre);
        dest.writeString(texte);
        dest.writeString(dateDeCreation.toString());
        dest.writeString(dateDerniereModif.toString());
    }
}
