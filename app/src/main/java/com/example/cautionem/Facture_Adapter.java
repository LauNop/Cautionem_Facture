package com.example.cautionem;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
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

    private static final String DIRECTORY_DOWNLAODS = Environment.getExternalStorageDirectory().getPath()+"/Downloads";
    //  Infos
    private Context context;
    private ArrayList<Facture> factureList;
    private Facture currentItem;
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
        currentItem = getItem(i);

        //  Récupération du nom de la facture
        TextView roleView = view.findViewById(R.id.nom_facture);
        ImageButton telecharger =view.findViewById(R.id.telecharger);
        roleView.setText(currentItem.getNom());
        telecharger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downlaod();
            }
        });

        return view;
    }

    public void downlaod()
    {
        storage = FirebaseStorage.getInstance("gs://cautionem-a1155.appspot.com/");
        storageRef = storage.getReference().child(assoId).child(currentItem.getNom());

        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String url = uri.toString();
                downlaodFiles(context, currentItem.getNom(), "",DIRECTORY_DOWNLAODS,url);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    private void downlaodFiles(Context context,String fileName, String fileExtension, String destinationDirectory, String url)
    {
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);

        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalFilesDir(context, destinationDirectory, fileName+fileExtension);

        downloadManager.enqueue(request);
    }
}
