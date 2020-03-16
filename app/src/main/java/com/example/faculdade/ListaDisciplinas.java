package com.example.faculdade;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ListaDisciplinas extends AppCompatActivity {
    protected ListView lista;
    protected AutorValue autorValue;
    protected ArrayAdapter<AutorValue> adapter;
    private AdapterListView adapterListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_disciplinas);

        lista = (ListView) findViewById(R.id.listView);

        createListView();
    }

    private void createListView() {
        AutorDAO dao = new AutorDAO(this);

        ArrayList<AutorValue> autores = (ArrayList<AutorValue>)
                new ArrayList(dao.getLista());
        dao.close();

        adapterListView = new AdapterListView(this, autores);
        lista.setAdapter(adapterListView);
    }


    @Override
    protected void onActivityResult(int codigo,int reultado,Intent it) {

        super.onActivityResult(codigo, reultado, it);
        this.adapter.notifyDataSetChanged();
    }

}
