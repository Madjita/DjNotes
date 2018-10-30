package com.android.samples.arch.componentsbasicsample;


import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.content.Context
import android.database.Cursor
import android.util.Log

import com.android.samples.arch.componentsbasicsample.Albom


class MyDatabaseHelper(context: Context) : SQLiteOpenHelper(context, "data.db", null, 4) {
    val TAG = "MyDatabaseHelper"
    val TABLE = "logs"

    companion object {
        public val ID: String = "_id"
        public val TIMESTAMP: String = "TIMESTAMP"
        public val TEXT: String = "TEXT"
    }

    val DATABASE_CREATE =
            "CREATE TABLE if not exists " + TABLE + " (" +
                    "${ID} integer PRIMARY KEY autoincrement," +
                    "${TIMESTAMP} integer," +
                    "${TEXT} text"+
                    ")"

    fun log(text: String) {
        val values = ContentValues()
        values.put(TEXT, text)
        values.put(TIMESTAMP, System.currentTimeMillis())
        getWritableDatabase().insert(TABLE, null, values);
    }

    fun getLogs() : Cursor {
        return getReadableDatabase()
                .query(TABLE, arrayOf(ID, TIMESTAMP, TEXT), null, null, null, null, null);
    }



    override fun onCreate(db: SQLiteDatabase) {

        val albom = Albom(0,null,null,null,null,null);

        db?.execSQL(albom.CREATE_TABLE);
    }

    override fun onUpgrade(p0: SQLiteDatabase, p1: Int, p2: Int) {


    }


}