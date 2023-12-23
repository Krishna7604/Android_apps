package com.example.gquiz_s;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class qFragment extends Fragment {

    Button op1,op2,op3,op4;
    String crt;
    TextView qus;
    View context;

    public qFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        context=inflater.inflate(R.layout.fragment_question, container, false);


        op1=context.findViewById(R.id.option1);
        op2=context.findViewById(R.id.option2);
        op3=context.findViewById(R.id.option3);
        op4=context.findViewById(R.id.option4);
        qus=context.findViewById(R.id.question);
        Bundle b=getArguments();

        qus.setText(b.getString("question"));
        op1.setText(b.getString("option1"));
        op2.setText(b.getString("option2"));
        op3.setText(b.getString("option3"));
        op4.setText(b.getString("option4"));
        crt=b.getString("crt");
        op1.setOnClickListener(this::onClick);
        op2.setOnClickListener(this::onClick);
        op3.setOnClickListener(this::onClick);
        op4.setOnClickListener(this::onClick);

        return context;
    }



    public void onClick(View v){
        Button clicked =context.findViewById(v.getId());
        if (clicked.getText().toString().equals(crt)) {
            clicked.setBackground(getContext().getDrawable(R.drawable.crtans));
            ((questions) getActivity()).score++;
            //Toast.makeText(getContext(), " "+i, Toast.LENGTH_SHORT).show();
        }
        else
            clicked.setBackground(getContext().getDrawable(R.drawable.wrgans));

        op1.setEnabled(false);
        op2.setEnabled(false);
        op3.setEnabled(false);
        op4.setEnabled(false);
        ( (questions) getActivity()).nextbtn.setEnabled(true);
        ( (questions) getActivity()).nextbtn.setBackgroundResource(R.drawable.nextbtn);

        Toast.makeText(getContext(), crt, Toast.LENGTH_SHORT).show();


    }

}