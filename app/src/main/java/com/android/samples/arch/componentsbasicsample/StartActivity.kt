package com.android.samples.arch.componentsbasicsample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlin.properties.Delegates
import android.util.Log


var dbHelper: MyDatabaseHelper by Delegates.notNull()

class StartActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {


        dbHelper = MyDatabaseHelper(this)
        var db = dbHelper.writableDatabase

        var flgaAlbom = false;
        val albom = Albom(0,null,null,null,null,null);


        db.execSQL("DROP TABLE IF EXISTS '"+albom.TABLE_NAME+"'");


        var listTable = ArrayList<String>();

        var cursor = db.rawQuery("SELECT * FROM sqlite_master  where type = 'table'", null);

        if (cursor.moveToFirst()) {

            do {
                val id = cursor.getInt(cursor.getColumnIndex("type"))
                val title = cursor.getString(cursor.getColumnIndex("tbl_name"))
                val content = cursor.getString(cursor.getColumnIndex("name"))

                listTable.add(content)

            } while (cursor.moveToNext())
        }

        for(i in listTable)
        {
            if(i == albom.TABLE_NAME)
            {
                flgaAlbom = true;
                break;
            }
        }



        if(flgaAlbom == false) {
            db.execSQL(albom.CREATE_TABLE);

            var x = 0
            while (x < 20) {
                x++
                db.execSQL("INSERT INTO 'albom' ( 'AlbomName', 'Executor', 'Year') VALUES ( 'Albom $x', 'Executor $x','$x' )");

            }


        }

        listTable.clear();


        cursor = db.rawQuery("SELECT * FROM 'albom'", null);

        if (cursor.moveToFirst()) {

            do {
                val id = cursor.getInt(cursor.getColumnIndex("id"))
                val albomName = cursor.getString(cursor.getColumnIndex("AlbomName"))
                val executor = cursor.getString(cursor.getColumnIndex("Executor"))
                val year = cursor.getString(cursor.getColumnIndex("Year"))

                listTable.add(albomName)

            } while (cursor.moveToNext())
        }








        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);


    }

}
