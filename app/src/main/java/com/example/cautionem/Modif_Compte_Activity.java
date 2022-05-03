package com.example.cautionem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Collection;

public class Modif_Compte_Activity extends AppCompatActivity {

    private EditText nomEditeText, prenomEditText, mailEditText;
    private Button ENbtn;
    private ImageView profil;
    private int idNumPhoto;
    private boolean nomBool, prenomBool, emailBoot;


    FirebaseFirestore db;
    FirebaseAuth mAuth;

    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modif_compte2);


        this.nomEditeText = findViewById(R.id.nom_utilisateur);
        this.prenomEditText = findViewById(R.id.prenom_utilisateur);
        this.mailEditText = findViewById(R.id.email_utilisateur);
        this.ENbtn = (Button) findViewById(R.id.ENbtn);
        this.profil =(ImageView) findViewById(R.id.profil_img);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        ENbtn.setEnabled(false);
        nomBool = false;
        prenomBool = false;
        emailBoot = false;

        Intent intent = getIntent();

        if(intent !=null)
        {
            if(intent.hasExtra("key1"))
            {
                bundle = getIntent().getExtras();
                idNumPhoto = bundle.getInt("key1");
                switch(idNumPhoto){
                    case 0:
                        profil.setImageResource(R.drawable.ic_lambda_profile);
                        break;
                    case 1:
                        profil.setImageResource(R.drawable.profil1);
                        break;
                    case 2:
                        profil.setImageResource(R.drawable.profil2);
                        break;
                    case 3:
                        profil.setImageResource(R.drawable.profil3);
                        break;
                    case 4:
                        profil.setImageResource(R.drawable.profil4);
                        break;
                    case 5:
                        profil.setImageResource(R.drawable.profil5);
                        break;
                    case 6:
                        profil.setImageResource(R.drawable.profil6);
                        break;
                    case 7:
                        profil.setImageResource(R.drawable.profil7);
                        break;
                    case 8:
                        profil.setImageResource(R.drawable.profil8);
                        break;
                }
                setUpInfo2();
                ENbtn.setEnabled(true);
            }
            else{
                setUpInfo();
            }
        }

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
                            .update("nom", nom, "prenom", prenom,"email",email,"numPicture",idNumPhoto)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("updateUserDoc Success", "DocumentSnapshot successfully updated!");
                                    //Changer l'id du doc dans Users/userId/Assos
                                    CollectionReference dbUserAsso = db.collection("Users").document(uid).collection("Assos");
                                    CollectionReference dbAsso = db.collection("Assos");
                                    dbUserAsso
                                            .get()
                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                    if (task.isSuccessful()) {
                                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                                            User_Asso userAsso = document.toObject(User_Asso.class);
                                                            dbAsso
                                                                    .document(userAsso.getId())
                                                                    .collection("Membres")
                                                                    .document(uid)
                                                                    .update("nom", nom, "prenom", prenom,"numPhoto",idNumPhoto)
                                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                        @Override
                                                                        public void onSuccess(Void unused) {

                                                                        }
                                                                    });
                                                            Log.d("getAssoId Success", document.getId() + " => " + document.getData());
                                                        }
                                                    }
                                                }
                                            });
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

        profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Photo_Activity.class);
                bundle = new Bundle();
                bundle.putInt("key1",2);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed(){
        startActivity(new Intent(getApplicationContext(), AssoActivity.class));
        finish();
    }
    private void setUpInfo() {
        //Accès au document détenant les information de l'asso
        FirebaseUser user = mAuth.getCurrentUser();
        String uid = user.getUid();
        DocumentReference docRef = db.collection("Users").document(uid);

        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                User user = documentSnapshot.toObject(User.class);
                nomEditeText.setText(user.getNom());
                prenomEditText.setText(user.getPrénom());
                mailEditText.setText(user.getEmail());
                idNumPhoto = user.getNumPicture();
                switch(idNumPhoto){
                    case 0:
                        profil.setImageResource(R.drawable.ic_lambda_profile);
                        break;
                    case 1:
                        profil.setImageResource(R.drawable.profil1);
                        break;
                    case 2:
                        profil.setImageResource(R.drawable.profil2);
                        break;
                    case 3:
                        profil.setImageResource(R.drawable.profil3);
                        break;
                    case 4:
                        profil.setImageResource(R.drawable.profil4);
                        break;
                    case 5:
                        profil.setImageResource(R.drawable.profil5);
                        break;
                    case 6:
                        profil.setImageResource(R.drawable.profil6);
                        break;
                    case 7:
                        profil.setImageResource(R.drawable.profil7);
                        break;
                    case 8:
                        profil.setImageResource(R.drawable.profil8);
                        break;
                }
            }
        });
    }

    private void setUpInfo2() {
        //Accès au document détenant les information de l'asso
        FirebaseUser user = mAuth.getCurrentUser();
        String uid = user.getUid();
        DocumentReference docRef = db.collection("Users").document(uid);

        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                User user = documentSnapshot.toObject(User.class);
                nomEditeText.setText(user.getNom());
                prenomEditText.setText(user.getPrénom());
                mailEditText.setText(user.getEmail());
            }
        });
    }
}