package com.example.ts;
 
public class Noti {
   
    int _id;
    String msg;
     
    public Noti(){
         
    }
   
    public Noti(String msg){
       this.msg=msg;
    }
     
    public int getID(){
        return this._id;
    }
    public void setID(int id){
        this._id = id;
    }
    public String getNoti(){
        return this.msg;
    }
     
    public void setNoti(String msg){
        this.msg = msg;
    }
}