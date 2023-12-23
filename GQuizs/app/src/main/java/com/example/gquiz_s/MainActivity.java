package com.example.gquiz_s;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databasecon d=new databasecon(MainActivity.this);

        rv=findViewById(R.id.listOfQuiZs);
        rv.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<vars> alist = d.getQuizzesList();
        Log.d("list", String.valueOf(alist.size()));
        listAdaptor adaptor=new listAdaptor(MainActivity.this,alist);
        rv.setAdapter(adaptor);
        DatabaseReference quizzesRef = FirebaseDatabase.getInstance().getReference("quizzes");

        quizzesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot quizSnapshot : dataSnapshot.getChildren()) {
                    // Assuming fbquiz has a constructor that matches the data structure in the database
                    fbquiz quiz = quizSnapshot.getValue(fbquiz.class);
                    //databasecon d =new databasecon(MainActivity.this);
                   // d.addListDetails(quiz.getId(),quiz.getName(),quiz.num_q);// Now you can access properties of the quiz object
                    if(!d.isListDetailExists(quiz.getId())) {
                        alist.add(new vars(quiz.getId(), quiz.getName(), quiz.getNum_q()));
                        adaptor.notifyItemInserted(alist.size()-1);
                        rv.scrollToPosition(alist.size()-1);
                        d.addListDetails(quiz.getId(), quiz.getName(), quiz.getNum_q());

                        ArrayList<ques> quizQuestions = quiz.getQuestions();
                        for (int i = 0; i < quizQuestions.size(); i++) {
                            d.addQuestions(quiz.getId(), quizQuestions.get(i).getQus(), quizQuestions.get(i).getOp1(), quizQuestions.get(i).getOp2(), quizQuestions.get(i).getOp3(), quizQuestions.get(i).getOp4(), quizQuestions.get(i).getCrt());

                        }
                    }

                    // Do something with the retrieved dat
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle errors
                Log.e("Firebase", "Error: " + databaseError.getMessage());
            }
        });



    }
}