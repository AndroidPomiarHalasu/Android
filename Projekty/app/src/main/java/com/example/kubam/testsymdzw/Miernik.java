package com.example.kubam.testsymdzw;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.TextView;
import java.lang.Math;



public class Miernik extends AppCompatActivity {
    TextView txt,odebranazInputa;
    Button btn, zatwierdz;
    private  Chronometer chronometer;
    private boolean running;
    private long    przesuniecie;
    int wartosc_stalej;
    EditText stala;

    private int licznik = 0;

    private static final int SAMPLE_RATE = 44100;
    boolean mShouldContinue = true;
    private static final int REQUEST_RECORD_AUDIO = 13;
    public double Laeq = 0;
    private Thread mThread;

  //  @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_miernik);
        requestMicrophonePermission();
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
                    };*/

                    if(!running){
                        chronometer.start();
                        chronometer.setBase(SystemClock.elapsedRealtime()- przesuniecie);

                        running=true;

                        if (mThread != null)
                            return;

                        mShouldContinue = true;
                        mThread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                record();
                            }
                        });
                        mThread.start();
                    }


                        //t.start();

            }
        });

    }


    public void stopChronometer(View v){
        if (running){
            chronometer.stop();
            przesuniecie = SystemClock.elapsedRealtime() - chronometer.getBase();
            running=false;

            if (mThread == null)
                return;

            mShouldContinue = false;
            mThread.interrupt();
            mThread = null;
        }
    }

    public  void resetChronometer(View v){
        chronometer.setBase(SystemClock.elapsedRealtime());
        

    }

    private void record() {
        Log.v("info", "Start");
        android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_AUDIO);


        // buffer size in bytes
        int bufferSize = AudioRecord.getMinBufferSize(SAMPLE_RATE,
                AudioFormat.CHANNEL_IN_MONO,
                AudioFormat.ENCODING_PCM_FLOAT);

        if (bufferSize == AudioRecord.ERROR || bufferSize == AudioRecord.ERROR_BAD_VALUE) {
            bufferSize = SAMPLE_RATE * 2;
        }

        short[] audioBuffer = new short[bufferSize / 2];
        float[] audioBufferFloat = new float[SAMPLE_RATE];

        AudioRecord record = new AudioRecord(MediaRecorder.AudioSource.UNPROCESSED,
                SAMPLE_RATE,
                AudioFormat.CHANNEL_IN_MONO,
                AudioFormat.ENCODING_PCM_FLOAT,
                bufferSize);

        if (record.getState() != AudioRecord.STATE_INITIALIZED) {
            Log.e("err", "Audio Record can't initialize!");
            return;
        }
        record.startRecording();

        Log.v("err", "Start recording");

        long shortsRead = 0;
        float sampleTime = 1/44100;
        while (mShouldContinue) {
            int numberOfShort = record.read(audioBufferFloat, 0, SAMPLE_RATE, AudioRecord.READ_BLOCKING);
            shortsRead += numberOfShort;
            float sum = 0;
            for(int i = 0; i < numberOfShort; i++)
            {
                sum = sum + audioBufferFloat[i] * audioBufferFloat[i];//W * sampleTime;
            }

            if(sum != 0) {
                Laeq = 10 * Math.log10(sum/2e-5);
            }else{
                Laeq = 0;
            }
            Log.e("Read", "Odczytano "+String.valueOf(numberOfShort)+" "+String.valueOf(Laeq)+" "+String.valueOf(sum));


            runOnUiThread(new Runnable() {
                public void run() {
                    // Update UI elements
                    String text = String.format("%.3f", Laeq)+" dB";
                    txt.setText(text);
                }
            });


        }

        record.stop();
        record.release();

        Log.v("info", String.format("Recording stopped. Samples read: %d", shortsRead));
    }

    private void requestMicrophonePermission() {

            ActivityCompat.requestPermissions(Miernik.this, new String[]{
                    android.Manifest.permission.RECORD_AUDIO}, REQUEST_RECORD_AUDIO);

    }



}
