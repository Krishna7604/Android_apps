package com.example.gquiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import org.checkerframework.checker.units.qual.C;

import java.util.ArrayList;

public class databasecon extends SQLiteOpenHelper {
    private static final String dbname="quizdb";
    private static final int version=1;
    private static final String t1="quizzes";
    private static final String id="id";
    private static final String qname="qname";
    private static final String num="num";
    private static final String level="level";

    private static final String t2="questions";
    private static final String qus="question";
    private static final String op1="option1";
    private static final String op2="option2";
    private static final String op3="option3";
    private static final String op4="option4";
    private static final String crt="correct";
    private static final String isp ="isPosted";

    public databasecon( Context context) {
        super(context, dbname, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+t1+" ("+id+" text primary key," +
                qname+" text," +
                num+" integer," +
                level+" text," +
                isp+" integer)");
       db.execSQL("create table "+t2+" (" +
               id+" text ," +
               qus+" text," +
               op1+" text," +
               op2+" text," +
               op3+" text," +
               op4+" text," +
               crt+" text)");



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {


    }
    public void addListDetails(String Id,String name,int nums,String diff,int isPosted){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(id,Id);
        values.put(qname,name);
        values.put(num,nums);
        values.put(level,diff);
        values.put(isp,isPosted);
        db.insert(t1,null,values);

    }

    public void addQuestions(String Id, String question, String responses, String responses1, String responses2, String responses3, String correct) {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(id,Id);
        values.put(qus,question);
        values.put(op1,responses);
        values.put(op2,responses1);
        values.put(op3,responses2);
        values.put(op4,responses3);
        values.put(crt,correct);
        db.insert(t2,null,values);

    }
    public ArrayList<vars> getQuizzesList(){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor c=db.rawQuery("select * from "+t1,null);
        ArrayList<vars> a=new ArrayList<>();
        while(c.moveToNext()){

            a.add(new vars(c.getString(0),c.getString(1),c.getInt(2),c.getInt(4)));
        }
        return a;
    }


    public ArrayList<ques> getQuestions(String getid) {
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor c=db.rawQuery("select * from "+t2+" where "+id+" = "+getid,null);
        ArrayList<ques> a=new ArrayList<>();
        while(c.moveToNext()){

            a.add(new ques(c.getString(1),c.getString(2),c.getString(3),c.getString(4),c.getString(5),c.getString(6)));

        }
        return a;
    }
    public vars getQuizDetailsById(String gid){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor c =db.rawQuery("select * from "+t1+" where "+id+" = "+gid,null);
        vars v=null;
        while(c.moveToNext()){
            v=new vars(c.getString(0),c.getString(1),Integer.parseInt(c.getString(2)));
        }
        return v;
    }

    public void updatePost(String gid) {
        SQLiteDatabase db=getWritableDatabase();
        ContentValues v =new ContentValues();
        v.put(isp,1);
        db.update(t1,v,id+" = "+gid,null);

    }
    public void deletequiz(String gid){
        SQLiteDatabase db=getWritableDatabase();
        db.delete(t1,id+" = "+gid,null);
        db.delete(t2,id+" = "+gid,null);

    }
}
