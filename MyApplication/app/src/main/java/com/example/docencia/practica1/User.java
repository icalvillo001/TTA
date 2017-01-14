package com.example.docencia.practica1;

/**
 * Created by aitor on 13/01/17.
 */
public class User {

    public int id;
    public String user;
    public int lessonNumber;
    public String lessonTitle;
    public int nextTest;
    public int nextExercise;

    public void setId(int id){
        this.id=id;
    }

    public void setUser(String user){
        this.user=user;
    }

    public String getUser(){
        return user;
    }



}
