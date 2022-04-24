package com.example.cautionem;

public class User_Asso {
    //  Info
    private String id;


    //  Classe
    public User_Asso(){

    }
    public User_Asso(String id){
        this.id = id;
    }

    // --- GETTERS ---
    public String getId(){return id;}

    // --- SETTERS ---
    public void setId(String id){this.id=id;}

    @Override
    public String toString(){
       return id;
    }
}
