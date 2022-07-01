package com.example.wecareapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.demo.retrofitwithpost.GetEventsVM
import com.example.wecareapp.model.*
import com.example.wecareapp.viewmodel.CreateLogUserVM
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class MainActivity : AppCompatActivity() {
    lateinit var viewModel: CreateLogUserVM
    lateinit var vm: GetEventsVM
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViewModel()
        val registro=findViewById<Button>(R.id.bt_signup)
        val login=findViewById<Button>(R.id.bt_login)
        val sharedPref = this?.getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        login.setOnClickListener(){
           createLogUser()
        }


        registro.setOnClickListener(){
            // TODO: 1-> REALIZAR REGISTRO DE USUARIO CON FIREBASE
            val intent = Intent(this, SelectRolActivity::class.java).apply {
                //putExtra("Username",user.name)
            }
            startActivity(intent)

        }

        // PRUEBA DE FIREBASE
        val analytics = FirebaseAnalytics.getInstance(this)
        val bundle = Bundle()
        bundle.putString("message","Integración de Firebase completa")
        analytics.logEvent("InitScreen", bundle)


    }



    private fun createLogUser(){
        //TODO: INICIAR SESION CON FIREBASE
        val Email: AutoCompleteTextView= findViewById<AutoCompleteTextView>(R.id.tv_emaillog)
        val Password: AutoCompleteTextView= findViewById<AutoCompleteTextView>(R.id.tv_passwordlog)
        //val user = User( Email, Password)
        //return viewModel.createNewUser(user,con)
        if(TextUtils.isEmpty(Email.text.toString().replace(" ","")) &&
            TextUtils.isEmpty(Password.text.toString().replace(" ",""))
        ){
            Toast.makeText(this, "Complete los datos", Toast.LENGTH_LONG).show()
        }else{
            FirebaseAuth.getInstance()
                .signInWithEmailAndPassword(Email.text.toString().replace(" ",""),Password.text.toString().replace(" ",""))
                .addOnCompleteListener{
                    val mAuth = FirebaseAuth.getInstance()
                    val uid = mAuth.currentUser?.uid
                    if (it.isSuccessful){
                        println(uid)
                        // TODO: INICIANDO SESION
                        db.collection("patients")
                            .document(uid.toString()) // CONSEGUIR EL USUARIO POR UID EN TABLA PATIENTS; SI NO ESTÁ EN ESTA TABLA, ESTARÁ EN LA TABLA SPECIALISTS
                            .get()
                            .addOnCompleteListener{
                                if (it.isSuccessful){
                                    if (it.getResult().get("patientRol") as String? == "patient"){
                                        val intent= Intent(this, SelectorActivity::class.java).apply {
                                            putExtra("namePatient",it.getResult().get("patientName") as String?)
                                        }
                                        startActivity(intent)
                                    }else{ }
                                }
                            }
                        db.collection("specialists")
                            .document(uid.toString())
                            .get()
                            .addOnCompleteListener{
                                if (it.isSuccessful){
                                    if (it.getResult().get("specialistRol") as String? == "specialist"){
                                        val intent= Intent(this, SpetialistSelectorActivity::class.java).apply {
                                            putExtra("nameSpecialist",it.getResult().get("specialistName") as String?)
                                        }
                                        startActivity(intent)
                                    }else{ }
                                }
                            }


                    }
                }
                .addOnFailureListener{
                    showAlert()
                }


        }

    }

    private fun showAlert(){
        val builder= AlertDialog.Builder(this)
        builder.setTitle("Error")

        builder.setMessage("Se ha producido un error autenticado al usuario")
        builder.setPositiveButton("Aceptar",null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(CreateLogUserVM::class.java)
        viewModel.getCreateNewUserObserver().observe(this, Observer <UserResponse?>{

            if(it  == null) {
                Toast.makeText(this, "Failed to create User", Toast.LENGTH_LONG).show()
            } else {
                //{"code":201,"meta":null,"data":{"id":2877,"name":"xxxxxaaaaabbbbb","email":"xxxxxaaaaabbbbb@gmail.com","gender":"male","status":"active"}}
                Toast.makeText(this, "Successfully created User", Toast.LENGTH_LONG).show()
            }
        })
    }

}
