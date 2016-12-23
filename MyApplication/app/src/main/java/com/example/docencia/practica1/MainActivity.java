package com.example.docencia.practica1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;



public class MainActivity extends AppCompatActivity {

    public static String[] usu={"iratxe"};
    public static String[] con={"iratxe"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void login(View view){

        Intent intent=new Intent(this,MenuActivity.class);
        String login=((EditText)findViewById(R.id.login)).getText().toString();
        String passwd=((EditText)findViewById(R.id.passwd)).getText().toString();
        Boolean entrar=false;
        entrar=authenticate(login,passwd);
        if(entrar==true){
            intent.putExtra(MenuActivity.EXTRA_LOGIN,login);
            startActivity(intent);
        }
    }
    public boolean authenticate(String login,String passwd){

        int pos=0,a=0,i;
        boolean resul=false;
        for(i=0;i<usu.length;i++){
            if(login.toString().equalsIgnoreCase(usu[i])){
                a=1;
                pos=i;
            }
        }
        if(a==1){
            if(passwd.toString().equalsIgnoreCase(con[pos])){
                    Toast.makeText(
                            this,
                            "Acceso correcto",
                            Toast.LENGTH_SHORT
                    ).show();
                    resul=true;

            }else{
                Toast.makeText(
                        this,
                        "Acceso incorrecto",
                        Toast.LENGTH_SHORT
                );
                resul= false;
            }
        }
    return resul;

    }

}
