package com.example.kubam.testsymdzw;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.TextView;




public class Miernik extends AppCompatActivity {
TextView txt,odebranazInputa;
Button btn, zatwierdz;
private  Chronometer chronometer;
private boolean running;
private long    przesuniecie;
int wartosc_stalej;
EditText stala;

private int licznik = 0;
  //  @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_miernik);

        btn = (Button) findViewById(R.id.btn_Pomiar);
        odebranazInputa = (TextView) findViewById(R.id.test);
        txt = (TextView) findViewById(R.id.text_db);
        chronometer = findViewById(R.id.zegar);
        chronometer.setFormat("Czas pomiaru: %s");
        chronometer.setBase(SystemClock.elapsedRealtime());
        stala = (EditText) findViewById(R.id.stala);

        zatwierdz = (Button) findViewById(R.id.zatwierdz);
        zatwierdz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wartosc_stalej = Integer.valueOf(stala.getText().toString());
                odebranazInputa.setText(String.valueOf(wartosc_stalej));
            }
        });


        //btn = (Button) findViewById(R.id.btn_STOP);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    /*Thread t = new Thread() {
                        //  @Override
                        public void run() {
                            while (!isInterrupted()) { //licz co 1s
                                try {
                                    Thread.sleep(1000);
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {

                                            licznik++;
                                            txt.setText(String.valueOf(licznik) + " dB");

                                        }
                                    });
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                if(licznik == 59){// zatrzyma sie na 60
                                    break;
                                }
                            }
                        } tu moze byc miejsce na funkcje pomiaru
                    };*/if(!running){
                        chronometer.start();
                        chronometer.setBase(SystemClock.elapsedRealtime()- przesuniecie);

                        running=true;}


                        //t.start();

            }
        });

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

    }





}
