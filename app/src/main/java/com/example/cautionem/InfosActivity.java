package com.example.cautionem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class InfosActivity extends AppCompatActivity {

    private EditText nAEditText, emailEditText, ribEditText;
    private Button savebtn;
    private String nomAsso,assoId;
    private boolean nABool, emailBool,ribBool;


    FirebaseFirestore db;
    FirebaseAuth mAuth;

    Bundle bundle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infos);

        // Text view number 1 to add hyperlink
        TextView linkTextView = findViewById(R.id.textView6);

        // method to redirect to provided link
        linkTextView.setMovementMethod(LinkMovementMethod.getInstance());

        // method to change color of link
        linkTextView.setLinkTextColor(Color.BLUE);

        this.nAEditText = findViewById(R.id.nomAssoPlace);
        this.emailEditText = findViewById(R.id.email_utilisateur);
        this.ribEditText = findViewById(R.id.ribPlace);
        this.savebtn = (Button) findViewById(R.id.savebtn);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        bundle = getIntent().getExtras();
        nomAsso = bundle.getString("key1");
        assoId = bundle.getString("key2");

        savebtn.setEnabled(false);
        nABool= false;
        emailBool = false;
        ribBool = false;
        setUpInfo();


        nAEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(nABool) {
                    savebtn.setEnabled(true);
                    Log.d("EditText InfosActivity", "It changed");
                }
                else{
                    nABool = true;
                }
            }
        });

        emailEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(emailBool) {
                    savebtn.setEnabled(true);
                    Log.d("EditText InfosActivity", "It changed");
                }
                else{
                    emailBool = true;
                }
            }
        });

        ribEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(ribBool) {
                    savebtn.setEnabled(true);
                    Log.d("EditText InfosActivity", "It changed");
                }
                else{
                    ribBool = true;
                }
            }
        });

        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nom = nAEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String rib = ribEditText.getText().toString();

                if(TextUtils.isEmpty(nom)){
                    nAEditText.setError("Le champ 'Prénom' ne peut pas être vide");
                    nAEditText.requestFocus();
                }
                else if(TextUtils.isEmpty(email)){
                    emailEditText.setError("Le champ 'Nom' ne peut pas être vide");
                    emailEditText.requestFocus();
                }
                else if(TextUtils.isEmpty(rib))
                {
                    ribEditText.setError("Le champ 'Nom' ne peut pas être vide");
                    ribEditText.requestFocus();
                }
                else {
                    FirebaseUser user = mAuth.getCurrentUser();
                    String uid = user.getUid();
                    DocumentReference assoDocRef = db.collection("Assos").document(assoId);
                    assoDocRef
                            .update("nom", nom, "email", email,"rib",rib)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("updateAssoDoc Success", "DocumentSnapshot successfully updated!");
                                    nomAsso = nom;
                                    db
                                            .collection("Users")
                                            .document(uid)
                                            .collection("Assos")
                                            .get()
                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                    if (task.isSuccessful()) {
                                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                                            User_Asso userAsso = document.toObject(User_Asso.class);
                                                            if(userAsso.getId() == assoId){
                                                                document.getReference().delete();
                                                                db
                                                                        .collection("Users")
                                                                        .document(uid)
                                                                        .collection("Assos")
                                                                        .document(nomAsso)
                                                                        .set(userAsso)
                                                                        .addOnSuccessListener(new OnSuccessListener<Void>(){
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
                                                            }
                                                            Log.d("getMembre Success", document.getId() + " => " + document.getData());
                                                        }
                                                    } else {
                                                        Log.d("get Fail", "Error getting documents: ", task.getException());
                                                    }
                                                }
                                            });
                                    //Changer l'id du doc dans Users/userId/Assos
                                    Toast.makeText(InfosActivity.this, "Les modifications sont enregistrées", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(), SuiviActivity.class);
        bundle = new Bundle();
        bundle.putString("key1",nomAsso);
        bundle.putString("key2",assoId);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.compte :
                //Go to profil Activity
                startActivity(new Intent(getApplicationContext(), Modif_Compte_Activity.class));
                finish();
                break;
            case R.id.déconnexion:
                mAuth.signOut();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setUpInfo() {
        //Accès au document détenant les information de l'asso
        FirebaseUser user = mAuth.getCurrentUser();
        String uid = user.getUid();
        DocumentReference docRef = db.collection("Assos").document(assoId);

        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Log.d("getAssoDoc Success", "Récupération des informations de l'asso "+nomAsso);
                Asso asso = documentSnapshot.toObject(Asso.class);
                nAEditText.setText(asso.getNom());
                emailEditText.setText(asso.getEmail());
                ribEditText.setText(asso.getRIB());
            }
        });
    }
}