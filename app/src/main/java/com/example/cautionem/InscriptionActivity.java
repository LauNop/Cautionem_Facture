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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class InscriptionActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button registerButton;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);

        usernameEditText = findViewById(R.id.username3);
        passwordEditText = findViewById(R.id.password2);
        registerButton = findViewById(R.id.nextbut2);

        mAuth = FirebaseAuth.getInstance();

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createUser();
            }
        });
    }

    private void createUser(){
        String email = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if(TextUtils.isEmpty(email)){
            usernameEditText.setError("Le champ 'Email' ne peut aps être vide");
            usernameEditText.requestFocus();
        }
        else if(TextUtils.isEmpty(password)){
            passwordEditText.setError("Le champ 'Mot de passe ne peut pas être vide");
            passwordEditText.requestFocus();
        }
        else{
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(InscriptionActivity.this,"Utilisateur a bien été enregistré",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), Inscription2Activity.class));
                        finish();
                    }
                    else{
                        Toast.makeText(InscriptionActivity.this,"Erreur de l'enregistrement utilisateur : "+ task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

}