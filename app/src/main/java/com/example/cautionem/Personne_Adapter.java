package com.example.cautionem;

import android.annotation.SuppressLint;
import android.app.Person;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Personne_Adapter extends BaseAdapter {

    //  Infos
    private Context context;
    private ArrayList<Personne>  personneList;
    private LayoutInflater inflater;

    //  Constructeur
    public Personne_Adapter(Context context, ArrayList<Personne> personneList) {
        this.context = context;
        this.personneList = personneList;
        this.inflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return personneList.size();
    }

    @Override
    public Personne getItem(int position) {
        return personneList.get(position);
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
        Personne currentItem = getItem(i);

        //  Récupération du role
        TextView roleView = view.findViewById(R.id.role_membre);
        roleView.setText(currentItem.getRole());

        //  Récupération pseudo
        TextView pseudoView = view.findViewById(R.id.pseudo_membre);
        pseudoView.setText(currentItem.getPseudo());

        return view;
    }
}

