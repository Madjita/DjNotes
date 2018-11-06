package com.android.samples.arch.componentsbasicsample

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.navigation.Navigation
import java.util.ArrayList


class EditTrackFragment : Fragment() {

    companion object {
        fun newInstance() = StartFragment()
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?

    ): View? {
        return inflater.inflate(R.layout.fragment_edittrack, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        var db = dbHelper.writableDatabase

        val id = arguments?.getString("trackId")?.toInt();


        var track: Track? = null;

        var cursor = db.rawQuery("SELECT * FROM 'track' Where id='$id'", null);

        if (cursor.moveToFirst()) {

            do {
                val id = cursor.getInt(cursor.getColumnIndex("id"))
                val trackName = cursor.getString(cursor.getColumnIndex("TrackName"))
                val time = cursor.getString(cursor.getColumnIndex("Time"))
                val teg = cursor.getString(cursor.getColumnIndex("Teg"))

                track = Track(id,trackName,time,teg);

            } while (cursor.moveToNext())
        }

        var trackName_current =  view?.findViewById<TextView>(R.id.textView_currentTrackName);
        var time_current =  view?.findViewById<TextView>(R.id.textView_currentTackTime);
        var teg_current =  view?.findViewById<TextView>(R.id.textView_currentTrackTeg);

        if (track != null) {

            trackName_current?.text = track.getTrackName();
            time_current?.text = track.getTime();
            teg_current?.text = track.getTeg();

        };

        view?.findViewById<Button>(R.id.buttonAddTreck)?.setOnClickListener {


            var trackName =  view?.findViewById<EditText>(R.id.editText_trackName)?.text.toString();
            var timeTrack =  view?.findViewById<EditText>(R.id.editText_timeTrack)?.text.toString();
            var teg =  view?.findViewById<EditText>(R.id.editText_tegTrack)?.text.toString();

            if(trackName == "")
            {
                trackName = track!!.getTrackName();
            }

            if(timeTrack == "")
            {
                timeTrack = track!!.getTime();
            }

            if(teg == "")
            {
                teg = track!!.getTeg();
            }


            db.execSQL("UPDATE 'track' SET TrackName = '$trackName', Time = '$timeTrack' , Teg = '$teg' WHERE id = $id");


            //Закрытие клавиатуры
            val imm = view?.context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view?.getWindowToken(),
                    InputMethodManager.RESULT_UNCHANGED_SHOWN);


            view?.let { Navigation.findNavController(it).popBackStack() }


        }

    }

}
