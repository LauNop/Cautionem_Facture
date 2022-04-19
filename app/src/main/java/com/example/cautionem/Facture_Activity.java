package com.example.cautionem;

import static com.example.cautionem.R.id.Asso_list;
import static com.example.cautionem.R.id.Membre_list;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Facture_Activity extends AppCompatActivity {

    private ArrayList<Facture> FactureList = new ArrayList<Facture>();
    private String nomAsso;

    FirebaseFirestore db;
    FirebaseAuth mAuth;

    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facture);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        bundle = getIntent().getExtras();
        nomAsso = bundle.getString("key1","Default");

        FactureList.add(new Facture("Facture 01"));
        FactureList.add(new Facture("Facture 02"));
        FactureList.add(new Facture("Facture 03"));

        assemblageFacture();

        // voir la liste


    }

    private void assemblageFacture() {
        FirebaseUser user = mAuth.getCurrentUser();
        String uid = user.getUid();
        CollectionReference dbFacture = db.collection("Users").document(uid).collection("Assos").document(nomAsso).collection("Factures");

        dbFacture
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Facture facture = document.toObject(Facture.class);
                                FactureList.add(facture);
                                ListView facturelistView = findViewById(Membre_list);
                                facturelistView.setAdapter(new Facture_Adapter(Facture_Activity.this,FactureList));
                                Toast.makeText(Facture_Activity.this,document.getId() + " => " + document.getData(),Toast.LENGTH_SHORT).show();
                                //Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        } else {
                            Toast.makeText(Facture_Activity.this,"Error getting documents: "+ task.getException(),Toast.LENGTH_SHORT).show();
                            //Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}