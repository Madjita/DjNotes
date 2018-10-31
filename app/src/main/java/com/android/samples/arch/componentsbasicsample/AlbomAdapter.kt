package com.android.samples.arch.componentsbasicsample

import android.app.Activity
import android.app.Fragment
import android.os.Handler
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityEvent
import android.widget.*
import androidx.navigation.Navigation

import org.jetbrains.anko.bundleOf
import java.util.*

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
       // imageView.setImageResource(listAlboms[position].getDirPng())
        executerText.text =  listAlboms[position].getExecutor()
        yearText.text = listAlboms[position].getYear()

        var x:Int = 0;

        var x_down:Float = 0F;

        var itrmloyaot =  rowView.findViewById(R.id.item) as LinearLayout


        var flag_down:Boolean = false;
        var flag_up:Boolean = false;
        var flag_move:Boolean = false;

        val timer:Timer = Timer();

        var time:Long = 0;


//        rowView.setOnClickListener {
//
//            if(flag_down && flag_up && (flag_move == false)) {
//
//                flag_down = false;
//                flag_move = false;
//                flag_up = false;
//
//                rowView?.let {
//
//                    var bundle = bundleOf("albomItem" to listAlboms[position].getId().toString())
//                    Navigation.findNavController(it).navigate(R.id.end_action, bundle)
//                }
//
//            }
//
//        }


       var del_button =  rowView.findViewById(R.id.albom_del) as Button

        var db = dbHelper.writableDatabase


        del_button.setOnClickListener {


          //  db.execSQL("DELETE FROM 'albom' WHERE id '"+position+"'")


        }




        rowView.setOnTouchListener (object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {

                var deltaTime = System.currentTimeMillis()-time

                when (event?.action) {
                    MotionEvent.ACTION_CANCEL -> {
                        flag_down = false;
                        flag_move = false;
                        flag_up = false;

                        Toast.makeText(context, "Cansel", Toast.LENGTH_LONG).show();

                    }

                    MotionEvent.ACTION_DOWN -> {

                        time = System.currentTimeMillis();

                        flag_down = true
                        flag_move = false;
                        flag_up = false;

                        x_down = event?.getX()
                    }

                    MotionEvent.ACTION_MOVE -> {


                        flag_move = true;

                        if(deltaTime > 100) {
//                            if (x < 100) {
//                                x += 5;
//                                itrmloyaot.setPaddingRelative(0, 0, x, 0)
//                            }
//                            else
//                            {
//                                event?.action = MotionEvent.ACTION_UP;
//                                return v?.onTouchEvent(event) ?: true
//                            }


                            if((x_down-event?.getX()) > 500 )
                            {
                                itrmloyaot.setPaddingRelative(0, 0, 200, 0)

                                Log.w("MOVE true", (x_down-event?.getX()).toString());

                            }
                            else
                            {
                                Log.w("MOVE false", (x_down-event?.getX()).toString());
                            }

                        }

                    }

                    MotionEvent.ACTION_UP -> {
                        flag_move = false;
                        flag_up = true;
                        x = 0;

                        itrmloyaot.setPadding(0,0,0,0)



                        if(deltaTime < 100)
                        {
                            Toast.makeText(context, "Time: " + deltaTime.toString(), Toast.LENGTH_LONG).show();

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
                            Toast.makeText(context,"Time: " + deltaTime.toString() + " LongTime", Toast.LENGTH_LONG).show();
                        }

                    }



                }

                return true
            }
        })
        


        return rowView
    }

    



}