package com.example.docencia.practica1;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.docencia.practica1.model.Exercise;
import com.example.docencia.practica1.prof.common.RestClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

/**
 * Created by root on 23/12/16.
 */

public class ExerciseActivity extends AppCompatActivity {

    public static final int PICTURE_REQUEST_CODE=1;
    public static final int VIDEO_REQUEST_CODE=2;
    public static final int AUDIO_REQUEST_CODE=3;
    public static final int READ_REQUEST_CODE=4;

    public final static String USUARIO= "com.example.docencia.nombreAuth";
    public final static String PASSWORD = "com.example.docencia.passAuth";

    protected Uri pictureUri;

    Exercise exercise = new Exercise();
    RestClient rest = new RestClient("http://u017633.ehu.eus:28080/ServidorTta/rest/tta");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        getExercise();
    }

    public void getExercise(){
        new AsyncTask<Void,Void,Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                try{
                    String nom="12345678A";
                    String passwd="tta";
                    //rest.setHttpBasicAuth(USUARIO,PASSWORD);
                    rest.setHttpBasicAuth(nom,passwd);
                    //Se solicita los datos del test al servidor
                    JSONObject json = rest.getJSON(String.format("getExercise?id=%d",1));
                    //Se coge el dato del enunciado
                    exercise.setWording(json.getString("wording"));

                    //Se coge las diferentes opciones y los datos necesarios
                    JSONArray array = json.getJSONArray("lessonBean");
                    for(int i=0;i<array.length();i++){
                        JSONObject itemJSON = array.getJSONObject(i);
                        Exercise.lessonBean lessonBean = new Exercise.lessonBean();
                        lessonBean.setId(itemJSON.getInt("id"));
                        lessonBean.setNumber(itemJSON.getInt("number"));
                        lessonBean.setTitle(itemJSON.getString("title"));
                        exercise.getLessonBeanList().add(lessonBean);

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
                TextView text=(TextView)findViewById(R.id.exercise_wording);
                text.setText(exercise.getWording());


                super.onPostExecute(aVoid);
            }
        }.execute();
    }
    public void sendPicture(View view){
        if(!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA))
            Toast.makeText(this,R.string.no_camera,Toast.LENGTH_SHORT).show();
        else{
            Intent intent =new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if(intent.resolveActivity(getPackageManager())!=null){
                File dir= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                try{
                    File file=File.createTempFile("tta",".jpg",dir);
                    pictureUri = Uri.fromFile(file);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT,pictureUri);
                    startActivityForResult(intent, PICTURE_REQUEST_CODE);

                }catch (IOException e){

                }
            }else{
                Toast.makeText(this,R.string.no_app,Toast.LENGTH_SHORT).show();
            }
        }

    }

    public void recordVideo(View view){
        if(!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA))
            Toast.makeText(this,R.string.no_camera,Toast.LENGTH_SHORT).show();
        else{
            Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
            if(intent.resolveActivity(getPackageManager())!=null)
                startActivityForResult(intent,VIDEO_REQUEST_CODE);
            else
                Toast.makeText(this,R.string.no_app,Toast.LENGTH_SHORT).show();
        }
    }

    public void recordAudio(View view){
        if(!getPackageManager().hasSystemFeature(PackageManager.FEATURE_MICROPHONE))
            Toast.makeText(this,R.string.no_micro,Toast.LENGTH_SHORT).show();
        else{
            Intent intent = new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
            if(intent.resolveActivity(getPackageManager())!=null)
                startActivityForResult(intent,AUDIO_REQUEST_CODE);
            else
                Toast.makeText(this,R.string.no_app,Toast.LENGTH_SHORT).show();
        }

    }

    public void sendFile(View view){
        Intent intent =new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        startActivityForResult(intent,READ_REQUEST_CODE);
    }
/*   @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode!= Activity.RESULT_OK)
            return;
        switch(requestCode){
            case READ_REQUEST_CODE:
                break;
            case VIDEO_REQUEST_CODE:
                break;
            case AUDIO_REQUEST_CODE:
                sendFile();
                break;
            case PICTURE_REQUEST_CODE:
                sendFile(pictureUri);
                break;

        }

    }*/

}
