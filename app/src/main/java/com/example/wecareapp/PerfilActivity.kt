package com.example.wecareapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wecareapp.model.Enfermedad
import com.example.wecareapp.model.Medicacion
import com.example.wecareapp.recyclerview.EnfermedadesAdapter
import com.example.wecareapp.recyclerview.MedicacionAdapter

class PerfilActivity : AppCompatActivity() {
    private lateinit var btnAgregarEnfermedad:Button
    private lateinit var enfermedadesRecyclerview:RecyclerView
    private lateinit var enfermedades:ArrayList<Enfermedad>
    private lateinit var eAdapter:EnfermedadesAdapter



    private lateinit var btnAgregarMedicacion:Button
    private lateinit var medicacionRecyclerView:RecyclerView
    private lateinit var medicaciones:ArrayList<Medicacion>
    private lateinit var mAdapter:MedicacionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)
        /*-----------ENFERMEDADES --------------------------------------------------------*/
        btnAgregarEnfermedad=findViewById(R.id.btnAgregarEnfermedad)
        enfermedadesRecyclerview=findViewById(R.id.listaEnfermedades)
        enfermedadesRecyclerview.layoutManager=LinearLayoutManager(this)
        /*Llenando arreglo de enfermedades*/
        enfermedades=ArrayList()
        /*
        for (i in 1..20) {
            enfermedades.add(Enfermedad("Enfermedad "+i))
        }
        */
        enfermedades.add(Enfermedad("Presión alta"))
        enfermedades.add(Enfermedad("Hipotiroidismo"))

        /*Configurando adapter de enfermedades*/
        eAdapter= EnfermedadesAdapter(enfermedades)
        enfermedadesRecyclerview.adapter=eAdapter

        /*Configurando dialog*/
        btnAgregarEnfermedad.setOnClickListener { addEnfermedad() }

        /*-----------MEDICACION---------------------------------------------------------*/
        btnAgregarMedicacion=findViewById(R.id.btnAgregarMedicacion)
        medicacionRecyclerView=findViewById(R.id.listaMedicacion)
        medicacionRecyclerView.layoutManager=LinearLayoutManager(this)

        medicaciones=ArrayList()
        /*
        for (i in 1..20) {
            medicacion.add(Medicacion("Medicacion "+i))
        }
        */
        medicaciones.add(Medicacion("Lozartan"))
        medicaciones.add(Medicacion("glandulas tiroideas"))
        /*Configurando adapter de medicacion*/
        mAdapter= MedicacionAdapter(medicaciones)
        medicacionRecyclerView.adapter=mAdapter

        /*Configurando dialog*/
        btnAgregarMedicacion.setOnClickListener { addMedicacion() }
    }
    private fun addEnfermedad(){
        var inflater=LayoutInflater.from(this)
        val v = inflater.inflate(R.layout.add_enfermedad,null)
        /**set view*/
        val textoEnfermedad = v.findViewById<EditText>(R.id.textoEnfermedad)

        val addDialog = AlertDialog.Builder(this)

        addDialog.setView(v)
        addDialog.setPositiveButton("Ok"){
                dialog,_->
            val enfermedad = textoEnfermedad.text.toString()
            enfermedades.add(Enfermedad(enfermedad))
            eAdapter.notifyDataSetChanged()
            Toast.makeText(this,"Condición agregada correctamente",Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }
        addDialog.setNegativeButton("Cancel"){
                dialog,_->
            dialog.dismiss()
            Toast.makeText(this,"Cancel",Toast.LENGTH_SHORT).show()

        }
        addDialog.create()
        addDialog.show()
    }

    private fun addMedicacion(){
        var inflater=LayoutInflater.from(this)
        val v = inflater.inflate(R.layout.add_medicacion,null)
        /**set view*/
        val textoMedicacion = v.findViewById<EditText>(R.id.textoMedicacion)

        val addDialog = AlertDialog.Builder(this)

        addDialog.setView(v)
        addDialog.setPositiveButton("Ok"){
                dialog,_->
            val medicacion = textoMedicacion.text.toString()
            medicaciones.add(Medicacion(medicacion))
            mAdapter.notifyDataSetChanged()
            Toast.makeText(this,"Medicación agregada correctamente",Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }
        addDialog.setNegativeButton("Cancel"){
                dialog,_->
            dialog.dismiss()
            Toast.makeText(this,"Cancel",Toast.LENGTH_SHORT).show()

        }
        addDialog.create()
        addDialog.show()
    }
}