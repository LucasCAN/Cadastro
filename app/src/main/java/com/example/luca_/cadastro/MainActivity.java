package com.example.luca_.cadastro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity implements Serializable {

    private EditText nome;
    private EditText cpf;
    private EditText telefone;
    private EditText email;
    private PessoaAcesso acesso;
    private Pessoa pessoa = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nome = findViewById(R.id.editNome);
        cpf = findViewById(R.id.editCPF);
        telefone = findViewById(R.id.editTel);
        email = findViewById(R.id.editEmail);

        acesso = new PessoaAcesso(this);

        Intent i = getIntent();
        if(i.hasExtra("pessoa")){
            pessoa = (Pessoa) i.getSerializableExtra("pessoa");
            nome.setText(pessoa.getNome());
            cpf.setText(pessoa.getCpf());
            telefone.setText(pessoa.getTelefone());
            email.setText(pessoa.getEmail());

        }

    }

    public void salvar(View view){

        if(pessoa == null) {
            pessoa = new Pessoa();
            pessoa.setNome(nome.getText().toString());
            pessoa.setCpf(cpf.getText().toString());
            pessoa.setTelefone(telefone.getText().toString());
            pessoa.setEmail(email.getText().toString());

            long id = acesso.inserir(pessoa);

            Toast.makeText(this, "Pessoa inserida com id " + id, Toast.LENGTH_SHORT).show();
        }else{
            pessoa.setNome(nome.getText().toString());
            pessoa.setCpf(cpf.getText().toString());
            pessoa.setTelefone(telefone.getText().toString());
            pessoa.setEmail(email.getText().toString());

            acesso.atualizar(pessoa);

            Toast.makeText(this, "Pessoa atualizado", Toast.LENGTH_SHORT).show();

        }
    }


}
