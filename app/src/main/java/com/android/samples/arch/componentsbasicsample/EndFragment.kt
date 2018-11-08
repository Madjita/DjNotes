package com.android.samples.arch.componentsbasicsample

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.media.Image
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CollapsingToolbarLayout
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.ActionBarContextView
import android.util.Log
import android.view.*
import android.widget.*
import androidx.navigation.Navigation
import org.jetbrains.anko.bundleOf
import java.util.ArrayList
import com.android.samples.arch.componentsbasicsample.R.id.item



class EndFragment : Fragment() {

    private lateinit var viewModel: EndViewModel

    private var myListAdapter: TrackAdapter? = null;
    lateinit var mSearch: MenuItem;

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

        //Tollbar seitings
        setHasOptionsMenu(true)
        var tollBar = view?.findViewById(R.id.toolbarTrack) as android.support.v7.widget.Toolbar
        (activity as AppCompatActivity).setSupportActionBar(tollBar)


        val id = arguments?.getString("albomItem")?.toInt();


        var db = dbHelper.writableDatabase

        var albom: Albom? = null;

        var cursor = db.rawQuery("SELECT * FROM 'albom' Where id='$id'", null);

        if (cursor.moveToFirst()) {

            do {
                val id = cursor.getInt(cursor.getColumnIndex("id"))
                val albomName = cursor.getString(cursor.getColumnIndex("AlbomName"))
                val year = cursor.getString(cursor.getColumnIndex("Year"))
                val dirPng = cursor.getString(cursor.getColumnIndex("DirPng"))
                val teg = cursor.getString(cursor.getColumnIndex("Teg"))
                val currentTime = cursor.getLong(cursor.getColumnIndex("Data"))

                albom = Albom(id,albomName,year,dirPng,teg);
                albom.setDataMillis(currentTime)


            } while (cursor.moveToNext())
        }

        tollBar.title = albom?.getAlbomName()


        var mCollapsingToolbar = view?.findViewById(R.id.collapsing) as CollapsingToolbarLayout

        mCollapsingToolbar.title = albom?.getAlbomName()


        var addTrack = view?.findViewById(R.id.addTrack) as FloatingActionButton


        addTrack.setOnClickListener{

            view?.let {
                var bundle = bundleOf("albomId" to id.toString())
                Navigation.findNavController(it).navigate(R.id.action_end_dest_to_AddTrackFragment, bundle)
            }

        }


        var listTrackItems = ArrayList<Track>();

       // cursor = db.rawQuery("SELECT * FROM 'track'", null);

        var zapros = "SELECT track.id,TrackName,Time,track.Teg,track.Data FROM 'track' " +
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
                val currentTime = cursor.getLong(cursor.getColumnIndex("Data"))


                listTrackItems.add(Track(id,trackName,time,teg))
                listTrackItems.last().setDataMillis(currentTime)

            } while (cursor.moveToNext())
        }

        myListAdapter = TrackAdapter(this.context as Activity,listTrackItems)



        // Observe data on the ViewModel, exposed as a LiveData
        viewModel.data.observe(this, Observer { data ->

            // Set the text exposed by the LiveData
           // view?.findViewById<TextView>(R.id.text)?.text = albom?.getExecutor()


            var path = albom?.getDirPng();

            if(path != "null")
            {
                view?.findViewById<ImageView>(R.id.backdrop)?.setImageURI(Uri.parse(path));
            }


            var listView = view?.findViewById<ListView>(R.id.listTrack)
            listView?.adapter = myListAdapter


            val mAppBarLayout = view?.findViewById(R.id.appbar) as AppBarLayout

            mAppBarLayout.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
                var isShow = false
                var scrollRange = -1

                override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                    if (scrollRange == -1) {
                        scrollRange = appBarLayout.totalScrollRange
                    }
                    if (scrollRange + verticalOffset == 0) {

                        if(isShow == false)
                        {
                            showOption()
                            Log.w("ERROR SCROLL true ", (!isShow).toString());
                        }
                        isShow = true


                    } else if (isShow) {
                        isShow = false
                        Log.w("ERROR SCROLL false", isShow.toString());
                        hideOption()
                    }
                }
            })
        })











    }


    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {

        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.track_menu, menu)



        mSearch = menu?.findItem(R.id.action_search_track)!!

        hideOption();


        var mSearchView = mSearch?.actionView as  android.support.v7.widget.SearchView
        mSearchView.queryHint = "Search"

        mSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener, android.support.v7.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                myListAdapter?.getFilter()?.filter(newText)

                return true
            }
        })

    }

    private fun hideOption() {
        //var item = menu.findItem(id)
        mSearch.isVisible = false
    }

    private fun showOption() {
       // var item = menu.findItem(id)
        mSearch.isVisible = true
    }


}