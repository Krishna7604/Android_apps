package com.example.gquiz;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class listAdaptor extends RecyclerView.Adapter<listAdaptor.ViewHolder> {
    Context context;
    ArrayList<vars> al;
    private databasecon d;

    listAdaptor(Context con, ArrayList<vars> a){
        this.context=con;
        this.al=a;
    }


    @NonNull
    @Override
    public listAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(context).inflate(R.layout.item_on_list,parent,false);
        ViewHolder vh =new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull listAdaptor.ViewHolder holder, int position) {
        holder.head.setText(al.get(position).name);
        holder.no.setText(String.valueOf(al.get(position).no));
        holder.itemid.setText(al.get(position).id);
        holder.isPosted=al.get(position).isPosted;

        holder.hid=al.get(position).id;
        if(holder.isPosted==1){
            holder.post.setEnabled(false);

            holder.post.setText("posted");
        }
        holder.c.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(context)
                        .setTitle("delete quiz")
                        .setMessage("do you want to delete this quiz")
                        .setIcon(R.drawable.baseline_delete_24)
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                al.remove(position);
                                d = new databasecon(context);
                                d.deletequiz(holder.hid);

                                notifyItemRemoved(position);
                            }
                        })
                        .setNegativeButton("no", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                builder.show();
                return true;
            }
        });

        holder.c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("hello",((TextView)v.findViewById(R.id.headtext)).getText().toString());
                String id=((TextView)v.findViewById(R.id.itemid)).getText().toString();


                Intent i =new Intent(context,questions.class);
                i.putExtra("id",id);


                context.startActivity(i);


            }
        });
        holder.post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.isPosted==0) {
                    holder.post.setEnabled(false);
                    holder.post.setText("posted");
                    al.get(position).isPosted=1;

                    d = new databasecon(context);
                    d.updatePost(holder.hid);
                    vars var = d.getQuizDetailsById(holder.hid);

                    ArrayList<ques> quizques = d.getQuestions(holder.hid);

                    fbquiz f = new fbquiz(var.id, var.name, var.no, quizques);
                    DatabaseReference refers = FirebaseDatabase.getInstance().getReference("quizzes");
                    String key = refers.push().getKey();
                    refers.child(key).setValue(f);
                }else{
                    Toast.makeText(context,"quiz already posted",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return al.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView head,no;
        TextView itemid;
        LinearLayout c;
        Button post;
        String hid;
        int isPosted;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            head=itemView.findViewById(R.id.headtext);
            no=itemView.findViewById(R.id.NOqus);
            c=itemView.findViewById(R.id.listitem);
            itemid=itemView.findViewById(R.id.itemid);
            post=itemView.findViewById(R.id.publish);


        }
    }
}
