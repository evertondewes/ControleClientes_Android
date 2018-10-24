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
    @Override
    protected String doInBackground(String... strings) {
        URL url;
        HttpURLConnection urlConnection = null;

        String resposta = new String();
        try {

            url = new URL("http://10.0.2.2:80//android/json1/crud_clientes.php");
            urlConnection = (HttpURLConnection) url.openConnection();

            switch (strings[0]){
                case "POST":

                    JSONObject cliente = new JSONObject();
                    cliente.put("nome", strings[1]);
                    cliente.put("rua", strings[2]);
                    cliente.put("numero", strings[3]);
                    cliente.put("bairro", strings[4]);


                    urlConnection.setRequestMethod("POST");

                    Writer writer = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream(), "UTF-8"));
                    writer.write(cliente.toString());

                    writer.close();

                    break;
                case "PUT":

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
        Log.d("Retorno: ", resposta);
        return resposta;
    }
}
