package com.android.samples.arch.componentsbasicsample

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.Navigation

import kotlin.properties.Delegates

import org.jetbrains.anko.bundleOf
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

        val id = arguments?.getString("executorItem")?.toInt();


        var db = dbHelper.writableDatabase


        var executorItem:Executor? = null;

        var cursor = db.rawQuery("SELECT * FROM 'executor' WHERE id = $id", null);

        if (cursor.moveToFirst()) {

            do {
                val id = cursor.getInt(cursor.getColumnIndex("id"))
                val executorName = cursor.getString(cursor.getColumnIndex("ExecutorName"))
                val teg = cursor.getString(cursor.getColumnIndex("Teg"))
                val currentTime = cursor.getLong(cursor.getColumnIndex("Data"))

                executorItem = Executor(id,executorName,teg)
                executorItem.setDataMillis(currentTime)

            } while (cursor.moveToNext())
        }


        var listAlboms = ArrayList<String>();
        var listAlbomsItems = executorItem?.getItemsAlbomsToExecutor();


        for (item in listAlbomsItems!!)
        {
            listAlboms.add(item.getAlbomName());
        }



        //перевернуть массив
        Collections.reverse(listAlboms)
        Collections.reverse(listAlbomsItems)

        val myListAdapter = AlbomAdapter(this.context as Activity,listAlbomsItems!!,listAlboms)


        // Observe data on the ViewModel, exposed as a LiveData
        viewModel.data.observe(this, Observer { data ->
            // Set the text exposed by the LiveData
            view?.findViewById<TextView>(R.id.text)?.text = data

            var listView = view?.findViewById<ListView>(R.id.listAlbom)
            listView?.adapter = myListAdapter


        })


//        // Set up a click listener on the login button
        view?.findViewById<FloatingActionButton>(R.id.addAlbom_bt)?.setOnClickListener {
            // Navigate to the login destination
            view?.let {
                var bundle = bundleOf("executorId" to id.toString())
                Navigation.findNavController(it).navigate(R.id.addAlbomFragment_action,bundle)
            }
        }

    }



}