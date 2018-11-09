package com.android.samples.arch.componentsbasicsample;


import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.content.Context
import android.database.Cursor
import android.util.Log

import com.android.samples.arch.componentsbasicsample.Albom
import java.util.*


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

//        var x = 0
//        while (x < 3) {
//            x++
//            db?.execSQL("INSERT INTO '"+executor.TABLE_NAME+"' ( 'ExecutorName', 'Teg') VALUES ( 'Executor$x', 'Teg $x' )");
//
//        }
//
//        x = 0;
//        while (x < 3) {
//            x++
//            db?.execSQL("INSERT INTO '"+albom.TABLE_NAME+"' ( 'AlbomName', 'Year') VALUES ( 'Albom$x', '$x' )");
//
//        }
//        x = 0;
//
//        while (x < 3) {
//            x++
//            db?.execSQL("INSERT INTO '"+track.TABLE_NAME+"' ( 'TrackName', 'Time') VALUES ( 'TrackName$x', '$x:$x' )");
//
//        }
//
//        db?.execSQL("INSERT INTO 'link_executor_to_albom' ( 'idExecutor','idAlbom') VALUES ( '1', '1' )");
//        db?.execSQL("INSERT INTO 'link_executor_to_albom' ( 'idExecutor','idAlbom') VALUES ( '1', '2' )");
//        db?.execSQL("INSERT INTO 'link_executor_to_albom' ( 'idExecutor','idAlbom') VALUES ( '1', '3' )");
//        db?.execSQL("INSERT INTO 'link_executor_to_albom' ( 'idExecutor','idAlbom') VALUES ( '2', '1' )");
//        db?.execSQL("INSERT INTO 'link_executor_to_albom' ( 'idExecutor','idAlbom') VALUES ( '2', '3' )");
//
//
//        db.execSQL("INSERT INTO 'link_albom_track' ( 'idAlbom','idTreack') VALUES ( '1', '1' )");
//        db.execSQL("INSERT INTO 'link_albom_track' ( 'idAlbom','idTreack') VALUES ( '1', '2' )");
//        db.execSQL("INSERT INTO 'link_albom_track' ( 'idAlbom','idTreack') VALUES ( '2', '2' )");


        //////////////////////////


        val currentTime = Calendar.getInstance().getTimeInMillis()

        //Это исполнители

        db?.execSQL("INSERT INTO '"+executor.TABLE_NAME+"' ( 'id','ExecutorName', 'Teg','Data') VALUES ( '1','Aguankó', 'Afro-Cuban','$currentTime' )");
        db?.execSQL("INSERT INTO '"+executor.TABLE_NAME+"' ( 'id','ExecutorName', 'Teg','Data') VALUES ( '2','Adresito Carabali', 'Salsa','$currentTime' )");
        db?.execSQL("INSERT INTO '"+executor.TABLE_NAME+"' ( 'id','ExecutorName', 'Teg','Data') VALUES ( '3','Alain Pérez', 'Timba','$currentTime' )");
        db?.execSQL("INSERT INTO '"+executor.TABLE_NAME+"' ( 'id','ExecutorName', 'Teg','Data') VALUES ( '4','Barbaro Fines y Su Mayimbe', 'Timba','$currentTime' )");
        db?.execSQL("INSERT INTO '"+executor.TABLE_NAME+"' ( 'id','ExecutorName', 'Teg','Data') VALUES ( '5','Daniel Peña', 'Salsa','$currentTime' )");
        db?.execSQL("INSERT INTO '"+executor.TABLE_NAME+"' ( 'id','ExecutorName', 'Teg','Data') VALUES ( '6','Tromboranga', 'Salsa Dura','$currentTime' )");


        //Это альбомы

        db?.execSQL("INSERT INTO '"+albom.TABLE_NAME+"' ( 'id','AlbomName', 'Year', 'Teg','Data') VALUES ( '1', 'Invisible', '2015', 'Afro-Cuban','$currentTime')");
        db?.execSQL("INSERT INTO '"+albom.TABLE_NAME+"' ( 'id','AlbomName', 'Year', 'Teg','Data') VALUES ( '2', 'Pattern Recognition', '2018', 'Afro-Cuban','$currentTime') ");
        db?.execSQL("INSERT INTO '"+albom.TABLE_NAME+"' ( 'id','AlbomName', 'Year', 'Teg','Data') VALUES ( '3', 'Exploto' , '2018', 'Salsa','$currentTime') ");
        db?.execSQL("INSERT INTO '"+albom.TABLE_NAME+"' ( 'id','AlbomName', 'Year', 'Teg','Data') VALUES ( '4', 'ADN' , '2017', 'Timba','$currentTime') ");
        db?.execSQL("INSERT INTO '"+albom.TABLE_NAME+"' ( 'id','AlbomName', 'Year', 'Teg','Data') VALUES ( '5', 'De la Habana a Peru' , '2011', 'Timba','$currentTime') ");
        db?.execSQL("INSERT INTO '"+albom.TABLE_NAME+"' ( 'id','AlbomName', 'Year', 'Teg','Data') VALUES ( '6', 'Te Vi Nacer' , '2016', 'Timba','$currentTime') ");
        db?.execSQL("INSERT INTO '"+albom.TABLE_NAME+"' ( 'id','AlbomName', 'Year', 'Teg','Data') VALUES ( '7', 'Eleven' , '2014', 'Latin','$currentTime') ");
        db?.execSQL("INSERT INTO '"+albom.TABLE_NAME+"' ( 'id','AlbomName', 'Year', 'Teg','Data') VALUES ( '8', 'Sancocho' , '2017', 'Latin','$currentTime') ");
        db?.execSQL("INSERT INTO '"+albom.TABLE_NAME+"' ( 'id','AlbomName', 'Year', 'Teg','Data') VALUES ( '9', 'Golpe con Melodia' , '2015', 'Salsa Dura','$currentTime') ");
        db?.execSQL("INSERT INTO '"+albom.TABLE_NAME+"' ( 'id','AlbomName', 'Year', 'Teg','Data') VALUES ( '10', 'Sangre, Sudor y Salsa' , '2016', 'Salsa Dura','$currentTime') ");
        db?.execSQL("INSERT INTO '"+albom.TABLE_NAME+"' ( 'id','AlbomName', 'Year', 'Teg','Data') VALUES ( '11', 'Tumbando Fronteras' , '2017', 'Salsa Dura','$currentTime') ");


        //Это ТРЕКИ
        db?.execSQL("INSERT INTO '"+track.TABLE_NAME+"' ( 'id','TrackName', 'Time', 'Teg','Data') VALUES ( '1', 'Sur la Seine', '6:02', 'Normal','$currentTime' )");
        db?.execSQL("INSERT INTO '"+track.TABLE_NAME+"' ( 'id','TrackName', 'Time', 'Teg','Data') VALUES ( '2', 'Invisible', '5:40', 'Normal','$currentTime' )");
        db?.execSQL("INSERT INTO '"+track.TABLE_NAME+"' ( 'id','TrackName', 'Time', 'Teg','Data') VALUES ( '3', 'Babuka', '4:34', 'Dislike','$currentTime' )");
        db?.execSQL("INSERT INTO '"+track.TABLE_NAME+"' ( 'id','TrackName', 'Time', 'Teg','Data') VALUES ( '4', 'Chicos dream', '3:14', 'Good','$currentTime' )");
        db?.execSQL("INSERT INTO '"+track.TABLE_NAME+"' ( 'id','TrackName', 'Time', 'Teg','Data') VALUES ( '5', 'Luna roja for Armando Peraza', '3:43', 'Good','$currentTime' )");
        db?.execSQL("INSERT INTO '"+track.TABLE_NAME+"' ( 'id','TrackName', 'Time', 'Teg','Data') VALUES ( '6', 'Señor Belgrave', '4:45', 'Dislike','$currentTime' )");
        db?.execSQL("INSERT INTO '"+track.TABLE_NAME+"' ( 'id','TrackName', 'Time', 'Teg','Data') VALUES ( '7', 'Madiba', '0:50', 'Dislike','$currentTime' )");
        db?.execSQL("INSERT INTO '"+track.TABLE_NAME+"' ( 'id','TrackName', 'Time', 'Teg','Data') VALUES ( '8', 'Un futuro brillante', '3:48', 'Dislike','$currentTime' )");

        db?.execSQL("INSERT INTO '"+track.TABLE_NAME+"' ( 'id','TrackName', 'Time', 'Teg','Data') VALUES ( '9',  'Late Night Religion', '2:43', 'Good','$currentTime' )");
        db?.execSQL("INSERT INTO '"+track.TABLE_NAME+"' ( 'id','TrackName', 'Time', 'Teg','Data') VALUES ( '10', 'Corazón Suave', '4:50', 'Normal','$currentTime' )");
        db?.execSQL("INSERT INTO '"+track.TABLE_NAME+"' ( 'id','TrackName', 'Time', 'Teg','Data') VALUES ( '11', 'Noche y Luna', '4:34', 'Normal','$currentTime' )");
        db?.execSQL("INSERT INTO '"+track.TABLE_NAME+"' ( 'id','TrackName', 'Time', 'Teg','Data') VALUES ( '12', 'Mojo Mohito', '3:56', 'Dislike','$currentTime' )");
        db?.execSQL("INSERT INTO '"+track.TABLE_NAME+"' ( 'id','TrackName', 'Time', 'Teg','Data') VALUES ( '13', 'Metaphorically Speaking', '3:58', 'Normal','$currentTime' )");
        db?.execSQL("INSERT INTO '"+track.TABLE_NAME+"' ( 'id','TrackName', 'Time', 'Teg','Data') VALUES ( '14', 'Los Niños', '3:02', 'Dislike','$currentTime' )");
        db?.execSQL("INSERT INTO '"+track.TABLE_NAME+"' ( 'id','TrackName', 'Time', 'Teg','Data') VALUES ( '15', 'Doctors Orders', '4:01', 'Normal','$currentTime' )");
        db?.execSQL("INSERT INTO '"+track.TABLE_NAME+"' ( 'id','TrackName', 'Time', 'Teg','Data') VALUES ( '16', 'Re-Vision', '5:20', 'Normal','$currentTime' )");
        db?.execSQL("INSERT INTO '"+track.TABLE_NAME+"' ( 'id','TrackName', 'Time', 'Teg','Data') VALUES ( '17', 'Pattern Recognition', '3:06', 'Good','$currentTime' )");
        db?.execSQL("INSERT INTO '"+track.TABLE_NAME+"' ( 'id','TrackName', 'Time', 'Teg','Data') VALUES ( '18', 'Señor Smoke', '4:41', 'Dislike','$currentTime' )");

        db?.execSQL("INSERT INTO '"+track.TABLE_NAME+"' ( 'id','TrackName', 'Time', 'Teg','Data') VALUES ( '19', 'Olga María', '3:45', 'Good','$currentTime' )");
        db?.execSQL("INSERT INTO '"+track.TABLE_NAME+"' ( 'id','TrackName', 'Time', 'Teg','Data') VALUES ( '20', 'Mi remordimient', '4:39', 'Normal','$currentTime' )");
        db?.execSQL("INSERT INTO '"+track.TABLE_NAME+"' ( 'id','TrackName', 'Time', 'Teg','Data') VALUES ( '21', 'Kako y José', '6:45', 'Dislike','$currentTime' )");
        db?.execSQL("INSERT INTO '"+track.TABLE_NAME+"' ( 'id','TrackName', 'Time', 'Teg','Data') VALUES ( '22', 'Guaguancó mensajero', '4:23', 'Normal','$currentTime' )");
        db?.execSQL("INSERT INTO '"+track.TABLE_NAME+"' ( 'id','TrackName', 'Time', 'Teg','Data') VALUES ( '23', 'Exploto', '4:35', 'Good','$currentTime' )");
        db?.execSQL("INSERT INTO '"+track.TABLE_NAME+"' ( 'id','TrackName', 'Time', 'Teg','Data') VALUES ( '24', 'El Sabroso', '5:27', 'Good','$currentTime' )");
        db?.execSQL("INSERT INTO '"+track.TABLE_NAME+"' ( 'id','TrackName', 'Time', 'Teg','Data') VALUES ( '25', 'Con el guante', '5:29', 'Normal','$currentTime' )");
        db?.execSQL("INSERT INTO '"+track.TABLE_NAME+"' ( 'id','TrackName', 'Time', 'Teg','Data') VALUES ( '26', 'A mi manera', '4:55', 'Dislike','$currentTime' )");

        db?.execSQL("INSERT INTO '"+track.TABLE_NAME+"' ( 'id','TrackName', 'Time', 'Teg','Data') VALUES ( '27', 'A ella le gustan mis canciones', '4:35', 'Normal','$currentTime' )");
        db?.execSQL("INSERT INTO '"+track.TABLE_NAME+"' ( 'id','TrackName', 'Time', 'Teg','Data') VALUES ( '28', 'ADN', '4:33', 'Good','$currentTime' )");
        db?.execSQL("INSERT INTO '"+track.TABLE_NAME+"' ( 'id','TrackName', 'Time', 'Teg','Data') VALUES ( '29', 'Amor contradictorio', '5:02', 'Good','$currentTime' )");
        db?.execSQL("INSERT INTO '"+track.TABLE_NAME+"' ( 'id','TrackName', 'Time', 'Teg','Data') VALUES ( '30', 'Antonio Rodriguez', '5:03', 'Good','$currentTime' )");
        db?.execSQL("INSERT INTO '"+track.TABLE_NAME+"' ( 'id','TrackName', 'Time', 'Teg','Data') VALUES ( '31', 'De flor en flor', '4:38', 'Normal','$currentTime' )");
        db?.execSQL("INSERT INTO '"+track.TABLE_NAME+"' ( 'id','TrackName', 'Time', 'Teg','Data') VALUES ( '32', 'Chacha', '4:45', 'Good','$currentTime' )");
        db?.execSQL("INSERT INTO '"+track.TABLE_NAME+"' ( 'id','TrackName', 'Time', 'Teg','Data') VALUES ( '33', 'Shorcito', '4:48', 'Normal','$currentTime' )");
        db?.execSQL("INSERT INTO '"+track.TABLE_NAME+"' ( 'id','TrackName', 'Time', 'Teg','Data') VALUES ( '34', 'Con corbata y sin cabeza', '4:20', 'Good','$currentTime' )");
        db?.execSQL("INSERT INTO '"+track.TABLE_NAME+"' ( 'id','TrackName', 'Time', 'Teg','Data') VALUES ( '35', 'Peligro ladrón', '4:41', 'Dislike','$currentTime' )");
        db?.execSQL("INSERT INTO '"+track.TABLE_NAME+"' ( 'id','TrackName', 'Time', 'Teg','Data') VALUES ( '36', 'Nos vemos luego', '4:43', 'Normal','$currentTime' )");
        db?.execSQL("INSERT INTO '"+track.TABLE_NAME+"' ( 'id','TrackName', 'Time', 'Teg','Data') VALUES ( '37', 'Bemba colorá', '4:57', 'Normal','$currentTime' )");
        db?.execSQL("INSERT INTO '"+track.TABLE_NAME+"' ( 'id','TrackName', 'Time', 'Teg','Data') VALUES ( '38', 'Lloraré', '4:02', 'Dislike','$currentTime' )");

        db?.execSQL("INSERT INTO '"+track.TABLE_NAME+"' ( 'id','TrackName', 'Time', 'Teg','Data') VALUES ( '39','Intro del Mayimbe', '6:01', 'Good','$currentTime' )");
        db?.execSQL("INSERT INTO '"+track.TABLE_NAME+"' ( 'id','TrackName', 'Time', 'Teg','Data') VALUES ( '40', 'El diablo', '5:10', 'Dislike','$currentTime' )");
        db?.execSQL("INSERT INTO '"+track.TABLE_NAME+"' ( 'id','TrackName', 'Time', 'Teg','Data') VALUES ( '41', 'La fiera', '5:11', 'Dislike','$currentTime' )");
        db?.execSQL("INSERT INTO '"+track.TABLE_NAME+"' ( 'id','TrackName', 'Time', 'Teg','Data') VALUES ( '42', 'Acuerdate', '5:40', 'Dislike','$currentTime' )");
        db?.execSQL("INSERT INTO '"+track.TABLE_NAME+"' ( 'id','TrackName', 'Time', 'Teg','Data') VALUES ( '43', 'De la Habana a Peru', '4:41', 'Dislike','$currentTime' )");
        db?.execSQL("INSERT INTO '"+track.TABLE_NAME+"' ( 'id','TrackName', 'Time', 'Teg','Data') VALUES ( '44', 'Congo Lucumi', '5:00', 'Dislike','$currentTime' )");
        db?.execSQL("INSERT INTO '"+track.TABLE_NAME+"' ( 'id','TrackName', 'Time', 'Teg','Data') VALUES ( '45', 'La sarten', '4:41', 'Dislike','$currentTime' )");
        db?.execSQL("INSERT INTO '"+track.TABLE_NAME+"' ( 'id','TrackName', 'Time', 'Teg','Data') VALUES ( '46', 'Tren bala', '5:28', 'Dislike','$currentTime' )");
        db?.execSQL("INSERT INTO '"+track.TABLE_NAME+"' ( 'id','TrackName', 'Time', 'Teg','Data') VALUES ( '47', 'El cuchi cuchi', '5:00', 'Dislike','$currentTime' )");

        db?.execSQL("INSERT INTO '"+track.TABLE_NAME+"' ( 'id','TrackName', 'Time', 'Teg','Data') VALUES ( '48', 'Yo Se (Intro)', '3:55', 'Good','$currentTime' )");
        db?.execSQL("INSERT INTO '"+track.TABLE_NAME+"' ( 'id','TrackName', 'Time', 'Teg','Data') VALUES ( '49', 'Miel y Candela', '8:11', 'Normal','$currentTime' )");
        db?.execSQL("INSERT INTO '"+track.TABLE_NAME+"' ( 'id','TrackName', 'Time', 'Teg','Data') VALUES ( '50', 'Si tu supieras Perú', '8:12', 'Good','$currentTime' )");
        db?.execSQL("INSERT INTO '"+track.TABLE_NAME+"' ( 'id','TrackName', 'Time', 'Teg','Data') VALUES ( '51', 'Te vi nacer', '7:53', 'Good','$currentTime' )");
        db?.execSQL("INSERT INTO '"+track.TABLE_NAME+"' ( 'id','TrackName', 'Time', 'Teg','Data') VALUES ( '52', 'Sabes bien', '6:37', 'Normal','$currentTime' )");
        db?.execSQL("INSERT INTO '"+track.TABLE_NAME+"' ( 'id','TrackName', 'Time', 'Teg','Data') VALUES ( '53', 'Las gallinas', '5:33', 'Good','$currentTime')");
        db?.execSQL("INSERT INTO '"+track.TABLE_NAME+"' ( 'id','TrackName', 'Time', 'Teg','Data') VALUES ( '54', 'Vuélvete loca', '6:47', 'Good','$currentTime' )");
        db?.execSQL("INSERT INTO '"+track.TABLE_NAME+"' ( 'id','TrackName', 'Time', 'Teg','Data') VALUES ( '55', 'La diabla', '6:46', 'Dislike','$currentTime' )");
        db?.execSQL("INSERT INTO '"+track.TABLE_NAME+"' ( 'id','TrackName', 'Time', 'Teg','Data') VALUES ( '56', 'Aguardiente', '5:42', 'Normal','$currentTime' )");

        db?.execSQL("INSERT INTO '"+track.TABLE_NAME+"' ( 'id','TrackName', 'Time', 'Teg','Data') VALUES ( '57', '30 thousand feet', '4:34', 'Normal','$currentTime'  )");
        db?.execSQL("INSERT INTO '"+track.TABLE_NAME+"' ( 'id','TrackName', 'Time', 'Teg','Data') VALUES ( '58', 'El taxi', '3:43', 'Good','$currentTime'  )");
        db?.execSQL("INSERT INTO '"+track.TABLE_NAME+"' ( 'id','TrackName', 'Time', 'Teg','Data') VALUES ( '59', 'On the road', '4:21', 'Normal','$currentTime'  )");
        db?.execSQL("INSERT INTO '"+track.TABLE_NAME+"' ( 'id','TrackName', 'Time', 'Teg','Data') VALUES ( '60', 'La sangre y la experencia', '4:10', 'Dislike','$currentTime'  )");
        db?.execSQL("INSERT INTO '"+track.TABLE_NAME+"' ( 'id','TrackName', 'Time', 'Teg','Data') VALUES ( '61', 'Jimenología en danzón', '5:06', 'Dislike','$currentTime'  )");
        db?.execSQL("INSERT INTO '"+track.TABLE_NAME+"' ( 'id','TrackName', 'Time', 'Teg','Data') VALUES ( '62', 'En tren', '3:41', 'Dislike','$currentTime'  )");
        db?.execSQL("INSERT INTO '"+track.TABLE_NAME+"' ( 'id','TrackName', 'Time', 'Teg','Data') VALUES ( '63', 'Percussion to go', '3:20', 'Normal','$currentTime'  )");
        db?.execSQL("INSERT INTO '"+track.TABLE_NAME+"' ( 'id','TrackName', 'Time', 'Teg','Data') VALUES ( '64', 'Inseparable', '4:00', 'Dislike','$currentTime'  )");
        db?.execSQL("INSERT INTO '"+track.TABLE_NAME+"' ( 'id','TrackName', 'Time', 'Teg','Data') VALUES ( '65', 'Lirio de los lirios', '10:12', 'Dislike','$currentTime'  )");
        db?.execSQL("INSERT INTO '"+track.TABLE_NAME+"' ( 'id','TrackName', 'Time', 'Teg','Data') VALUES ( '66', 'Lirio de los lirios (full version)', '12:50', 'Dislike','$currentTime'  )");

        db?.execSQL("INSERT INTO '"+track.TABLE_NAME+"' ( 'id','TrackName', 'Time', 'Teg','Data') VALUES ( '67', 'Moliendo café', '4:14', 'Normal','$currentTime'  )");
        db?.execSQL("INSERT INTO '"+track.TABLE_NAME+"' ( 'id','TrackName', 'Time', 'Teg','Data') VALUES ( '68', 'Soledad', '5:29', 'Good','$currentTime'  )");
        db?.execSQL("INSERT INTO '"+track.TABLE_NAME+"' ( 'id','TrackName', 'Time', 'Teg','Data') VALUES ( '69', 'Sazón dominicano', '3:29', 'Dislike','$currentTime'  )");
        db?.execSQL("INSERT INTO '"+track.TABLE_NAME+"' ( 'id','TrackName', 'Time', 'Teg','Data') VALUES ( '70', 'Por alguien como tú', '5:32', 'Dislike','$currentTime'  )");
        db?.execSQL("INSERT INTO '"+track.TABLE_NAME+"' ( 'id','TrackName', 'Time', 'Teg','Data') VALUES ( '71', 'Quizás quizás', '3:13', 'Dislike','$currentTime'  )");
        db?.execSQL("INSERT INTO '"+track.TABLE_NAME+"' ( 'id','TrackName', 'Time', 'Teg','Data') VALUES ( '72', 'Porque ahora', '5:29', 'Dislike','$currentTime'  )");
        db?.execSQL("INSERT INTO '"+track.TABLE_NAME+"' ( 'id','TrackName', 'Time', 'Teg','Data') VALUES ( '73', 'El amor', '4:38', 'Dislike','$currentTime'  )");
        db?.execSQL("INSERT INTO '"+track.TABLE_NAME+"' ( 'id','TrackName', 'Time', 'Teg','Data') VALUES ( '74', 'Fly me to the moon', '3:51', 'Good','$currentTime'  )");
        db?.execSQL("INSERT INTO '"+track.TABLE_NAME+"' ( 'id','TrackName', 'Time', 'Teg','Data') VALUES ( '75', 'Respect', '3:35', 'Dislike','$currentTime'  )");
        db?.execSQL("INSERT INTO '"+track.TABLE_NAME+"' ( 'id','TrackName', 'Time', 'Teg','Data') VALUES ( '76', 'Vive la vida hoy', '5:46', 'Normal','$currentTime' )");
        db?.execSQL("INSERT INTO '"+track.TABLE_NAME+"' ( 'id','TrackName', 'Time', 'Teg','Data') VALUES ( '77', 'Mercedita', '3:50', 'Dislike','$currentTime'  )");

        db?.execSQL("INSERT INTO '"+track.TABLE_NAME+"' ( 'id','TrackName', 'Time', 'Teg','Data') VALUES ( '78', 'Golpe con Melodia', '5:45', 'Good','$currentTime'  )");
        db?.execSQL("INSERT INTO '"+track.TABLE_NAME+"' ( 'id','TrackName', 'Time', 'Teg','Data') VALUES ( '79', 'Quinto Mayor', '4:04', 'Good','$currentTime'  )");
        db?.execSQL("INSERT INTO '"+track.TABLE_NAME+"' ( 'id','TrackName', 'Time', 'Teg','Data') VALUES ( '80', 'Dame Felicidad', '3:08', 'Good','$currentTime' )");
        db?.execSQL("INSERT INTO '"+track.TABLE_NAME+"' ( 'id','TrackName', 'Time', 'Teg','Data') VALUES ( '81', 'Motivación Sexual', '4:35', 'Good','$currentTime'  )");
        db?.execSQL("INSERT INTO '"+track.TABLE_NAME+"' ( 'id','TrackName', 'Time', 'Teg','Data') VALUES ( '82', 'Carretera', '6:07', 'Dislike','$currentTime'  )");
        db?.execSQL("INSERT INTO '"+track.TABLE_NAME+"' ( 'id','TrackName', 'Time', 'Teg','Data') VALUES ( '83', 'La Jicotea', '5:31', 'Dislike','$currentTime'  )");
        db?.execSQL("INSERT INTO '"+track.TABLE_NAME+"' ( 'id','TrackName', 'Time', 'Teg','Data') VALUES ( '84', 'Maria Antonia', '5:02', 'Dislike','$currentTime'  )");
        db?.execSQL("INSERT INTO '"+track.TABLE_NAME+"' ( 'id','TrackName', 'Time', 'Teg','Data') VALUES ( '85', 'La Mafia', '4:14', 'Normal','$currentTime'  )");

        db?.execSQL("INSERT INTO '"+track.TABLE_NAME+"' ( 'id','TrackName', 'Time', 'Teg','Data') VALUES ( '86', 'Ah caraj (aquí que pasó)', '5:20', 'Good','$currentTime'  )");
        db?.execSQL("INSERT INTO '"+track.TABLE_NAME+"' ( 'id','TrackName', 'Time', 'Teg','Data') VALUES ( '87', 'Cambumbo', '5:58', 'Good','$currentTime'  )");
        db?.execSQL("INSERT INTO '"+track.TABLE_NAME+"' ( 'id','TrackName', 'Time', 'Teg','Data') VALUES ( '88', 'Mi China Colombiana', '4:47', 'Good' ,'$currentTime' )");
        db?.execSQL("INSERT INTO '"+track.TABLE_NAME+"' ( 'id','TrackName', 'Time', 'Teg','Data') VALUES ( '89', 'La pepa de mango', '6:15', 'Dislike','$currentTime'  )");
        db?.execSQL("INSERT INTO '"+track.TABLE_NAME+"' ( 'id','TrackName', 'Time', 'Teg','Data') VALUES ( '90', 'No me vuelvo a enamorar', '5:56', 'Dislike' ,'$currentTime' )");
        db?.execSQL("INSERT INTO '"+track.TABLE_NAME+"' ( 'id','TrackName', 'Time', 'Teg','Data') VALUES ( '91', 'El rey sin corona', '6:08', 'Normal','$currentTime'  )");
        db?.execSQL("INSERT INTO '"+track.TABLE_NAME+"' ( 'id','TrackName', 'Time', 'Teg','Data') VALUES ( '92', 'Que linda que estas', '5:09', 'Good','$currentTime'  )");
        db?.execSQL("INSERT INTO '"+track.TABLE_NAME+"' ( 'id','TrackName', 'Time', 'Teg','Data') VALUES ( '93', 'Sangre, sudor y salsa', '5:34', 'Normal','$currentTime'  )");

        db?.execSQL("INSERT INTO '"+track.TABLE_NAME+"' ( 'id','TrackName', 'Time', 'Teg','Data') VALUES ( '94', 'Otro Ladrillo en la Pared', '6:13', 'Good' ,'$currentTime' )");
        db?.execSQL("INSERT INTO '"+track.TABLE_NAME+"' ( 'id','TrackName', 'Time', 'Teg','Data') VALUES ( '95', 'Guaguanco Pal Barrio', '4:54', 'Good','$currentTime'  )");
        db?.execSQL("INSERT INTO '"+track.TABLE_NAME+"' ( 'id','TrackName', 'Time', 'Teg','Data') VALUES ( '96', 'Hortensia', '4:46', 'Dislike','$currentTime' )");
        db?.execSQL("INSERT INTO '"+track.TABLE_NAME+"' ( 'id','TrackName', 'Time', 'Teg','Data') VALUES ( '97', 'Nunca Pa Atras', '4:40', 'Normal','$currentTime'  )");
        db?.execSQL("INSERT INTO '"+track.TABLE_NAME+"' ( 'id','TrackName', 'Time', 'Teg','Data') VALUES ( '98', 'Y No Lo Digo Yo', '4:46', 'Good','$currentTime' )");
        db?.execSQL("INSERT INTO '"+track.TABLE_NAME+"' ( 'id','TrackName', 'Time', 'Teg','Data') VALUES ( '99', 'Tu y Yo', '4:06', 'Dislike','$currentTime'  )");
        db?.execSQL("INSERT INTO '"+track.TABLE_NAME+"' ( 'id','TrackName', 'Time', 'Teg','Data') VALUES ( '100', 'Para Buenaventura', '4:22', 'Normal','$currentTime'  )");
        db?.execSQL("INSERT INTO '"+track.TABLE_NAME+"' ( 'id','TrackName', 'Time', 'Teg','Data') VALUES ( '101', 'Antes Que Tu Hables de Mi', '6:49', 'Normal' ,'$currentTime' )");
        db?.execSQL("INSERT INTO '"+track.TABLE_NAME+"' ( 'id','TrackName', 'Time', 'Teg','Data') VALUES ( '102', 'Pachanga Chango', '4:40', 'Normal','$currentTime'  )");


        //ЭТО ВЛОЖЕННОСТЬ АЛЬБОМА В ИСПОЛНИТЕЛЯ
        db?.execSQL("INSERT INTO 'link_executor_to_albom' ( 'idExecutor','idAlbom') VALUES ( '1', '1' )");
        db?.execSQL("INSERT INTO 'link_executor_to_albom' ( 'idExecutor','idAlbom') VALUES ( '1', '2' )");
        db?.execSQL("INSERT INTO 'link_executor_to_albom' ( 'idExecutor','idAlbom') VALUES ( '2', '3' )");
        db?.execSQL("INSERT INTO 'link_executor_to_albom' ( 'idExecutor','idAlbom') VALUES ( '3', '4' )");
        db?.execSQL("INSERT INTO 'link_executor_to_albom' ( 'idExecutor','idAlbom') VALUES ( '4', '5' )");
        db?.execSQL("INSERT INTO 'link_executor_to_albom' ( 'idExecutor','idAlbom') VALUES ( '4', '6' )");
        db?.execSQL("INSERT INTO 'link_executor_to_albom' ( 'idExecutor','idAlbom') VALUES ( '5', '7' )");
        db?.execSQL("INSERT INTO 'link_executor_to_albom' ( 'idExecutor','idAlbom') VALUES ( '5', '8' )");
        db?.execSQL("INSERT INTO 'link_executor_to_albom' ( 'idExecutor','idAlbom') VALUES ( '6', '9' )");
        db?.execSQL("INSERT INTO 'link_executor_to_albom' ( 'idExecutor','idAlbom') VALUES ( '6', '10' )");
        db?.execSQL("INSERT INTO 'link_executor_to_albom' ( 'idExecutor','idAlbom') VALUES ( '6', '11' )");


        //ЭТО ВЛОЖЕННОСТЬ ТРЕКА АЛЬБОМ
        db.execSQL("INSERT INTO 'link_albom_track' ( 'idAlbom','idTreack') VALUES ( '1', '1' )");
        db.execSQL("INSERT INTO 'link_albom_track' ( 'idAlbom','idTreack') VALUES ( '1', '2' )");
        db.execSQL("INSERT INTO 'link_albom_track' ( 'idAlbom','idTreack') VALUES ( '1', '3' )");
        db.execSQL("INSERT INTO 'link_albom_track' ( 'idAlbom','idTreack') VALUES ( '1', '4' )");
        db.execSQL("INSERT INTO 'link_albom_track' ( 'idAlbom','idTreack') VALUES ( '1', '5' )");
        db.execSQL("INSERT INTO 'link_albom_track' ( 'idAlbom','idTreack') VALUES ( '1', '6' )");
        db.execSQL("INSERT INTO 'link_albom_track' ( 'idAlbom','idTreack') VALUES ( '1', '7' )");
        db.execSQL("INSERT INTO 'link_albom_track' ( 'idAlbom','idTreack') VALUES ( '1', '8' )");

        db.execSQL("INSERT INTO 'link_albom_track' ( 'idAlbom','idTreack') VALUES ( '2', '9' )");
        db.execSQL("INSERT INTO 'link_albom_track' ( 'idAlbom','idTreack') VALUES ( '2', '10' )");
        db.execSQL("INSERT INTO 'link_albom_track' ( 'idAlbom','idTreack') VALUES ( '2', '11' )");
        db.execSQL("INSERT INTO 'link_albom_track' ( 'idAlbom','idTreack') VALUES ( '2', '12' )");
        db.execSQL("INSERT INTO 'link_albom_track' ( 'idAlbom','idTreack') VALUES ( '2', '13' )");
        db.execSQL("INSERT INTO 'link_albom_track' ( 'idAlbom','idTreack') VALUES ( '2', '14' )");
        db.execSQL("INSERT INTO 'link_albom_track' ( 'idAlbom','idTreack') VALUES ( '2', '15' )");
        db.execSQL("INSERT INTO 'link_albom_track' ( 'idAlbom','idTreack') VALUES ( '2', '16' )");
        db.execSQL("INSERT INTO 'link_albom_track' ( 'idAlbom','idTreack') VALUES ( '2', '17' )");
        db.execSQL("INSERT INTO 'link_albom_track' ( 'idAlbom','idTreack') VALUES ( '2', '18' )");

        db.execSQL("INSERT INTO 'link_albom_track' ( 'idAlbom','idTreack') VALUES ( '3', '19' )");
        db.execSQL("INSERT INTO 'link_albom_track' ( 'idAlbom','idTreack') VALUES ( '3', '20' )");
        db.execSQL("INSERT INTO 'link_albom_track' ( 'idAlbom','idTreack') VALUES ( '3', '21' )");
        db.execSQL("INSERT INTO 'link_albom_track' ( 'idAlbom','idTreack') VALUES ( '3', '22' )");
        db.execSQL("INSERT INTO 'link_albom_track' ( 'idAlbom','idTreack') VALUES ( '3', '23' )");
        db.execSQL("INSERT INTO 'link_albom_track' ( 'idAlbom','idTreack') VALUES ( '3', '24' )");
        db.execSQL("INSERT INTO 'link_albom_track' ( 'idAlbom','idTreack') VALUES ( '3', '25' )");
        db.execSQL("INSERT INTO 'link_albom_track' ( 'idAlbom','idTreack') VALUES ( '3', '26' )");

        db.execSQL("INSERT INTO 'link_albom_track' ( 'idAlbom','idTreack') VALUES ( '4', '27' )");
        db.execSQL("INSERT INTO 'link_albom_track' ( 'idAlbom','idTreack') VALUES ( '4', '28' )");
        db.execSQL("INSERT INTO 'link_albom_track' ( 'idAlbom','idTreack') VALUES ( '4', '29' )");
        db.execSQL("INSERT INTO 'link_albom_track' ( 'idAlbom','idTreack') VALUES ( '4', '30' )");
        db.execSQL("INSERT INTO 'link_albom_track' ( 'idAlbom','idTreack') VALUES ( '4', '31' )");
        db.execSQL("INSERT INTO 'link_albom_track' ( 'idAlbom','idTreack') VALUES ( '4', '32' )");
        db.execSQL("INSERT INTO 'link_albom_track' ( 'idAlbom','idTreack') VALUES ( '4', '33' )");
        db.execSQL("INSERT INTO 'link_albom_track' ( 'idAlbom','idTreack') VALUES ( '4', '34' )");
        db.execSQL("INSERT INTO 'link_albom_track' ( 'idAlbom','idTreack') VALUES ( '4', '35' )");
        db.execSQL("INSERT INTO 'link_albom_track' ( 'idAlbom','idTreack') VALUES ( '4', '36' )");
        db.execSQL("INSERT INTO 'link_albom_track' ( 'idAlbom','idTreack') VALUES ( '4', '37' )");
        db.execSQL("INSERT INTO 'link_albom_track' ( 'idAlbom','idTreack') VALUES ( '4', '38' )");

        db.execSQL("INSERT INTO 'link_albom_track' ( 'idAlbom','idTreack') VALUES ( '5', '39' )");
        db.execSQL("INSERT INTO 'link_albom_track' ( 'idAlbom','idTreack') VALUES ( '5', '40' )");
        db.execSQL("INSERT INTO 'link_albom_track' ( 'idAlbom','idTreack') VALUES ( '5', '41' )");
        db.execSQL("INSERT INTO 'link_albom_track' ( 'idAlbom','idTreack') VALUES ( '5', '42' )");
        db.execSQL("INSERT INTO 'link_albom_track' ( 'idAlbom','idTreack') VALUES ( '5', '43' )");
        db.execSQL("INSERT INTO 'link_albom_track' ( 'idAlbom','idTreack') VALUES ( '5', '44' )");
        db.execSQL("INSERT INTO 'link_albom_track' ( 'idAlbom','idTreack') VALUES ( '5', '45' )");
        db.execSQL("INSERT INTO 'link_albom_track' ( 'idAlbom','idTreack') VALUES ( '5', '46' )");
        db.execSQL("INSERT INTO 'link_albom_track' ( 'idAlbom','idTreack') VALUES ( '5', '47' )");


        db.execSQL("INSERT INTO 'link_albom_track' ( 'idAlbom','idTreack') VALUES ( '6', '48' )");
        db.execSQL("INSERT INTO 'link_albom_track' ( 'idAlbom','idTreack') VALUES ( '6', '49' )");
        db.execSQL("INSERT INTO 'link_albom_track' ( 'idAlbom','idTreack') VALUES ( '6', '50' )");
        db.execSQL("INSERT INTO 'link_albom_track' ( 'idAlbom','idTreack') VALUES ( '6', '51' )");
        db.execSQL("INSERT INTO 'link_albom_track' ( 'idAlbom','idTreack') VALUES ( '6', '52' )");
        db.execSQL("INSERT INTO 'link_albom_track' ( 'idAlbom','idTreack') VALUES ( '6', '53' )");
        db.execSQL("INSERT INTO 'link_albom_track' ( 'idAlbom','idTreack') VALUES ( '6', '54' )");
        db.execSQL("INSERT INTO 'link_albom_track' ( 'idAlbom','idTreack') VALUES ( '6', '55' )");
        db.execSQL("INSERT INTO 'link_albom_track' ( 'idAlbom','idTreack') VALUES ( '6', '56' )");

        db.execSQL("INSERT INTO 'link_albom_track' ( 'idAlbom','idTreack') VALUES ( '7', '57' )");
        db.execSQL("INSERT INTO 'link_albom_track' ( 'idAlbom','idTreack') VALUES ( '7', '58' )");
        db.execSQL("INSERT INTO 'link_albom_track' ( 'idAlbom','idTreack') VALUES ( '7', '59' )");
        db.execSQL("INSERT INTO 'link_albom_track' ( 'idAlbom','idTreack') VALUES ( '7', '60' )");
        db.execSQL("INSERT INTO 'link_albom_track' ( 'idAlbom','idTreack') VALUES ( '7', '61' )");
        db.execSQL("INSERT INTO 'link_albom_track' ( 'idAlbom','idTreack') VALUES ( '7', '62' )");
        db.execSQL("INSERT INTO 'link_albom_track' ( 'idAlbom','idTreack') VALUES ( '7', '63' )");
        db.execSQL("INSERT INTO 'link_albom_track' ( 'idAlbom','idTreack') VALUES ( '7', '64' )");
        db.execSQL("INSERT INTO 'link_albom_track' ( 'idAlbom','idTreack') VALUES ( '7', '65' )");
        db.execSQL("INSERT INTO 'link_albom_track' ( 'idAlbom','idTreack') VALUES ( '7', '66' )");

        db.execSQL("INSERT INTO 'link_albom_track' ( 'idAlbom','idTreack') VALUES ( '8', '67' )");
        db.execSQL("INSERT INTO 'link_albom_track' ( 'idAlbom','idTreack') VALUES ( '8', '68' )");
        db.execSQL("INSERT INTO 'link_albom_track' ( 'idAlbom','idTreack') VALUES ( '8', '69' )");
        db.execSQL("INSERT INTO 'link_albom_track' ( 'idAlbom','idTreack') VALUES ( '8', '70' )");
        db.execSQL("INSERT INTO 'link_albom_track' ( 'idAlbom','idTreack') VALUES ( '8', '71' )");
        db.execSQL("INSERT INTO 'link_albom_track' ( 'idAlbom','idTreack') VALUES ( '8', '72' )");
        db.execSQL("INSERT INTO 'link_albom_track' ( 'idAlbom','idTreack') VALUES ( '8', '73' )");
        db.execSQL("INSERT INTO 'link_albom_track' ( 'idAlbom','idTreack') VALUES ( '8', '74' )");
        db.execSQL("INSERT INTO 'link_albom_track' ( 'idAlbom','idTreack') VALUES ( '8', '75' )");
        db.execSQL("INSERT INTO 'link_albom_track' ( 'idAlbom','idTreack') VALUES ( '8', '76' )");
        db.execSQL("INSERT INTO 'link_albom_track' ( 'idAlbom','idTreack') VALUES ( '8', '77' )");

        db.execSQL("INSERT INTO 'link_albom_track' ( 'idAlbom','idTreack') VALUES ( '9', '78' )");
        db.execSQL("INSERT INTO 'link_albom_track' ( 'idAlbom','idTreack') VALUES ( '9', '79' )");
        db.execSQL("INSERT INTO 'link_albom_track' ( 'idAlbom','idTreack') VALUES ( '9', '80' )");
        db.execSQL("INSERT INTO 'link_albom_track' ( 'idAlbom','idTreack') VALUES ( '9', '81' )");
        db.execSQL("INSERT INTO 'link_albom_track' ( 'idAlbom','idTreack') VALUES ( '9', '82' )");
        db.execSQL("INSERT INTO 'link_albom_track' ( 'idAlbom','idTreack') VALUES ( '9', '83' )");
        db.execSQL("INSERT INTO 'link_albom_track' ( 'idAlbom','idTreack') VALUES ( '9', '84' )");
        db.execSQL("INSERT INTO 'link_albom_track' ( 'idAlbom','idTreack') VALUES ( '9', '85' )");

        db.execSQL("INSERT INTO 'link_albom_track' ( 'idAlbom','idTreack') VALUES ( '10', '86' )");
        db.execSQL("INSERT INTO 'link_albom_track' ( 'idAlbom','idTreack') VALUES ( '10', '87' )");
        db.execSQL("INSERT INTO 'link_albom_track' ( 'idAlbom','idTreack') VALUES ( '10', '88' )");
        db.execSQL("INSERT INTO 'link_albom_track' ( 'idAlbom','idTreack') VALUES ( '10', '89' )");
        db.execSQL("INSERT INTO 'link_albom_track' ( 'idAlbom','idTreack') VALUES ( '10', '90' )");
        db.execSQL("INSERT INTO 'link_albom_track' ( 'idAlbom','idTreack') VALUES ( '10', '91' )");
        db.execSQL("INSERT INTO 'link_albom_track' ( 'idAlbom','idTreack') VALUES ( '10', '92' )");
        db.execSQL("INSERT INTO 'link_albom_track' ( 'idAlbom','idTreack') VALUES ( '10', '93' )");

        db.execSQL("INSERT INTO 'link_albom_track' ( 'idAlbom','idTreack') VALUES ( '11', '94' )");
        db.execSQL("INSERT INTO 'link_albom_track' ( 'idAlbom','idTreack') VALUES ( '11', '95' )");
        db.execSQL("INSERT INTO 'link_albom_track' ( 'idAlbom','idTreack') VALUES ( '11', '96' )");
        db.execSQL("INSERT INTO 'link_albom_track' ( 'idAlbom','idTreack') VALUES ( '11', '97' )");
        db.execSQL("INSERT INTO 'link_albom_track' ( 'idAlbom','idTreack') VALUES ( '11', '98' )");
        db.execSQL("INSERT INTO 'link_albom_track' ( 'idAlbom','idTreack') VALUES ( '11', '99' )");
        db.execSQL("INSERT INTO 'link_albom_track' ( 'idAlbom','idTreack') VALUES ( '11', '100' )");
        db.execSQL("INSERT INTO 'link_albom_track' ( 'idAlbom','idTreack') VALUES ( '11', '101' )");
        db.execSQL("INSERT INTO 'link_albom_track' ( 'idAlbom','idTreack') VALUES ( '11', '102' )");





        //////////////////////




    }

    override fun onUpgrade(p0: SQLiteDatabase, p1: Int, p2: Int) {


    }


}