package com.example.wecareapp

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.wecareapp.api.GoogleFitApiService

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view:View=inflater.inflate(R.layout.fragment_home, container, false)
        val feeling=view.findViewById<SeekBar>(R.id.feelingM)
        val feelingText=view.findViewById<TextView>(R.id.feeling_numberM)
        feeling?.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seek: SeekBar,
                                           progress: Int, fromUser: Boolean) {
                // write custom code for progress is changed
                feelingText.text=(feeling.progress/10).toString()
            }

            override fun onStartTrackingTouch(seek: SeekBar) {
                // write custom code for progress is started
            }

            override fun onStopTrackingTouch(seek: SeekBar) {
                // write custom code for progress is stopped
                feelingText.text=(feeling.progress/10).toString()

            }
        })


        Addevent(view)
        return view
    }

    fun Addevent(view:View) {
        var add_event=view.findViewById<ImageButton>(R.id.ib_addevent)
        add_event.setOnClickListener(View.OnClickListener {
            val builder = AlertDialog.Builder(this@HomeFragment.requireContext())
            val view = layoutInflater.inflate(R.layout.dialog_home, null)
            builder.setView(view)
            val dialog = builder.create()
            dialog.show()

            view.findViewById<Button>(R.id.button2).setOnClickListener{
                val intent = Intent(this@HomeFragment.requireContext(), RegisterEventActivity::class.java).apply {
                    //putExtra("Username",user.name)
                }
                startActivity(intent)
            }
            view.findViewById<Button>(R.id.button).setOnClickListener{
                dialog.hide()
            }

        })
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}