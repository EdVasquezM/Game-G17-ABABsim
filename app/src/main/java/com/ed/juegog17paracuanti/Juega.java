package com.ed.juegog17paracuanti;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;
import android.widget.TextView;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class Juega extends AppCompatActivity {

    public int puntage;
    public int tap = 0;
    public int counter = 15;
    TextView Contador;
    public boolean start = true;
    public TextView TextAB;
    public TextView Multip;


    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_juega);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Contador = findViewById(R.id.Contador);
        Contador.setText("00:15");

        Intent i = getIntent();
        puntage = i.getIntExtra("PUNT", 0);

        TextAB = findViewById(R.id.TextoAB);
        TextAB.setText(i.getStringExtra("ABAB"));

        Multip = findViewById(R.id.Multiplicador);
        Multip.setText(i.getStringExtra("MULTIP"));

        View mControlsView = findViewById(R.id.fullscreen_content_controls);


        // Set up the user interaction to manually show or hide the system UI.
        mControlsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if (start){
                   cDT();
                   start = false;
               } else {
                   tap++;
               }
            }
        });
    }

    public void cDT (){
        new CountDownTimer(15000, 1000){
            public void onTick(long millisUntilFinished){
                Contador.setText("00:"+ counter);
                counter--;
            }
            public  void onFinish(){
                String newTextAB = TextAB.getText().toString().substring(1);
                if(tap <= puntage) puntage = puntage + tap;
                else puntage = tap - puntage;
                if (newTextAB.equals("")) alert();
                else {
                    if (Character.toString(newTextAB.charAt(0)).equals("A")){
                        Intent i = new Intent(Juega.this, Mide.class);
                        i.putExtra("ABAB", TextAB.getText().toString().substring(1));
                        i.putExtra("MULTIP", Multip.getText().toString());
                        i.putExtra("PUNT", puntage);
                        startActivity(i);
                    }
                    if (Character.toString(newTextAB.charAt(0)).equals("B")){
                        Intent i = new Intent(Juega.this, Juega.class);
                        i.putExtra("ABAB", TextAB.getText().toString().substring(1));
                        i.putExtra("MULTIP", Multip.getText().toString());
                        i.putExtra("PUNT", puntage);
                        startActivity(i);
                    } Juega.this.finish();
                }
            }
        }.start();
    }

    public void alert (){
        String multip = Multip.getText().toString();
        if (multip.equals("Puntos x2")) puntage = puntage*2;
        if (multip.equals("Puntos x4")) puntage = puntage*4;
        new AlertDialog.Builder(this)
                .setTitle("Resultado del experimento")
                .setMessage("Puntage final: "+ puntage)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Juega.this.finish();
                    }
                }).setCancelable(false).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button.
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
