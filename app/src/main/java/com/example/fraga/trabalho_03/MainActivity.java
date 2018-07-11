package com.example.fraga.trabalho_03;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void cadastrar(View v){
        Intent it = new Intent(this, CadastrarActivity.class);
        startActivity(it);

    }

    public void listar(View v){
        Intent it = new Intent(this, ListarActivity.class);
        startActivity(it);

    }
}
