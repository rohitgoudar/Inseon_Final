package com.appdeveloperrohitgmail.inseon_final;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class Start_Survey extends AppCompatActivity {

    ImageButton start_survey;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start__survey);
        start_survey = (ImageButton)findViewById((R.id.startButton));
        start_survey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(Start_Survey.this,Question_Option.class);
                startActivity(startIntent);
            }
        });
    }
}
