package com.example.trabalho.Adapters;

import android.content.ContentValues;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.trabalho.R;

import java.util.List;

public class Adaptador extends BaseAdapter {

    Context context;
    List<ContentValues> cvList;

    public Adaptador(Context pContext, List<ContentValues> pListaCV)
    {
        this.context = pContext;
        this.cvList = pListaCV;
    }

    @Override
    public int getCount() {
        return this.cvList.size();
    }

    @Override
    public Object getItem(int i) {
        return this.cvList.get(i);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.listviewcustomizado, null);
        TextView txtId = (TextView) view.findViewById(R.id.txtId);
        TextView txtModelo = (TextView) view.findViewById(R.id.txtModelo);
        TextView txtMarca = (TextView) view.findViewById(R.id.txtMarca);
        TextView txtAno = (TextView) view.findViewById(R.id.txtAno);

        ContentValues cv = cvList.get(i);

        txtId.setText(String.valueOf(cv.getAsInteger("id")));
        txtModelo.setText(String.valueOf(cv.getAsString("modelo")));
        txtMarca.setText(String.valueOf(cv.getAsString("marca")));
        txtAno.setText(String.valueOf(cv.getAsString("ano")));


        return view;
    }
}
