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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


public class Inscription2Activity extends AppCompatActivity {

    private EditText prénomEditText;
    private EditText nomEditText;
    private Button next;

    FirebaseFirestore db;
    FirebaseAuth mAuth;
    FirebaseStorage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription2);

        this.next = (Button) findViewById(R.id.nextbut);
        this.prénomEditText = findViewById(R.id.prénomInput);
        this.nomEditText = findViewById(R.id.nomInput);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProfileUser();
            }
        });
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        //Custom with a dialog "Are you sure to exit ?"
    }

    private void ProfileUser(){
        String prénom = prénomEditText.getText().toString();
        String nom = nomEditText.getText().toString();

        FirebaseUser user = mAuth.getCurrentUser();
        String uid = user.getUid();

        // Create a storage reference from our app
        StorageReference storageRef = storage.getReference();

        if(TextUtils.isEmpty(prénom)){
            prénomEditText.setError("Le champ 'Prénom' ne peut pas être vide");
            prénomEditText.requestFocus();
        }
        else if(TextUtils.isEmpty(nom)){
            nomEditText.setError("Le champ 'Nom' ne peut pas être vide");
            nomEditText.requestFocus();
        }
        else{
            DocumentReference washingtonRef = db.collection("Users").document(uid);

// Set the "isCapital" field of the city 'DC'
            washingtonRef
                    .update("prénom", prénom,"nom",nom)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(Inscription2Activity.this,"DocumentSnapshot successfully updated!",Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Inscription2Activity.this,"Error updating document",Toast.LENGTH_SHORT).show();
                        }
                    });
            startActivity(new Intent(getApplicationContext(),AssoActivity.class));
            finish();
        }
    }
}