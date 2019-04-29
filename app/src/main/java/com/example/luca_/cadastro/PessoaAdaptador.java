package com.example.luca_.cadastro;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

public class PessoaAdaptador extends BaseAdapter {

    public static RecyclerInterface recyclerInterface;

    private List<Pessoa> pessoas;
    private Activity activity;


    public PessoaAdaptador(Activity activity, List<Pessoa> pessoas) {
        this.activity = activity;
        this.pessoas = pessoas;

    }

    @Override
    public int getCount() {
        return pessoas.size();
    }

    @Override
    public Object getItem(int position) {
        return pessoas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return pessoas.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = activity.getLayoutInflater().inflate(R.layout.itens, parent, false);
        TextView nome = view.findViewById(R.id.text_Nome);
        TextView cpf = view.findViewById(R.id.text_CPF);
        TextView telefone = view.findViewById(R.id.text_Telefone);
        TextView email = view.findViewById(R.id.text_Email);

        Pessoa p = pessoas.get(position);
        nome.setText(p.getNome());
        cpf.setText(p.getCpf());
        telefone.setText(p.getTelefone());
        email.setText(p.getEmail());

        return view;
    }

}
