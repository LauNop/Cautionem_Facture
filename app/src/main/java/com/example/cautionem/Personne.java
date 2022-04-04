package com.example.cautionem;

public class Personne {

    //  Infos
    private String role;
    private String pseudo;

    //  Classe
    public Personne(String role, String pseudo){
        this.role=role;
        this.pseudo=pseudo;
    }

    //  Méthodes
    public String getRole(){return role;}

    public String getPseudo() {return pseudo;}
}
