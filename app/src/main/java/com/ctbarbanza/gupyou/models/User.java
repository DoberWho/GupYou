package com.ctbarbanza.gupyou.models;

import androidx.annotation.NonNull;

public class User {


    public String uid;

    public String nick;
    public String name;
    public String img;
    public String facebook;
    public String google;
    public String twitter;
    public String instagram;


    @Override
    public String toString() {
        return ""+uid+"|"+nick+"|"+name+"|"+img;
    }
}
