package com.android.samples.arch.componentsbasicsample

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.*

class AlbomAdapter(private val context: Activity, private val listAlboms: ArrayList<Albom>,private val listTitle: ArrayList<String>)
    : ArrayAdapter<String>(context, R.layout.custom_listalbom, listTitle) {

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.custom_listalbom, null, true)

        val titleText = rowView.findViewById(R.id.title) as TextView
        val imageView = rowView.findViewById(R.id.icon) as ImageView
        val executerText = rowView.findViewById(R.id.executer) as TextView
        val yearText = rowView.findViewById(R.id.year) as TextView

        titleText.text = listAlboms[position].getAlbomName();
       // imageView.setImageResource(listAlboms[position].getDirPng())
        executerText.text =  listAlboms[position].getExecutor()
        yearText.text = listAlboms[position].getYear()

        return rowView
    }
}