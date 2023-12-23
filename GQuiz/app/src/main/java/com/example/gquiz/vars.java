package com.example.gquiz;

public class vars {
    String id,name;
    int no;
    int isPosted=0;
    vars(String id,String name,int no,int isposted){
        this.id=id;
        this.name=name;
        this.no=no;
        this.isPosted=isposted;
    }

    vars(String id,String name,int no){
        this.id=id;
        this.name=name;
        this.no=no;
    }
}
