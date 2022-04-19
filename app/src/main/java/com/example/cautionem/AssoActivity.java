package com.example.cautionem;

import static com.example.cautionem.R.id.Asso_list;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class AssoActivity extends AppCompatActivity {

    private ArrayList<Asso> AssoList = new ArrayList<Asso>();

    FirebaseFirestore db;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asso);


        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        assemblageAsso();
    }

    public void assemblageAsso(){
        FirebaseUser user = mAuth.getCurrentUser();
        String uid = user.getUid();
        CollectionReference dbAsso = db.collection("Users").document(uid).collection("Assos");

        dbAsso
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Asso asso = document.toObject(Asso.class);
                                AssoList.add(asso);
                                ListView assoListView = findViewById(Asso_list);
                                assoListView.setAdapter(new Asso_Adapter(AssoActivity.this, AssoList));
                                Toast.makeText(AssoActivity.this,document.getId() + " => " + document.getData(),Toast.LENGTH_SHORT).show();
                                //Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        } else {
                            Toast.makeText(AssoActivity.this,"Error getting documents: "+ task.getException(),Toast.LENGTH_SHORT).show();
                            //Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        /*DocumentReference docRef = db.collection("cities").document("BJ");
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Asso asso = documentSnapshot.toObject(Asso.class);
            }
        });*/
    }
}