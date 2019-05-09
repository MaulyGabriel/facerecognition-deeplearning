package com.solinftec.recognition;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;

import java.io.File;

public class MainActivity extends Activity implements AdapterView.OnItemSelectedListener {

    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        String training = intent.getStringExtra("training");

        if(training != null && !training.isEmpty()){
            intent.removeExtra("training");
        }

        double accuracy = intent.getDoubleExtra("accuracy", 0);

        if(accuracy != 0){
            intent.removeExtra("accuracy");
        }

        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

        final EditText edt_nome = (EditText) findViewById(R.id.edt_nome);


        Button callNewPerson = (Button) findViewById(R.id.btn_cadastrar);
        callNewPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), CadastroActivity.class);

                //tenho que verificar se realmente estou recebendo algo e verificar se o nome já possui cadastro
                intent.putExtra("Name", edt_nome.getText().toString());
                intent.putExtra("Folder", "Training");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(intent);

            }
        });

        Button callRecognition = (Button) findViewById(R.id.btn_reconhecer);

        callRecognition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(v.getContext(), ReconhecimentoActivity.class));
            }
        });




    }

    private boolean isNameAlreadyUsed(File[] list, String name){
        boolean used = false;
        if(list != null && list.length > 0){
            for(File person : list){
                // The last token is the name --> Folder name = Person name
                String[] tokens = person.getAbsolutePath().split("/");
                final String foldername = tokens[tokens.length-1];
                if(foldername.equals(name)){
                    used = true;
                    break;
                }
            }
        }
        return used;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
