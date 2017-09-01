package com.appdeveloperrohitgmail.inseon_final;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Rohit Goudar on 11-08-2017.
 */

public class Survey_Sync extends AppCompatActivity {

    Button survey_start;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.survey_sync);

        survey_start = (Button)findViewById(R.id.survey);
        survey_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent start_survey= new Intent(Survey_Sync.this,select_survey2.class);
                startActivity(start_survey);
            }
        });

    }
}
