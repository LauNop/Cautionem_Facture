package com.example.cautionem;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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
        this.share = findViewById(R.id.savebtn);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        bundle = getIntent().getExtras();
        nomAsso = bundle.getString("key1","Default");

        setUpInfo();
    }

    private void setUpInfo() {
        FirebaseUser user = mAuth.getCurrentUser();
        String uid = user.getUid();
        DocumentReference docRef = db.collection("Users").document(uid).collection("Assos").document(nomAsso);

        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Asso asso = documentSnapshot.toObject(Asso.class);
                nAEditText.setText(asso.getNom());
                emailEditText.setText(asso.getEmail());
                ribEditText.setText(asso.getRIB());
            }
        });


    }
}