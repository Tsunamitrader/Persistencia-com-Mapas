package com.example.fraga.trabalho_03;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import com.example.fraga.trabalho_03.Controller.Ponto;
import com.example.fraga.trabalho_03.Model.DaoPonto;

public class CadastrarActivity extends AppCompatActivity {

    EditText edtTitulo, edtDescricao;
    TextView txtLat, txtLon, txtEnd;
    Double lat, lon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar);

        edtTitulo = findViewById(R.id.edtTitulo);
        edtDescricao = findViewById(R.id.edtDescricao);

        txtLat = findViewById(R.id.txtLat);
        txtLon = findViewById(R.id.txtLon);
        txtEnd = findViewById(R.id.txtEnd);
    }

    public void salvarPonto(View v){

        //populando o objeto do Controller: Ponto.java
        Ponto p = new Ponto();
        p.setTitulo(edtTitulo.getText().toString());
        p.setDescricao(edtDescricao.getText().toString());
        p.setEndereco(txtEnd.getText().toString());

        //mandando o objeto do Controller populado para a camada de persistencia
        DaoPonto pontoDao = new DaoPonto(this);
        String resultado = pontoDao.inserePonto(p);

        //printando um Toast
        Toast.makeText(this, resultado, Toast.LENGTH_LONG).show();

    }


    public void listarPontos(View v){
        Intent it = new Intent(this, ListarActivity.class);
        startActivity(it);
    }

    public void configurarServico(){
        try{
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            LocationListener locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    atualizaLocalizacao(location);

                }

                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) {

                }

                @Override
                public void onProviderEnabled(String s) {

                }

                @Override
                public void onProviderDisabled(String s) {

                }
            };

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

        }
        catch (SecurityException e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();

        }
    }


    public void atualizaLocalizacao (Location localizacao){
        lat = localizacao.getLatitude();
        lon = localizacao.getLongitude();

        txtLat.setText("Latitude" + lat.toString());
        txtLon.setText("Longitude" + lon.toString());

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permission[], int[] grantResult){

        switch (requestCode){
            case 1:{
                if(grantResult.length > 0 && grantResult[0] == PackageManager.PERMISSION_GRANTED)
                    configurarServico();
                else
                    Toast.makeText(this, "Nao funcionou ...", Toast.LENGTH_LONG).show();
            }
        }

    }

    public void ondeEstou(View v){
        if((ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                && (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED))
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
        else
            configurarServico();

    }

    public void endereco(View v){
        try{
            Geocoder geocoder;
            List<Address> end;
            geocoder = new Geocoder(this, Locale.getDefault());
            end = geocoder.getFromLocation(lat, lon, 1);

            String endereco;
            endereco = end.get(0).getAddressLine(0);
            txtEnd.setText(endereco);


        }
        catch (IOException e ){
            Toast.makeText(this, e.getMessage().toString(), Toast.LENGTH_LONG).show();

        }
    }

    public void mapa(View v){
        Intent it = new Intent(this, MapsActivity.class);
        it.putExtra("lat", lat);
        it.putExtra("lon", lon);
        startActivity(it);
    }


}
