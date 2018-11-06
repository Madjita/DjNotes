package com.android.samples.arch.componentsbasicsample

import android.app.Activity
import android.app.Activity.RESULT_OK
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
import android.view.inputmethod.InputMethodManager.HIDE_NOT_ALWAYS
import android.content.Context.INPUT_METHOD_SERVICE
import android.content.Intent
import android.provider.MediaStore
import android.graphics.Bitmap
import java.io.ByteArrayOutputStream
import java.io.File
import java.net.URI
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.os.Environment
import java.util.*


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

    private lateinit var dirpng: String

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

        dirpng = "null";


        view?.findViewById<Button>(R.id.find)?.setOnClickListener {

            val intent = Intent(Intent.ACTION_SEND)
                    .setType("image/*")//*/*
                    .setAction(Intent.ACTION_GET_CONTENT)

            startActivityForResult(Intent.createChooser(intent, "Select a file"), 111)

        }

        view?.findViewById<Button>(R.id.button)?.setOnClickListener {


           var albomName =  view?.findViewById<EditText>(R.id.editText_albom)?.text.toString();
            var year =  view?.findViewById<EditText>(R.id.editText_year)?.text.toString();
            var teg =  view?.findViewById<EditText>(R.id.editText_teg)?.text.toString();


            var list: ArrayList<String>? = null;

            list?.add(albomName);
            list?.add(year);
           // list?.add(dirpng);
            list?.add(teg);

            val currentTime = Calendar.getInstance().getTimeInMillis()

            db.execSQL("INSERT INTO 'albom' ( 'AlbomName', 'Year','DirPng','Teg','Data') VALUES ( '$albomName','$year','$dirpng','$teg','$currentTime')");


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


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 111 && resultCode == RESULT_OK) {

            var img = view?.findViewById<ImageView>(R.id.img);


            val selectedFile = data?.data!!//The uri with the location of the file

           img?.setImageURI(selectedFile);


            var path = getPath(selectedFile);

            dirpng = path!!;

            img?.visibility = View.VISIBLE;
        }
    }


    /**
     * helper to retrieve the path of an image URI
     */
    fun getPath(uri: Uri?): String? {
        // just some safety built in
        if (uri == null) {
            // TODO perform some logging or show user feedback
            return null
        }
        // try to retrieve the image from the media store first
        // this will only work for images selected from gallery
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = context?.getContentResolver()?.query(uri, projection, null, null, null)
        if (cursor != null) {
            val column_index = cursor!!
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor!!.moveToFirst()
            return cursor!!.getString(column_index)
        }
        // this is our fallback here
        return uri.path
    }

}
