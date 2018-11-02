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



class ExecutorAdapter(private val context: Activity, private val listExecutors: ArrayList<Executor>, private val listTitle: ArrayList<String>)
    : ArrayAdapter<String>(context, R.layout.custom_listexecutor, listTitle) {

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.custom_listexecutor, null, true)

        val executorName = rowView.findViewById(R.id.ExecutorName) as TextView
        val countAlbomsToExecutor = rowView.findViewById(R.id.countAlbomsToExecutor) as TextView
        var itrmloyaot =  rowView.findViewById(R.id.itemExecutor) as CardView


        var listAlboms = listExecutors[position].getItemsAlbomsToExecutor();


        executorName.text = listExecutors[position].getExecutorName();
        countAlbomsToExecutor.text = listAlboms.count().toString()

        var db = dbHelper.writableDatabase

        var x:Int = 0;
        var x_down:Float = 0F;



        var defaultColor = itrmloyaot.solidColor

        var flag_down:Boolean = false;
        var flag_up:Boolean = false;
        var flag_move:Boolean = false;


        var time:Long = 0;



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

                           // event?.action = MotionEvent.ACTION_UP;
                          //  return true


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

                                var bundle = bundleOf("executorItem" to listExecutors[position].getId().toString())
                                Navigation.findNavController(it).navigate(R.id.action_artistListFragment_to_launcher_home, bundle)
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



                                for (item in listAlboms)
                                {
                                    var listT = item.getAllTrack();

                                    for (item in listT)
                                    {
                                        db.execSQL("DELETE FROM ${item.TABLE_NAME} WHERE id ='"+item.getId()+"'")
                                    }

                                    //Удалить альбом
                                    db.execSQL("DELETE FROM ${item.TABLE_NAME} WHERE id ='"+item.getId()+"'")
                                }

                                db.execSQL("DELETE FROM 'executor' WHERE id ='"+listExecutors[position].getId()+"'")

                                listTitle.removeAt(position);
                                listExecutors.removeAt(position);
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