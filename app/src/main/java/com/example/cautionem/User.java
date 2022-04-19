package com.example.cautionem;

import androidx.annotation.Nullable;

public class User {
    private String uid;
    private String prénom;
    private String nom;
    private String email;
    @Nullable
    private String urlPicture;


    public User(String uid,String email) {
        this.uid = uid;
        this.email = email;
    }

    // --- GETTERS ---
    public String getUid() { return uid; }
    public String getPrénom() { return prénom; }
    public String getNom() { return nom; }
    public String getEmail() { return email; }
    @Nullable
    public String getUrlPicture() { return urlPicture; }

    // --- SETTERS ---
    public void setUid(String uid) { this.uid = uid; }
    public void setUsername(String prénom) { this.prénom = prénom; }
    public void setNom(String nom) { this.nom = nom; }
    public void setEmail(String email) {this.email = email;}
    public void setUrlPicture(@Nullable String urlPicture) { this.urlPicture = urlPicture; }

}
