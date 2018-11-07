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

        val executor = Executor(0,null,null);
        val albom = Albom(0,null,null,null,null);
        val track = Track(0,null,null,null);

        db?.execSQL(albom.CREATE_TABLE);
        db?.execSQL(executor.CREATE_TABLE);
        db?.execSQL(track.CREATE_TABLE);

        db?.execSQL("CREATE TABLE link_executor_to_albom (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "idExecutor INTEGER NOT NULL," +
                "idAlbom INTEGER NOT NULL," +
                "FOREIGN KEY (idExecutor) REFERENCES executor(id) ON DELETE CASCADE," +
                "FOREIGN KEY (idAlbom) REFERENCES albom(id) ON DELETE CASCADE" +
                ")");

        db?.execSQL("CREATE TABLE link_albom_track (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "idAlbom INTEGER NOT NULL," +
                "idTreack INTEGER NOT NULL," +
                "FOREIGN KEY (idAlbom) REFERENCES albom(id) ON DELETE CASCADE," +
                "FOREIGN KEY (idTreack) REFERENCES track(id) ON DELETE CASCADE" +
                ")");

        var x = 0
        while (x < 3) {
            x++
            db?.execSQL("INSERT INTO '"+executor.TABLE_NAME+"' ( 'ExecutorName', 'Teg') VALUES ( 'Executor$x', 'Teg $x' )");

        }

        x = 0;
        while (x < 3) {
            x++
            db?.execSQL("INSERT INTO '"+albom.TABLE_NAME+"' ( 'AlbomName', 'Year') VALUES ( 'Albom$x', '$x' )");

        }
        x = 0;

        while (x < 3) {
            x++
            db?.execSQL("INSERT INTO '"+track.TABLE_NAME+"' ( 'TrackName', 'Time') VALUES ( 'TrackName$x', '$x:$x' )");

        }

        db?.execSQL("INSERT INTO 'link_executor_to_albom' ( 'idExecutor','idAlbom') VALUES ( '1', '1' )");
        db?.execSQL("INSERT INTO 'link_executor_to_albom' ( 'idExecutor','idAlbom') VALUES ( '1', '2' )");
        db?.execSQL("INSERT INTO 'link_executor_to_albom' ( 'idExecutor','idAlbom') VALUES ( '1', '3' )");
        db?.execSQL("INSERT INTO 'link_executor_to_albom' ( 'idExecutor','idAlbom') VALUES ( '2', '1' )");
        db?.execSQL("INSERT INTO 'link_executor_to_albom' ( 'idExecutor','idAlbom') VALUES ( '2', '3' )");


        db.execSQL("INSERT INTO 'link_albom_track' ( 'idAlbom','idTreack') VALUES ( '1', '1' )");
        db.execSQL("INSERT INTO 'link_albom_track' ( 'idAlbom','idTreack') VALUES ( '1', '2' )");
        db.execSQL("INSERT INTO 'link_albom_track' ( 'idAlbom','idTreack') VALUES ( '2', '2' )");
    }

    override fun onUpgrade(p0: SQLiteDatabase, p1: Int, p2: Int) {


    }


}