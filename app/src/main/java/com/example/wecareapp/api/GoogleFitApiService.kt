package com.example.wecareapp.api

import android.app.Activity
import android.util.Log
import android.view.View
import android.widget.TextView
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.fitness.Fitness
import com.google.android.gms.fitness.FitnessOptions
import com.google.android.gms.fitness.data.DataPoint
import com.google.android.gms.fitness.data.DataSet
import com.google.android.gms.fitness.data.DataType
import com.google.android.gms.fitness.data.HealthDataTypes
import com.google.android.gms.fitness.request.DataReadRequest
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.concurrent.TimeUnit

class GoogleFitApiService{
    val bloodPressureOptions= FitnessOptions.builder()
        .addDataType(DataType.TYPE_HEART_RATE_BPM, FitnessOptions.ACCESS_READ)
        .addDataType(HealthDataTypes.TYPE_BLOOD_PRESSURE, FitnessOptions.ACCESS_READ)
        .build()

    fun createAPIClient(activity:Activity): DataPoint? {

        val account = GoogleSignIn.getAccountForExtension(activity, bloodPressureOptions)
        if (!GoogleSignIn.hasPermissions(account, bloodPressureOptions)) {
            GoogleSignIn.requestPermissions(
                activity, // your activity
                1, // e.g. 1
                account,
                bloodPressureOptions
            )
            Log.i("IF","ENTRE ")
            return null
        } else {
            Log.i("ELSE","ENTRE ")
            return accessGoogleFit(activity)
        }

    }

    fun accessGoogleFit(activity:Activity): DataPoint {
        val end = LocalDateTime.now()
        val startDate = end.minusDays(4)
        val endSeconds = end.atZone(ZoneId.systemDefault()).toEpochSecond()
        val startSeconds = startDate.atZone(ZoneId.systemDefault()).toEpochSecond()
        val account = GoogleSignIn.getAccountForExtension(activity, bloodPressureOptions)

        //OBTENER LA CANTIDAD DE PASOS

        val readRequest = DataReadRequest.Builder()
            .aggregate(HealthDataTypes.TYPE_BLOOD_PRESSURE)
            .aggregate(DataType.TYPE_HEART_RATE_BPM)
            .setTimeRange(startSeconds, endSeconds, TimeUnit.SECONDS)
            .bucketByTime(5, TimeUnit.MINUTES) //Guarda en grupos de 5 minutos de intervalo
            .build()

        lateinit var result:DataPoint

        Fitness.getHistoryClient(activity, account)
            .readData(readRequest)
            .addOnSuccessListener { response ->
                for (dataSet in response.buckets.flatMap { it.dataSets }) {
                    if(dataSet.dataPoints.size>0){
                       result= dumpDataSet(dataSet)
                    }
                }
                Log.i("Conexion", "Conectado")
            }
            .addOnFailureListener({ e -> Log.d("PRUEBAS", "OnFailure()", e) })

        return result
    }


    fun dumpDataSet(dataSet: DataSet): DataPoint {
        Log.i("Tipo de dato", "Data returned for Data type: ${dataSet.dataType.name}")
        Log.i("DATAPOINT",dataSet.dataPoints.toString())
        lateinit var dataPoint:DataPoint
        for (dp in dataSet.dataPoints) {
            Log.i("Heart rate","Data point:")
            Log.i("Data type name","\tType: ${dp.dataType.name}")
            Log.i("Start time","\tStart: ${dp.getStartTimeString()}")
            Log.i("End time","\tEnd: ${dp.getEndTimeString()}")

            for (field in dp.dataType.fields) {
                Log.i("heart rate","\tField: ${field.name.toString()} Value: ${dp.getValue(field)}")
            }

            //updateView(dp)
            //OBTENER EL ULTIMO VALOR
            dataPoint=dp
        }
        return dataPoint
    }
    fun DataPoint.getStartTimeString() = Instant.ofEpochSecond(this.getStartTime(TimeUnit.SECONDS))
        .atZone(ZoneId.systemDefault())
        .toLocalDateTime().toString()

    fun DataPoint.getEndTimeString() = Instant.ofEpochSecond(this.getEndTime(TimeUnit.SECONDS))
        .atZone(ZoneId.systemDefault())
        .toLocalDateTime().toString()



    fun printTest(){
        Log.i("Prueba","Prueba de integracion con service")
    }
}