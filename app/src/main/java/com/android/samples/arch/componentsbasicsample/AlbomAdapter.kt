package com.android.samples.arch.componentsbasicsample

import android.app.Activity
import android.app.AlertDialog
import android.app.Fragment
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Handler
import android.support.v7.widget.CardView
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityEvent
import android.widget.*
import androidx.navigation.Navigation

import org.jetbrains.anko.bundleOf
import java.util.*
import android.util.DisplayMetrics
import java.io.File
import java.net.URL
import java.nio.file.Files.exists
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import android.net.Uri.parse
import android.os.Environment
import android.support.v4.provider.DocumentFile
import org.jetbrains.anko.Android
import java.net.URI
import java.nio.file.Files.exists




class AlbomAdapter(private val context: Activity, private val listAlboms: ArrayList<Albom>,private val listTitle: ArrayList<String>)
    : ArrayAdapter<String>(context, R.layout.custom_listalbom, listTitle) {

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.custom_listalbom, null, true)

        val titleText = rowView.findViewById(R.id.title) as TextView
        val imageView = rowView.findViewById(R.id.icon) as ImageView
        val executerText = rowView.findViewById(R.id.executer) as TextView
        val yearText = rowView.findViewById(R.id.year) as TextView

        titleText.text = listAlboms[position].getAlbomName();

        var path = listAlboms[position].getDirPng();
        var uri = Uri.parse(path);



        if(path != "null")
        {

            imageView.setImageURI(null);
            imageView.setImageURI(uri);


//
//            val imgFile = File(path)
//
//            if (imgFile.exists()) {
//
//
//                val myBitmap = BitmapFactory.decodeFile(path)
//                imageView.setImageBitmap(myBitmap)
//
//
//            }


        }

        yearText.text = listAlboms[position].getYear()

        var x:Int = 0;

        var x_down:Float = 0F;

        var itrmloyaot =  rowView.findViewById(R.id.item) as CardView

        var defaultColor = itrmloyaot.solidColor

        var flag_down:Boolean = false;
        var flag_up:Boolean = false;
        var flag_move:Boolean = false;


        var time:Long = 0;


        var db = dbHelper.writableDatabase



        rowView.setOnTouchListener (object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {

                var deltaTime = System.currentTimeMillis()-time

                when (event?.action) {


                    MotionEvent.ACTION_DOWN -> {

                        time = System.currentTimeMillis();

                        flag_down = true
                        flag_move = false;
                        flag_up = false;



                        x_down = event?.getX()

                        Log.w("ACTION_DOWN", x_down.toString());
                    }

                    MotionEvent.ACTION_MOVE -> {


                        flag_move = true;

                        if(deltaTime > deltaTimeConst) {

                          //      event?.action = MotionEvent.ACTION_UP;
                            //    return true


//                            if((x_down-event?.getX()) > 200 )
//                            {
//
//                                itrmloyaot.translationX = (-250).toFloat();
//
//                               // itrmloyaot.setPaddingRelative(0, 0, 200, 0)
//
//                                Log.w("MOVE true", (x_down-event?.getX()).toString());
//
//                            }
//                            else
//                            {
//                                Log.w("MOVE false", (x_down-event?.getX()).toString());
//
//                                event?.action = MotionEvent.ACTION_UP;
//                                return v?.onTouchEvent(event) ?: true
//                            }

                        }

                    }

                    MotionEvent.ACTION_UP -> {
                        flag_move = false;
                        flag_up = true;
                        x = 0;


                        //itrmloyaot.setPadding(0,0,0,0)



                        if(deltaTime < deltaTimeConst)
                        {
                            itrmloyaot.translationX = 0F;

                            //Toast.makeText(context, "Time: " + deltaTime.toString(), Toast.LENGTH_SHORT).show();

                            v?.performClick()

                            flag_down = false;
                            flag_move = false;
                            flag_up = false;


                            v?.let {

                                var bundle = bundleOf("albomItem" to listAlboms[position].getId().toString())
                                Navigation.findNavController(it).navigate(R.id.end_action, bundle)
                            }
                        }
                        else
                        {
                            //Toast.makeText(context,"Time: " + deltaTime.toString() + " LongTime", Toast.LENGTH_SHORT).show();

                            // Change the app background color
                            itrmloyaot.setBackgroundColor(Color.RED)

                            // Initialize a new instance of
                            val builder = AlertDialog.Builder(context)

                            // Set the alert dialog title
                            builder.setTitle("Удаление")

                            // Display a message on alert dialog
                            builder.setMessage("Удалить элемент?")

                            // Set a positive button and its click listener on alert dialog
                            builder.setPositiveButton("Да"){dialog, which ->

                                //Удалить все треки прикрепленные к этому альбому
                                var listT = listAlboms[position].getAllTrack();

                                for (item in listT)
                                {
                                    db.execSQL("DELETE FROM ${item.TABLE_NAME} WHERE id ='"+item.getId()+"'")
                                }

                                //Удалить альбом
                                db.execSQL("DELETE FROM 'albom' WHERE id ='"+listAlboms[position].getId()+"'")

                                listTitle.removeAt(position);
                                listAlboms.removeAt(position);
                                notifyDataSetChanged();


                            }


                            // Display a negative button on alert dialog
                            builder.setNegativeButton("Нет"){dialog,which ->

                                // Change the app background color
                                itrmloyaot.setBackgroundColor(defaultColor)

                            }


                            // Display a neutral button on alert dialog
                            builder.setNeutralButton("Отмена"){_,_ ->

                                // Change the app background color
                                itrmloyaot.setBackgroundColor(defaultColor)

                            }

                            // Finally, make the alert dialog using builder
                            val dialog: AlertDialog = builder.create()

                            // Display the alert dialog on app interface
                            dialog.show()

                        }

                    }

//                    MotionEvent.ACTION_CANCEL -> {
//                        flag_down = false;
//                        flag_move = false;
//                        flag_up = false;
//
//                        Toast.makeText(context, "Cansel", Toast.LENGTH_SHORT).show();
//
//                    }



                }

                return true
            }
        })
        


        return rowView
    }


}