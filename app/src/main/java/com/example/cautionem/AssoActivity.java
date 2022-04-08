package com.example.cautionem;

import static com.example.cautionem.R.id.Asso_list;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class AssoActivity extends AppCompatActivity {

    private ArrayList<Asso> AssoList = new ArrayList<Asso>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asso);

        // liste des membres

        AssoList.add(new Asso("Laun le boss"));
        AssoList.add(new Asso("Asso 02"));
        AssoList.add(new Asso("Mafiat MEDEVILLE"));
        AssoList.add(new Asso("SUS DOG GANG"));

        // voir la liste
        ListView AssoList = findViewById(Asso_list);
        AssoList.setAdapter(new Asso_Adapter(this, this.AssoList));

    }
}