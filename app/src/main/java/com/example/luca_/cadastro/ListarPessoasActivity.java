package com.example.luca_.cadastro;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ListarPessoasActivity extends AppCompatActivity {

    private ListView listView;
    private PessoaAcesso acesso;
    private List<Pessoa> pessoas;
    private List<Pessoa> pessoasFiltro = new ArrayList<>();

    protected ImageButton btnDelete;
    protected ImageButton btnEdit;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_pessoas);

        listView = findViewById(R.id.lista_pessoas);
        acesso = new PessoaAcesso(this);
        pessoas = acesso.listarTodos();
        pessoasFiltro.addAll(pessoas);

        //ArrayAdapter<Pessoa> adapter = new ArrayAdapter<Pessoa>(this,android.R.layout.simple_list_item_1,pessoasFiltro);
        PessoaAdaptador adapter = new PessoaAdaptador(this, pessoasFiltro);
        listView.setAdapter(adapter);

        registerForContextMenu(listView);//ao segurar o list aparece excluir e atualizar

    }

    public boolean onCreateOptionsMenu (Menu menu){
        MenuInflater i = getMenuInflater();
        i.inflate(R.menu.menu_principal,menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                procurar(newText);
                return false;
            }
        });
        return true;
    }


    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu,v,menuInfo);
        MenuInflater i = getMenuInflater();
        i.inflate(R.menu.menu_contexto,menu);

    }

    public void procurar(String nome){
        pessoasFiltro.clear();
        for(Pessoa p : pessoas){
            if(p.getNome().toLowerCase().contains(nome.toLowerCase())){
                pessoasFiltro.add(p);
            }
        }
        listView.invalidateViews();
    }

    public void cadastrar(MenuItem item){
        Intent it = new Intent(this,MainActivity.class);
        startActivity(it);
    }


    public void excluir(MenuItem item){
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final Pessoa pessoaExcluir = pessoasFiltro.get(menuInfo.position);//posicao do item

        AlertDialog dialog = new AlertDialog.Builder(this).setTitle("Atenção!").setMessage("Deseja excluir?")
                            .setNegativeButton("NÃO",null).setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        pessoasFiltro.remove(pessoaExcluir);
                        pessoas.remove(pessoaExcluir);
                        acesso.excluir(pessoaExcluir);
                        listView.invalidateViews();
                    }
                }).create();
        dialog.show();
    }

    public void atualizar(MenuItem item){
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final Pessoa pessoaAtualizar = pessoasFiltro.get(menuInfo.position);//posicao do item

        Intent it = new Intent(this, MainActivity.class);
        it.putExtra("pessoa",pessoaAtualizar);
        startActivity(it);

    }

    @Override
    public void onResume(){
        super.onResume();
        pessoas = acesso.listarTodos();
        pessoasFiltro.clear();
        pessoasFiltro.addAll(pessoas);
        listView.invalidateViews();
    }

    @Override
    protected void  onDestroy(){
        super.onDestroy();
    }

    @Override
    protected void  onRestart(){
        super.onRestart();
    }

    @Override
    protected void  onPause(){
        super.onPause();
    }


}
