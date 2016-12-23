package com.example.docencia.practica1;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by root on 23/12/16.
 */

public class TestActivity extends AppCompatActivity implements View.OnClickListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        //Rellenar el radioGroup cuando se ejecuta.
        RadioGroup group = (RadioGroup)findViewById(R.id.test_choices);
        for(int i=0;i<5;i++){
            RadioButton radio =new RadioButton(this);
            radio.setId(i);
            radio.setText(datos.choice[i]);
            radio.setOnClickListener(this);
            group.addView(radio);

        }
    }
    @Override
    public void onClick(View v){
        findViewById(R.id.button_send_test).setVisibility(View.VISIBLE);
    }

    //No me sale este metodo para ponerle en onclick en el boton de enviar ¿Por que?
    public void send(View v, LinearLayout layout){
        RadioGroup group = (RadioGroup)findViewById(R.id.test_choices);
        int choices = group.getChildCount();
        for(int i=0;i<choices;i++)
            group.getChildAt(i).setEnabled(false);
        //ese layout que es? Lo he definido con LinearLayout pero no estoy segura
        layout.removeView(findViewById(R.id.button_send_test));
        group.getChildAt(2).setBackgroundColor(Color.GREEN);
        if( group.getCheckedRadioButtonId()==-1){
            Toast.makeText(this,
                    "Selecciona una opcion",
                    Toast.LENGTH_SHORT);
        }else{
           int select= group.getCheckedRadioButtonId();
            if(select!=2){
                group.getChildAt(select).setBackgroundColor(Color.RED);
                findViewById(R.id.button_help).setVisibility(View.VISIBLE);
            }else{
                Toast.makeText(this,
                        "Respuesta correcta",
                        Toast.LENGTH_SHORT);
            }
        }
    }
    //No me sale este metodo para ponerle en onclick en el boton de ayuda. ¿Por que?
    public void help(View v, LinearLayout layout){
        WebView w = new WebView(this);
        w.loadData(datos.ayuda,"text/html",null);
        w.setBackgroundColor(Color.TRANSPARENT);
        w.setLayerType(WebView.LAYER_TYPE_SOFTWARE,null);
        //ese layout que es? Lo he definido con LinearLayout pero no estoy segura
        layout.addView(w);

    }
}
