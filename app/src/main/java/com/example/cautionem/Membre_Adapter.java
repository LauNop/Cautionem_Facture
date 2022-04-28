package com.example.cautionem;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class Membre_Adapter extends BaseAdapter {

    //  Infos
    private Context context;
    private ArrayList<Membre> membreList;
    private LayoutInflater inflater;


    //  Constructeur
    public Membre_Adapter(Context context, ArrayList<Membre> membreList) {
        this.context = context;
        this.membreList = membreList;
        this.inflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return membreList.size();
    }

    @Override
    public Membre getItem(int position) {
        return membreList.get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @SuppressLint({"ViewHolder", "InflateParams"})
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View view1 = view;

        view = inflater.inflate(R.layout.adapter_membre, null);

        //  Infos du membre
        Membre currentItem = getItem(i);

        //  Récupération du role
        TextView roleView = view.findViewById(R.id.role_membre);
        roleView.setText(currentItem.getRole());

        //  Récupération pseudo
        TextView pseudoView = view.findViewById(R.id.pseudo_membre);
        pseudoView.setText(currentItem.getPrénom()+" "+currentItem.getNom());

        //  Récuperation image profil
        ImageView imageView = view.findViewById(R.id.icon_membre);
        int idNumPhoto = currentItem.getNumPhoto();

        switch(idNumPhoto){
            case 0:
                imageView.setImageResource(R.drawable.ic_lambda_profile);
                break;
            case 1:
                imageView.setImageResource(R.drawable.profil1);
                break;
            case 2:
                imageView.setImageResource(R.drawable.profil2);
                break;
            case 3:
                imageView.setImageResource(R.drawable.profil3);
                break;
            case 4:
                imageView.setImageResource(R.drawable.profil4);
                break;
            case 5:
                imageView.setImageResource(R.drawable.profil5);
                break;
            case 6:
                imageView.setImageResource(R.drawable.profil6);
                break;
            case 7:
                imageView.setImageResource(R.drawable.profil7);
                break;
            case 8:
                imageView.setImageResource(R.drawable.profil8);
                break;
        }

        return view;
    }
}

