package com.android.samples.arch.componentsbasicsample

class Track(id: Int, trackName: String?, time: String?, teg: String?){


    public var  TABLE_NAME: String = "track";
    public var COLUMN_ID: String = "id"; // Ид таблицы Альбом
    public var COLUMN_TRACKNAME: String = "TrackName"; // Название колонки Трека
    public var COLUMN_TIME: String = "Time"; // Время трека
    public var COLUMN_TEG: String = "Teg"; // Название колонки Тег


    private var id:Int = 0;
    private var trackName:String ;
    private var time:String;
    private var teg: String ;


    init {
        this.id = id;
        this.trackName = trackName.toString();
        this.time = time.toString();
        this.teg = teg.toString();
    }


    // Create table SQL query
    public var CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_TRACKNAME + " TEXT NOT NULL," +
                    COLUMN_TIME + " TEXT ," +
                    COLUMN_TEG + " TEXT" + ")";



    /////////////////////////

    public fun getId(): Int {
        return this.id;
    }

    public fun setId(id: Int) {
        this.id = id;
    }

    ///////////////////////////////

    public fun getTrackName(): String {
        return this.trackName;
    }

    public fun setAlbomName(trackName: String) {
        this.trackName = trackName;
    }

    ///////////////////////////////////////

    public fun getTime(): String {
        return this.time;
    }

    public fun setExecutor(time: String) {
        this.time = time;
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
