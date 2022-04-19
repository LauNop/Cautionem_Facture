package com.example.cautionem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SuiviActivity extends AppCompatActivity {

    private Button info,upload,facture;
    private TextView nA;
    private String nomAsso;

    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suivi);

        this.info =  findViewById(R.id.nextbut6);
        this.upload =  findViewById(R.id.nextbut5);
        this.facture =  findViewById(R.id.nextbut7);
        this.nA =  findViewById(R.id.titreAssoNom);

        bundle = getIntent().getExtras();
        nomAsso = bundle.getString("key1","Default");
        this.nA.setText(nomAsso);

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),InfosActivity.class));
                finish();

            }
        });

        facture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Facture_Activity.class));
                finish();
            }
        });

    }
}