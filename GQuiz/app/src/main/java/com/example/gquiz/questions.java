package com.example.gquiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class questions extends AppCompatActivity {
      int pos=0;
      public int score=0;


    public ArrayList<ques> questionsArray;
    Button nextbtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        Intent get=getIntent();
        String id=get.getStringExtra("id");



        databasecon d = new databasecon(this);
        questionsArray=d.getQuestions(id);
        Log.d("size oƒ files", String.valueOf(questionsArray.size()));
        this.score=0;

        changeQuestion(0);
        nextbtn=findViewById(R.id.nextbtn);
        nextbtn.setEnabled(false);
        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                changeQuestion(1);
                v.setEnabled(false);
                v.setBackgroundResource(R.drawable.nextnot);
            }
        });


    }
    public void changeQuestion(int flag) {
        if (this.pos >= this.questionsArray.size()) {
            Toast.makeText(this, "total score:"+this.score, Toast.LENGTH_SHORT).show();

            Intent i = new Intent(questions.this, result.class);
            i.putExtra("score", String.valueOf(this.score));
            i.putExtra("total",questionsArray.size());

            // Start the new activity
            startActivity(i);

            // Finish the current activity to prevent going back to it
            finish();


        } else {
            if(this.pos==this.questionsArray.size()-1)
                nextbtn.setText("submit");
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            Fragment f = new qFragment();
            Bundle b = new Bundle();
            b.putString("question", questionsArray.get(this.pos).qus);
            b.putString("option1", questionsArray.get(this.pos).op1);
            b.putString("option2", questionsArray.get(this.pos).op2);
            b.putString("option3", questionsArray.get(this.pos).op3);
            b.putString("option4", questionsArray.get(this.pos).op4);
            b.putString("crt",questionsArray.get(this.pos).crt);
            f.setArguments(b);
            if (flag == 0)
                ft.add(R.id.frag1, f);
            else
                ft.replace(R.id.frag1, f);
            ft.commit(); Log.d("key","question"+this.pos);
            this.pos++;




        }
    }


}