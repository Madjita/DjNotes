package com.android.samples.arch.componentsbasicsample

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.navigation.Navigation

import android.widget.ArrayAdapter;
import kotlin.properties.Delegates

import android.widget.Toast
import java.util.*

class StartFragment : Fragment() {

    private lateinit var viewModel: StartViewModel

    companion object {
        fun newInstance() = StartFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?

    ): View? {
        return inflater.inflate(R.layout.fragment_start, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // Obtain ViewModel from ViewModelProviders, using this fragment as LifecycleOwner.
        viewModel = ViewModelProviders.of(this).get(StartViewModel::class.java);


        var db = dbHelper.writableDatabase
        var listAlboms = ArrayList<String>();
        var listAlbomsItems = ArrayList<Albom>();
        var cursor = db.rawQuery("SELECT * FROM 'albom'", null);

        if (cursor.moveToFirst()) {

            do {
                val id = cursor.getInt(cursor.getColumnIndex("id"))
                val albomName = cursor.getString(cursor.getColumnIndex("AlbomName"))
                val executor = cursor.getString(cursor.getColumnIndex("Executor"))
                val year = cursor.getString(cursor.getColumnIndex("Year"))
                val dirPng = cursor.getString(cursor.getColumnIndex("DirPng"))
                val teg = cursor.getString(cursor.getColumnIndex("Teg"))

                listAlboms.add(albomName)
                listAlbomsItems.add(Albom(id,albomName,executor,year,dirPng,teg))


            } while (cursor.moveToNext())
        }

        //перевернуть массив
        Collections.reverse(listAlboms)
        Collections.reverse(listAlbomsItems)

        val myListAdapter = AlbomAdapter(this.context as Activity,listAlbomsItems,listAlboms)


      //  val listItems = listOf(listAlboms)
      //  var adapter = ArrayAdapter(this.context, android.R.layout.simple_list_item_1, listAlboms)



        // Observe data on the ViewModel, exposed as a LiveData
        viewModel.data.observe(this, Observer { data ->
            // Set the text exposed by the LiveData
            view?.findViewById<TextView>(R.id.text)?.text = data

            var listView = view?.findViewById<ListView>(R.id.listAlbom)
            listView?.adapter = myListAdapter


        })


//        // Set up a click listener on the login button
        view?.findViewById<Button>(R.id.addAlbom_bt)?.setOnClickListener {
            // Navigate to the login destination
            view?.let { Navigation.findNavController(it).navigate(R.id.addAlbomFragment_action) }
        }


    }



}