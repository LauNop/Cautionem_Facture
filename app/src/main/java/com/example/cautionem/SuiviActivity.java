package com.example.cautionem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class SuiviActivity extends AppCompatActivity {

    private Button info, membre,facture;
    private TextView nA;

    FirebaseAuth mAuth;

    Bundle bundle;
    private String nomAsso;
    private String assoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suivi);

        this.info = (Button) findViewById(R.id.nextbut6);
        this.membre = (Button) findViewById(R.id.nextbut5);
        this.facture = (Button) findViewById(R.id.nextbut7);
        this.nA =  findViewById(R.id.titreAssoNom);

        mAuth = FirebaseAuth.getInstance();

        bundle = getIntent().getExtras();
        nomAsso = bundle.getString("key1");
        assoId = bundle.getString("key2");
        this.nA.setText(nomAsso);

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), InfosActivity.class);
                bundle = new Bundle();
                bundle.putString("key1",nomAsso);
                bundle.putString("key2",assoId);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();

            }
        });

        facture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Facture_Activity.class);
                bundle = new Bundle();
                bundle.putString("key1",nomAsso);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });

        membre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Membre_Activity.class);
                bundle = new Bundle();
                bundle.putString("key1",nomAsso);
                bundle.putString("key2",assoId);
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
}