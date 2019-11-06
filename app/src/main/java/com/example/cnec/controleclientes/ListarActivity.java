package com.example.cnec.controleclientes;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ListarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar);


    }

    @Override
    protected void onStart() {
        super.onStart();
        this.listar(null);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        LinhaTabela l = (LinhaTabela) v;
        menu.add(0, 0, 0, "Excluir").setActionView(v);
        menu.add(0, 1, 0, "Atualizar").setActionView(v);
        menu.add(0, 2, 0, "Detalhes").setActionView(v);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        LinhaTabela lt = (LinhaTabela) item.getActionView();

        switch (item.getItemId()) {
            case 0: // excluir
                Toast.makeText(getApplicationContext(), "Excluir " + lt.identificador, Toast.LENGTH_LONG).show();

                ClientesCRUDRemoto c = new ClientesCRUDRemoto();
                c.listarActivity = this;
                c.execute("DELETE",String.valueOf(lt.identificador) );

                break;
            case 1:
                // atualizar
                Intent intent = new Intent(this, AtualizarActivity.class);
                intent.putExtra("JSONCliente", lt.JSONCliente.toString());
                startActivity(intent);

                break;
        }
        return true;
    }

    public void listar(View v) {
        ClientesCRUDRemoto c = new ClientesCRUDRemoto();

        c.listarActivity = this;

        EditText te = findViewById(R.id.etFiltrarNome);



        c.execute("GET", te.getText().toString());

    }

    public void cancelar(View v) {
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


                tr = new LinhaTabela(this);

                tr.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        view.showContextMenu();
                        return true;
                    }
                });

                tr.identificador = Integer.parseInt(JSONCliente.get("id").toString());

                tr.JSONCliente = JSONCliente;

                registerForContextMenu(tr);

                tr.addView(twId);
                tr.addView(twNome);

                tl.addView(tr);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
