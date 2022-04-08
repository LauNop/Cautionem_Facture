package com.example.cautionem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.firestore.FirebaseFirestore;

public class Inscription2Activity extends AppCompatActivity {

    private EditText pseudoEditText;
    private Button next;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription2);

        this.next = (Button) findViewById(R.id.nextbut);
        this.pseudoEditText = findViewById(R.id.pseudo1);

        db = FirebaseFirestore.getInstance();


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PseudoUser();
            }
        });
    }

    private void PseudoUser(){
        String pseudo = pseudoEditText.getText().toString();

        if(TextUtils.isEmpty(pseudo)){
            pseudoEditText.setError("Le champ 'pseudo' ne peut pas être vide");
            pseudoEditText.requestFocus();
        }
        else{
            //Enregistré la data
            startActivity(new Intent(getApplicationContext(),AssoActivity.class));
            finish();
        }
    }
}