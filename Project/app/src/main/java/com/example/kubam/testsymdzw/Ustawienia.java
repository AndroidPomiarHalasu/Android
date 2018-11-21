package com.example.kubam.testsymdzw;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class Ustawienia extends AppCompatActivity {
    private Button button;
    RadioGroup radioGroup;
    RadioButton radioButton;
    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        radioGroup = findViewById(R.id.radioGroup);

        setContentView(R.layout.activity_ustawienia);
        button = (Button) findViewById(R.id.ustawienia2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMiernik();
            }
        });

    }
    public void openMiernik(){
        Intent intent = new Intent(this, Miernik.class);
        startActivity(intent);
    }
}
