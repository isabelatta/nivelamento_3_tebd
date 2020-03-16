package com.example.faculdade;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterListView extends BaseAdapter {

    private LayoutInflater mInflater;
    private ArrayList<AutorValue> itens;

    public AdapterListView(Context context, ArrayList<AutorValue> itens) {
        this.itens = itens;
        mInflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return itens.size();
    }


    public AutorValue getItem(int position) {
        return itens.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View view, ViewGroup parent) {
        ItemSuporte itemHolder;
        if (view == null) {
            view = mInflater.inflate(R.layout.item_list, null);

            itemHolder = new ItemSuporte();
            itemHolder.titulo = ((TextView) view.findViewById(R.id.autor_nome));
            itemHolder.subTitulo = ((TextView) view.findViewById(R.id.autor_endereco));

            view.setTag(itemHolder);
        } else {
            itemHolder = (ItemSuporte) view.getTag();
        }

        AutorValue item = itens.get(position);
        itemHolder.titulo.setText(item.getNome());
        itemHolder.subTitulo.setText("Endereço: " + item.getEndereco());

        return view;
    }

    /**
     * Classe de suporte para os itens do layout.
     */
    private class ItemSuporte {

        TextView titulo;
        TextView subTitulo;
    }

}