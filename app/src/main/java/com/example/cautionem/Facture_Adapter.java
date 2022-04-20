package com.example.cautionem;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class Facture_Adapter extends BaseAdapter {

    //  Infos
    private Context context;
    private ArrayList<Facture> factureList;
    private LayoutInflater inflater;

    //  Constructeur
    public Facture_Adapter(Context context, ArrayList<Facture> factureList) {
        this.context = context;
        this.factureList = factureList;
        this.inflater = LayoutInflater.from(context);
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
        roleView.setText(currentItem.getRole());

        return view;
    }
}
