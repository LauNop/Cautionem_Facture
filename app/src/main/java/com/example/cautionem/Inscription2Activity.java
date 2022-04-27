package com.example.cautionem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
    private ImageView profil;

    FirebaseFirestore db;
    FirebaseAuth mAuth;
    FirebaseStorage storage;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription2);

        this.next = (Button) findViewById(R.id.nextbut);
        this.prénomEditText = findViewById(R.id.prénomInput);
        this.nomEditText = findViewById(R.id.nomInput);
        this.profil = (ImageView) findViewById(R.id.imagePen);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();


        profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Photo_Activity.class));
                finish();
            }
        });


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
            DocumentReference docRef = db.collection("Users").document(uid);

            docRef
                    .update("prénom", prénom,"nom",nom)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("updateUserDoc Success", "DocumentSnapshot successfully updated!");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("updateUserDoc Success", "Error updating document");
                        }
                    });
            startActivity(new Intent(getApplicationContext(),AssoActivity.class));
            finish();
        }
    }
}