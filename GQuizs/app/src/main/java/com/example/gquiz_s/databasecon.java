package com.example.gquiz_s;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class databasecon extends SQLiteOpenHelper {
    private static final String dbname="quiz-db";
    private static final int version=1;
    private static final String t1="quizzes";
    private static final String id="id";
    private static final String qname="qname";
    private static final String num="num";


    private static final String t2="questions";
    private static final String qus="question";
    private static final String op1="option1";
    private static final String op2="option2";
    private static final String op3="option3";
    private static final String op4="option4";
    private static final String crt="correct";
    private static databasecon instance;

    public static synchronized databasecon getInstance(Context context) {
        if (instance == null) {
            instance = new databasecon(context.getApplicationContext());
        }
        return instance;
    }

    public databasecon( Context context) {
        super(context, dbname, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+t1+" ("+id+" text primary key," +
                qname+" text," +
                num+" integer)");
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
    public void addListDetails(String Id,String name,int nums){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(id,Id);
        values.put(qname,name);
        values.put(num,nums);

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

            a.add(new vars(c.getString(0),c.getString(1),c.getInt(2)));
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
    public boolean isListDetailExists( String Id) {
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + t1 + " WHERE " + id + " = ?", new String[]{Id});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }
    public void deletequiz(String gid){
        SQLiteDatabase db=getWritableDatabase();
        db.delete(t1,id+" = "+gid,null);
        db.delete(t2,id+" = "+gid,null);

    }
}
