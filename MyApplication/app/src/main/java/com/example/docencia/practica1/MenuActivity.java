package com.example.docencia.practica1;

/**
 * Created by root on 22/12/16.
 */
import android.content.Intent;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MenuActivity extends AppCompatActivity {
    //Se define variable para rellenar la parte de arriba de la pantalla con el nombre introducido por el usuario
    public final static String EXTRA_LOGIN = "com.example.docencia.login";
    String nombre;
    String pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Intent intent=getIntent();
        TextView textLogin=(TextView)findViewById(R.id.menu_login);
        textLogin.setText(intent.getStringExtra(EXTRA_LOGIN));
        datosLogin();

    }
    public void datosLogin(){
        Intent intent = getIntent();
        Bundle extras= intent.getExtras();
        nombre = extras.getString("nombre");
        pass= extras.getString("pass");

    }
    public void test(View v){
        Intent intent=new Intent(this,TestActivity.class);
        intent.putExtra("nombre",nombre);
        intent.putExtra("pass",pass);
        startActivity(intent);
    }

    public void exercise(View v){
        Intent intent=new Intent(this,ExerciseActivity.class);
        intent.putExtra("nombre",nombre);
        intent.putExtra("pass",pass);
        startActivity(intent);
    }

}
