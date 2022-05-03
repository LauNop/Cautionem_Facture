package com.example.cautionem;

import static com.example.cautionem.R.id.Membre_list;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Membre_Activity extends AppCompatActivity {

    private ArrayList<Membre> MembreList = new ArrayList<Membre>();
    private String nomAsso;
    private String assoId;
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
        nomAsso = bundle.getString("key1");
        assoId = bundle.getString("key2");

        assemblageMembre();

        lien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                DynamicLink dynamicLink = FirebaseDynamicLinks.getInstance().createDynamicLink()
                        .setLink(Uri.parse("https://www.asso.com/?assoId="+assoId+"&nomAsso="+nomAsso))
                        .setDomainUriPrefix("https://cautionem.page.link")
                        // Open links with this app on Android
                        .setAndroidParameters(new DynamicLink.AndroidParameters.Builder().build())
                        .buildDynamicLink();

                Uri dynamicLinkUri = dynamicLink.getUri();
                ClipData clip = ClipData.newPlainText("label",dynamicLinkUri.toString());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(Membre_Activity.this, "Lien copié", Toast.LENGTH_SHORT).show();
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

    private void assemblageMembre() {
        FirebaseUser user = mAuth.getCurrentUser();
        String uid = user.getUid();
        final String[] assoId = new String[1];



        db
                .collection("Users")
                .document(uid)
                .collection("Assos")
                .document(nomAsso)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        assoId[0] = documentSnapshot.toObject(User_Asso.class).getId();
                        CollectionReference dbMembre = db.collection("Assos").document(assoId[0]).collection("Membres");
                        dbMembre
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                Membre membre = document.toObject(Membre.class);
                                                MembreList.add(membre);
                                                Log.d("getMembre Success", document.getId() + " => " + document.getData());
                                            }
                                            membreListView.setAdapter(new Membre_Adapter(Membre_Activity.this, MembreList));
                                        } else {
                                            Log.d("getMembre Fail", "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                    }
                });
    }
}