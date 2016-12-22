package com.example.docencia.practica1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void login(View view){

        Intent intent=new Intent(this,MenuActivity.class);
        String login=((EditText)findViewById(R.id.login)).getText().toString();
        String passwd=((EditText)findViewById(R.id.passwd)).getText().toString();
        if(authenticate(login,passwd)){
            intent.putExtra(MenuActivity.EXTRA_LOGIN,login);
            startActivity(intent);
        }
    }
    public boolean authenticate(String login,String passwd){
        //En este apartado se compararia el usuario introducido con los usuarios y contraseñas
        //guardadas en una base de datos o en local. Como todavia no se ha implementado
        //la funcion devuelve true. De esta forma, pasaran todos los usuarios.
        return true;
    }

}
