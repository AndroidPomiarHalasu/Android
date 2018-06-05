package com.example.kubam.testsymdzw;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Miernik extends AppCompatActivity {
TextView txt;
Button btn;
private int licznik = 0;
  //  @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_miernik);

        btn = (Button) findViewById(R.id.btn_Pomiar);
        txt = (TextView) findViewById(R.id.text_db);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Thread t = new Thread() {
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
                        }
                    };


                    t.start();

            }
        });

    }

}
