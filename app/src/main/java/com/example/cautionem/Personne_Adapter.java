package com.example.cautionem;

import android.app.Person;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class Personne_Adapter extends BaseAdapter {

    //  Infos
    private Context context;
    private List<Personne>  personneList;
    private LayoutInflater inflater;

    //  Constructeur
    public Personne_Adapter(Context context, List<Personne> personneList) {
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

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        view = inflater.inflate(R.layout.adapter_membre, null);

        //  Infos du membre
        Personne currentItem = getItem(i);
        String role = currentItem.getRole();
        String pseudo = currentItem.getPseudo();

        //  Récupération du role
        TextView roleView = view.findViewById(R.id.role_membre);
        roleView.setText(role);

        //  Récupération pseudo
        TextView pseudoView = view.findViewById(R.id.pseudo_membre);
        pseudoView.setText(pseudo);

        return view;
    }
}

