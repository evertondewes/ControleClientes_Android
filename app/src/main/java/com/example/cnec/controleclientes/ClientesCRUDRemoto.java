package com.example.cnec.controleclientes;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by CNEC on 23/10/2018.
 */

public class ClientesCRUDRemoto extends AsyncTask<String, Void, String> {

    public ListarActivity listarActivity;

    @Override
    protected String doInBackground(String... strings) {
        URL url;
        HttpURLConnection urlConnection = null;

        String resposta = new String();
        try {

            url = new URL("http://10.0.2.2:80//android/json1/crud_clientes.php");
            urlConnection = (HttpURLConnection) url.openConnection();
            JSONObject cliente;
            Writer writer;
            switch (strings[0]){
                case "POST":

                    cliente = new JSONObject();
                    cliente.put("nome", strings[1]);
                    cliente.put("rua", strings[2]);
                    cliente.put("numero", strings[3]);
                    cliente.put("bairro", strings[4]);

                    urlConnection.setRequestMethod("POST");

                    writer = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream(), "UTF-8"));
                    writer.write(cliente.toString());

                    writer.close();

                    break;
                case "PUT":
                    cliente = new JSONObject();
                    cliente.put("id", strings[1]);
                    cliente.put("nome", strings[2]);
                    cliente.put("rua", strings[3]);
                    cliente.put("numero", strings[4]);
                    cliente.put("bairro", strings[5]);

                    urlConnection.setRequestMethod("PUT");

                    writer = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream(), "UTF-8"));
                    writer.write(cliente.toString());

                    writer.close();
                    break;

                case "DELETE":

                    cliente = new JSONObject();
                    cliente.put("id", strings[1]);

                    urlConnection.setRequestMethod("DELETE");

                    writer = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream(), "UTF-8"));
                    writer.write(cliente.toString());

                    writer.close();
                    break;
            }


            InputStream in = urlConnection.getInputStream();

            InputStreamReader isw = new InputStreamReader(in);

            int data = isw.read();
            while (data != -1) {
                char current = (char) data;
                data = isw.read();

                resposta += current;

            }
        } catch (Exception e) {
            Log.d("Erro: ", e.toString());

        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
//        Log.d("Retorno: ", resposta);
        return resposta;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        if(this.listarActivity!= null) {
            this.listarActivity.exibirListagem(s);
        }
    }


}
