package com.android.samples.arch.componentsbasicsample

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import java.util.ArrayList


class EditAlbomFragment : Fragment() {

    private lateinit var viewModel: EndViewModel

    private lateinit var dirpngEditAlbom: String

    companion object {
        fun newInstance() = StartFragment()
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?

    ): View? {
        return inflater.inflate(R.layout.fragment_edit_albom, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        var db = dbHelper.writableDatabase

        val id = arguments?.getString("albomItem")?.toInt();

        var albom: Albom? = null;

        var cursor = db.rawQuery("SELECT * FROM 'albom' Where id='$id'", null);

        if (cursor.moveToFirst()) {

            do {
                val id = cursor.getInt(cursor.getColumnIndex("id"))
                val albomName = cursor.getString(cursor.getColumnIndex("AlbomName"))
                val year = cursor.getString(cursor.getColumnIndex("Year"))
                val dirPng = cursor.getString(cursor.getColumnIndex("DirPng"))
                val teg = cursor.getString(cursor.getColumnIndex("Teg"))

                albom = Albom(id,albomName,year,dirPng,teg);

            } while (cursor.moveToNext())
        }


        var img = view?.findViewById<ImageView>(R.id.imgEditAlbom);
        var albomName_current =  view?.findViewById<TextView>(R.id.textView_currentNameAlbom);
        var year_current =  view?.findViewById<TextView>(R.id.textView_currentYearAlbom);
        var teg_current =  view?.findViewById<TextView>(R.id.textView_currentTegAlbom);

        if (albom != null) {

            dirpngEditAlbom = albom.getDirPng()
            img?.setImageURI( Uri.parse(dirpngEditAlbom));

            if(dirpngEditAlbom != "null") {
                img?.visibility = View.VISIBLE;
            }


            albomName_current?.text = albom.getAlbomName();
            year_current?.text = albom.getYear();
            teg_current?.text = albom.getTeg();


            view?.findViewById<EditText>(R.id.editText_albom)?.setText(albom.getAlbomName())
            view?.findViewById<EditText>(R.id.editText_year)?.setText(albom.getYear())
            view?.findViewById<EditText>(R.id.editText_teg)?.setText(albom.getTeg())

        };


        view?.findViewById<Button>(R.id.find)?.setOnClickListener {

            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_PICK

            startActivityForResult(Intent.createChooser(intent, "Select Picture"), 100)

        }

        view?.findViewById<Button>(R.id.button)?.setOnClickListener {


            var albomName =  view?.findViewById<EditText>(R.id.editText_albom)?.text.toString();
            var year =  view?.findViewById<EditText>(R.id.editText_year)?.text.toString();
            var teg =  view?.findViewById<EditText>(R.id.editText_teg)?.text.toString();

            if(albomName == "")
            {
                albomName = albom!!.getAlbomName();
            }

            if(year == "")
            {
                year = albom!!.getYear();
            }

            if(teg == "")
            {
                teg = albom!!.getTeg();
            }

            var list: ArrayList<String>? = null;

            list?.add(albomName);
            list?.add(year);
            list?.add(teg);

            db.execSQL("UPDATE 'albom' SET AlbomName = '$albomName', Year = '$year',DirPng = '$dirpngEditAlbom',Teg = '$teg' WHERE id =$id");


            //Закрытие клавиатуры
            val imm = view?.context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view?.getWindowToken(),
                    InputMethodManager.RESULT_UNCHANGED_SHOWN);


            view?.let { Navigation.findNavController(it).popBackStack() }


        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {

            var img = view?.findViewById<ImageView>(R.id.imgEditAlbom);


            val selectedFile = data?.data!!//The uri with the location of the file

            img?.setImageURI(selectedFile);

            img?.visibility = View.VISIBLE;

            var path = getPath(selectedFile);


            if(path == null)
            {
                dirpngEditAlbom = selectedFile.toString()
            }
            else
            {
                dirpngEditAlbom = path!!;
            }

        }
        else
        {
            // Initialize a new instance of
            val builder = AlertDialog.Builder(context)
            // Set the alert dialog title
            builder.setTitle("Картинка")
            // Display a message on alert dialog
            builder.setMessage("Оставить картинку?")
            // Set a positive button and its click listener on alert dialog
            builder.setPositiveButton("Да"){dialog, which ->

            }

            // Display a negative button on alert dialog
            builder.setNegativeButton("Нет"){dialog,which ->
                dirpngEditAlbom = "null"
                view?.findViewById<ImageView>(R.id.img)?.visibility = View.GONE;
            }

            // Display a neutral button on alert dialog
            builder.setNeutralButton("Отмена"){_,_ ->
            }
            // Finally, make the alert dialog using builder
            val dialog: AlertDialog = builder.create()
            // Display the alert dialog on app interface
            dialog.show()
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
