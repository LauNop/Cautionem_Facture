package com.example.cautionem;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Facture_Adapter extends BaseAdapter {

    //  Infos
    private Context context;
    private ArrayList<Facture> factureList;
    private LayoutInflater inflater;
    FirebaseStorage storage;
    StorageReference storageRef;
    String assoId;

    //  Constructeur
    public Facture_Adapter(Context context, ArrayList<Facture> factureList,String assoId) {
        this.context = context;
        this.factureList = factureList;
        this.inflater = LayoutInflater.from(context);
        this.assoId =assoId;
    }

    @Override
    public int getCount() {
        return factureList.size();
    }

    @Override
    public Facture getItem(int position) { return factureList.get(position); }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @SuppressLint({"ViewHolder", "InflateParams"})
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View view1 = view;

        view = inflater.inflate(R.layout.adapter_facture, null);

        //  Infos d'une facture
        Facture currentItem = getItem(i);

        //  Récupération du nom de la facture
        TextView roleView = view.findViewById(R.id.nom_facture);
        ImageButton telecharger =view.findViewById(R.id.telecharger);
        roleView.setText(currentItem.getNom());
        telecharger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                storage = FirebaseStorage.getInstance("gs://cautionem-a1155.appspot.com/");
                // Create a storage reference from our app
                storageRef = storage.getReference().child(assoId);

                StorageReference islandRef = storageRef.child(currentItem.getNom());

                File localFolder = new File(Environment.getExternalStorageDirectory().getPath(), "Cautionem_Factures");
                File localFile = new File(localFolder, currentItem.getNom());
                if(localFolder.exists()){
                    try{
                        localFile.mkdir();
                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                }
                else{
                    try{
                        localFolder.mkdir();
                        localFile.mkdir();
                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                }
                Log.d("Fichier enregistré ici: ",Environment.getExternalStorageDirectory().getPath());
                /* try {
                    localFile = File.createTempFile("Cautionem_Factures", "pdf");
                    Log.d("File Created","Téléchargement du fichier:"+currentItem.getNom());
                } catch (IOException e) {
                    e.printStackTrace();
                }*/

                islandRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        Log.d("Download Success","Téléchargement du fichier:"+currentItem.getNom());
                        // Local temp file has been created
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Log.d("Download Fail","Téléchargement du fichier:"+currentItem.getNom()+" échoué");
                        // Handle any errors
                    }
                });
            }
        });

        return view;
    }
}
