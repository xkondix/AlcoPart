package com.konradkowalczyk.alcopart;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AlcoDatabaseHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "alcoData";
    public static final int DB_VERSION = 1;

    public AlcoDatabaseHelper(Context context)
    {
        super(context,DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        updateMyDatabase(db,0,DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        updateMyDatabase(db,oldVersion,newVersion);

    }

    public static void insertAlco(SQLiteDatabase db, String name,int type,String brand, String number,int pojemnosc,
                                  int favorite,int collect, int resourceId)
    {
        ContentValues alcoValues = new ContentValues();
        alcoValues.put("NAME",name);
        alcoValues.put("BRAND",brand);
        alcoValues.put("NUMBER",number);
        alcoValues.put("TYPE",type);
        alcoValues.put("CAPACITY",pojemnosc);
        alcoValues.put("FAVOURITE",favorite);
        alcoValues.put("COLLECT",collect);
        alcoValues.put("IMAGE_ID",resourceId);
        db.insert("ALCOHOL",null,alcoValues);
    }


    public void updateMyDatabase(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        if(oldVersion < 1)
        {
            db.execSQL("CREATE TABLE ALCOHOL(_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "NAME TEXT, "
                    + "BRAND TEXT, "
                    + "TYPE INTEGER, "
                    + "NUMBER TEXT, "
                    + "CAPACITY INTEGER, "
                    + "FAVOURITE INTEGER, "
                    + "COLLECT INTEGER, "
                    + "IMAGE_ID INTEGER);");

        insertAlco(db,"Heineken 0.0",R.string.piwo,"Heineken",
                "5900699102663",500,0,0
                ,R.drawable.heineken_0_500);

        }
    }

}
