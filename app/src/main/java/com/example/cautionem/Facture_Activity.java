package com.example.cautionem;

import static com.example.cautionem.R.id.Asso_list;
import static com.example.cautionem.R.id.Facture_list;
import static com.example.cautionem.R.id.Membre_list;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Facture_Activity extends AppCompatActivity {

    private ArrayList<Facture> FactureList = new ArrayList<Facture>();
    private String nomAsso;
    private ListView facturelistView;

    FirebaseFirestore db;
    FirebaseAuth mAuth;

    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facture);

       facturelistView = findViewById(Facture_list);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        bundle = getIntent().getExtras();
        nomAsso = bundle.getString("key1","Default");

        FactureList.add(new Facture("Facture 01"));
        FactureList.add(new Facture("Facture 02"));
        FactureList.add(new Facture("Facture 03"));

        assemblageFacture();
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

    private void assemblageFacture() {
        FirebaseUser user = mAuth.getCurrentUser();
        String uid = user.getUid();
        final String[] assoId = new String[1];



        db
                .collection("Users")
                .document(uid).collection("Assos")
                .document(nomAsso)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Log.d("getAssoId Success", "Récupération de l'"+documentSnapshot.getData()+" de l'Asso "+documentSnapshot.getId());
                        assoId[0] = documentSnapshot.toObject(User_Asso.class).getId();
                        CollectionReference dbFacture = db.collection("Assos").document(assoId[0]).collection("Factures");
                        dbFacture
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        Log.d("accessCollectionFacture Success", "");
                                        if (task.isSuccessful()) {
                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                Facture facture = document.toObject(Facture.class);
                                                FactureList.add(facture);
                                                Log.d("getFactureDoc Success", document.getId() + " => " + document.getData());
                                            }
                                            facturelistView.setAdapter(new Facture_Adapter(Facture_Activity.this,FactureList));
                                        } else {
                                            Log.d("getAllFactureDoc Fail", "");
                                        }
                                    }
                                });
                    }
                });


    }
}