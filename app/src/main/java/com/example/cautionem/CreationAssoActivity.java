package com.example.cautionem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import com.google.firebase.firestore.DocumentSnapshot;
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
            DocumentReference userDocRef = db.collection("Users").document(uid);
            String idDoc = addedDoc.getId();

            //Création des objets pour la structuration des documents
            //Objet pour Users/"UserId"/Assos
            User_Asso userAsso = new User_Asso(idDoc);
            //Objet pour Assos
            Asso asso = new Asso(nomAsso,email,rib,idDoc);
            //Objet pour Assos/"AssoId"/Membres
            final Membre[] membre = new Membre[1];




            //Ajout du document dans la collection : Users/"UserId"/Assos
            dbUserAsso.document(nomAsso).set(userAsso).addOnSuccessListener(new OnSuccessListener<Void>(){
                @Override
                public void onSuccess(Void aVoid) {
                    Log.d("addUserAssoDoc Success", "Document "+nomAsso+" ajouté");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("addUserAssoDoc Fail", "Echec : Ajout du Document "+nomAsso);
                }
            });

            //Ajout du document dans la collection : Assos
            addedDoc.set(asso).addOnSuccessListener(new OnSuccessListener<Void>(){
                @Override
                public void onSuccess(Void aVoid) {
                    Log.d("addAssoDoc Success", "Document "+nomAsso+" ajouté");
                    userDocRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            Log.d("getUser Success", "Les informations du nouveau membre ont été récupérés");
                            User user = documentSnapshot.toObject(User.class);
                            membre[0] = new Membre(user.getPrénom(),user.getNom(),Membre.R1,user.getNumPicture());

                            addedDoc
                                    .collection("Membres")
                                    .document(uid)
                                    .set(membre[0]).addOnSuccessListener(new OnSuccessListener<Void>(){
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(CreationAssoActivity.this,"Le nouveau membre  a bien été enregistré",Toast.LENGTH_SHORT).show();
                                    Log.d("addMembre Success", "Le nouveau membre "+user.getPrénom()+" "+user.getNom()+"  a bien été enregistré");
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("addMembre Fail", "Echec : Ajout du nouveau membre"+user.getPrénom()+" "+user.getNom());
                                }
                            });
                        }
                    });

                    Intent intent = new Intent(getApplicationContext(), SuiviActivity.class);
                    bundle = new Bundle();
                    bundle.putString("key1",nomAsso);
                    bundle.putString("key2",idDoc);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("addAssoDoc Fail", "Echec : Ajout du Document "+nomAsso);
                }
            });
        }
    }
}