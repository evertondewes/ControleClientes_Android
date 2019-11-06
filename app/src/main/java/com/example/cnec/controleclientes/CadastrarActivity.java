package com.example.cnec.controleclientes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class CadastrarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar);
    }

    public void cadastrar(View v){

        ClientesCRUDRemoto c = new ClientesCRUDRemoto();

        EditText nome = findViewById(R.id.etNome);
        EditText rua = findViewById(R.id.etRua);
        EditText numero = findViewById(R.id.etNumero);
        EditText bairro = findViewById(R.id.etBairro);

        c.execute("POST",
                nome.getText().toString(),
                rua.getText().toString(),
                numero.getText().toString(),
                bairro.getText().toString());
        finish();
    }

    public void cancelar(View v){
        finish();
    }
}
