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
                                  String percent, int favorite,int collect, int resourceId)
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
                    + "TYPE INTEGER, "
                    + "NUMBER TEXT, "
                    + "CAPACITY INTEGER, "
                    + "PERCENT TEXT, "
                    + "FAVOURITE INTEGER, "
                    + "COLLECT INTEGER, "
                    + "IMAGE_ID INTEGER);");

        insertAlco(db,"Heineken 0.0",R.string.piwo,"Heineken",
                "5900699102663",500,"0,0",0,0
                ,R.drawable.heineken_0_500);

        insertAlco(db,"Tyskie Gronie Jasne",R.string.piwo,"Tyskie",
                    "5901359000237",500,"5,2",0,0
                    ,R.drawable.tyskie_gronie_piwo_jasne_500);

        insertAlco(db,"Tyskie Gronie Jasne",R.string.piwo,"Tyskie",
                    "5901359000312",330,"5,2",0,0
                    ,R.drawable.tyskie_gronie_piwo_jasne_330);

        insertAlco(db,"Bacardi Superior",R.string.rum,"Bacardi",
                    "5010677014205",700,"37,5",0,0
                    ,R.drawable.bacardi_superior_700);

        insertAlco(db,"Bacardi Carta Blanca",R.string.rum,"Bacardi",
                    "5010677013147",700,"37,5",0,0
                    ,R.drawable.bacardi_carta_blanca_700);

        insertAlco(db,"Bacardi Carta Negra",R.string.rum,"Bacardi",
                    "5010677039093",700,"40,0",0,0
                    ,R.drawable.bacardi_carta_negra_700ml);

        insertAlco(db,"Bacardi Mojito",R.string.rum,"Bacardi",
                    "5010677211079",700,"14,9",0,0
                    ,R.drawable.bacardi_mohito_700);

        insertAlco(db,"Captain Jack",R.string.piwo,"Captain Jack",
                    "5901359084084",400,"6,0",0,0
                    ,R.drawable.capitan_jack_400);

        insertAlco(db,"Captain Jack",R.string.piwo,"Captain Jack",
                    "5901359094243",500,"6,0",0,0
                    ,R.drawable.captain_jack_500);

        insertAlco(db,"Captain Jack",R.string.piwo,"Captain Jack",
                    "5901359094458",400,"6,0",0,0
                    ,R.drawable.captain_jack_orange_400);

            insertAlco(db,"Żubr Piwo Jasne",R.string.piwo,"Żubr",
                    "5901359272146",650,"6,0",0,0
                    ,R.drawable.zubr_jasne_650);

            insertAlco(db,"Żubrówka Biała",R.string.wódka,"Żubrówka",
                    "5900343001892",500,"40,0",0,0
                    ,R.drawable.zubrowka_biala_500);

            insertAlco(db,"Finlandia",R.string.wódka,"Finlandia",
                    "6412709021776",700,"40,0",0,0
                    ,R.drawable.finlandia_700);

            insertAlco(db,"Finlandia Blackcurant",R.string.wódka,"Finlandia",
                    "5099873001899",500,"40,0",0,0
                    ,R.drawable.finlandia_blackcurant_500);

            insertAlco(db,"Ballantines Finest",R.string.whisky,"Ballantines",
                    "5010106113127",700,"40,0",0,0
                    ,R.drawable.ballentaise_fintes_700);

            insertAlco(db,"Stroh 80",R.string.rum,"Stroh",
                    "9001700000025",700,"80,0",0,0
                    ,R.drawable.stroh_80_700);
        }
    }

}
