package com.example.cautionem;

import androidx.annotation.Nullable;

public class User {
    private String uid;
    private String username;
    private Boolean isMentor;
    private String email;
    @Nullable
    private String urlPicture;


    public User(String uid, String username, @Nullable String urlPicture,String email) {
        this.uid = uid;
        this.username = username;
        this.urlPicture = urlPicture;
        this.email = email;
        this.isMentor = false;
    }

    // --- GETTERS ---
    public String getUid() { return uid; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    @Nullable
    public String getUrlPicture() { return urlPicture; }
    public Boolean getIsMentor() { return isMentor; }

    // --- SETTERS ---
    public void setUsername(String username) { this.username = username; }
    public void setUid(String uid) { this.uid = uid; }
    public void setEmail(String email) {this.email = email;}
    public void setUrlPicture(@Nullable String urlPicture) { this.urlPicture = urlPicture; }
    public void setIsMentor(Boolean mentor) { isMentor = mentor; }
}
