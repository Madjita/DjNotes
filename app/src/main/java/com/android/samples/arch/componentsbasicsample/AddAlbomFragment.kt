package com.android.samples.arch.componentsbasicsample

import android.app.Activity
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.Navigation
import org.w3c.dom.Text
import java.util.ArrayList
import java.util.Observer


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [AddAlbomFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [AddAlbomFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class AddAlbomFragment : Fragment() {

    private lateinit var viewModel: EndViewModel

    companion object {
        fun newInstance() = StartFragment()
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?

    ): View? {
        return inflater.inflate(R.layout.fragment_add_albom, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        var db = dbHelper.writableDatabase



        view?.findViewById<Button>(R.id.button)?.setOnClickListener {


           var albomName =  view?.findViewById<EditText>(R.id.editText_albom)?.text.toString();
           var executer =  view?.findViewById<EditText>(R.id.editText_executor)?.text.toString();
            var year =  view?.findViewById<EditText>(R.id.editText_year)?.text.toString();
            var dirpng =  view?.findViewById<EditText>(R.id.editText_dirpng)?.text.toString();
            var teg =  view?.findViewById<EditText>(R.id.editText_teg)?.text.toString();

            var list: ArrayList<String>? = null;

            list?.add(albomName);
            list?.add(executer);
            list?.add(year);
            list?.add(dirpng);
            list?.add(teg);

            db.execSQL("INSERT INTO 'albom' ( 'AlbomName', 'Executor', 'Year') VALUES ( '$albomName', '$executer','$year' )");

            view?.let { Navigation.findNavController(it).popBackStack() }



           // Toast.makeText(context, list,Toast.LENGTH_LONG).show();
        }




    }

}
