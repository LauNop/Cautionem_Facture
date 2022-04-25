package com.example.cautionem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class InfosActivity extends AppCompatActivity {

    private EditText nAEditText, emailEditText, ribEditText;
    private Button share;
    private String nomAsso;


    FirebaseFirestore db;
    FirebaseAuth mAuth;

    Bundle bundle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infos);

        this.nAEditText = findViewById(R.id.nomAssoPlace);
        this.emailEditText = findViewById(R.id.emailPlace);
        this.ribEditText = findViewById(R.id.ribPlace);
        this.share = (Button) findViewById(R.id.savebtn);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        bundle = getIntent().getExtras();
        nomAsso = bundle.getString("key1","Default");

        setUpInfo();
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

    private void setUpInfo() {
        //Accès au document détenant les information de l'asso
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
                       assoId[0] = documentSnapshot.toObject(User_Asso.class).getId();
                        Log.d("getAssoId Success", "Récupération de l'Id : "+assoId[0]);
                        DocumentReference docRef = db.collection("Assos").document(assoId[0]);

                        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                Log.d("getAssoDoc Success", "Récupération des informations de l'asso "+nomAsso);
                                Asso asso = documentSnapshot.toObject(Asso.class);
                                nAEditText.setText(asso.getNom());
                                emailEditText.setText(asso.getEmail());
                                ribEditText.setText(asso.getRIB());
                            }
                        });
                    }
                });
    }
}