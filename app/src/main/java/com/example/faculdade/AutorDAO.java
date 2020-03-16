package com.example.faculdade;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.LinkedList;
import java.util.List;

public class AutorDAO extends SQLiteOpenHelper {

    private static final String DATABASE = "Artigos";
    private static final int VERSAO = 1;

    public AutorDAO(Context context) {
        super(context, DATABASE, null, VERSAO);
    }

    public void onCreate(SQLiteDatabase db) {
        String ddl = "CREATE TABLE Autor (id INTEGER PRIMARY KEY,"
                + " nome TEXT UNIQUE NOT NULL," +
                " endereco TEXT UNIQUE NOT NULL" +
                ");";
        db.execSQL(ddl);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int velha, int nova) {
        String ddl = "DROP TABLE IF EXISTS Autor";
        db.execSQL(ddl);
        onCreate(db);
    }

    public void salvar(AutorValue autorValue) {
        ContentValues values = new ContentValues();
        values.put("nome", autorValue.getNome());
        values.put("endereco", autorValue.getEndereco());
        getWritableDatabase().insert("Autor", null, values);
    }

    public List getLista() {
        List<AutorValue> autores = new LinkedList<AutorValue>();

        String query = "SELECT * FROM Autor";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        AutorValue autor = null;
        if (cursor.moveToFirst()) {
            do {
                autor = new AutorValue();
                autor.setId(Long.parseLong(cursor.getString(0)));
                autor.setNome(cursor.getString(1));
                autor.setEndereco(cursor.getString(2));
                autores.add(autor);
            } while (cursor.moveToNext());
        }

        return autores;
    }

    public void dropAll(){
        String ddl ="DROP TABLE IF EXISTS Autor";
        getWritableDatabase().execSQL(ddl);
        onCreate( getWritableDatabase());

    }
}
