package com.android.samples.arch.componentsbasicsample;

import java.util.*

class Albom(id: Int, albomName: String?,year: String?, dirPng: String?, teg: String?){


    public var  TABLE_NAME: String = "albom";
    public var COLUMN_ID: String = "id"; // Ид таблицы Альбом
    public var COLUMN_ALBOMNAME: String = "AlbomName"; // Название колонки Албома
    public var COLUMN_YEAR: String = "Year"; // Название колонки Год
    public var COLUMN_DIRPNG: String= "DirPng"; // Название колонки Путь до картинки
    public var COLUMN_TEG: String = "Teg"; // Название колонки Тег


    private var id:Int = 0;
    private var albomName:String ;
    private var year:String ;
    private var  dirPng:String ;
    private var teg: String ;


    init {
        this.id = id
        this.albomName = albomName.toString();
        this.year = year.toString();
        this.dirPng = dirPng.toString();
        this.teg = teg.toString();
    }


    // Create table SQL query
    public var CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_ALBOMNAME + " TEXT NOT NULL," +
                    COLUMN_YEAR + " TEXT,"+
                    COLUMN_DIRPNG + " TEXT,"+
                    COLUMN_TEG + " TEXT" + ")";



    /////////////////////////

    public fun getId(): Int {
        return this.id;
    }

    public fun setId(id: Int) {
        this.id = id;
    }

    ///////////////////////////////

    public fun getAlbomName(): String {
        return this.albomName;
    }

    public fun setAlbomName(albomName: String) {
        this.albomName = albomName;
    }

    ///////////////////////////////////////

    public fun getYear(): String {
        return this.year;
    }

    public fun setYear(year: String) {
        this.year = year;
    }


    ///////////////////////////////////////

    public fun getDirPng(): String {
        return this.dirPng;
    }

    public fun setDirPng(dirPng: String) {
        this.dirPng = dirPng;
    }
    ///////////////////////////////////////

    public fun getTeg(): String {
        return this.teg;
    }

    public fun setTeg(teg: String) {
        this.teg = teg;
    }

    ///////////////////////////////////////


    public fun getAllTrack(): ArrayList<Track>
    {

        var db = dbHelper.writableDatabase

        var listTracksItems = ArrayList<Track>();

        var cursor = db.rawQuery("SELECT track.id, TrackName, Time,track.Teg FROM track "+
                "JOIN link_albom_track ON link_albom_track.idTreack = track.Id "+
                "JOIN  albom ON link_albom_track.idAlbom= albom.id "+
                "WHERE albom.id = "+getId(), null);

        if (cursor.moveToFirst()) {

            do {
                val id = cursor.getInt(cursor.getColumnIndex("id"))
                val trackName = cursor.getString(cursor.getColumnIndex("TrackName"))
                val time = cursor.getString(cursor.getColumnIndex("Time"))
                val teg = cursor.getString(cursor.getColumnIndex("Teg"))

                listTracksItems.add(Track(id,trackName,time,teg))


            } while (cursor.moveToNext())
        }

        //перевернуть массив
        Collections.reverse(listTracksItems)

        return listTracksItems;
    }

}
