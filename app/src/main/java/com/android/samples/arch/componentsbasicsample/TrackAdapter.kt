package com.android.samples.arch.componentsbasicsample

import android.app.Activity
import android.app.AlertDialog
import android.graphics.Color
import android.net.Uri
import android.support.v7.widget.CardView
import android.util.Log
import android.view.*
import android.widget.*
import androidx.navigation.Navigation
import org.jetbrains.anko.bundleOf

class TrackAdapter(private val context: Activity, private val listTracks: ArrayList<Track>)//, private val listTrakName: ArrayList<String>)
   // : ArrayAdapter<String>(context, R.layout.custom_listtrack, listTrakName) {


    : BaseAdapter(){


    val mInflater: LayoutInflater = LayoutInflater.from(context)
    private var listFiltered : java.util.ArrayList<Track> = listTracks
    private  var filterText: String = "";


    override fun getCount(): Int {
        return listFiltered.size
    }

    override fun getItem(position: Int): Any {
        return listFiltered[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }




    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.custom_listtrack, null, true)

        val trackText = rowView.findViewById(R.id.trackName) as TextView
        val timeText = rowView.findViewById(R.id.timeTrack) as TextView

        trackText.text = listFiltered[position].getTrackName();
        timeText.text =  listFiltered[position].getTime();


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

                        if(deltaTime > deltaTimeConst) {

                          //  event?.action = MotionEvent.ACTION_UP;
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


                        if(deltaTime < deltaTimeConst)
                        {
                            itrmloyaot.translationX = 0F;

                            //Toast.makeText(context, "Time: " + deltaTime.toString(), Toast.LENGTH_SHORT).show();

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

                            itrmloyaot.setBackgroundColor(Color.parseColor( "#33FF66"))

                            val popup = PopupMenu(context, timeText)
                            popup.menu.add(Menu.NONE, 0, Menu.NONE, "Редактировать")
                            popup.menu.add(Menu.NONE, 1, Menu.NONE, "Удалить")
                            popup.show()
                            popup.setOnDismissListener(PopupMenu.OnDismissListener {

                                itrmloyaot.setBackgroundColor(defaultColor)

                            });

                            popup.setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener {
                                override fun onMenuItemClick(menuItem: MenuItem): Boolean {


                                    when (menuItem.getItemId()) {
                                        0 -> {

                                            itrmloyaot.setBackgroundColor(defaultColor)

                                            v?.let {
                                                var bundle = bundleOf("trackId" to listFiltered[position].getId().toString())
                                                Navigation.findNavController(it).navigate(R.id.editTrackFragment_action,bundle)
                                            }


                                        }
                                        1 -> {
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


                                                db.execSQL("DELETE FROM 'link_albom_track' WHERE idTreack ='"+listFiltered[position].getId()+"'")
                                                db.execSQL("DELETE FROM 'track' WHERE id ='"+listFiltered[position].getId()+"'")

                                                listTracks.remove(listFiltered[position]);

                                                if(filterText.count() > 0 ) {
                                                    listFiltered.removeAt(position);
                                                }

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
                                    }//code
                                    return true
                                }


                            })





                            //////////////////



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



    /////////


    fun getFilter(): Filter {
        var myFilter = object : Filter() {

            override fun performFiltering(constraint: CharSequence?): FilterResults {

                var constraint = constraint
                // NOTE: this function is *always* called from a background thread, and
                // not the UI thread.
                constraint = constraint!!.toString().toLowerCase()

                filterText = constraint;

                val resultFilter = Filter.FilterResults()

                if (constraint != null && constraint.toString().length > 0) {
                    val filt = java.util.ArrayList<Track>()
                    val lItems = listTracks

                    var i = 0
                    val l = lItems.size
                    while (i < l) {
                        val m = lItems[i]
                        if (m.getTrackName().toLowerCase().contains(constraint)) {
                            filt.add(m)
                        }
                        i++
                    }
                    resultFilter.count = filt.size
                    resultFilter.values = filt
                } else {

                    resultFilter.count = listTracks.size
                    resultFilter.values = listTracks

                }

                return resultFilter

            }

            override fun publishResults(contraint: CharSequence, results: FilterResults) {

                listFiltered = results.values as java.util.ArrayList<Track>

                if (listFiltered.size > 0)
                    notifyDataSetChanged();
                else
                    notifyDataSetInvalidated();
            }
        }

        return myFilter
    }


    ////////


}