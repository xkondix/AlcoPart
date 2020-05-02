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

    public static void insertAlco(SQLiteDatabase db, String name,String type,String brand, String number,int pojemnosc,
                                  String percent, int favorite,int collect, String resourceId)
    {
        ContentValues alcoValues = new ContentValues();
        alcoValues.put("NAME",name);
        alcoValues.put("BRAND",brand);
        alcoValues.put("NUMBER",number);
        alcoValues.put("TYPE",type);
        alcoValues.put("CAPACITY",pojemnosc);
        alcoValues.put("PERCENT",percent);
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
                    + "TYPE TEXT, "
                    + "NUMBER TEXT, "
                    + "CAPACITY INTEGER, "
                    + "PERCENT TEXT, "
                    + "FAVOURITE INTEGER, "
                    + "COLLECT INTEGER, "
                    + "IMAGE_ID TEXT);");

        insertAlco(db,"Heineken 0.0","Piwo","Heineken",
                "5900699102663",500,"0,0",0,0
                ,"heineken_0_500");

        insertAlco(db,"Tyskie Gronie Jasne","Piwo","Tyskie",
                    "5901359000237",500,"5,2",0,0
                    ,"tyskie_gronie_piwo_jasne_500");

        insertAlco(db,"Tyskie Gronie Jasne","Piwo","Tyskie",
                    "5901359000312",330,"5,2",0,0
                    ,"tyskie_gronie_piwo_jasne_330");

        insertAlco(db,"Bacardi Superior","Rum","Bacardi",
                    "5010677014205",700,"37,5",0,0
                    ,"bacardi_superior_700");

        insertAlco(db,"Bacardi Carta Blanca","Rum","Bacardi",
                    "5010677013147",700,"37,5",0,0
                    ,"bacardi_carta_blanca_700");

        insertAlco(db,"Bacardi Carta Negra","Rum","Bacardi",
                    "5010677039093",700,"40,0",0,0
                    ,"bacardi_carta_negra_700ml");

        insertAlco(db,"Bacardi Mojito","Rum","Bacardi",
                    "5010677211079",700,"14,9",0,0
                    ,"bacardi_mohito_700");

        insertAlco(db,"Captain Jack","Piwo","Captain Jack",
                    "5901359084084",400,"6,0",0,0
                    ,"capitan_jack_400");

        insertAlco(db,"Captain Jack","Piwo","Captain Jack",
                    "5901359094243",500,"6,0",0,0
                    ,"captain_jack_500");

        insertAlco(db,"Captain Jack","Piwo","Captain Jack",
                    "5901359094458",400,"6,0",0,0
                    ,"captain_jack_orange_400");

            insertAlco(db,"Żubr Piwo Jasne","Piwo","Żubr",
                    "5901359272146",650,"6,0",0,0
                    ,"zubr_jasne_650");

            insertAlco(db,"Żubrówka Biała","Wódka","Żubrówka",
                    "5900343001892",500,"40,0",0,0
                    ,"zubrowka_biala_500");

            insertAlco(db,"Finlandia","Wódka","Finlandia",
                    "6412709021776",700,"40,0",0,0
                    ,"finlandia_700");

            insertAlco(db,"Finlandia Blackcurant","Wódka","Finlandia",
                    "5099873001899",500,"40,0",0,0
                    ,"finlandia_blackcurant_500");

            insertAlco(db,"Ballantines Finest","Whisky","Ballantines",
                    "5010106113127",700,"40,0",0,0
                    ,"ballentaise_fintes_700");

            insertAlco(db,"Stroh 80","Rum","Stroh",
                    "9001700000025",700,"80,0",0,0
                    ,"stroh_80_700");
        }
    }

}
