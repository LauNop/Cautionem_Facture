package com.example.cautionem;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public final class UserRepository {

    private static final String COLLECTION_NAME = "users";
    private static final String USERNAME_FIELD = "username";
    private static final String EMAIL_FIELD = "email";
    private static final String IS_MENTOR_FIELD = "isMentor";

    private FirebaseAuth mAuth;
    // Get the Collection Reference
    private CollectionReference getUsersCollection(){
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }

    // Create User in Firestore
    public void createUser(String email) {
        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null){
            String urlPicture = (user.getPhotoUrl() != null) ? user.getPhotoUrl().toString() : null;
            String username = user.getDisplayName();
            String uid = user.getUid();

            User userToCreate = new User(uid, username, urlPicture,email);

            Task<DocumentSnapshot> userData = getUserData();
            // If the user already exist in Firestore, we get his data (isMentor)
            userData.addOnSuccessListener(documentSnapshot -> {
                if (documentSnapshot.contains(IS_MENTOR_FIELD)){
                    userToCreate.setIsMentor((Boolean) documentSnapshot.get(IS_MENTOR_FIELD));
                }
                this.getUsersCollection().document(uid).set(userToCreate);
            });
        }
    }

    // Get User Data from Firestore
    public Task<DocumentSnapshot> getUserData(){
        String uid = mAuth.getUid();
        if(uid != null){
            return this.getUsersCollection().document(uid).get();
        }else{
            return null;
        }
    }

    // Update User Username
    public Task<Void> updateUsername(String username) {
        String uid = mAuth.getUid();
        if(uid != null){
            return this.getUsersCollection().document(uid).update(USERNAME_FIELD, username);
        }else{
            return null;
        }
    }

    public Task<Void> updateEmail(String email) {
        String uid = mAuth.getUid();
        if(uid != null){
            return this.getUsersCollection().document(uid).update(EMAIL_FIELD, email);
        }else{
            return null;
        }
    }

    // Update User isMentor
    public void updateIsMentor(Boolean isMentor) {
        String uid = mAuth.getUid();
        if(uid != null){
            this.getUsersCollection().document(uid).update(IS_MENTOR_FIELD, isMentor);
        }
    }

    // Delete the User from Firestore
    public void deleteUserFromFirestore() {
        String uid = mAuth.getUid();
        if(uid != null){
            this.getUsersCollection().document(uid).delete();
        }
    }

}