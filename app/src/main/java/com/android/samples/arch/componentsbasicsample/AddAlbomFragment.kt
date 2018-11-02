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
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.navigation.Navigation
import org.w3c.dom.Text
import java.util.ArrayList
import java.util.Observer
import android.view.inputmethod.InputMethodManager.HIDE_NOT_ALWAYS
import android.content.Context.INPUT_METHOD_SERVICE






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

        val id = arguments?.getString("executorId")?.toInt();

        view?.findViewById<Button>(R.id.button)?.setOnClickListener {


           var albomName =  view?.findViewById<EditText>(R.id.editText_albom)?.text.toString();
            var year =  view?.findViewById<EditText>(R.id.editText_year)?.text.toString();
            var dirpng =  view?.findViewById<EditText>(R.id.editText_dirpng)?.text.toString();
            var teg =  view?.findViewById<EditText>(R.id.editText_teg)?.text.toString();

            var list: ArrayList<String>? = null;

            list?.add(albomName);
            list?.add(year);
            list?.add(dirpng);
            list?.add(teg);

            db.execSQL("INSERT INTO 'albom' ( 'AlbomName', 'Year','DirPng','Teg') VALUES ( '$albomName','$year','$dirpng','$teg')");


            var cursor = db.rawQuery("SELECT id FROM 'albom' WHERE AlbomName = '$albomName'", null);

            if (cursor.moveToFirst()) {

                do {
                    val idAlbom = cursor.getInt(cursor.getColumnIndex("id"))
                    db.execSQL("INSERT INTO 'link_executor_to_albom' ( 'idAlbom','idExecutor') VALUES ( '$idAlbom', '$id' )");

                } while (cursor.moveToNext())
            }



            //Закрытие клавиатуры
            val imm = view?.context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view?.getWindowToken(),
                    InputMethodManager.RESULT_UNCHANGED_SHOWN);


            view?.let { Navigation.findNavController(it).popBackStack() }


        }

    }

}
