package com.example.fraga.trabalho_03;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.fraga.trabalho_03.Controller.Ponto;
import com.example.fraga.trabalho_03.Model.CriaBanco;
import com.example.fraga.trabalho_03.Model.DaoPonto;

public class EditarActivity extends AppCompatActivity {

    EditText edtId, edtDescricao, edtTitulo, edtEndereco;
    Cursor pontoCursor;
    DaoPonto pontoDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);

        edtId = findViewById(R.id.edtId);
        edtDescricao = findViewById(R.id.edtDescricaoEdt);
        edtTitulo = findViewById(R.id.edtTituloEdt);
        edtEndereco = findViewById(R.id.edtEnderecoEdt);

        Intent it = getIntent();
        String codigo = this.getIntent().getStringExtra("codigo");
        pontoDao = new DaoPonto(this);
        pontoCursor = pontoDao.buscaPorId(Integer.parseInt(codigo));

        edtId.setEnabled(false);
        edtId.setText(pontoCursor.getString(pontoCursor.getColumnIndexOrThrow(CriaBanco.ID)));
        edtTitulo.setText(pontoCursor.getString(pontoCursor.getColumnIndexOrThrow(CriaBanco.TITULO)));
        edtDescricao.setText(pontoCursor.getString(pontoCursor.getColumnIndexOrThrow(CriaBanco.DESCRICAO)));
        edtEndereco.setText(pontoCursor.getString(pontoCursor.getColumnIndexOrThrow(CriaBanco.ENDERECO)));

    }

    public void alterar(View v){

        Ponto pontoAlterado = new Ponto();

        pontoAlterado.setId(Integer.parseInt(edtId.getText().toString()));
        pontoAlterado.setTitulo(edtTitulo.getText().toString());
        pontoAlterado.setDescricao(edtDescricao.getText().toString());
        pontoAlterado.setEndereco(edtEndereco.getText().toString());


        //passando o objeto alterado para a camada de persistencia
        pontoDao.alterarRegistro(pontoAlterado);
        Intent it = new Intent(this, ListarActivity.class);
        startActivity(it);
    }

    public void deletar(View v){

        pontoDao.deletarRegistro(Integer.parseInt(edtId.getText().toString()));
        Intent it = new Intent(this, ListarActivity.class);
        startActivity(it);
    }



}
