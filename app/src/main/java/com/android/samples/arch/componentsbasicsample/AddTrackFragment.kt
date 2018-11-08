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
import androidx.navigation.Navigation
import java.util.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [AddTrackFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [AddTrackFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class AddTrackFragment : Fragment() {


    companion object {
        fun newInstance() = StartFragment()
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?

    ): View? {
        return inflater.inflate(R.layout.fragment_add_track, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        var db = dbHelper.writableDatabase

        val id = arguments?.getString("albomId")?.toInt();

        view?.findViewById<Button>(R.id.buttonAddTreck)?.setOnClickListener {


            var trackName =  view?.findViewById<EditText>(R.id.editText_trackName)?.text.toString();
            var timeTrack =  view?.findViewById<EditText>(R.id.editText_timeTrack)?.text.toString();
            var teg =  view?.findViewById<EditText>(R.id.editText_tegTrack)?.text.toString();

            var list: ArrayList<String>? = null;

            list?.add(trackName);
            list?.add(timeTrack);
            list?.add(teg);

            val currentTime = Calendar.getInstance().getTimeInMillis()

            db.execSQL("INSERT INTO 'track' ( 'TrackName', 'Time','Teg','Data') VALUES ( '$trackName', '$timeTrack','$teg','$currentTime')");


            var cursor = db.rawQuery("SELECT * FROM 'track' WHERE TrackName = '$trackName' AND Data = '$currentTime'", null);

            if (cursor.moveToFirst()) {

                do {
                    val idTreack = cursor.getInt(cursor.getColumnIndex("id"))
                    db.execSQL("INSERT INTO 'link_albom_track' ( 'idAlbom','idTreack') VALUES ( '$id', '$idTreack' )");

                } while (cursor.moveToNext())
            }




            //Закрытие клавиатуры
            val imm = view?.context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view?.getWindowToken(),
                    InputMethodManager.RESULT_UNCHANGED_SHOWN);


            view?.let { Navigation.findNavController(it).popBackStack() }


        }


    }

}
