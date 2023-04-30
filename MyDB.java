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

    private static final String CREATE_TABLE = String.format("CREATE TABLE %s (%s int NOT NULL PRIMARY KEY AUTOINCREMENT, %s int, %s int, %s int);", DBTableName, STU_Number, MID_I, MID_II, FINAL);

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
