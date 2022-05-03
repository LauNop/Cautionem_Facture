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
    private ImageView thepen;
    private int idNumPhoto;

    FirebaseFirestore db;
    FirebaseAuth mAuth;
    FirebaseStorage storage;

    Bundle bundle;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription2);

        this.next = (Button) findViewById(R.id.nextbut);
        this.prénomEditText = findViewById(R.id.prénomInput);
        this.nomEditText = findViewById(R.id.nomInput);
        this.thepen = (ImageView) findViewById(R.id.imagePen);
        this.profil = (ImageView) findViewById(R.id.profil_img);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();

        Intent intent = getIntent();

        if(intent !=null)
        {
            if(intent.hasExtra("key1"))
            {
                bundle = getIntent().getExtras();
                idNumPhoto = bundle.getInt("key1", 0);
            }
            else{
                    idNumPhoto = 0;
                }
        }

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

        thepen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Photo_Activity.class);
                bundle = new Bundle();
                bundle.putInt("key1",1);
                intent.putExtras(bundle);
                startActivity(intent);
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
                    .update("prénom", prénom,"nom",nom,"numPicture",idNumPhoto)
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