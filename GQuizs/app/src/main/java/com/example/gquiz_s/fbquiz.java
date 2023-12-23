package com.example.gquiz_s;

import java.util.ArrayList;

public class fbquiz {
    String id,name;
    int num_q;
    ArrayList<ques> questions;
    fbquiz(String id, String name, int num_q, ArrayList<ques> qus){
        this.id=id;
        this.name=name;
        this.num_q=num_q;
        this.questions=qus;

    }
    fbquiz(){}

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNum_q(int num_q) {
        this.num_q = num_q;
    }

    public void setQuestions(ArrayList<ques> questions) {
        this.questions = questions;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getNum_q() {
        return num_q;
    }

    public ArrayList<ques> getQuestions() {
        return questions;
    }
}
