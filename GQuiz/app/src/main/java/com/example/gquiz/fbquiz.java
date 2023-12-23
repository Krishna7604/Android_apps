package com.example.gquiz;

import java.util.ArrayList;

public class fbquiz {
    String id,name;
    int num_q;
    ArrayList<ques> questions;
    fbquiz(String id, String name,int num_q,ArrayList<ques> qus){
        this.id=id;
        this.name=name;
        this.num_q=num_q;
        this.questions=qus;

    }
    fbquiz(){}

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
