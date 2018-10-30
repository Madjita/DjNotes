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

        // Observe data on the ViewModel, exposed as a LiveData
        viewModel.data.observe(this, Observer { data ->
            // Set the text exposed by the LiveData
            view?.findViewById<TextView>(R.id.text)?.text = data
        })

    }
}