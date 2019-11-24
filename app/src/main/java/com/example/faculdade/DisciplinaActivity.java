package com.example.faculdade;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class DisciplinaActivity  extends AppCompatActivity {
    protected EditText editTextDisciplin;
    protected DisciplinaValue disciplinaSelecionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.disciplina_layout);
        Button button = (Button) findViewById(R.id.botao);

        this.editTextDisciplin = (EditText) findViewById(R.id.disciplina);
        this.editTextDisciplin.setText("teste1");
        Intent intent = getIntent();

        disciplinaSelecionada = (DisciplinaValue) intent.getSerializableExtra("disciplinaSelecionada");

        if(disciplinaSelecionada!=null){
            button.setText("Alterar");
            editTextDisciplin.setText(disciplinaSelecionada.getDisciplina());
        }



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DisciplinaDAO dao = new DisciplinaDAO(DisciplinaActivity.this);
                if (disciplinaSelecionada==null) {
                    DisciplinaValue disciplinaValue = new DisciplinaValue();
                    disciplinaValue.setDisciplina(editTextDisciplin.getText().toString());
                    dao.salvar(disciplinaValue);
                }else {
                    disciplinaSelecionada.setDisciplina(editTextDisciplin.getText().toString());
                    dao.alterar(disciplinaSelecionada);
                }

                finish();
            }
        });
    }
}
