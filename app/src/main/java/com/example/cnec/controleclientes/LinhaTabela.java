package com.example.cnec.controleclientes;

import android.content.Context;
import android.widget.TableRow;

import org.json.JSONObject;

/**
 * Created by CNEC on 29/10/2018.
 */

public class LinhaTabela extends TableRow {

    public int identificador;

    public JSONObject JSONCliente;


    public LinhaTabela(Context context) {
        super(context);
    }

}
