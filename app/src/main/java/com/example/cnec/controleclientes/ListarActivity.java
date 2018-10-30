package com.example.cnec.controleclientes;

import android.annotation.SuppressLint;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ListarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar);

//        ConstraintLayout cl = findViewById(R.id.clListar);
//        registerForContextMenu(cl);


        this.listar(null);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        Log.d("View", String.valueOf(v.toString()));
       // Log.d("menuInfo", String.valueOf(menuInfo.toString()));

        getMenuInflater().inflate(R.menu.opcoes_cliente, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        Log.d("MenuItem", String.valueOf(item.getItemId()));
//        Log.d("getMenuInfo", String.valueOf(item.getMenuInfo().toString()));
        LinhaTabela lt = (LinhaTabela)item.getActionView();

        Log.d("identificador", lt.identificador);
        switch (item.getItemId()) {



        }
        return true;
    }

    public void listar(View v){
        ClientesCRUDRemoto c = new ClientesCRUDRemoto();

        c.listarActivity = this;

        c.execute("GET");

    }

    public void cancelar(View v){
        finish();
    }

    public void exibirListagem(String o) {
        try {
            JSONArray jsonArray = new JSONArray(o);
            TableLayout tl = findViewById(R.id.tlResultado);
            tl.removeAllViews();

            TextView twId = new TextView(this);
            twId.setText("Id");
            twId.setTextSize(30);

            TextView twNome = new TextView(this);
            twNome.setText("Nome");
            twNome.setTextSize(30);

            LinhaTabela tr = new LinhaTabela(this);
            registerForContextMenu(tr);
            tr.addView(twId);
            tr.addView(twNome);
            tl.addView(tr);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject JSONCliente = jsonArray.getJSONObject(i);

                twId = new TextView(this);
                twId.setText(JSONCliente.get("id").toString());
                twId.setTextSize(30);

                twNome = new TextView(this);
                twNome.setText(JSONCliente.get("nome").toString());
                twNome.setTextSize(30);


//                Button b = new Button(this);
//              //  b.setId(Integer.getInteger(JSONCliente.get("id").toString()));
//                b.setText(JSONCliente.get("id").toString());
//
//                b.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//
//                    }
//                });

                tr = new LinhaTabela(this);
tr.setOnLongClickListener(new View.OnLongClickListener() {
    @Override
    public boolean onLongClick(View view) {
        view.showContextMenu();

        return true;
    }
});

                tr.identificador = JSONCliente.get("id").toString();

                registerForContextMenu(tr);

                tr.addView(twId);
               // tr.addView(b);
                tr.addView(twNome);

                tl.addView(tr);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
