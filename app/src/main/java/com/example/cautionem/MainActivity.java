package com.example.cautionem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private Button connect,inscript;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        this.connect = (Button) findViewById(R.id.connectbut);
        this.inscript = (Button) findViewById(R.id.inscriptbut);

        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent otherActivity = new Intent(getApplicationContext(),com.example.cautionem.ui.login.LoginActivity.class);
                startActivity(otherActivity);
                finish();
            }
        });

        inscript.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent otherActivity = new Intent(getApplicationContext(),InscriptionActivity.class);
                startActivity(otherActivity);
                finish();
            }
        });
    }

    public void onStart(){
        super .onStart();
        //Check if user is signed in (non-null and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            //reload();
            System.out.println("Hello this is bad");
        }
    }

}