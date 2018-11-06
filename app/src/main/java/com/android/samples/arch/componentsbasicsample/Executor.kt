package com.android.samples.arch.componentsbasicsample

import android.app.Activity
import java.util.*
import kotlin.collections.ArrayList

import java.text.DateFormat

class Executor(id: Int, executorName: String?, teg: String?){


    public var  TABLE_NAME: String = "executor";
    public var COLUMN_ID: String = "id"; // Ид таблицы Альбом
    public var COLUMN_EXECUTOR: String = "ExecutorName"; // Название колонки Исполнителя
    public var COLUMN_TEG: String = "Teg"; // Название колонки Тег
    public var COLUMN_DATA: String = "Data"; // Название колонки Дата (в милисекундах)


    private var id:Int = 0;
    private var executorName:String
    private var teg: String ;
    private var data: Long = 0;


    init {
        this.id = id
        this.executorName = executorName.toString();
        this.teg = teg.toString();
    }


    // Create table SQL query
    public var CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_EXECUTOR + " TEXT NOT NULL," +
                    COLUMN_TEG + " TEXT," +
                    COLUMN_DATA +" INTEGER " +")";

    /////////////////////////

    public fun getId(): Int {
        return this.id;
    }

    public fun setId(id: Int) {
        this.id = id;
    }

    ///////////////////////////////////////

    public fun getExecutorName(): String {
        return this.executorName;
    }

    public fun setExecutorName(executorName: String) {
        this.executorName = executorName;
    }

    ///////////////////////////////////////

    public fun getTeg(): String {
        return this.teg;
    }

    public fun setTeg(teg: String) {
        this.teg = teg;
    }

    ///////////////////////////////////////

    public fun getDataMillis(): Long {
        return this.data;
    }

    public fun getDta(): String {
        return DateFormat.getDateTimeInstance().format(Date(this.data))
    }

    public fun setDataMillis(data: Long) {
        this.data = data;
    }

    ///////////////////////////////////////


    public fun getItemsAlbomsToExecutor(): ArrayList<Albom>{

        var db = dbHelper.writableDatabase

        var listAlbomsItems = ArrayList<Albom>();

        var cursor = db.rawQuery("SELECT albom.id, AlbomName, Year, DirPng, albom.Teg FROM albom "+
                "JOIN link_executor_to_albom ON link_executor_to_albom.idAlbom = albom.Id "+
                "JOIN  executor ON link_executor_to_albom.idExecutor= executor.id "+
                "WHERE executor.id = "+getId(), null);

        if (cursor.moveToFirst()) {

            do {
                val id = cursor.getInt(cursor.getColumnIndex("id"))
                val albomName = cursor.getString(cursor.getColumnIndex("AlbomName"))
                val year = cursor.getString(cursor.getColumnIndex("Year"))
                val dirPng = cursor.getString(cursor.getColumnIndex("DirPng"))
                val teg = cursor.getString(cursor.getColumnIndex("Teg"))

                listAlbomsItems.add(Albom(id,albomName,year,dirPng,teg))


            } while (cursor.moveToNext())
        }

        //перевернуть массив
        Collections.reverse(listAlbomsItems)

        return listAlbomsItems;
    }

}
