package com.example.docencia.practica1;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;
import android.widget.MediaController;

import java.io.IOException;

/**
 * Created by root on 23/12/16.
 */

public class TestActivity extends AppCompatActivity {

    public static String[] choice={"Version de la aplicacion", "Listado de componentes de la aplicacion","Opciones del menu de ajustes","Opciones del menu de ajustes","Nivel minimo de la API android requerida","Nombre del paquete java de la aplicacion"};
    public static String[] ayuda={"The manifest describes the components of the application","http://www.acercadehtml.com/manual-html/que-es-html.html4","http://u017633.ehu.eus:28080/static/ServidorTta/AndroidManifest.mp4","http://u017633.ehu.eus:28080/static/ServidorTta/AndroidManifest.mp4","http://u017633.ehu.eus:28080/static/ServidorTta/AndroidManifest.mp4"};
    public static String[] type={"text/html","text/html","audio","video","audio"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        //Rellenar el radioGroup cuando se ejecuta.
        RadioGroup group = (RadioGroup)findViewById(R.id.test_choices);
        for(int i=0;i<5;i++){
            RadioButton radio =new RadioButton(this);
            radio.setId(i);
            radio.setText(choice[i]);
            radio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    findViewById(R.id.button_send_test).setVisibility(View.VISIBLE);
                }
            });
            group.addView(radio);

        }
    }


    public void send(View v){
        RadioGroup group = (RadioGroup)findViewById(R.id.test_choices);
        int choices = group.getChildCount();
        for(int i=0;i<choices;i++)
            group.getChildAt(i).setEnabled(false);

        LinearLayout layout=(LinearLayout)findViewById(R.id.layout);
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
                if(ayuda[select]!=null){
                    findViewById(R.id.button_help).setVisibility(View.VISIBLE);
                }
            }else{
                Toast.makeText(this,
                        "Respuesta correcta",
                        Toast.LENGTH_SHORT);
            }
        }
    }

 /*   public void help(View v){
        WebView w = new WebView(this);
        w.loadData(ayuda,"text/html",null);
        w.setBackgroundColor(Color.TRANSPARENT);
        w.setLayerType(WebView.LAYER_TYPE_SOFTWARE,null);

        LinearLayout layout=(LinearLayout)findViewById(R.id.layout);
        layout.addView(w);

    }*/
    //Añado el nuevo metodo que visualizara la ayuda en funcion del tipo que sea
    public void help(View v){
        RadioGroup group = (RadioGroup)findViewById(R.id.test_choices);
        int select= group.getCheckedRadioButtonId();

       switch(type[select]) {
           case "text/html":
               showHtml(ayuda[select]);
               break;
           case "video":
               showVideo(ayuda[select]);
               break;
           case "audio":
               showAudio(ayuda[select]);
               break;

       }
    }
    public void showHtml(String advise){
        if(advise.substring(0,10).contains("://")){
            //En caso de que el mensaje sea una url
            Uri uri=Uri.parse(advise);
            Intent intent = new Intent(Intent.ACTION_VIEW,uri);
            startActivity(intent);
        }else{
            //En caso de que el mensaje este guardado
            WebView w = new WebView(this);
            w.loadData(advise,"text/html",null);
            w.setBackgroundColor(Color.TRANSPARENT);
            w.setLayerType(WebView.LAYER_TYPE_SOFTWARE,null);

            LinearLayout layout=(LinearLayout)findViewById(R.id.layout);
            layout.addView(w);
        }

    }
    public void showVideo(String advise){
        VideoView video =new VideoView(this);
        video.setVideoURI(Uri.parse(advise));
        ViewGroup.LayoutParams params=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        video.setLayoutParams(params);
        MediaController mediacontroller = new MediaController(this) {
            @Override
            public void hide(){

            }

            @Override
            public boolean dispatchKeyEvent(KeyEvent event){
                if(event.getKeyCode()==KeyEvent.KEYCODE_BACK)
                    finish();
                return super.dispatchKeyEvent(event);
            }
        };
        mediacontroller.setAnchorView(video);
        video.setMediaController(mediacontroller);

        LinearLayout layout=(LinearLayout)findViewById(R.id.layout);
        layout.addView(video);
    }

    public void showAudio(String advise){
        View audio=new View(this);

        AudioPlayer audioPlayer=new AudioPlayer(audio);
        try {
            audioPlayer.setAudioUri(Uri.parse(advise));
        } catch (IOException e) {
            e.printStackTrace();
        }
        LinearLayout layout=(LinearLayout)findViewById(R.id.layout);
        layout.addView(audio);

    }
}
