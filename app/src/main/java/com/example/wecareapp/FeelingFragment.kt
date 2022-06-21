package com.example.wecareapp

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.demo.retrofitwithpost.GetEventsVM
import com.example.wecareapp.model.Event
import com.example.wecareapp.recyclerview.EventosAdapter
import org.w3c.dom.Text
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList

class FeelingFragment : Fragment(){

    // TODO: Rename and change types of parameters
    lateinit var eventAdapter:EventosAdapter
    lateinit var eventosRecyclerView:RecyclerView
    lateinit var listaEventos:ArrayList<Event>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_feeling2, container, false)
        eventosRecyclerView=view.findViewById(R.id.listaEventos)
        eventosRecyclerView.layoutManager=LinearLayoutManager(activity)
        //llenando arreglo de eventos
        listaEventos= ArrayList()
        listaEventos.add(Event("Ansiosa","Tengo un examen importante cerca y aún no me siento del todo preparada " +
                "por que no he tenido tiempo para estudiar eb estos dias. Empecé a pensar " +
                "en cómo sentiría si saco una mala nota y me faltó la respiración",2,
                    "Mal","Angustiada, llorando y sentía que no era lo suficientemente buena, me hiperventile", "17-04-2022  15:30" ,1))

        listaEventos.add(Event("Estresada","Me angustie por que hoy tengo mucho por hacer y no quiero comenzar lo cual " +
                "esta causandome mucho estrés y preocupación",5,
            "Estresante","Angustiada, estresada, me dolia la cabeza", "22-04-2022  17:48" ,1))

        listaEventos.add(Event("Feliz","Escuchar mi musica favorita me pone de muy buen humor" ,7,
            "Bien","Feliz y energetica mientras bailaba mi música favorita", "29-04-2022  19:17" ,1))
        eventAdapter=EventosAdapter(EventosAdapter.onEventClickListener {event->verDetalle(event)} ,listaEventos)
        eventosRecyclerView.adapter=eventAdapter
        //initRecyclerView(view)
       // initViewModel()


        // Inflate the layout for this fragment
        return view
    }
    fun verDetalle(evento:Event){
        //Log.i("prueba", "Si funciona je")
        var inflater=LayoutInflater.from(activity)
        val v=inflater.inflate(R.layout.detalle_evento,null)
        val nombreText:TextView=v.findViewById(R.id.textViewEventName)
        val dateText:TextView=v.findViewById(R.id.textViewEventDate)
        val descripcionText:TextView=v.findViewById(R.id.textViewEventDescription)
        val scoreText:TextView=v.findViewById(R.id.textViewEventScore)
        val resultText:TextView=v.findViewById(R.id.textViewEventResult)
        val detailText:TextView=v.findViewById(R.id.textViewEventDetail)
        nombreText.text=evento.eventName
        descripcionText.text=evento.eventDescription
        scoreText.text=evento.eventScore.toString()
        resultText.text=evento.eventResult
        detailText.text=evento.eventDetail
        dateText.text=evento.eventDate.toString()
        val addDialog=AlertDialog.Builder(activity)
        addDialog.setView(v)
        addDialog.setPositiveButton("Ok"){
            dialog,_->
            dialog.dismiss()
        }
        addDialog.create()
        addDialog.show()
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FeelingFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FeelingFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
    /*
    private fun initRecyclerView(view:View){
        var recyclerView = view.findViewById<RecyclerView>(R.id.listaEventos)
        recyclerView.layoutManager=LinearLayoutManager(activity)

        recyclerViewAdapter= RecyclerViewAdapter()
        recyclerView.adapter=recyclerViewAdapter

    }

     */
    /*
    fun initViewModel() {

        viewModel = ViewModelProvider(this).get( GetEventsVM::class.java)
        viewModel.getUserListObserverable().observe(viewLifecycleOwner, Observer<List<EventGet>?> {
            if(it == null) {

            } else {

                recyclerViewAdapter.EventList = it.toMutableList()
                recyclerViewAdapter.notifyDataSetChanged()
            }
        })
        viewModel.EventList()
    }

     */


}