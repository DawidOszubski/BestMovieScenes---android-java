package com.example.bestmoviescenes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.ListView;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MovieDBHandler extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "Movies";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "Movies";
    private static final String COLUMN_ID = "_id";
    private static final String MOVIE_TITLE = "movie_title";
    private static final String ICON_IMG = "icon_image";
    private static final String QUOTE = "movie_quote";
    private static final String MAIN_WORDS_QUOTE = "main_words_quote";
    private static final String ISFAVOURITE ="is_favourite";
    private static final String FAVOURITE_IMG = "favourite_icon";

    public MovieDBHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MOVIE_TITLE + " TEXT, "+
                ICON_IMG + " INTEGER, "+
                QUOTE + " TEXT UNIQUE, "+
                MAIN_WORDS_QUOTE + " TEXT,"+
                ISFAVOURITE + " BOOLEAN,"+
                FAVOURITE_IMG + " INTEGER);";

        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

    public void addHandler(MovieDb movieDb) {
        ContentValues values = new ContentValues();
        values.put(MOVIE_TITLE, movieDb.getMovieTitle());
        values.put(ICON_IMG, movieDb.getVideoIcon());
        values.put(QUOTE, movieDb.getVideoText());
        values.put(MAIN_WORDS_QUOTE, movieDb.getVideoMainWords());
        values.put(ISFAVOURITE, movieDb.isFavourite());
        values.put(FAVOURITE_IMG, movieDb.getFavaouriteIcon());

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public ArrayList<MovieDb> getMovies(String movieTitle){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<MovieDb> arrayList = new ArrayList<>();
        String query = "SELECT * FROM "+ TABLE_NAME + " WHERE " + MOVIE_TITLE +" = " + "'"+movieTitle+"'";
        Cursor cursor = db.rawQuery(query,null);
        if (cursor.moveToFirst()){
            do {
                String movie_title = cursor.getString(1);
                int icon_img = cursor.getInt(2);
                String quote = cursor.getString(3);
                String quote_main_words = cursor.getString(4);
                Boolean isFavourite = cursor.getInt(5) == 1 ? true: false;
                int favaourite_icon = cursor.getInt(6);
                MovieDb movieDb = new MovieDb(movie_title, icon_img, quote,quote_main_words,isFavourite,favaourite_icon);
                arrayList.add(movieDb);
            } while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return arrayList;
    }

    public ArrayList<MovieDb> getFavouriteMovies(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<MovieDb> arrayList = new ArrayList<>();
        String query = "SELECT * FROM "+ TABLE_NAME + " WHERE " + ISFAVOURITE +" ='1'" ;
        Cursor cursor = db.rawQuery(query,null);
        if (cursor.moveToFirst()){
            do {
                String movie_title = cursor.getString(1);
                int icon_img = cursor.getInt(2);
                String quote = cursor.getString(3);
                String quote_main_words = cursor.getString(4);
                Boolean isFavourite = cursor.getInt(5) == 1 ? true: false;
                int favaourite_icon = cursor.getInt(6);
                MovieDb movieDb = new MovieDb(movie_title, icon_img, quote,quote_main_words,isFavourite,favaourite_icon);
                arrayList.add(movieDb);
            } while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return arrayList;
    }

    public void addToFavaourite(String text){
        SQLiteDatabase db = this.getWritableDatabase();
        int favouritebtn = R.drawable.ic_icon_heart_foreground;
        String query = "UPDATE Movies SET is_favourite = 1, favourite_icon ='"+favouritebtn+"' WHERE movie_quote =" + "'"+text+"'";
        db.execSQL(query);
        db.close();
    }

    public void deleteFromFavaourite(String text){
        SQLiteDatabase db = this.getWritableDatabase();
        int favouritebtn = R.drawable.ic_baseline_shadow_24;
        String query = "UPDATE Movies SET is_favourite = 0, favourite_icon ='"+favouritebtn+"' WHERE movie_quote =" + "'"+text+"'";
        db.execSQL(query);
        db.close();
    }

    public Boolean checkIfFavourite(String text){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM "+ TABLE_NAME + " WHERE movie_quote =" + "'"+text+"'";
        Cursor cursor = db.rawQuery(query,null);
        Boolean isFavourite =false;
        if (cursor!=null && cursor.moveToFirst()){
        isFavourite = cursor.getInt(5) == 1 ? true: false;
        }
        cursor.close();
        db.close();

        return  isFavourite;
    }

    public int getToggleButtonImage(String text){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM "+ TABLE_NAME + " WHERE movie_quote =" + "'"+text+"'";
        Cursor cursor = db.rawQuery(query,null);
       int toggleButtonImg = 0;
        if (cursor!=null && cursor.moveToFirst()){
            toggleButtonImg = cursor.getInt(6);
        }
        cursor.close();
        db.close();

        return  toggleButtonImg;
    }
}
