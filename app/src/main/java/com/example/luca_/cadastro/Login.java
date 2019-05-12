

package com.example.luca_.cadastro;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Login extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemSelectedListener {
    public static final String ARQ_PREFS = "MeuArquivoPreferencias";
    public static final String chaveNome = "chaveNome";
    public static final String chaveEmail = "chaveEmail";
    public static final String chaveGuardaDados = "chaveGuardaDados";
    public static final String chaveSexo = "chaveSexo";


    SharedPreferences configuracoes;
    SharedPreferences.Editor editor;
    CheckBox guardaPrefs;
    EditText editNome, editEmail, editSexo;
    String nome, email,sexo;
    Spinner spinner;
    String selectedItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        guardaPrefs = (CheckBox) findViewById(R.id.chkGuardaInfo);
        editNome = (EditText) findViewById(R.id.editNome);
        editEmail = (EditText) findViewById(R.id.editEmail);
        configuracoes = getSharedPreferences(ARQ_PREFS, Context.MODE_PRIVATE);
        editor = configuracoes.edit();
        guardaPrefs.setChecked(configuracoes.getBoolean(chaveGuardaDados,true));
        editNome.setText(configuracoes.getString(chaveNome,nome));
        editEmail.setText(configuracoes.getString(chaveEmail,email));

        spinner = findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.Opções,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        //selectedItem = spinner.getSelectedItem().toString();
        //selectedItem.set(configuracoes.getString(chaveSexo,sexo));
    }
    public void onClick(View v){
        if (guardaPrefs.isChecked()) {
            editor.putString(chaveNome, editNome.getText().toString());
            editor.putString(chaveEmail, editEmail.getText().toString());
           // editor.putString(chaveSexo, selectedItem);//
            int selectedPosition = spinner.getSelectedItemPosition();
            editor.putInt("spinnerSelection", selectedPosition);
            editor.putBoolean(chaveGuardaDados,true);
            editor.commit();
        } else {
            editor.putString(chaveNome, "");
            editor.putString(chaveEmail, "");
            //editor.putString(chaveSexo, "");
            spinner.setSelection(configuracoes.getInt("spinnerSelection",0));
            editor.putBoolean(chaveGuardaDados, false);
            editor.commit();
        }
        Intent it = new Intent(this,ListarPessoasActivity.class);
        startActivity(it);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(),text,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}