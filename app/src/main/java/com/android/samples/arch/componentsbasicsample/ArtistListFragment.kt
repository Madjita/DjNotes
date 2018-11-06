package com.android.samples.arch.componentsbasicsample

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.navigation.Navigation
import org.jetbrains.anko.bundleOf
import java.util.*
import android.R.menu
import android.view.MenuInflater
import android.support.v4.view.MenuItemCompat
import android.widget.*
import android.support.v4.view.MenuItemCompat.expandActionView
import android.support.v7.app.AppCompatActivity


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ArtistListFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ArtistListFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class ArtistListFragment : Fragment() {

    companion object {
        fun newInstance() = StartFragment()
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?

    ): View? {
        return inflater.inflate(R.layout.fragment_artist_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


      //  var tollBar = view?.findViewById(R.id.toolbarArtist) as android.support.v7.widget.Toolbar

       // (activity as AppCompatActivity).setSupportActionBar(tollBar)

        var db = dbHelper.writableDatabase

        var listExecutors = ArrayList<String>();
        var listExecutorsItems = ArrayList<Executor>();

        var cursor = db.rawQuery("SELECT * FROM executor ", null); //LIMIT 4 OFFSET 4

        if (cursor.moveToFirst()) {

            do {
                val id = cursor.getInt(cursor.getColumnIndex("id"))
                val executorName = cursor.getString(cursor.getColumnIndex("ExecutorName"))
                val teg = cursor.getString(cursor.getColumnIndex("Teg"))
                val currentTime = cursor.getLong(cursor.getColumnIndex("Data"))

                listExecutors.add(executorName)
                listExecutorsItems.add(Executor(id,executorName,teg))
                listExecutorsItems.last().setDataMillis(currentTime);

               // val lol = listExecutorsItems.last().getDta();


            } while (cursor.moveToNext())
        }

        //перевернуть массив
        Collections.reverse(listExecutors)
        Collections.reverse(listExecutorsItems)

        val myListAdapter = ExecutorAdapter(this.context as Activity,listExecutorsItems,listExecutors)


        var listView = view?.findViewById<ListView>(R.id.listArtist)

        listView?.adapter = myListAdapter



        view?.findViewById<FloatingActionButton>(R.id.addArtist_bt)?.setOnClickListener {
            // Navigate to the login destination
            view?.let {
                Navigation.findNavController(it).navigate(R.id.addExecutorFragment)
            }
        }

    }

//addExecutorFragment
//    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
//
//        inflater?.inflate(R.menu.menu,menu);
//
//        val searchItem = menu?.findItem(R.id.action_search)
//
//        val searchView = MenuItemCompat.getActionView(searchItem) as SearchView
//        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String): Boolean {
//                // perform query here
//
//                // workaround to avoid issues with some emulators and keyboard devices firing twice if a keyboard enter is used
//                // see https://code.google.com/p/android/issues/detail?id=24599
//                searchView.clearFocus()
//
//                return true
//            }
//
//            override fun onQueryTextChange(newText: String): Boolean {
//                return false
//            }
//        })
//
//
//        // Expand the search view and request focus
//        searchItem?.expandActionView()
//        searchView.requestFocus()
//
//
//        super.onCreateOptionsMenu(menu, inflater)
//    }
}




