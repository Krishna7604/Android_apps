package com.example.gquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class result extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Intent i=getIntent();

         TextView t = findViewById(R.id.scoresss);
        t.setText( i.getStringExtra("score"));
        TextView t1 = findViewById(R.id.total);
        t1.setText( String.valueOf(i.getIntExtra("total",10)));
    }
}