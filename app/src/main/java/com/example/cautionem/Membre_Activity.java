package com.example.cautionem;

import static com.example.cautionem.R.id.Asso_list;
import static com.example.cautionem.R.id.Membre_list;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Membre_Activity extends AppCompatActivity {

    private ArrayList<Personne> PersonneList = new ArrayList<Personne>();
    private String nomAsso;
    private ListView membreListView;
    FloatingActionButton lien;

    FirebaseFirestore db;
    FirebaseAuth mAuth;

    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_membre);

        membreListView = findViewById(Membre_list);
        lien = (FloatingActionButton) findViewById(R.id.lien);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        bundle = getIntent().getExtras();
        nomAsso = bundle.getString("key1","Default");

        // liste des membres

        PersonneList.add(new Personne("Président", "Laun le BOSS"));
        PersonneList.add(new Personne("Trésorier", "Arsène"));
        PersonneList.add(new Personne("Secrétaire", "Louis"));

        // voir la liste
        assemblageMembre();

        lien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Membre_Activity.this, "Création lien", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(), SuiviActivity.class);
        bundle = new Bundle();
        bundle.putString("key1",nomAsso);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

    private void assemblageMembre() {
        FirebaseUser user = mAuth.getCurrentUser();
        String uid = user.getUid();
        final String[] assoId = new String[1];
        CollectionReference dbMembre = db.collection("Assos").document(assoId[0]).collection("Membres");


        db
                .collection("Users")
                .document(uid).collection("Assos")
                .document(nomAsso)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        assoId[0] = documentSnapshot.toObject(User_Asso.class).getId();
                    }
                });
        dbMembre
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Personne membre = document.toObject(Personne.class);
                                PersonneList.add(membre);
                                Toast.makeText(Membre_Activity.this,document.getId() + " => " + document.getData(),Toast.LENGTH_SHORT).show();
                                //Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                            membreListView.setAdapter(new Personne_Adapter(Membre_Activity.this, PersonneList));
                        } else {
                            Toast.makeText(Membre_Activity.this,"Error getting documents: "+ task.getException(),Toast.LENGTH_SHORT).show();
                            //Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}