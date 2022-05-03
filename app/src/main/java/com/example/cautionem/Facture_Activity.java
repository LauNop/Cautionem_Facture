package com.example.cautionem;

import static com.example.cautionem.R.id.Asso_list;
import static com.example.cautionem.R.id.Facture_list;
import static com.example.cautionem.R.id.Membre_list;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.view.View;
import com.google.android.material.button.MaterialButton;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

        FloatingActionButton storageBtn = findViewById(R.id.storage_btn);

        storageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkPermission()){
                    //permission allowed
                    Intent intent = new Intent(Facture_Activity.this, FileListActivity.class);
                    String path = Environment.getExternalStorageDirectory().getPath();
                    intent.putExtra("path",path);
                    startActivity(intent);
                }else{
                    //permission not allowed
                    requestPermission();

                }
            }
        });

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


    private boolean checkPermission(){
        int result = ContextCompat.checkSelfPermission(Facture_Activity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(result == PackageManager.PERMISSION_GRANTED){
            return true;
        }else
            return false;
    }

    private void requestPermission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(Facture_Activity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            Toast.makeText(Facture_Activity.this,"Storage permission is requires,please allow from settings",Toast.LENGTH_SHORT).show();
        }else
            ActivityCompat.requestPermissions(Facture_Activity.this,new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},111);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.compte :
                //Go to profil Activity
                startActivity(new Intent(getApplicationContext(), Modif_Compte_Activity.class));
                finish();
                break;
            case R.id.déconnexion:
                mAuth.signOut();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
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