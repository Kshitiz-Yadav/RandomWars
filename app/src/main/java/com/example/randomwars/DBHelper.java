package com.example.randomwars;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private Cursor cursor;
    private static SQLiteDatabase dbw;

    public DBHelper(Context context){
        super(context, "RandomWars.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE HighScores( 'Index' INT PRIMARY KEY, 'PlayerName' TEXT, 'Score' INT)");
        db = this.getWritableDatabase();
        dbw = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("PlayerName", "Player");
        contentValues.put("Score", 0);
        contentValues.put("Index", 1);
        db.insert("HighScores", null, contentValues);
        contentValues.put("Index", 2);
        db.insert("HighScores", null, contentValues);
        contentValues.put("Index", 3);
        db.insert("HighScores", null, contentValues);
        contentValues.put("Index", 4);
        db.insert("HighScores", null, contentValues);
        contentValues.put("Index", 5);
        db.insert("HighScores", null, contentValues);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS HighScores");
    }

    public void checkHighScore(int score, String playerName){
        int currScore;
        for(int i=1;i<=5;i++){
            cursor = dbw.rawQuery("SELECT Score FROM HighScores WHERE 'Index' = " + i, null);
            currScore = cursor.getInt(cursor.getColumnIndex("Score"));
            if(currScore < score){
                update(i, score, playerName);
                break;
            }
        }
    }

    public void update(int index, int score, String playerName){
        int currScore;
        String currName;
        ContentValues contentValues = new ContentValues();
        for(int i=5;i>index;i--){
            cursor = dbw.rawQuery("SELECT PlayerName, Score FROM HighScores WHERE 'Index' = " + (i - 1), null);
            currName = cursor.getString(cursor.getColumnIndex("PlayerName"));
            currScore = cursor.getInt(cursor.getColumnIndex("Score"));
            contentValues.put("PlayerName", currName);
            contentValues.put("Score", currScore);
            dbw.update("HighScores", contentValues, "Index = " + i, null);
        }
        contentValues.put("PlayerName", playerName);
        contentValues.put("Score", score);
        dbw.update("HighScores", contentValues, "Index = " + index, null);
    }

    public static Cursor getInfo(){
        Cursor cursor = dbw.rawQuery("SELECT * FROM 'HighScores'", null);
        return cursor;
    }
}
