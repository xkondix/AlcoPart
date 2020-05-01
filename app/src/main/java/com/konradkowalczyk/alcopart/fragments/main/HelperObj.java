package com.konradkowalczyk.alcopart.fragments.main;

import com.google.firebase.Timestamp;

public class HelperObj
{
    private String recenzja,nick,nazwa;
    private float ocena;
    private int id,jpg;
    private Timestamp data;

    public HelperObj(String recenzja, String nick, int jpg, Timestamp  data, float ocena, int id)
    {
        this.recenzja=recenzja;
        this.nazwa=nazwa;
        this.nick=nick;
        this.jpg=jpg;
        this.data=data;
        this.ocena=ocena;
        this.id=id;
    }

    public HelperObj(){};

    public String getRecenzja()
    {
        return recenzja;
    }

    public void setRecenzja(String recenzja)
    {
        this.recenzja=recenzja;
    }

    public String getNazwa()
    {
        return nazwa;
    }

    public void setNazwa(String nazwa)
    {
        this.nazwa=nazwa;
    }

    public Timestamp  getData()
    {
        return data;
    }

    public void setData(Timestamp data)
    {
        this.data=data;
    }

    public String getNick()
    {
        return nick;
    }

    public void setNick(String nick)
    {
        this.nick=nick;
    }

    public int getJpg()
    {
        return jpg;
    }

    public void setJpg(int jpg)
    {
        this.jpg=jpg;
    }

    public float getOcena()
    {
        return ocena;
    }

    public void setOcena(float ocena)
    {
        this.ocena=ocena;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id=id;
    }

}
