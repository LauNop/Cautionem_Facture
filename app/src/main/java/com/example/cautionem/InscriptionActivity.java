package com.example.cautionem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


public class InscriptionActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button registerButton;

    FirebaseFirestore db;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);

        usernameEditText = findViewById(R.id.username3);
        passwordEditText = findViewById(R.id.password2);
        registerButton = (Button) findViewById(R.id.nextbut2);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        getDynamicLinkFromFirebase();

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createUser();
            }
        });
    }

    private void getDynamicLinkFromFirebase() {
        FirebaseDynamicLinks.getInstance()
                .getDynamicLink(getIntent())
                .addOnSuccessListener(new OnSuccessListener<PendingDynamicLinkData>() {
                    @Override
                    public void onSuccess(PendingDynamicLinkData pendingDynamicLinkData) {
                        Log.i("InscriptionActivity","Nous avons un lien dynamique");
                        Uri deeplink = null;

                        if(pendingDynamicLinkData!=null){
                            deeplink = pendingDynamicLinkData.getLink();
                        }

                        if(deeplink!=null){
                            Log.i("InscriptionActivity","Voici le lien dynamique \n" + deeplink.toString());
                        }

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(InscriptionActivity.this, e.toString(), Toast.LENGTH_SHORT).show();

                    }
                });
    }

    private void createUser(){
        String email = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if(TextUtils.isEmpty(email)){
            usernameEditText.setError("Le champ 'Email' ne peut aps être vide");
            usernameEditText.requestFocus();
        }
        else if(TextUtils.isEmpty(password)){
            passwordEditText.setError("Le champ 'Mot de passe ne peut pas être vide");
            passwordEditText.requestFocus();
        }
        else{
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        addDataToFirestore(email);

                        startActivity(new Intent(getApplicationContext(), Inscription2Activity.class));
                        finish();
                    }
                    else{
                        Toast.makeText(InscriptionActivity.this,"Erreur de l'enregistrement utilisateur : "+ task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

    @Override
    public void onBackPressed(){
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }

    public void addDataToFirestore(String email){
        CollectionReference dbUser = db.collection("Users");

        FirebaseUser user = mAuth.getCurrentUser();
        String uid = user.getUid();
        // adding our data to our users object class.
        User userModel = new User(uid,email);

        // below method is use to add data to Firebase Firestore.
        dbUser.document(uid).set(userModel).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                // after the data addition is successful
                // we are displaying a success toast message.
                Log.d("createUser Success", "L'utilisateur "+uid+" a bien été enregistré");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w("createUser Fail", "Erreur dans l'ajout de l'utilisateur", e);
            }
        });
    }

}