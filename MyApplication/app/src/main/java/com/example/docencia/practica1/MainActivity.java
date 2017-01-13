package com.example.docencia.practica1;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


public class MainActivity extends AppCompatActivity {

    //Se define la direccion del servidor
    RestClient rest = new RestClient("http://u017633.ehu.eus:28080/ServidorTta/rest/tta");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //Se hace el login cuando se pulsa el boton
    public void login(View view) throws IOException, JSONException{

        //Se coge los datos que introduce el usuario
        final String login=((EditText)findViewById(R.id.login)).getText().toString();
        final String passwd=((EditText)findViewById(R.id.passwd)).getText().toString();

        //Se define un objeto user que cogera los datos del usuario
        final User user = new User();

        //Se crea un nuevo hilo para la autenticacion contra el servidor y coger los datos de usuario
        new AsyncTask<Void,Void,Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    //Se hace la autenticacion con el servidor
                    rest.setHttpBasicAuth(login,passwd);
                    //Se coge los datos del usuario
                    JSONObject json = rest.getJSON(String.format("getStatus?dni=%s", login));
                    user.setUser(json.getString("user"));
                }catch (IOException e){
                    e.printStackTrace();
                }catch (JSONException e1){
                    e1.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {

                //Con los datos de usuario se hace la nueva actividad y se pasa el dato necesario
                Intent intent=new Intent(MainActivity.this,MenuActivity.class);
                intent.putExtra(MenuActivity.EXTRA_LOGIN,user.getUser());
                startActivity(intent);
                super.onPostExecute(aVoid);
            }
        }.execute();

    }

}
