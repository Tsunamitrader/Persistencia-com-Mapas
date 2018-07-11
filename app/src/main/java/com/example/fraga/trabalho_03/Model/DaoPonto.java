package com.example.fraga.trabalho_03.Model;

/**
 * Created by fraga on 6/27/18.
 */


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.fraga.trabalho_03.Controller.Ponto;


//intermediario entre CriaBanco.java e Ponto.java
public class DaoPonto {

    private SQLiteDatabase db;
    private CriaBanco banco;
    private static final String CAMPOS[] = {"titulo","endereco", "descricao", "_id"};

    public DaoPonto(Context context){
        banco = new CriaBanco(context);
    }

    //insere dados do objeto Ponto no bd
    public String inserePonto(Ponto ponto){

        ContentValues valores = new ContentValues();
        db = banco.getWritableDatabase();
        long resultado;

        //populando objeto valores
        //valores.put(sql, objeto)
        valores.put(banco.TITULO, ponto.getTitulo());
        valores.put(banco.DESCRICAO, ponto.getDescricao());
        valores.put(banco.ENDERECO, ponto.getEndereco());


        resultado = db.insert(banco.TABELA, null, valores);
        db.close();

        if(resultado == -1)
            return "Erro ao inserir";
        else
            return "Inserido com sucesso";
    }

    public Cursor listarPontos(){

        db = banco.getWritableDatabase();

        Cursor pontos = db.query(banco.TABELA, CAMPOS, null, null, null, null, null);

        if(pontos != null)
            pontos.moveToFirst();

        db.close();
        return pontos;
    }

    public void alterarRegistro(Ponto ponto){

        ContentValues valores = new ContentValues();
        db = banco.getWritableDatabase();
        String where = banco.ID + " = " + ponto.getId();

        valores.put(banco.TITULO, ponto.getTitulo());
        valores.put(banco.DESCRICAO, ponto.getDescricao());
        valores.put(banco.ENDERECO, ponto.getEndereco());


        db.update(banco.TABELA, valores, where, null);
        db.close();
    }

    public void deletarRegistro(int id){

        String where = banco.ID + " = " + id;
        db = banco.getWritableDatabase();
        db.delete(banco.TABELA, where, null);
        db.close();
    }

    public Cursor buscaPorId(int id){

        String where = banco.ID + " = " + id;
        db = banco.getWritableDatabase();
        Cursor cursor = db.query(banco.TABELA, CAMPOS, where, null, null, null, banco.TITULO);

        if(cursor != null)
            cursor.moveToFirst();

        db.close();
        return cursor;

    }




}
