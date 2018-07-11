package com.example.fraga.trabalho_03.Model;

/**
 * Created by fraga on 6/27/18.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CriaBanco extends SQLiteOpenHelper {

    //criação de constantes
    static final String NOME_BANCO = "banco.db";
    public static final String TABELA = "pontos";
    public static final String ID = "_id";
    public static final String TITULO = "titulo";
    public static final String DESCRICAO = "descricao";
    public static final String ENDERECO = "endereco";

    static final int VERSAO = 1;



    //Metodo construtor
    public CriaBanco(Context context) {
        super(context, NOME_BANCO, null, VERSAO);
    }

    //metodo herdado e a ser customizado
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABELA + "(" + ID + " integer primary key autoincrement," + TITULO + " text," + ENDERECO + " text," + DESCRICAO + " text )";
        db.execSQL(sql);

    }


    //método herdado e a ser customizado
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABELA);
        onCreate(db);

    }
}
