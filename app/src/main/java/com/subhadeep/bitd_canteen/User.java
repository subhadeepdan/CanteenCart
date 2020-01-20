package com.subhadeep.bitd_canteen;

public class User {

    String password;
    String usertype;
    String username;

    public User() {
        //Empty Constructor For Firebase
    }


    public User(String username, String password, String usertype)
    {
        this.username = username; //Parameterized for Program-Inhouse objects.
        this.password = password;
        this.usertype = usertype;
    }

    //Getters and Setters
    public String getUsername()
    {
        return username;
    }
    public void setUsername(String username)
    {
        this.username = username;
    }
    public String getPassword()
    {
        return password;
    }
    public void setPassword(String password)
    {
        this.password = password;
    }
    public String getUsertype()
    {
        return usertype;
    }
    public void setUsertype(String username)
    {
        this.usertype = usertype;
    }
}