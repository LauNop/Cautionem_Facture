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

import com.google.android.gms.tasks.OnFailureListener;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class Facture_Activity extends AppCompatActivity {

    private ArrayList<Facture> FactureList = new ArrayList<Facture>();
    private String nomAsso,assoId;
    private ListView facturelistView;

    FirebaseFirestore db;
    FirebaseAuth mAuth;
    FirebaseStorage storage;

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
                    bundle = new Bundle();
                    bundle.putString("key2",assoId);
                    intent.putExtras(bundle);
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
        storage = FirebaseStorage.getInstance("gs://cautionem-a1155.appspot.com/");

        bundle = getIntent().getExtras();
        nomAsso = bundle.getString("key1","Default");
        assoId = bundle.getString("key2");

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
        bundle.putString("key2",assoId);
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

        StorageReference listRef = storage.getReference().child(assoId);

        listRef.listAll()
                .addOnSuccessListener(new OnSuccessListener<ListResult>() {
                    @Override
                    public void onSuccess(ListResult listResult) {
                        for (StorageReference prefix : listResult.getPrefixes()) {
                            // All the prefixes under listRef.
                            // You may call listAll() recursively on them.
                        }

                        for (StorageReference item : listResult.getItems()) {
                            FactureList.add(new Facture(item.getName()));
                            // All the items under listRef.
                        }
                        facturelistView.setAdapter(new Facture_Adapter(Facture_Activity.this,FactureList,assoId));
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Uh-oh, an error occurred!
                    }
                });
    }
}