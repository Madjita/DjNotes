package com.android.samples.arch.componentsbasicsample

import android.app.Activity
import android.app.AlertDialog
import android.graphics.Color
import android.support.v7.widget.CardView
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.Navigation
import org.jetbrains.anko.bundleOf
import java.util.ArrayList

class TrackAdapter(private val context: Activity, private val listTracks: ArrayList<Track>, private val listTrakName: ArrayList<String>)
    : ArrayAdapter<String>(context, R.layout.custom_listtrack, listTrakName) {

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.custom_listtrack, null, true)

        val trackText = rowView.findViewById(R.id.trackName) as TextView
        val timeText = rowView.findViewById(R.id.timeTrack) as TextView

        trackText.text = listTracks[position].getTrackName();
        timeText.text =  listTracks[position].getTime();

        var x_down:Float = 0F;

        var itrmloyaot =  rowView.findViewById(R.id.itemTrack) as CardView

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

                    }

                    MotionEvent.ACTION_MOVE -> {


                        flag_move = true;

                        if(deltaTime > 150) {

                            event?.action = MotionEvent.ACTION_UP;
                            return true


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


                        if(deltaTime < 100)
                        {
                            itrmloyaot.translationX = 0F;

                            Toast.makeText(context, "Time: " + deltaTime.toString(), Toast.LENGTH_SHORT).show();

                            v?.performClick()

                            flag_down = false;
                            flag_move = false;
                            flag_up = false;


//                            v?.let {
//                                Navigation.findNavController(it).navigate(R.id.action_end_dest_to_AddTrackFragment)
//                            }
                        }
                        else
                        {
                            Toast.makeText(context,"Time: " + deltaTime.toString() + " LongTime", Toast.LENGTH_SHORT).show();

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

                                db.execSQL("DELETE FROM 'track' WHERE id ='"+listTracks[position].getId()+"'")

                                listTracks.removeAt(position);
                                listTrakName.removeAt(position);
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