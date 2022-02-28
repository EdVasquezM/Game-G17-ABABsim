package com.ed.juegog17paracuanti;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public TextView TextAb;
    public TextView Multiplicador;
    public FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextAb = findViewById(R.id.TextoAB);
        Multiplicador = findViewById(R.id.Multiplicador);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(FabOCL());

        Button Abtn = findViewById(R.id.Abtn);
        Abtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textAbAcu = TextAb.getText().toString();
                int s = textAbAcu.length();
                if(s < 4){
                    TextAb.setText(textAbAcu + "A");
                    if (s == 2) Multiplicador.setText("Puntos x2");
                    if (s == 3) Multiplicador.setText("Puntos x4");
                } else {
                    Snackbar.make(v, "Maximo cuatro pasos", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });

        Button Bbtn = findViewById(R.id.Bbtn);
        Bbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textAbAcu = TextAb.getText().toString();
                int s = textAbAcu.length();
                if(s < 4){
                    TextAb.setText(textAbAcu + "B");
                    if (s == 2) Multiplicador.setText("Puntos x2");
                    if (s == 3) Multiplicador.setText("Puntos x4");
                } else {
                    Snackbar.make(v, "Maximo cuatro pasos", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });
    }

    public View.OnClickListener FabOCL (){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i;
                if (Character.toString(TextAb.getText().toString().charAt(0)).equals("A"))
                    i = new Intent(MainActivity.this, Mide.class);
                else i = new Intent(MainActivity.this, Juega.class);
                i.putExtra("ABAB", TextAb.getText().toString());
                i.putExtra("MULTIP", Multiplicador.getText().toString());
                startActivity(i);
                fab.setImageResource(android.R.drawable.ic_menu_revert);
                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TextAb.setText("");
                        Multiplicador.setText("");
                        fab.setImageResource(android.R.drawable.ic_media_play);
                        fab.setOnClickListener(FabOCL());
                    }
                });
            }
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
