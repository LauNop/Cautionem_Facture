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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Inscription2Activity extends AppCompatActivity {

    private EditText prénomEditText;
    private EditText nomEditText;
    private Button next;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription2);

        this.next = (Button) findViewById(R.id.nextbut);
        this.prénomEditText = findViewById(R.id.prénomInput);
        this.nomEditText = findViewById(R.id.nomInput);

        db = FirebaseFirestore.getInstance();


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PseudoUser();
            }
        });
    }

    private void PseudoUser(){
        String prénom = prénomEditText.getText().toString();
        String nom = nomEditText.getText().toString();

        if(TextUtils.isEmpty(prénom)){
            prénomEditText.setError("Le champ 'Prénom' ne peut pas être vide");
            prénomEditText.requestFocus();
        }
        else if(TextUtils.isEmpty(nom)){
            nomEditText.setError("Le champ 'Nom' ne peut pas être vide");
            nomEditText.requestFocus();
        }
        else{
            startActivity(new Intent(getApplicationContext(),AssoActivity.class));
            finish();
        }
    }
}