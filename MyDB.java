package com.example.cmse419_sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyDB extends SQLiteOpenHelper {

    private static final String DBName = "myDatabase";
    private static final String DBTableName = "myStudents";
    private static final int DBversion=1;

    private static final String STU_Number="_id";
    private static final String MID_I="mid1";
    private static final String MID_II="mid2";
    private static final String FINAL="final";

    private static final String CREATE_TABLE = String.format("CREATE TABLE %s (%s INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, %s INTEGER, %s INTEGER, %s TEXT);", DBTableName, STU_Number, MID_I, MID_II, FINAL);

    private static final String DROP_TABLE = String.format("DROP TABLE IF EXISTS %s", DBTableName);

    // This field can be local or attribute depending on the context.
    private Context context;

    public MyDB(@Nullable Context context) {
        // You can put the third argument as null
        super(context, DBName, null, DBversion);
        this.context= context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_TABLE);
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL(DROP_TABLE);
            onCreate(db);
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    void AddStu(int stdNo, int mid1, int mid2, int finalGr){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(STU_Number, stdNo);
        cv.put(MID_I, mid1);
        cv.put(MID_II, mid2);
        cv.put(FINAL, finalGr);

        try {
            db.insert(DBTableName, null, cv);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public Cursor getStudents(){
        final String readQuery = String.format("SELECT * FROM %s", DBTableName);
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = null;
        if(db != null){
            c = db.rawQuery(readQuery,null);
        }

        return c;
    }

    void deleteStudent(int studentNo){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DBTableName, STU_Number + " = "+ studentNo, null);
        db.close();
    }


}


// DB CLASS - MULTIPLE TABLES

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyDB extends SQLiteOpenHelper {

    private  static final String DBName="myDatabase3";
    private  static final String DBTableCMSE="cmse";
    private  static final String DBTableCMPE="cmpe";
    private  static final String DBTableBLGM="blgm";
    private  static final int DBversion=1;

    private  static final String STU_Number="_id";
    private  static final String NAME="name";
    private  static final String SURNAME="surname";

    private  static final String CREATE_CMSE_TABLE="CREATE TABLE " + DBTableCMSE + " (" + STU_Number + " INTEGER PRIMARY KEY, " + NAME + " TEXT, " + SURNAME + " TEXT) ;";
    private  static final String CREATE_CMPE_TABLE="CREATE TABLE " + DBTableCMPE + " (" + STU_Number + " INTEGER PRIMARY KEY, " + NAME + " TEXT, " + SURNAME + " TEXT) ;";
    private  static final String CREATE_BLGM_TABLE="CREATE TABLE " + DBTableBLGM + " (" + STU_Number + " INTEGER PRIMARY KEY, " + NAME + " TEXT, " + SURNAME + " TEXT) ;";


    private  static final String DROP_CMSE_TABLE="DROP TABLE IF EXISTS " + DBTableCMSE;
    private  static final String DROP_CMPE_TABLE="DROP TABLE IF EXISTS " + DBTableCMPE;
    private  static final String DROP_BLGM_TABLE="DROP TABLE IF EXISTS " + DBTableBLGM;



    private Context context;
    public MyDB(@Nullable Context context) {
        super(context, DBName, null, DBversion);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            db.execSQL(CREATE_CMSE_TABLE);
            db.execSQL(CREATE_CMPE_TABLE);
            db.execSQL(CREATE_BLGM_TABLE);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        try {
            db.execSQL(DROP_CMSE_TABLE);
            db.execSQL(DROP_CMPE_TABLE);
            db.execSQL(DROP_BLGM_TABLE);
            onCreate(db);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    void AddStu(int stno,String name,String surname,String program){

        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues cv=new ContentValues();
        cv.put(STU_Number,stno);
        cv.put(NAME,name);
        cv.put(SURNAME,surname);

        try {
            if (program.contains("CMSE")){
                db.insert(DBTableCMSE,null,cv);
            } else if (program.contains("CMPE")) {
                db.insert(DBTableCMPE,null,cv);
            } else if (program.contains("BLGM")) {
                db.insert(DBTableBLGM,null,cv);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

    Cursor GetData(String program){
        String readqueryCMSE="SELECT * FROM " + DBTableCMSE;
        String readqueryCMPE="SELECT * FROM " + DBTableCMPE;
        String readqueryBLGM="SELECT * FROM " + DBTableBLGM;
        SQLiteDatabase db=this.getReadableDatabase();

        Cursor c=null;
        if (db!=null) {
            if (program.contains("CMSE")){
                c = db.rawQuery(readqueryCMSE, null);
            } else if (program.contains("CMPE")) {
                c = db.rawQuery(readqueryCMPE, null);
            } else if (program.contains("BLGM")) {
                c = db.rawQuery(readqueryBLGM, null);
            }
        }

        return c;
    }

    void update(int stno,String name,String surname,String program ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(NAME,name);
        cv.put(SURNAME,surname);
        if (program.contains("CMSE")){
            db.update(DBTableCMSE,cv,STU_Number+" = ?",new String[]{String.valueOf(stno)});
        } else if (program.contains("CMPE")) {
            db.update(DBTableCMPE,cv,STU_Number+" = ?",new String[]{String.valueOf(stno)});
        } else if (program.contains("BLGM")) {
            db.update(DBTableBLGM,cv,STU_Number+" = ?",new String[]{String.valueOf(stno)});
        }
    }

    void Delete(int stno,String program){
        SQLiteDatabase db = this.getWritableDatabase();
        if (program.contains("CMSE")){
            db.delete(DBTableCMSE,STU_Number+" = ?",new String[]{String.valueOf(stno)});
        } else if (program.contains("CMPE")) {
            db.delete(DBTableCMPE,STU_Number+" = ?",new String[]{String.valueOf(stno)});
        } else if (program.contains("BLGM")) {
            db.delete(DBTableBLGM,STU_Number+" = ?",new String[]{String.valueOf(stno)});
        }

    }
}

