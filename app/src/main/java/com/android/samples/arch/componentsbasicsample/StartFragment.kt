package com.android.samples.arch.componentsbasicsample

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.*
import android.widget.*
import androidx.navigation.Navigation

import kotlin.properties.Delegates

import org.jetbrains.anko.bundleOf
import java.util.*

class StartFragment : Fragment() {

    private lateinit var viewModel: StartViewModel

    private var myListAdapter: AlbomAdapter? = null;

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



        //Tollbar seitings
        setHasOptionsMenu(true)
        var tollBar = view?.findViewById(R.id.toolbarAlbom) as android.support.v7.widget.Toolbar
        (activity as AppCompatActivity).setSupportActionBar(tollBar)


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



        tollBar.title = executorItem?.getExecutorName()




        var listAlbomsItems = executorItem?.getItemsAlbomsToExecutor();

        //перевернуть массив
        Collections.reverse(listAlbomsItems)

        myListAdapter = AlbomAdapter(this.context as Activity,listAlbomsItems!!)


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



    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {

        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.albom_menu, menu)

        var mSearch = menu?.findItem(R.id.action_search_albom)

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


}