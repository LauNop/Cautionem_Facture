package com.example.cautionem;

public class Asso {

    //  Info
    private String nom;
    private String email;
    private String RIB;

    //  Classe
    public Asso(){

    }
    public Asso(String nom,String email,String RIB){
        this.nom = nom;
        this.email = email;
        this.RIB = RIB;
    }

    // --- GETTERS ---
    public String getNom(){return nom;}
    public String getEmail(){return email;}
    public String getRIB(){return RIB;}

    // --- SETTERS ---
    public void setNom(String nom){this.nom=nom;}
    public void setEmail(String email){this.email=email;}
    public void setRIB(String RIB){this.RIB=RIB;}
}
