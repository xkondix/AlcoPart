package com.konradkowalczyk.alcopart.fragments.alco;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class AlcomatItem {

    private String procenty,ml;
    private GregorianCalendar data;

    public AlcomatItem(String procenty, String ml, GregorianCalendar data)
    {
        this.data=data;
        this.procenty=procenty;
        this.ml=ml;
    }

    public GregorianCalendar getGregorian()
    {
        return data;
    }

    public void  setGregorian(GregorianCalendar data)
    {
        this.data=data;
    }

    public String getMl()
    {
        return ml;
    }

    public void setMl(String ml)
    {
        this.ml=ml;
    }

    public String geProcent()
    {
        return procenty;
    }

    public void setProcent(String procenty)
    {
        this.procenty=procenty;
    }

    public String getData()
    {
        return data.get(Calendar.DAY_OF_MONTH) +"/"
                +(data.get(Calendar.MONTH)+1) +"/"+ data.get(Calendar.YEAR);
    }

    public String getTime()
    {
        return data.get(Calendar.HOUR)+":"+data.get(Calendar.MINUTE)
                +" "+(Integer.valueOf(data.get(Calendar.AM_PM)) == 0 ? "am" : "pm");

    }

}
