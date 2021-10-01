package com.example.bestmoviescenes;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FavouriteDB extends SQLiteOpenHelper {

    private static String DATABASE_NAME = "MoviesDB";
    private static String TABLE_NAME = "MovieListTable";
    public static  String KEY_ID = "id";
    public static String MOVIE_TITLE = "movieTitle";
    public static String MOVIE_AUDIO_TEXT = "movieAudioText";
    public static String MOVIE_IMAGE = "movieImage";
    public static String FAVAOURITE_STATUS = "favouriteStatus";

    private  static String CREATE_TABLE = "CREATE TABLE" + TABLE_NAME + "(" + KEY_ID + "TEXT," + MOVIE_TITLE + "TEXT," + MOVIE_AUDIO_TEXT + "TEXT," + MOVIE_IMAGE + "TEXT," + FAVAOURITE_STATUS+ "TEXT";

    public FavouriteDB(Context context){
        super(context,DATABASE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
    sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void createTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        for(int x =1; x<6; x++){
            cv.put(KEY_ID,x);
            cv.put(FAVAOURITE_STATUS, "0");
            db.insert(TABLE_NAME, null,cv);
        }
    }

}
