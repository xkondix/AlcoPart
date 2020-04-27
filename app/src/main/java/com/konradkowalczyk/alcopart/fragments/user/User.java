package com.konradkowalczyk.alcopart.fragments.user;

import java.util.HashMap;
import java.util.Map;

public class User {
    public static boolean  iflog = false;
    private String id;
    private String name;
    public static String email;
    public static String password;
    private Map<String,String[]> comment;


    public User() {
    }

    public User(String id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        comment = new HashMap<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void addToMap(String idAlco,String[] value)
    {
        this.comment.put(idAlco,value);
    }

}