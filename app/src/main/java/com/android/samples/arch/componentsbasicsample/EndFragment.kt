package com.android.samples.arch.componentsbasicsample

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.CollapsingToolbarLayout
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.widget.ActionBarContextView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.navigation.Navigation
import org.jetbrains.anko.bundleOf
import java.util.ArrayList

class EndFragment : Fragment() {

    private lateinit var viewModel: EndViewModel

    companion object {
        fun newInstance() = StartFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_end, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // Obtain ViewModel from ViewModelProviders, using this fragment as LifecycleOwner.
        viewModel = ViewModelProviders.of(this).get(EndViewModel::class.java)


        val id = arguments?.getString("albomItem")?.toInt();


        var db = dbHelper.writableDatabase

        var albom: Albom? = null;

        var cursor = db.rawQuery("SELECT * FROM 'albom' Where id='$id'", null);

        if (cursor.moveToFirst()) {

            do {
                val id = cursor.getInt(cursor.getColumnIndex("id"))
                val albomName = cursor.getString(cursor.getColumnIndex("AlbomName"))
                val executor = cursor.getString(cursor.getColumnIndex("Executor"))
                val year = cursor.getString(cursor.getColumnIndex("Year"))
                val dirPng = cursor.getString(cursor.getColumnIndex("DirPng"))
                val teg = cursor.getString(cursor.getColumnIndex("Teg"))

                albom = Albom(id,albomName,executor,year,dirPng,teg);



            } while (cursor.moveToNext())
        }


        var mCollapsingToolbar = view?.findViewById(R.id.collapsing) as CollapsingToolbarLayout

        mCollapsingToolbar.title = albom?.getAlbomName()


        var addTrack = view?.findViewById(R.id.addTrack) as FloatingActionButton


        addTrack.setOnClickListener{

            view?.let {
                var bundle = bundleOf("albomId" to id.toString())
                Navigation.findNavController(it).navigate(R.id.action_end_dest_to_AddTrackFragment, bundle)
            }

        }



        var listTracks = ArrayList<String>();
        var listTrackItems = ArrayList<Track>();

       // cursor = db.rawQuery("SELECT * FROM 'track'", null);

        var zapros = "SELECT track.id,TrackName,Time,track.Teg FROM 'track' " +
                "JOIN link_albom_track ON link_albom_track.idTreack = track.id " +
                "JOIN  albom ON link_albom_track.idAlbom = albom.id "+
                "WHERE albom.id = '"+id+"'";

        cursor = db.rawQuery(zapros, null);

        if (cursor.moveToFirst()) {

            do {
                val id = cursor.getInt(cursor.getColumnIndex("id"))
                val trackName = cursor.getString(cursor.getColumnIndex("TrackName"))
                val time = cursor.getString(cursor.getColumnIndex("Time"))
                val teg = cursor.getString(cursor.getColumnIndex("Teg"))

                listTracks.add(trackName)
                listTrackItems.add(Track(id,trackName,time,teg))

            } while (cursor.moveToNext())
        }

        val myListAdapter = TrackAdapter(this.context as Activity,listTrackItems,listTracks)



        // Observe data on the ViewModel, exposed as a LiveData
        viewModel.data.observe(this, Observer { data ->

            // Set the text exposed by the LiveData
           // view?.findViewById<TextView>(R.id.text)?.text = albom?.getExecutor()

            var listView = view?.findViewById<ListView>(R.id.listTrack)
            listView?.adapter = myListAdapter
        })

    }


}