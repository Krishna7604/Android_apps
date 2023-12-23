package com.example.gquiz;

public class ques {
    String qus,op1,op2,op3,op4,crt;
    public ques(String qus,String op1,String op2,String op3,String op4,String crt){
        this.qus=qus;
        this.op1=op1;
        this.op2=op2;
        this.op3=op3;
        this.op4=op4;
        this.crt=crt;

    }

    public String getQus() {
        return qus;
    }

    public String getOp1() {
        return op1;
    }

    public String getOp2() {
        return op2;
    }

    public String getOp3() {
        return op3;
    }

    public String getOp4() {
        return op4;
    }

    public String getCrt() {
        return crt;
    }
}
