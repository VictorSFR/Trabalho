package com.example.trabalho;

import android.content.ContentValues;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.trabalho.Carro;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class Cadastrar extends AppCompatActivity
{
    private Button btnCadastro, btnVoltar;
    private EditText txtModelo, txtMarca, txtAno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar);

        txtModelo = findViewById(R.id.txtModelo);
        txtMarca = findViewById(R.id.txtMarca);
        txtAno = findViewById(R.id.txtAno);

        btnCadastro = findViewById(R.id.btnCadastro);
        btnVoltar = findViewById(R.id.btnVoltar);

        btnCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                //Criando objeto carro
                Carro carro = new Carro(txtModelo.getText().toString().trim(),
                                        txtMarca.getText().toString().trim(),
                                        Integer.valueOf(txtAno.getText().toString().trim()));




                ContentValues cv = new ContentValues();
                cv.put("modelo", txtModelo.getText().toString().trim());
                cv.put("marca", txtMarca.getText().toString().trim());
                cv.put("ano", txtAno.getText().toString().trim());

                DBHelper bd = new DBHelper(Cadastrar.this);
                String msg = "";

                if(bd.inserirCarro(cv) > 0)
                {
                    msg = "Carro inserido com sucesso!";
                    limparCampos();

                    //Temporariamente mostrar o livro inserido no "log"
                    List<ContentValues> cvList = bd.pesquisarCarro();

                    for (int i = 0; i<cvList.size(); i++)
                    {
                        ContentValues cvAux = cvList.get(i);
                        Log.i("ID Carro", cvAux.getAsString("id"));
                        Log.i("Modelo Carro", cvAux.getAsString("modelo"));
                        Log.i("Marca Carro", cvAux.getAsString("marca"));
                        Log.i("Ano Livro", cvAux.getAsString("ano"));
                    }
                }
                else
                {
                    msg = "Ocorreu um erro ao inserir o carro;";
                }

                Toast.makeText(Cadastrar.this, msg, Toast.LENGTH_LONG).show();
            }
        });

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void limparCampos()
    {
        txtAno.setText("");
        txtMarca.setText("");
        txtModelo.setText("");
        txtModelo.requestFocus();
    }
}