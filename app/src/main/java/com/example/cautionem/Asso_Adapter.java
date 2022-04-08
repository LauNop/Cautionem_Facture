package com.example.cautionem;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class Asso_Adapter extends BaseAdapter {

    //  Infos
    private Context context;
    private ArrayList<Asso> AssoList;
    private LayoutInflater inflater;

    //  Constructeur
    public Asso_Adapter(Context context, ArrayList<Asso> AssoList) {
        this.context = context;
        this.AssoList = AssoList;
        this.inflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return AssoList.size();
    }

    @Override
    public Asso getItem(int position) {
        return AssoList.get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @SuppressLint({"ViewHolder", "InflateParams"})
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View view1 = view;

        view = inflater.inflate(R.layout.adapter_asso, null);

        //  Infos du membre
        Asso currentItem = getItem(i);

        //  Récupération du nom de l'asso
        TextView nomView = view.findViewById(R.id.nom_asso);
        nomView.setText(currentItem.getNom());

        return view;
    }
}
