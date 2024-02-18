package com.example.myapplication;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Instant;
import java.util.Date;

public class Note {
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

}
