package com.example.gquiz;

import static com.androidnetworking.AndroidNetworking.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.StringRequestListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.app.Dialog;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    RecyclerView rv;
    FloatingActionButton fbtn;
    Button genbtn;
    ArrayList<vars> alist;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String url="https://quiz-generator-jibtnlmtja-uc.a.run.app/";
        //String s="[{\"question\":\"What is the scientific name for a cat?\",\"responses\":[\"Feliscatus\",\"Canislupusfamiliaris\",\"Pantheraleo\",\"Ursusarctos\"],\"correct\":\"Feliscatus\"},{\"question\":\"Whatistheaveragelifespanofanindoorcat?\",\"responses\":[\"10-15years\",\"5-10years\",\"15-20years\",\"20-25years\"],\"correct\":\"10-15years\"},{\"question\":\"Whatisthenameforagroupofcats?\",\"responses\":[\"Apride\",\"Apack\",\"Aherd\",\"Aclowder\"],\"correct\":\"Aclowder\"},{\"question\":\"Whatisthetopspeedofacat?\",\"responses\":[\"20mph\",\"30mph\",\"40mph\",\"50mph\"],\"correct\":\"30mph\"},{\"question\":\"Whatpercentageofcatsareoverweightorobese?\",\"responses\":[\"25%\",\"35%\",\"45%\",\"55%\"],\"correct\":\"35%\"}]";
        databasecon d=new databasecon(MainActivity.this);

        rv=findViewById(R.id.listOfQuiZs);
        rv.setLayoutManager(new LinearLayoutManager(this));
        alist=d.getQuizzesList();

        listAdaptor adaptor=new listAdaptor(MainActivity.this,alist);
        rv.setAdapter(adaptor);
        fbtn=findViewById(R.id.floatbtn);
        fbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dia=new Dialog(MainActivity.this);
                dia.setContentView(R.layout.create_quiz);
                Spinner spin=dia.findViewById(R.id.spin);
                ArrayList<String> a=new ArrayList<>();
                a.add("easy");a.add("intermediate");a.add("hard");
                ArrayAdapter<String> adap=new ArrayAdapter<>(dia.getContext(), android.R.layout.simple_spinner_dropdown_item,a );
                spin.setAdapter(adap);
                Button genbtn=dia.findViewById(R.id.genbtn);
                genbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        genbtn.setEnabled(false);
                        String name= ((EditText) dia.findViewById(R.id.Qname)).getText().toString();
                        String num= ((EditText) dia.findViewById(R.id.Qnum)).getText().toString();

                        String diff= a.get(((Spinner) dia.findViewById(R.id.spin)).getSelectedItemPosition());

                        String i=String.format("%04d",new Random().nextInt(9000)+1000);
                        Log.d("data","name: "+name+"\n num: "+num+"\n diff: "+diff+"\n i: "+i);

                        if(!name.equals("") && !num.equals("")){



                            d.addListDetails(i,name,Integer.parseInt(num),diff,0);
                            AndroidNetworking.initialize(MainActivity.this);






                            AndroidNetworking.get(url)
                                    .addQueryParameter("topic",name)
                                    .addQueryParameter("num_q",num)
                                    .addQueryParameter("diff",diff)
                            .setPriority(Priority.LOW).build().getAsJSONArray(new JSONArrayRequestListener() {
                                @Override
                                public void onResponse(JSONArray r) {
                                    Log.d("response",r.toString());
                                    Toast.makeText(MainActivity.this,"respnse"+r.toString(),Toast.LENGTH_SHORT).show();


                                    try {
                                        JSONObject obj;
                                        for (int z=0;z<r.length();z++) {
                                            obj = r.getJSONObject(z);
                                            JSONArray arr = obj.getJSONArray("responses");
//
                                            try {
                                                d.addQuestions(i, obj.getString("question"), arr.getString(0), arr.getString(1), arr.getString(2), arr.getString(3), obj.getString("correct"));
                                            }catch (Exception e){

                                               d.addQuestions(i, obj.getString("question"), arr.getString(0), arr.getString(1), arr.getString(2), arr.getString(3), arr.getString(obj.getInt("correct")));

                                            }
                                        }

                                    } catch (JSONException e) {
                                        throw new RuntimeException(e);
                                    } finally {

                                    }
                                }

                                @Override
                                public void onError(ANError anError) {
                                    Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();

                                }
                            });
//
                          alist.add(new vars(i,name,Integer.parseInt(num),0));
                          adaptor.notifyItemInserted(alist.size()-1);
                          rv.scrollToPosition(alist.size()-1);

                          dia.dismiss();


                        }else{
                            Toast.makeText(MainActivity.this,"fill all the details",Toast.LENGTH_SHORT).show();
                        }


                    }
                });
                dia.show();
            }
        });










    }
}