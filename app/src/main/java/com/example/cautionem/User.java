package com.example.cautionem;

import androidx.annotation.Nullable;

public class User {
    private String uid;
    private String prénom;
    private String nom;
    private String email;
    private int numPicture;

    public User(){
        this.numPicture = 0;

    }

    public User(String uid,String email) {
        this.uid = uid;
        this.email = email;
        this.numPicture = 0;
    }

    // --- GETTERS ---
    public String getUid() { return uid; }
    public String getPrénom() { return prénom; }
    public String getNom() { return nom; }
    public String getEmail() { return email; }
    public int getNumPicture() { return numPicture; }

    // --- SETTERS ---
    public void setUid(String uid) { this.uid = uid; }
    public void setUsername(String prénom) { this.prénom = prénom; }
    public void setNom(String nom) { this.nom = nom; }
    public void setEmail(String email) {this.email = email;}
    public void setNumPicture(int numPicture) { this.numPicture = numPicture; }

}
