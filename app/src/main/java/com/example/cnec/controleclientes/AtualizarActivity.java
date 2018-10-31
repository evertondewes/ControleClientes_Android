package com.example.cnec.controleclientes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class AtualizarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atualizar);

        Intent intent = getIntent();
        String strCliente = intent.getStringExtra("JSONCliente");

        try {
            JSONObject JSONCliente = new JSONObject(strCliente);

            TextView tvId = findViewById(R.id.tvId);
            EditText etNome = findViewById(R.id.etNome);
            EditText etRua = findViewById(R.id.etRua);
            EditText etNumero = findViewById(R.id.etNumero);
            EditText etBairro = findViewById(R.id.etBairro);

            tvId.setText(JSONCliente.get("id").toString());
            etNome.setText(JSONCliente.get("nome").toString());
            etRua.setText(JSONCliente.get("rua").toString());
            etNumero.setText(JSONCliente.get("numero").toString());
            etBairro.setText(JSONCliente.get("bairro").toString());



        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    public void atualizar(View v){

        ClientesCRUDRemoto c = new ClientesCRUDRemoto();

        TextView tvId = findViewById(R.id.tvId);
        EditText nome = findViewById(R.id.etNome);
        EditText rua = findViewById(R.id.etRua);
        EditText numero = findViewById(R.id.etNumero);
        EditText bairro = findViewById(R.id.etBairro);

        c.execute("PUT", tvId.getText().toString(), nome.getText().toString(),
                rua.getText().toString(),
                numero.getText().toString(),
                bairro.getText().toString());
        finish();
    }

    public void cancelar(View v){
        finish();
    }
}
