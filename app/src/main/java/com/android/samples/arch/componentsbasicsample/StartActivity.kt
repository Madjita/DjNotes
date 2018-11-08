package com.android.samples.arch.componentsbasicsample

import android.Manifest
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlin.properties.Delegates
import android.util.Log
import android.Manifest.permission
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.support.v4.app.ActivityCompat
import android.content.pm.PackageManager
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog


import android.hardware.Camera;
import android.graphics.SurfaceTexture
import android.hardware.Camera.Parameters.FLASH_MODE_ON
import android.hardware.Camera.Parameters.FLASH_MODE_ON
import java.io.IOException


var dbHelper: MyDatabaseHelper by Delegates.notNull()

var deltaTimeConst: Int = 200;

private val RECORD_REQUEST_CODE = 101
private val RECORD_REQUEST_CODE_CAMERA = 100
private val TAG = "Error"




@Suppress("DEPRECATION")
class StartActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {


        setupPermissions()



        dbHelper = MyDatabaseHelper(this)
        var db = dbHelper.writableDatabase

        var flgaAlbom = false;

        val executor = Executor(0,null,null);
        val albom = Albom(0,null,null,null,null);
        val track = Track(0,null,null,null);


//        db.execSQL("DROP TABLE IF EXISTS '"+executor.TABLE_NAME+"'");
//        db.execSQL("DROP TABLE IF EXISTS '"+albom.TABLE_NAME+"'");
//        db.execSQL("DROP TABLE IF EXISTS '"+track.TABLE_NAME+"'");
//        db.execSQL("DROP TABLE IF EXISTS 'link_executor_to_albom'");
//        db.execSQL("DROP TABLE IF EXISTS 'link_albom_track'");


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
            db.execSQL(executor.CREATE_TABLE);
            db.execSQL(albom.CREATE_TABLE);
            db.execSQL(track.CREATE_TABLE);

            db.execSQL("CREATE TABLE link_executor_to_albom (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "idExecutor INTEGER NOT NULL," +
                    "idAlbom INTEGER NOT NULL," +
                    "FOREIGN KEY (idExecutor) REFERENCES executor(id) ON DELETE CASCADE," +
                    "FOREIGN KEY (idAlbom) REFERENCES albom(id) ON DELETE CASCADE" +
                    ")");

            db.execSQL("CREATE TABLE link_albom_track (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "idAlbom INTEGER NOT NULL," +
                    "idTreack INTEGER NOT NULL," +
                    "FOREIGN KEY (idAlbom) REFERENCES albom(id) ON DELETE CASCADE," +
                    "FOREIGN KEY (idTreack) REFERENCES track(id) ON DELETE CASCADE" +
                    ")");

            var x = 0
            while (x < 10) {
                x++
                db.execSQL("INSERT INTO '"+executor.TABLE_NAME+"' ( 'ExecutorName', 'Teg') VALUES ( 'Executor $x', 'Teg $x' )");

            }

            x = 0;
            while (x < 10) {
                x++
                db.execSQL("INSERT INTO '"+albom.TABLE_NAME+"' ( 'AlbomName', 'Year') VALUES ( 'Albom $x', '$x' )");

            }
            x = 0;

            while (x < 10) {
                x++
                db.execSQL("INSERT INTO '"+track.TABLE_NAME+"' ( 'TrackName', 'Time') VALUES ( 'TrackName $x', '$x:$x' )");

            }

            db.execSQL("INSERT INTO 'link_executor_to_albom' ( 'idExecutor','idAlbom') VALUES ( '1', '1' )");
            db.execSQL("INSERT INTO 'link_executor_to_albom' ( 'idExecutor','idAlbom') VALUES ( '1', '2' )");
            db.execSQL("INSERT INTO 'link_executor_to_albom' ( 'idExecutor','idAlbom') VALUES ( '1', '3' )");
            db.execSQL("INSERT INTO 'link_executor_to_albom' ( 'idExecutor','idAlbom') VALUES ( '2', '1' )");
            db.execSQL("INSERT INTO 'link_executor_to_albom' ( 'idExecutor','idAlbom') VALUES ( '2', '3' )");


            db.execSQL("INSERT INTO 'link_albom_track' ( 'idAlbom','idTreack') VALUES ( '1', '1' )");
            db.execSQL("INSERT INTO 'link_albom_track' ( 'idAlbom','idTreack') VALUES ( '1', '2' )");
            db.execSQL("INSERT INTO 'link_albom_track' ( 'idAlbom','idTreack') VALUES ( '2', '2' )");



        }

        listTable.clear();


        cursor = db.rawQuery("SELECT * FROM 'albom'", null);

        if (cursor.moveToFirst()) {

            do {
                val id = cursor.getInt(cursor.getColumnIndex("id"))
                val albomName = cursor.getString(cursor.getColumnIndex("AlbomName"))
                val year = cursor.getString(cursor.getColumnIndex("Year"))

                listTable.add(albomName)

            } while (cursor.moveToNext())
        }



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);


    }


    private fun setupPermissions() {

        val permission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)

        makeRequest_CAMERA()

    }


    private fun makeRequest_READ_EXTERNAL_STORAGE() {
        ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                RECORD_REQUEST_CODE)
    }

    private fun makeRequest_CAMERA() {
        ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.CAMERA),
                RECORD_REQUEST_CODE_CAMERA)
    }




        override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
            when (requestCode) {
                RECORD_REQUEST_CODE -> {

                    if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {

                        val builder = AlertDialog.Builder(this)
                        builder.setMessage("Для работы приложения нужно разрешение \"Память\".")
                                .setTitle("Ошибка.")

                        builder.setPositiveButton("OK"
                        ) { dialog, id ->
                            Log.i(TAG, "Clicked")
                            makeRequest_READ_EXTERNAL_STORAGE()
                        }

                        val dialog = builder.create()
                        dialog.show()


                    } else {

                        Log.i(TAG, "Пользователь дал разрешение")

                      //  setFlashlightOff();



                    }

                    return;
                }

                RECORD_REQUEST_CODE_CAMERA -> {
                    if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {

                        val builder = AlertDialog.Builder(this)
                        builder.setMessage("Для работы приложения нужно разрешение \"Камера\".")
                                .setTitle("Ошибка.")

                        builder.setPositiveButton("OK"
                        ) { dialog, id ->
                            Log.i(TAG, "Clicked")
                            makeRequest_CAMERA()
                        }

                        val dialog = builder.create()
                        dialog.show()


                    } else {

                        Log.i(TAG, "Пользователь дал разрешение")


                      //  setFlashlightOn()  //Включить фанарик в новом потоке

                        val permission = ContextCompat.checkSelfPermission(this,
                                Manifest.permission.READ_EXTERNAL_STORAGE)

                        makeRequest_READ_EXTERNAL_STORAGE()

                    }

                    return;
                }

            }
        }


    var camera: Camera? = null;

    private fun setFlashlightOn()
    {

        camera = Camera.open();

        var params = camera?.parameters;

        params?.flashMode = Camera.Parameters.FLASH_MODE_ON

        camera?.parameters = params
        camera?.startPreview();


        Thread(Runnable {
            if (camera != null) {
                params = camera?.parameters


                if (params != null) {
                    val supportedFlashModes = params?.supportedFlashModes


                    if (supportedFlashModes!!.contains(Camera.Parameters.FLASH_MODE_TORCH)) {
                        params?.flashMode = Camera.Parameters.FLASH_MODE_TORCH
                    } else if (supportedFlashModes!!.contains(Camera.Parameters.FLASH_MODE_ON)) {
                        params?.flashMode = Camera.Parameters.FLASH_MODE_ON
                    } else camera = null


                    if (camera != null) {
                        camera?.parameters = params
                        camera?.startPreview()
                        try {
                            camera?.setPreviewTexture(SurfaceTexture(0))


                        } catch (e: IOException) {
                            e.printStackTrace()
                        }

                    }
                }
            }
        }).start()
    }


    private fun setFlashlightOff() {
        Thread(Runnable {
            if (camera != null) {
                var params = camera?.parameters;

                params?.setFlashMode(Camera.Parameters.FLASH_MODE_OFF)
                camera?.setParameters(params)
                camera?.stopPreview()
            }
        }).start()
    }



}
