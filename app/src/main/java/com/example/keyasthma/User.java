package com.example.keyasthma;

public class User {
    public String userName;
    public String userPassword;
    public String email;
    public String confPass;

    public User(){

    }
    public User (String userName,String userPassword,String email,String confPass){
        this.userName=userName;
        this.userPassword=userPassword;
        this.email=email;
        this.confPass=confPass;
    }

}
