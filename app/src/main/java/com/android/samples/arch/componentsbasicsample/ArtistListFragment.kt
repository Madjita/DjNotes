package com.android.samples.arch.componentsbasicsample

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.navigation.Navigation
import org.jetbrains.anko.bundleOf
import java.util.*


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


        var db = dbHelper.writableDatabase

        var listExecutors = ArrayList<String>();
        var listExecutorsItems = ArrayList<Executor>();

        var cursor = db.rawQuery("SELECT * FROM executor", null);

        if (cursor.moveToFirst()) {

            do {
                val id = cursor.getInt(cursor.getColumnIndex("id"))
                val executorName = cursor.getString(cursor.getColumnIndex("ExecutorName"))
                val teg = cursor.getString(cursor.getColumnIndex("Teg"))

                listExecutors.add(executorName)
                listExecutorsItems.add(Executor(id,executorName,teg))


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
}
