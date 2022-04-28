package com.example.cautionem;

import androidx.annotation.Nullable;

public class Membre {
    final static String R1 = "Président";
    final static String R2 = "Vice-président";
    final static String R3 = "Trésorier";
    final static String R4 = "Secrétaire";
    private String prénom;
    private String nom;
    private String role;
    private int numPhoto;

    public Membre(){

    }

    public Membre(String prénom,String nom,String role,int numPhoto) {
        this.prénom = prénom;
        this.nom = nom;
        this.role = role;
        this.numPhoto = numPhoto;
    }

    // --- GETTERS ---
    public String getPrénom() { return prénom; }
    public String getNom() { return nom; }
    public String getRole() { return role; }
    public int getNumPhoto() { return numPhoto;}

    // --- SETTERS ---

    public void setUsername(String prénom) { this.prénom = prénom; }
    public void setNom(String nom) { this.nom = nom; }
    public void setRole(String role) {this.role = role;}
    public void setNumPhoto(int numPhoto) {this.numPhoto = numPhoto;}
}
