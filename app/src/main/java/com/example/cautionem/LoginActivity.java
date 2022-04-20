package com.example.cautionem;



import android.content.Intent;
import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {


    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;

    FirebaseAuth mAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_connexion);


        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.nextbut8);

        mAuth = FirebaseAuth.getInstance();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });
    }

    @Override
    public void onBackPressed(){
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }
    private void loginUser(){
        String email = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if(TextUtils.isEmpty(email)){
            usernameEditText.setError("Le champ 'Email' ne peut aps être vide");
            usernameEditText.requestFocus();
        }
        else if(TextUtils.isEmpty(password)){
            passwordEditText.setError("Le champ 'Mot' de passe ne peut pas être vide");
            passwordEditText.requestFocus();
        }
        else{
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(LoginActivity.this,"Vous êtes connecté",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), AssoActivity.class));
                        finish();
                    }
                    else{
                        Toast.makeText(LoginActivity.this,"Erreur de connexion : "+ task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}