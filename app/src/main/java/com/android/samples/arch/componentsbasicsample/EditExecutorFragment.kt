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


class EditExecutorFragment : Fragment() {

    companion object {
        fun newInstance() = StartFragment()
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?

    ): View? {
        return inflater.inflate(R.layout.fragment_edit_executor, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        var db = dbHelper.writableDatabase

        val id = arguments?.getString("executorItem")?.toInt();

        var executor: Executor? = null;

        var cursor = db.rawQuery("SELECT * FROM 'executor' Where id='$id'", null);

        if (cursor.moveToFirst()) {

            do {
                val id = cursor.getInt(cursor.getColumnIndex("id"))
                val albomName = cursor.getString(cursor.getColumnIndex("ExecutorName"))
                val teg = cursor.getString(cursor.getColumnIndex("Teg"))

                executor = Executor(id,albomName,teg);

            } while (cursor.moveToNext())
        }

        var executorName_current =  view?.findViewById<TextView>(R.id.textView_currentExecutorName);
        var teg_current =  view?.findViewById<TextView>(R.id.textView_currentTegExecutor);

        if (executor != null) {

            executorName_current?.text = executor.getExecutorName();
            teg_current?.text = executor.getTeg();

        };


        view?.findViewById<Button>(R.id.button)?.setOnClickListener {


            var executer_Name =  view?.findViewById<EditText>(R.id.editText_executor)?.text.toString();
            var teg =  view?.findViewById<EditText>(R.id.editText_teg)?.text.toString();


            if(executer_Name == "")
            {
                executer_Name = executor!!.getExecutorName();
            }

            if(teg == "")
            {
                teg = executor!!.getTeg();
            }

            db.execSQL("UPDATE 'executor' SET ExecutorName = '$executer_Name', Teg = '$teg' WHERE id =$id");


            //Закрытие клавиатуры
            val imm = view?.context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view?.getWindowToken(),
                    InputMethodManager.RESULT_UNCHANGED_SHOWN);


            view?.let { Navigation.findNavController(it).popBackStack() }


        }

    }

}
