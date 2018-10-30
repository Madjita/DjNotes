package com.android.samples.arch.componentsbasicsample;

class Albom(id: Int, albomName: String?, executor: String?, year: String?, dirPng: String?, teg: String?){


    public var  TABLE_NAME: String = "albom";
    public var COLUMN_ID: String = "id"; // Ид таблицы Альбом
    public var COLUMN_ALBOMNAME: String = "AlbomName"; // Название колонки Албома
    public var COLUMN_EXECUTOR: String = "Executor"; // Название колонки Исполнителя
    public var COLUMN_YEAR: String = "Year"; // Название колонки Год
    public var COLUMN_DIRPNG: String= "DirPng"; // Название колонки Путь до картинки
    public var COLUMN_TEG: String = "Teg"; // Название колонки Тег


    private var id:Int = 0;
    private var albomName:String ;
    private var executor:String
    private var year:String ;
    private var  dirPng:String ;
    private var teg: String ;


    init {
        this.id = id
        this.albomName = albomName.toString();
        this.executor = executor.toString();
        this.year = year.toString();
        this.dirPng = dirPng.toString();
        this.teg = teg.toString();
    }


    // Create table SQL query
    public var CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_ALBOMNAME + " TEXT NOT NULL," +
                    COLUMN_EXECUTOR + " TEXT NOT NULL," +
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

    public fun getExecutor(): String {
        return this.executor;
    }

    public fun setExecutor(executor: String) {
        this.executor = executor;
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

}
