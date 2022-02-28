package com.ed.juegog17paracuanti;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.concurrent.ThreadLocalRandom;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class Mide extends AppCompatActivity {

    public int puntage;
    public TextView Contador;
    public ProgressBar mControlsView;
    public TextView TextAB;
    public TextView Multip;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_mide);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Contador = findViewById(R.id.Contador);

        Intent i = getIntent();
        puntage = i.getIntExtra("PUNT", 0);

        TextAB = findViewById(R.id.TextoAB);
        TextAB.setText(i.getStringExtra("ABAB"));

        Multip = findViewById(R.id.Multiplicador);
        Multip.setText(i.getStringExtra("MULTIP"));

        mControlsView = findViewById(R.id.fullscreen_content_controls);
        View mFrame = findViewById(R.id.fullscreen_content);

        cDT();


        // Set up the user interaction to manually show or hide the system UI.
        mFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (puntage != 0) {
                    String newTextAB = TextAB.getText().toString().substring(1);
                    if (newTextAB.equals("")) alert();
                    else {
                        if (Character.toString(newTextAB.charAt(0)).equals("B")){
                            Intent i = new Intent(Mide.this, Juega.class);
                            i.putExtra("ABAB", newTextAB);
                            i.putExtra("MULTIP", Multip.getText().toString());
                            i.putExtra("PUNT", puntage);
                            startActivity(i);
                        }
                        if (Character.toString(newTextAB.charAt(0)).equals("A")){
                            Intent i = new Intent(Mide.this, Mide.class);
                            i.putExtra("ABAB", newTextAB);
                            i.putExtra("MULTIP", Multip.getText().toString());
                            i.putExtra("PUNT", puntage);
                            startActivity(i);
                        } Mide.this.finish();
                    }
                }
                else Snackbar.make(view, "espera para obtener un puntage", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }

    public void cDT (){
        new CountDownTimer(3000, 1000){
            public void onTick(long millisUntilFinished){
            }
            public  void onFinish(){
                int random = ThreadLocalRandom.current().nextInt(0, 101);
                int diferencia;
                if(random <= puntage) diferencia = puntage - random;
                else diferencia = random - puntage;
                puntage = puntage + diferencia;
                Contador.setText(String.valueOf(random));
                mControlsView.setVisibility(View.GONE);
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
                        Mide.this.finish();
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
