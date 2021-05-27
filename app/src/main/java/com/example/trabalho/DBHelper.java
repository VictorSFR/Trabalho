package com.example.trabalho;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper
{
    //CONSTANTES "ATRIBUTOS"
    private static final String DATABASE_NAME = "carros";
    private static final int DATABASE_VERSION = 1;
    private static final String CREATE_TABLE_CARRO = "CREATE TABLE carro (" +
            " id INTEGER PRIMARY KEY AUTOINCREMENT," +
            " modelo TEXT NOT NULL, " +
            " marca TEXT NOT NULL, " +
            " ano INTEGER NOT NULL);";

    private static final String DROP_TABLE_CARRO = " DROP TABLE carro";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(CREATE_TABLE_CARRO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL(DROP_TABLE_CARRO);
        onCreate(db);
    }

    public long inserirCarro(ContentValues cv)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        long id = db.insert("carro", null, cv);
        return id;
    }

    public boolean removerCarro(String idCarro)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("carro", "id" + "=?", new String[]{idCarro}) > 0;
    }

    public List<ContentValues> pesquisarCarro(String pModelo)
    {
        String sql = "SELECT id, modelo, marca, ano FROM carro WHERE modelo LIKE ?";
        String where[] = new String[]{"%"+pModelo+"%"};
        return pesquisarCarro(sql, where);
    }

    public List<ContentValues> pesquisarCarro(Integer pAno)
    {
        String sql = "SELECT id, modelo, marca, ano FROM carro WHERE ano = ?";
        String where[] = new String[]{String.valueOf(pAno)};
        return pesquisarCarro(sql, where);
    }

    public List<ContentValues> pesquisarCarro()
    {
        String sql = "SELECT id, modelo, marca, ano FROM carro";
        String where[] = null;
        return pesquisarCarro(sql, where);
    }

    private List<ContentValues> pesquisarCarro(String sql, String[] where)
    {
        List<ContentValues> lista = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(sql, where);

        if(c.moveToFirst())
        {
            do {
                ContentValues cv = new ContentValues();
                cv.put("id", c.getInt(c.getColumnIndex("id")));
                cv.put("modelo", c.getString(c.getColumnIndex("modelo")));
                cv.put("marca", c.getString(c.getColumnIndex("marca")));
                cv.put("ano", c.getInt(c.getColumnIndex("ano")));
                lista.add(cv);
            }while(c.moveToNext());
        }

        return lista;
    }
}
