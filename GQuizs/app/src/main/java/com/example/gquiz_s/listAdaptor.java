package com.example.gquiz_s;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class listAdaptor extends RecyclerView.Adapter<listAdaptor.ViewHolder>{
    Context context;
    ArrayList<vars> al;
    databasecon d;
    listAdaptor(Context con, ArrayList<vars> a){
        this.context=con;
        this.al=a;
    }
    @NonNull
    @Override
    public listAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.item_on_list,parent,false);
        ViewHolder vh =new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull listAdaptor.ViewHolder holder, int position) {
        holder.head.setText(al.get(position).name);
        holder.no.setText(String.valueOf(al.get(position).no));
        holder.itemid.setText(al.get(position).id);
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
                                String id=((TextView)v.findViewById(R.id.itemid)).getText().toString();
                                d.deletequiz(id);

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

    }

    @Override
    public int getItemCount() {
        return al.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView head,no;
        TextView itemid;
        LinearLayout c;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            head=itemView.findViewById(R.id.headtext);
            no=itemView.findViewById(R.id.NOqus);
            c=itemView.findViewById(R.id.listitem);
            itemid=itemView.findViewById(R.id.itemid);
        }
    }
}
