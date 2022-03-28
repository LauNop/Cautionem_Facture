package com.example.cautionem;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class Membre_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_membre);

        // liste des membres
        List<Personne> personneList = new ArrayList<>();
        personneList.add(new Personne("Président","Laun le BOSS"));
        personneList.add(new Personne("Trésorier","Arsène"));
        personneList.add(new Personne("Secrétaire","Louis"));

        // voir la liste
        ListView Membrelist = findViewById(R.id.Membre_list);
        Membrelist.setAdapter(new Personne_Adapter(this, personneList));

    }

}