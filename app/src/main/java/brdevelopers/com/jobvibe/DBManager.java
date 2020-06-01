package brdevelopers.com.jobvibe;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DBManager extends SQLiteOpenHelper {

    private static String DBName="JobDetails.db";
    private static int DBVersion=1;
    private static String Viewed="Viewed";
    private static String Saved="Saved";
    private static String Notification="Notification";
    private static String TableImage="TableImage";

    public DBManager(Context context){
        super(context,DBName,null,DBVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+Viewed+"(Auto integer primary key AUTOINCREMENT,JOB_ID text,Cemail text)");
        db.execSQL("CREATE TABLE "+Saved+"(Auto integer primary key AUTOINCREMENT,JOB_ID text,Cemail text)");
        db.execSQL("CREATE TABLE "+Notification+"(Auto integer primary key AUTOINCREMENT,JOB_ID text,Cemail text,ViewedJob text)");
        db.execSQL("CREATE TABLE "+TableImage+"(Auto integer primary key AUTOINCREMENT,Cemail text,Image blob not null)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+Viewed);
        db.execSQL("DROP TABLE IF EXISTS "+Saved);
        db.execSQL("DROP TABLE IF EXISTS "+Notification);
        db.execSQL("DROP TABLE IF EXISTS "+TableImage);
    }



}
