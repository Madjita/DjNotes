package com.android.samples.arch.componentsbasicsample

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.Navigation
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


        // Observe data on the ViewModel, exposed as a LiveData
        viewModel.data.observe(this, Observer { data ->

            // Set the text exposed by the LiveData
            view?.findViewById<TextView>(R.id.text)?.text = albom?.getExecutor()
        })

    }

    public fun setData(data:String)
    {
        view?.findViewById<TextView>(R.id.text)?.text = data
    }

}