package com.example.wecareapp

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.fitness.Fitness
import com.google.android.gms.fitness.FitnessOptions
import com.google.android.gms.fitness.data.*
import com.google.android.gms.fitness.request.DataReadRequest
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.concurrent.TimeUnit

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [StatsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class StatsFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_stats, container, false)
        var heartRateText:TextView=view.findViewById(R.id.heartRate)
        var oxygenText:TextView=view.findViewById(R.id.oxygen)


    createAPIClient(heartRateText , oxygenText)
        return view
    }

    val healthDataOptions= FitnessOptions.builder()
        .addDataType(DataType.TYPE_HEART_RATE_BPM, FitnessOptions.ACCESS_READ)
        .addDataType(HealthDataTypes.TYPE_OXYGEN_SATURATION, FitnessOptions.ACCESS_READ)
        .build()

    private fun createAPIClient(heartRateText:TextView, oxygenText:TextView) {

        val account = GoogleSignIn.getAccountForExtension(activity as Activity, healthDataOptions)

        if (!GoogleSignIn.hasPermissions(account, healthDataOptions)) {
            GoogleSignIn.requestPermissions(
                this, // your activity
                1, // e.g. 1
                account,
                healthDataOptions
            )
            println("            ")
            println("            ")
            println("            ")
            accessGoogleFit(heartRateText, oxygenText)
        } else {
            accessGoogleFit(heartRateText, oxygenText)
            println("            ")
            println("            ")
            println("            ")
        }

    }

    private fun accessGoogleFit(heartRateText:TextView, oxygenText: TextView) {
        println("            ")
        println("            ")
        println("            ")
        val end = LocalDateTime.now()
        val startDate = end.minusDays(7)
        val endSeconds = end.atZone(ZoneId.systemDefault()).toEpochSecond()
        val startSeconds = startDate.atZone(ZoneId.systemDefault()).toEpochSecond()
        val account = GoogleSignIn.getAccountForExtension(activity as Activity, healthDataOptions)
        println("            ")
        if (!GoogleSignIn.hasPermissions(account, healthDataOptions)) {
            GoogleSignIn.requestPermissions(
                this, // your activity
                1, // e.g. 1
                account,
                healthDataOptions
            )}
        //OBTENER LA CANTIDAD DE PASOS
        println("            ")
        val readRequest = DataReadRequest.Builder()
            .aggregate(HealthDataTypes.TYPE_OXYGEN_SATURATION)
            .aggregate(DataType.TYPE_HEART_RATE_BPM)
            .setTimeRange(startSeconds, endSeconds, TimeUnit.SECONDS)
            .bucketByTime(5, TimeUnit.MINUTES) //Guarda en grupos de 5 minutos de intervalo
            .build()
        println("AQUÍ ESTA FALLANDO ----- The user must be signed in to make this API call.")
        Fitness.getHistoryClient(activity as Activity, account)
            .readData(readRequest)
            .addOnSuccessListener { response ->
                for (dataSet in response.buckets.flatMap { it.dataSets }) {
                    if(dataSet.dataPoints.size>0){
                        dumpDataSet(dataSet, heartRateText, oxygenText)
                    }
                }
                Log.i("Conexion", "Conectado")
            }
            .addOnFailureListener({ e -> Log.d("PRUEBAS", "OnFailure()", e) })
        println("            ")
    }
    fun dumpDataSet(dataSet: DataSet,  heartRateText:TextView, oxygenText: TextView) {
       Log.i("Tipo de dato", "Data returned for Data type: ${dataSet.dataType.name}")
    //    Log.i("DATAPOINT",dataSet.dataPoints.toString())
        for (dp in dataSet.dataPoints) {
           // Log.i("Heart rate","Data point:")
           // Log.i("Data type name","\tType: ${dp.dataType.name}")
          //  Log.i("Start time","\tStart: ${dp.getStartTimeString()}")
          //  Log.i("End time","\tEnd: ${dp.getEndTimeString()}")

            for (field in dp.dataType.fields) {
                Log.i("dato","\tField: ${field.name.toString()} Value: ${dp.getValue(field)}")
            }

            updateView(dp,heartRateText, oxygenText)
            //OBTENER EL ULTIMO VALOR
        }
    }

    fun DataPoint.getStartTimeString() = Instant.ofEpochSecond(this.getStartTime(TimeUnit.SECONDS))
        .atZone(ZoneId.systemDefault())
        .toLocalDateTime().toString()

    fun DataPoint.getEndTimeString() = Instant.ofEpochSecond(this.getEndTime(TimeUnit.SECONDS))
        .atZone(ZoneId.systemDefault())
        .toLocalDateTime().toString()

    fun updateView(data:DataPoint, heartRateText:TextView, oxygenText: TextView){
        Log.i("DATA",data.toString())
        if(data is DataPoint){
            for (field in data.dataType.fields) {
                //Log.i("WHEN", "entre con valor: ${field.name}")
                when (field.name) {
                    "average" -> {
                        //.i("RITMO PROMEDIO","${dp.getValue(field)}")

                        heartRateText.text = "${data.getValue(field)} BPM"
                    }

                    "oxygen_saturation_average"
                    -> {
                        oxygenText.text = "${data.getValue(field)} %"
                    }
                }
            }
        }
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment StatsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            StatsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}