package com.example.cautionem;

import static com.example.cautionem.R.id.Asso_list;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class AssoActivity extends AppCompatActivity {

    private ArrayList<Asso> AssoList = new ArrayList<Asso>();

    FloatingActionButton main_but,crée_but,rejoindre_but;
    Button articles_btn,FC_btn;
    boolean isOpen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asso);

        // liste des membres

        AssoList.add(new Asso("Asso 01", "Hello@gmail.com", "THERIBXXXXXXX"));
        AssoList.add(new Asso("Asso 02", "Hello@gmail.com", "THERIBXXXXXXX"));


        // voir la liste
        ListView AssoList = findViewById(Asso_list);
        AssoList.setAdapter(new Asso_Adapter(this, this.AssoList));

        main_but = (FloatingActionButton) findViewById(R.id.but_main);
        crée_but = (FloatingActionButton) findViewById(R.id.crée);
        rejoindre_but = (FloatingActionButton) findViewById(R.id.rejoindre);

        //set the click listener on the add btn
        main_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SetVisible();
            }
        });
        crée_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SetVisible();
                Toast.makeText(AssoActivity.this, "edit clicked", Toast.LENGTH_SHORT).show();
            }
        });

        rejoindre_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SetVisible();
                Toast.makeText(AssoActivity.this, "image clicked", Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void SetVisible() {

        if (isOpen) {

            crée_but.setClickable(false);
            rejoindre_but.setClickable(false);
            crée_but.setVisibility(View.INVISIBLE);
            rejoindre_but.setVisibility(View.INVISIBLE);
            isOpen = false;

        } else {

            crée_but.setClickable(true);
            rejoindre_but.setClickable(true);
            crée_but.setVisibility(View.VISIBLE);
            rejoindre_but.setVisibility(View.VISIBLE);
            isOpen = true;
        }

    }
}