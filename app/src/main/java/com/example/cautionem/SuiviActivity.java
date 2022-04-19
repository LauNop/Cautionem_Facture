package com.example.cautionem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SuiviActivity extends AppCompatActivity {

    private Button info,upload,facture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suivi);

        this.info =  findViewById(R.id.nextbut6);
        this.upload =  findViewById(R.id.nextbut5);
        this.facture =  findViewById(R.id.nextbut7);

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