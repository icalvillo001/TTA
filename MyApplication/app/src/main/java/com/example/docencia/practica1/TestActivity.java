package com.example.docencia.practica1;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 23/12/16.
 */

public class TestActivity extends AppCompatActivity {

    public static String[] choice={"Version de la aplicacion", "Listado de componentes de la aplicacion","Opciones del menu de ajustes","Opciones del menu de ajustes","Nivel minimo de la API android requerida","Nombre del paquete java de la aplicacion"};
    public static String[] ayuda={"The manifest describes the components of the application","http://www.acercadehtml.com/manual-html/que-es-html.html4","http://u017633.ehu.eus:28080/static/ServidorTta/AndroidManifest.mp4","http://u017633.ehu.eus:28080/static/ServidorTta/AndroidManifest.mp4","http://u017633.ehu.eus:28080/static/ServidorTta/AndroidManifest.mp4"};
    public static String[] type={"text/html","text/html","audio","video","audio"};
    final Test test=new Test();
    int respuestaCorrecta=0;
    RestClient rest = new RestClient("http://u017633.ehu.eus:28080/ServidorTta/rest/tta");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        try{
            getTest();
        }catch (IOException e)
        {
            e.printStackTrace();
        }catch (JSONException e){
            e.printStackTrace();
        }


    }
    public void getTest() throws IOException,JSONException{
        final int id=1;

        new AsyncTask<Void,Void,Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                try{
                    String login="12345678A";
                    String passwd="tta";
                    rest.setHttpBasicAuth(login,passwd);
                    //Se solicita los datos del test al servidor
                    JSONObject json = rest.getJSON(String.format("getTest?id=%d",id));
                    //Se coge el dato del enunciado
                    test.setWording(json.getString("wording"));
                    //Se coge las diferentes opciones y los datos necesarios
                    JSONArray array = json.getJSONArray("choices");
                    for(int i=0;i<array.length();i++){
                        JSONObject itemJSON = array.getJSONObject(i);

                        Test.Choice choice = new Test.Choice();

                        choice.setId(itemJSON.getInt("id"));
                        choice.setAnswer(itemJSON.getString("answer"));
                        choice.setCorrect(itemJSON.getBoolean("correct"));
                        choice.setAdvise(itemJSON.optString("advise",null));



                        test.getChoices().add(choice);
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }catch (IOException e){
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {

                //Se visualiza el enunciado del test
                TextView text=(TextView)findViewById(R.id.enunciadoTest);
                text.setText(test.getWording());

                //Se visualizan las opciones del test
                RadioGroup group = (RadioGroup)findViewById(R.id.test_choices);
                for(int i=0;i<5;i++){
                    RadioButton radio =new RadioButton(TestActivity.this);
                    radio.setId(test.getChoices().get(i).getId());
                    radio.setText(test.getChoices().get(i).getAnswer());
                    if(test.getChoices().get(i).getCorrect()==true){
                        respuestaCorrecta=i;
                    }
                    radio.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            findViewById(R.id.button_send_test).setVisibility(View.VISIBLE);
                        }
                    });
                    group.addView(radio);

                }

                super.onPostExecute(aVoid);
            }
        }.execute();

    }

    public void send(View v){

        RadioGroup group = (RadioGroup)findViewById(R.id.test_choices);
        int choices = group.getChildCount();
        for(int i=0;i<choices;i++)
            group.getChildAt(i).setEnabled(false);

        LinearLayout layout=(LinearLayout)findViewById(R.id.layout);
        layout.removeView(findViewById(R.id.button_send_test));

        //group.getChildAt(2).setBackgroundColor(Color.GREEN);
       // group.getChildAt(test.getChoices().get().getCorrect());
        group.getChildAt(respuestaCorrecta).setBackgroundColor(Color.GREEN);

        if( group.getCheckedRadioButtonId()==-1){
            Toast.makeText(this,
                    "Selecciona una opcion",
                    Toast.LENGTH_SHORT);
        }else{
           int select= group.getCheckedRadioButtonId();
            if(select!=respuestaCorrecta){
                group.getChildAt(select).setBackgroundColor(Color.RED);
               /* if(ayuda[select]!=null){
                    findViewById(R.id.button_help).setVisibility(View.VISIBLE);
                }*/
                if(test.getChoices().get(select).getAdvise()!=null){
                    findViewById(R.id.button_help).setVisibility(View.VISIBLE);
                }
            }else{
                Toast.makeText(this,
                        "Respuesta correcta",
                        Toast.LENGTH_SHORT);
            }
        }
    }

    //Añado el nuevo metodo que visualizara la ayuda en funcion del tipo que sea
    public void help(View v){

        RadioGroup group = (RadioGroup)findViewById(R.id.test_choices);
        int select= group.getCheckedRadioButtonId();

       //switch(type[select])
       switch (type[select]){
           case "text/html":
               showHtml(test.getChoices().get(select).getAdvise());
               break;
           case "video":
               showVideo(test.getChoices().get(select).getAdvise());
               break;
           case "audio":
               showAudio(test.getChoices().get(select).getAdvise());
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
