package com.example.kubam.testsymdzw;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.TextView;




public class Miernik extends AppCompatActivity {
    private Button button;
TextView txt,odebranazInputa;
Button btn, zatwierdz;
private  Chronometer chronometer;
private boolean running;
private long    przesuniecie;
int wartosc_stalej = 0,czas;
EditText stala;

private int licznik = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_miernik);
        // przycisk ustawienia
        button = (Button) findViewById(R.id.ustawienia1);
        button.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
            openUstawienia();
            }
        });

        btn = (Button) findViewById(R.id.btn_Pomiar);
        txt = (TextView) findViewById(R.id.text_db);
        chronometer = findViewById(R.id.zegar);
        chronometer.setFormat("Czas pomiaru: %s");
        chronometer.setBase(SystemClock.elapsedRealtime());






        //btn = (Button) findViewById(R.id.btn_STOP);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    /*
                         tu moze byc miejsce na funkcje pomiaru
                    */
                    if(!running){
                        chronometer.start();
                        chronometer.setBase(SystemClock.elapsedRealtime()- przesuniecie);

                        running=true;}




            }
        });


    }

    public void openUstawienia(){
        Intent intent = new Intent(this, Ustawienia.class);
        startActivity(intent);

    }
    public void stopChronometer(View v){
        if (running){
            chronometer.stop();
            przesuniecie = SystemClock.elapsedRealtime() - chronometer.getBase();
            running=false;
        }
    }

    public  void resetChronometer(View v){
        chronometer.setBase(SystemClock.elapsedRealtime());
        przesuniecie = 0;

    }





}
