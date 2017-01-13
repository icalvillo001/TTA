package com.example.docencia.practica1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


public class MainActivity extends AppCompatActivity {

    public static String[] usu={"12345678A"};
    public static String[] con={"tta"};

    RestClient rest = new RestClient("http://u017633.ehu.eus:28080/ServidorTta/rest/tta");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void login(View view) throws IOException, JSONException{

        Intent intent=new Intent(this,MenuActivity.class);
        String login=((EditText)findViewById(R.id.login)).getText().toString();
        String passwd=((EditText)findViewById(R.id.passwd)).getText().toString();
        Boolean entrar=false;
        entrar=authenticate(login,passwd);
        if(entrar==true){
            User user=new User();
            user=getDatosUsuario(login);
            intent.putExtra(MenuActivity.EXTRA_LOGIN,user.getUser());
            startActivity(intent);
        }
    }
    public User getDatosUsuario(final String login) throws IOException,JSONException{
        final User user = new User();
        new Thread(new Runnable() {

            @Override
            public void run(){
               try{
                   JSONObject json=rest.getJSON(String.format("getStatus?dni=%s",login));

                   user.setUser(json.getString("user"));

               }catch (IOException e){
                   e.printStackTrace();
               }catch (JSONException e1){
                   e1.printStackTrace();
               }

            }

            }).start();
        return user;


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
