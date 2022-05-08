package com.example.cautionem;

public class Facture {

    //  Infos
    private String nom;

    //  Classe
    public Facture(){

    }
    public Facture(String nom){
        this.nom=nom;

    }

    // --- GETTERS ---
    public String getNom(){return nom;}

    // --- SETTERS ---
    public void setNom(String nom){this.nom =nom;}

}
