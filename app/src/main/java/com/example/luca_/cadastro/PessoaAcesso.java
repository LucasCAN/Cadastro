package com.example.luca_.cadastro;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class PessoaAcesso {

    private Conexao conexao;
    private SQLiteDatabase banco;

    public PessoaAcesso(Context context){
        conexao = new Conexao(context);
        banco = conexao.getWritableDatabase();
    }

    public long inserir(Pessoa pessoa){
        ContentValues values = new ContentValues();
        values.put("nome", pessoa.getNome());
        values.put("cpf", pessoa.getCpf());
        values.put("telefone", pessoa.getTelefone());
        values.put("email", pessoa.getEmail());

        return banco.insert("pessoa", null, values);
    }

    public List<Pessoa> listarTodos(){
        List<Pessoa> pessoas = new ArrayList<>();

        Cursor cursor = banco.query("pessoa", new String[]{"id","nome","cpf","telefone","email"},
                                    null, null,null,null,null);

        while(cursor.moveToNext()){
            Pessoa p = new Pessoa();
            p.setId(cursor.getInt(0));
            p.setNome(cursor.getString(1));
            p.setCpf(cursor.getString(2));
            p.setTelefone(cursor.getString(3));
            p.setEmail(cursor.getString(4));

            pessoas.add(p);
        }

        return pessoas;
    }

    public void excluir(Pessoa p){
        banco.delete("pessoa","id = ?", new String[]{p.getId().toString()});
    }

    public void atualizar(Pessoa p){
        ContentValues values = new ContentValues();
        values.put("nome", p.getNome());
        values.put("cpf", p.getCpf());
        values.put("telefone", p.getTelefone());
        values.put("email", p.getEmail());

        banco.update("pessoa",values,"id = ?", new String[]{p.getId().toString()});

    }

}
