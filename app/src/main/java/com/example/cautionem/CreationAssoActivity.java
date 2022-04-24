package com.example.cautionem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class CreationAssoActivity extends AppCompatActivity {

    private EditText nomAssoEditText,emailEditText, ribEditText;
    private Button btn;

    FirebaseFirestore db;
    FirebaseAuth mAuth;

    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creation_asso);

        nomAssoEditText = findViewById(R.id.nomAsso);
        emailEditText = findViewById(R.id.email);
        ribEditText = findViewById(R.id.rib);
        btn = (Button) findViewById(R.id.nextbut3);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateAsso();
            }
        });
    }

    @Override
    public void onBackPressed(){
        startActivity(new Intent(getApplicationContext(), AssoActivity.class));
        finish();
    }

    private void CreateAsso() {
        String email = emailEditText.getText().toString();
        String nomAsso = nomAssoEditText.getText().toString();
        String rib = ribEditText.getText().toString();

        FirebaseUser user = mAuth.getCurrentUser();
        String uid = user.getUid();

        CollectionReference dbUserAsso = db.collection("Users").document(uid).collection("Assos");
        CollectionReference dbAsso = db.collection("Assos");


        if (TextUtils.isEmpty(email)) {
            emailEditText.setError("Le champ 'Email' ne peut aps être vide");
            emailEditText.requestFocus();
        } else if (TextUtils.isEmpty(nomAsso)) {
            nomAssoEditText.setError("Le champ 'Mot de passe ne peut pas être vide");
            nomAssoEditText.requestFocus();
        }
        else if(TextUtils.isEmpty(nomAsso)){
            ribEditText.setError("Le champ 'Mot de passe ne peut pas être vide");
            ribEditText.requestFocus();
        }
        else{
            //Génération d'un document à ID unique pour la collection Assos
            DocumentReference addedDoc = dbAsso.document();
            String idDoc = addedDoc.getId();

            //Création des objets pour la structuration des documents
            //Objet pour Users/"UserId"/Assos
            User_Asso userAsso = new User_Asso(idDoc);
            //Objet pour Assos
            Asso asso = new Asso(nomAsso,email,rib);

            //Ajout du doc dans la collection : Users/"UserId"/Assos
            dbUserAsso.document(nomAsso).set(userAsso).addOnSuccessListener(new OnSuccessListener<Void>(){
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(CreationAssoActivity.this,"étape1 succès",Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(CreationAssoActivity.this, "étape1 échec:\n" + e, Toast.LENGTH_SHORT).show();
                }
            });

            //Ajout du doc dans la collection : Assos
            addedDoc.set(asso).addOnSuccessListener(new OnSuccessListener<Void>(){
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(CreationAssoActivity.this,"L'asso "+nomAsso+" a bien été enregistré",Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getApplicationContext(), SuiviActivity.class);
                    bundle = new Bundle();
                    bundle.putString("key1",nomAsso);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(CreationAssoActivity.this, "Erreur dans l'ajout de l'utilisateur \n" + e, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}