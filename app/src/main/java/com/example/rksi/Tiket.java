package com.example.rksi;

public class Tiket {
    public String name;
    public String description;
    public String email_user;
    public String doing_by;
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getDescription(){
        return description;
    }
    public void setDescription(String description){
        this.description = description;
    }
    public String getEmail_user(){
        return email_user;
    }
    public void setEmail_user(String email_user){
        this.email_user = email_user;
    }
    public String getDoing_by(){
        return doing_by;
    }
    public void setDoing_by(String doingBy){
        this.doing_by = doingBy;
    }
}
