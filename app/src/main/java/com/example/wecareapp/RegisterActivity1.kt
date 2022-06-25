package com.example.wecareapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.wecareapp.model.Event
import com.example.wecareapp.model.Patient
import com.example.wecareapp.model.PatientResponse
import com.example.wecareapp.viewmodel.CreatePatientVM
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


//import com.example.wecareapp.viewmodel.MainViewModelFactory
import retrofit2.Response


class RegisterActivity1 : AppCompatActivity() {
    lateinit var viewModel: CreatePatientVM
    private val db = FirebaseFirestore.getInstance();
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register1)

        var myResponse: MutableLiveData<Response<Patient>> = MutableLiveData()
        setContentView(R.layout.activity_register1)
        //    val policy = ThreadPolicy.Builder().permitAll().build()
        //      StrictMode.setThreadPolicy(policy)
        val registro=findViewById<Button>(R.id.bt_crearC1)
        val tv_name:EditText =findViewById<EditText>(R.id.tv_nombre)
        val tv_lastname: EditText=findViewById<EditText>(R.id.tv_apellido)
        val tv_mail: EditText=findViewById<EditText>(R.id.tv_correo)
        val tv_password:EditText=findViewById<EditText>(R.id.tv_contraseña)
        val tv_confirm_Password:EditText=findViewById<EditText>(R.id.tv_contraseña_confirmar)

        initViewModel()
        registro.setOnClickListener(){
            // TODO: VALIDACIONES DE LOS CAMPOS
            if(TextUtils.isEmpty(tv_name.text.toString()) &&
                TextUtils.isEmpty(tv_lastname.text.toString()) &&
                TextUtils.isEmpty(tv_mail.text.toString()) &&
                TextUtils.isEmpty(tv_password.text.toString()) &&
                TextUtils.isEmpty(tv_confirm_Password.text.toString())
            ){
                Toast.makeText(this,"Complete los datos", Toast.LENGTH_SHORT).show()
            }
            else if( tv_password.text.toString() != tv_confirm_Password.text.toString()){
                Toast.makeText(this,"Contraseña debe ser igual", Toast.LENGTH_SHORT).show()
            }
            else if(tv_password.length()<6){
                Toast.makeText(this,"La contraseña debe ser mayor a 6 carácteres", Toast.LENGTH_SHORT).show()
            }
            else{
                createPatient()
            }

        }
    }

    private fun createPatient() {
        val FirstName: EditText = findViewById<EditText>(R.id.tv_nombre)
        val Lastname: EditText = findViewById<EditText>(R.id.tv_apellido)
        val Email: EditText = findViewById<EditText>(R.id.tv_correo)
        val Password: EditText = findViewById<EditText>(R.id.tv_contraseña)
        val ConfirPassowrd: EditText = findViewById<EditText>(R.id.tv_contraseña_confirmar)


        val patient  = Patient(FirstName.text.toString().replace("       ",""),
            Lastname.text.toString().replace("       ",""),
            Email.text.toString().replace("       ",""),
            Password.text.toString().replace("       ",""),
            ConfirPassowrd.text.toString().replace("       ",""))
        // TODO: ANTES
        //viewModel.createNewPatient(patient)
        //TODO: AHORA -> registro con usuario paciente
        FirebaseAuth.getInstance()
            .createUserWithEmailAndPassword(Email.text.toString(),Password.text.toString())
            .addOnCompleteListener{
                val mAuth = FirebaseAuth.getInstance()

                if (it.isSuccessful){
                    val uid = mAuth.currentUser?.uid
                    //TODO: ESTA VEZ, EL MAIL SERÁ LA CLAVE PARA LA BASE DE DATOS(NOSQL)
                    db.collection("patients").document().set(
                        hashMapOf(
                            "patientId" to uid,
                            "patientName" to patient.Firstname,
                            "patientLastname" to patient.Lastname,
                            "patientEmail" to patient.Email,
                            "patientRol" to "patient",
                            "patientLinked" to true
                        )
                    )
                    Toast.makeText(this, "Registro de usuario paciente completado", Toast.LENGTH_LONG).show()
                    val intent = Intent(this, MainActivity::class.java).apply {
                        //putExtra("Username",user.name)
                    }
                    startActivity(intent)
                } else {
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
        viewModel = ViewModelProvider(this)[CreatePatientVM::class.java]
        viewModel.getCreateNewPatientObserver().observe(this, Observer <PatientResponse?>{

            if(it  == null) {
                Toast.makeText(this, "Failed to create User", Toast.LENGTH_LONG).show()
            } else {
                //{"code":201,"meta":null,"data":{"id":2877,"name":"xxxxxaaaaabbbbb","email":"xxxxxaaaaabbbbb@gmail.com","gender":"male","status":"active"}}
                Toast.makeText(this, "Successfully created User", Toast.LENGTH_LONG).show()
            }
        })
    }
}




/*  fun sendPostRequest(
      Firstname: String,
      Lastname: String,
      Email: String,
      Password: String,
  ) {
      var reqParam = URLEncoder.encode("Firstname", "UTF-8") + "=" + URLEncoder.encode(Firstname, "UTF-8")
      reqParam += "&" + URLEncoder.encode("Lastname", "UTF-8") + "=" + URLEncoder.encode(Lastname, "UTF-8")
      reqParam += "&" + URLEncoder.encode("Email", "UTF-8") + "=" + URLEncoder.encode(Email, "UTF-8")
      reqParam += "&" + URLEncoder.encode("Password", "UTF-8") + "=" + URLEncoder.encode(Password, "UTF-8")
      val mURL = URL("http://localhost:43654/identity/register_patient")
      with(mURL.openConnection() as HttpURLConnection) {
          // optional default is GET
          requestMethod = "POST"
          val wr = OutputStreamWriter(getOutputStream());
          wr.write(reqParam);
          wr.flush();
          println("URL : $url")
          println("Response Code : $responseCode")
          BufferedReader(InputStreamReader(inputStream)).use {
              val response = StringBuffer()
              var inputLine = it.readLine()
              while (inputLine != null) {
                  response.append(inputLine)
                  inputLine = it.readLine()
              }
              println("Response : $response")
          }
      }
  }*/




