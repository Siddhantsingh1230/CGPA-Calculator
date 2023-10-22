package com.example.cgpacalc;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ResultDatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "Results";
    private static int DB_VERSION = 1;
    private static final String TABLE_NAME = "Result";
    private static final String KEY_RESULT_ID = "id";
    private static final String CGPA = "cgpa";

    public ResultDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // when the obj of this class is created this methods runs and creates a new db of same
        // name if not created or will return the reference of previously existing database of same name
        String createTblSQL = "CREATE TABLE " + TABLE_NAME + "(" +
                KEY_RESULT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CGPA + " DOUBLE )";
        db.execSQL(createTblSQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP Table IF EXISTS " + TABLE_NAME);
        //Again create table after dropping previous version table
        onCreate(db);
    }

    public void addResult(double cgpa) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(CGPA, cgpa);
        db.insert(TABLE_NAME, null, cv);
    }

    public void dropTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("Drop TABLE IF EXISTS " + TABLE_NAME);
    }

    public ArrayList<Double> getResults() {
        SQLiteDatabase db = this.getReadableDatabase();
        // returns the cursor
        Cursor cr = db.rawQuery("SELECT * FROM " + TABLE_NAME +" ORDER BY "+KEY_RESULT_ID+" desc"
                , null);
        //Moving cursor to next record

        //Data creation
        ArrayList<Double> data = new ArrayList<>();
        while (cr.moveToNext()) {
            data.add(cr.getDouble(1));
        }
        return data;
    }
}
