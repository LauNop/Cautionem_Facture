package com.example.cautionem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Modif_Compte_Activity extends AppCompatActivity {

    private EditText nomEditeText, prenomEditText, mailEditText;
    private Button ENbtn;
    private String nomAsso,assoId;
    private boolean nomBool, prenomBool, emailBoot;


    FirebaseFirestore db;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modif_compte2);


        this.nomEditeText = findViewById(R.id.nom_utilisateur);
        this.prenomEditText = findViewById(R.id.prenom_utilisateur);
        this.mailEditText = findViewById(R.id.email_utilisateur);
        this.ENbtn = (Button) findViewById(R.id.ENbtn);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        ENbtn.setEnabled(false);
        nomBool = false;
        prenomBool = false;
        emailBoot = false;
        setUpInfo();


        nomEditeText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(nomBool) {
                    ENbtn.setEnabled(true);
                    Log.d("EditText InfosActivity", "It changed");
                }
                else{
                    nomBool = true;
                }
            }
        });

        prenomEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(prenomBool) {
                    ENbtn.setEnabled(true);
                    Log.d("EditText InfosActivity", "It changed");
                }
                else{
                    prenomBool = true;
                }
            }
        });

        mailEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(emailBoot) {
                    ENbtn.setEnabled(true);
                    Log.d("EditText InfosActivity", "It changed");
                }
                else{
                    emailBoot = true;
                }
            }
        });

        ENbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nom = nomEditeText.getText().toString();
                String prenom = prenomEditText.getText().toString();
                String email = mailEditText.getText().toString();

                if(TextUtils.isEmpty(nom)){
                    nomEditeText.setError("Le champ 'Prénom' ne peut pas être vide");
                    nomEditeText.requestFocus();
                }
                else if(TextUtils.isEmpty(prenom)){
                    prenomEditText.setError("Le champ 'Nom' ne peut pas être vide");
                    prenomEditText.requestFocus();
                }
                else if(TextUtils.isEmpty(email))
                {
                    mailEditText.setError("Le champ 'Nom' ne peut pas être vide");
                    mailEditText.requestFocus();
                }
                else {
                    FirebaseUser user = mAuth.getCurrentUser();
                    String uid = user.getUid();
                    DocumentReference userDocRef = db.collection("Users").document(uid);
                    userDocRef
                            .update("nom", nom, "prenom", prenom,"email",email)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("updateUserDoc Success", "DocumentSnapshot successfully updated!");
                                    //Changer l'id du doc dans Users/userId/Assos
                                    Toast.makeText(Modif_Compte_Activity.this, "Les modifications sont enregistrées", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("updateAssoDoc Fail", "Error updating document");
                                }
                            });
                }
            }
        });
    }


    private void setUpInfo() {
        //Accès au document détenant les information de l'asso
        FirebaseUser user = mAuth.getCurrentUser();
        String uid = user.getUid();
        DocumentReference docRef = db.collection("Users").document(uid);

        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Log.d("getAssoDoc Success", "Récupération des informations de l'asso "+nomAsso);
                User user = documentSnapshot.toObject(User.class);
                nomEditeText.setText(user.getNom());
                prenomEditText.setText(user.getPrénom());
                mailEditText.setText(user.getEmail());
            }
        });
    }
}