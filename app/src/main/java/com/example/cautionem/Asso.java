package com.example.cautionem;

public class Asso {

    //  Info
    private String nom;
    private String email;
    private String RIB;
    private String assoId;

    //  Classe
    public Asso(){

    }
    public Asso(String nom,String email,String RIB,String assoId){
        this.nom = nom;
        this.email = email;
        this.RIB = RIB;
        this.assoId = assoId;
    }

    // --- GETTERS ---
    public String getNom(){return nom;}
    public String getEmail(){return email;}
    public String getRIB(){return RIB;}
    public String getAssoId(){return assoId;}

    // --- SETTERS ---
    public void setNom(String nom){this.nom=nom;}
    public void setEmail(String email){this.email=email;}
    public void setRIB(String RIB){this.RIB=RIB;}
    public void setAssoId(String assoId){this.assoId=assoId;}
}
