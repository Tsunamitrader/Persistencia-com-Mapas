package com.example.fraga.trabalho_03;


import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fraga.trabalho_03.Model.DaoPonto;

public class ListarActivity extends AppCompatActivity {

    ListView lst01;
    TextView txtID, txtTitulo, txtDescricao, txtEndereco;
    private DaoPonto pontoDao;
    private Cursor pontoCursor;
    private CursorAdapter adapter;
    private static final String CAMPOS[] = {"titulo", "descricao", "endereco", "_id"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar);

        lst01 = findViewById(R.id.lst01);

        pontoDao = new DaoPonto(this);
        pontoCursor = pontoDao.listarPontos();

        if (pontoCursor.getCount() > 0){
            adapter = new SimpleCursorAdapter(this, R.layout.activity_listar, pontoCursor, CAMPOS, new int[]{R.id.txtID, R.id.txtTitulo, R.id.txtDescricao, R.id.txtEndereco}, 1);
            lst01.setAdapter(adapter);
            lst01.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    pontoCursor.moveToPosition(position);
                    String codigo = pontoCursor.getString(pontoCursor.getColumnIndexOrThrow("_id"));

                    Intent it = new Intent(ListarActivity.this, EditarActivity.class);
                    it.putExtra("codigo", codigo);
                    startActivity(it);
                    finish();
                }
            });
        }
        else
            Toast.makeText(this, "Nenhum registro encontrado", Toast.LENGTH_LONG).show();


    }
}
