package com.example.trabalho;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.trabalho.Adapters.Adaptador;

import java.util.ArrayList;
import java.util.List;

public class Pesquisar extends AppCompatActivity {

    private Button btnVoltar;
    private TableLayout tblResultado;
    private ListView lstvResultado;
    private Adaptador meuAdp;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesquisar);
        btnVoltar = findViewById(R.id.btnVoltar1);
        lstvResultado = findViewById(R.id.lstvResultado);

        Intent it = getIntent();

        if(it !=null)
        {
            int tipo = it.getIntExtra("tipo", 0);
            String chave = it.getStringExtra("pesquisa");

            List<ContentValues> lista = new ArrayList<>();

            if(tipo == R.id.rbtModelo)
            {
                lista = new DBHelper(this).pesquisarCarro(chave);
            }
            else if(tipo == R.id.rbtAno)
            {
                try
                {
                    int ano = Integer.parseInt(chave);
                    lista = new DBHelper(this).pesquisarCarro(ano);
                }
                catch (Exception e)
                {
                    lista = new DBHelper(this).pesquisarCarro();
                }
            }
            else if(tipo == R.id.rbtTodos)
            {
                lista = new DBHelper(this).pesquisarCarro();
            }

            if(lista != null)
            {
                if(lista.size() > 0)
                {
                    meuAdp = new Adaptador(this, lista);
                    lstvResultado.setAdapter(meuAdp);
                    meuAdp.notifyDataSetChanged();
                }
                else
                {
                    Toast.makeText(Pesquisar.this, "A pesquisa não encontrou nenhum resultado!",
                            Toast.LENGTH_LONG).show();
                }
            }

            lstvResultado.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l)
                {
                    final int positionToRemove = i;
                    ContentValues c = (ContentValues)lstvResultado.getItemAtPosition(positionToRemove);
                    String id = String.valueOf(c.getAsInteger("id"));
                    String modelo = String.valueOf(c.getAsString("modelo"));

                    AlertDialog.Builder adb=new AlertDialog.Builder(Pesquisar.this);
                    adb.setTitle("REMOVER?");
                    adb.setMessage("Tem certeza que deseja remover o carro " + modelo + "?");

                    adb.setNegativeButton("Cancelar", null);
                    adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which)
                        {
                            try
                            {
                                new DBHelper(Pesquisar.this).removerCarro(id);
                                Toast.makeText(Pesquisar.this, "Livro Removido com Sucesso", Toast.LENGTH_LONG);
                                meuAdp.notifyDataSetChanged();
                            }
                            catch (Exception e)
                            {
                                Toast.makeText(Pesquisar.this, "Não foi possível remover o livro.", Toast.LENGTH_LONG);
                            }

                        }});
                    adb.show();

                    return false;
                }
            });
        }

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                onBackPressed();
            }
        });
    }

}