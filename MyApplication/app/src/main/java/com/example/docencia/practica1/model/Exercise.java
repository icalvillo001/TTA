package com.example.docencia.practica1.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aitor on 13/01/17.
 */
public class Exercise {

    public int id;
    public String wording;

    List<lessonBean> lessonBeanList= new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWording() {
        return wording;
    }

    public void setWording(String wording) {
        this.wording = wording;
    }

    public List<lessonBean> getLessonBeanList() {
        return lessonBeanList;
    }

    public void setLessonBeanList(List<lessonBean> lessonBeanList) {
        this.lessonBeanList = lessonBeanList;
    }

    public static class lessonBean{

        int id;
        int number;
        String title;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

}
