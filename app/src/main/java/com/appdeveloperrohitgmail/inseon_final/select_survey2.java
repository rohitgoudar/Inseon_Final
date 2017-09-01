package com.appdeveloperrohitgmail.inseon_final;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class select_survey2 extends AppCompatActivity {

    Spinner s;
    String[] Survey_Array={"Survey1","Survey2","Survey3","Survey4"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_survey2);

        s=(Spinner)findViewById(R.id.spinner);

        ArrayAdapter adapter =new ArrayAdapter(select_survey2.this,android.R.layout.simple_spinner_item,Survey_Array);
        s.setAdapter(adapter);
        Button b =(Button)findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(select_survey2.this,Start_Survey.class);
                startActivity(i);
            }
        });
    }
}
