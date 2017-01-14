package com.example.docencia.practica1;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aitor on 13/01/17.
 */
public class Test{

    public String wording;

    public List<Choice> choices = new ArrayList<>();

    public String getWording(){
        return wording;
    }

    public void setWording(String w){
        this.wording=w;
    }

    public List<Choice> getChoices() {
        return choices;
    }

    public void setChoices(List<Choice> choices) {
        this.choices = choices;
    }

    static class Choice{

        public int id;
        public String advise;
        public String answer;
        public Boolean correct;
        public String mime;

        public List<resourceType> resourceTypes = new ArrayList<>();

        public int getId(){
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getAdvise() {
            return advise;
        }

        public void setAdvise(String advise) {
            this.advise = advise;
        }

        public String getAnswer() {
            return answer;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }

        public Boolean getCorrect() {
            return correct;
        }

        public void setCorrect(Boolean correct) {
            this.correct = correct;
        }

        public String getMime() {
            return mime;
        }

        public void setMime(String mime) {
            this.mime = mime;
        }

        public List<resourceType> getResourceTypes() {
            return resourceTypes;
        }

        public void setResourceTypes(List<resourceType> resourceTypes) {
            this.resourceTypes = resourceTypes;
        }

        static class resourceType{
            int id;
            String description;
            String mime;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getMime() {
                return mime;
            }

            public void setMime(String mime) {
                this.mime = mime;
            }
        }
    }

}
