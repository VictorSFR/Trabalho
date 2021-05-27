package com.example.trabalho;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity
{
    private Button btnCadastrar, btnPesquisar;
    private RadioGroup rgPesquisa;
    private EditText txtPesquisar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCadastrar = findViewById(R.id.btnCadastrar);
        btnPesquisar = findViewById(R.id.btnCadastro);
        rgPesquisa = findViewById(R.id.rgPesquisa);
        txtPesquisar = findViewById(R.id.txtPesquisar);

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                mudarTelaCadastrar();
            }
        });

        btnPesquisar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                mudarTelaPesquisar();
            }
        });
    }

    private void mudarTelaCadastrar()
    {
        Intent it = new Intent(this, Cadastrar.class);
        startActivity(it);
    }

    private void mudarTelaPesquisar()
    {
        Intent it = new Intent(this, Pesquisar.class);
        it.putExtra("tipo", rgPesquisa.getCheckedRadioButtonId());
        it.putExtra("pesquisa", txtPesquisar.getText().toString());
        startActivity(it);
    }

}