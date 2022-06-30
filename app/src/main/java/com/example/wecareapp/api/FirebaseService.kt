package com.example.wecareapp.api

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class FirebaseService {
    val db = FirebaseFirestore.getInstance();
    val usuario = FirebaseAuth.getInstance().currentUser;

    private fun crearHistoria(eventId: String, stateId: String){

        val histories = db.collection("histories")
        val histoy = hashMapOf(
            "eventId" to eventId,
            "patientId" to usuario?.uid,
            "stateId" to stateId,
        )
        histories.add(histoy).addOnSuccessListener { Log.d("Mensaje","Se ha logrado registrar la historia") }
    }

    private fun crearEstadoPaciente(state02Stats: Int, stateBPM: String){

        val states = db.collection("states")
        val state = hashMapOf(
            "state02Stats" to state02Stats,
            "patientId" to usuario?.uid,
            "stateBPM" to stateBPM,
            "stateDateTime" to FieldValue.serverTimestamp()
        )
        states.add(state).addOnSuccessListener { Log.d("Mensaje","Se ha logrado registrar la historia") }
    }

    private fun crearEvento(eventDetail: String, eventDescription: String, eventName: String, eventScore: Float,eventResult: String){

        val events = db.collection("events");
        val event = hashMapOf(
            "eventDetail" to eventDetail,
            "eventDescription" to eventDescription,
            "eventName" to eventName,
            "eventScore" to eventScore,
            "eventResult" to eventResult,
            "patientId" to usuario?.uid,
        )

        events.add(event).addOnSuccessListener {
            Log.d("Mensaje","Se ha logrado crear el evento")
            }
            .addOnFailureListener {
                Log.e("Error","No se ha podido crear el evento")
               };
    }

    private fun crearMedicacion(medicationName: String){

        val medications = db.collection("medications");
        val medication = hashMapOf(
            "patientId" to usuario?.uid,
            "medicationName" to medicationName,
        )

        medications.add(medication).addOnSuccessListener {
            Log.d("Mensaje","Se ha logrado crear el evento")
            }
            .addOnFailureListener {
                Log.e("Error","No se ha podido crear el evento")
                };
    }

    private fun crearEnfermedad(diseaseName: String){
        val diseases = db.collection("medications");
        val disease = hashMapOf(
            "patientId" to usuario?.uid,
            "diseaseName" to diseaseName,
        )

        diseases.add(disease).addOnSuccessListener {
            Log.d("Mensaje","Se ha logrado crear el evento") }
            .addOnFailureListener {
                Log.e("Error","No se ha podido crear el evento") };
    }
}