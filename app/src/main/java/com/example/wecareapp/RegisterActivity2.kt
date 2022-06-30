package com.example.wecareapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.Toast
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.wecareapp.model.Specialist
import com.example.wecareapp.model.SpecialistResponse
import com.example.wecareapp.viewmodel.CreateSpecialistVM
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RegisterActivity2 : AppCompatActivity() {
    lateinit var viewModel: CreateSpecialistVM
    private val db = FirebaseFirestore.getInstance();
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register2)

        val registro=findViewById<Button>(R.id.bt_crearC2)
        val Firstname=findViewById<EditText>(R.id.tv_nombre1)
        val Lastname=findViewById<EditText>(R.id.tv_apellido1)
        val Email=findViewById<EditText>(R.id.tv_correo1)
        val Password=findViewById<EditText>(R.id.tv_contraseña1)
        val ConfirmPassword=findViewById<EditText>(R.id.tv_confirm_password)
        val Esp=findViewById<EditText>(R.id.tv_especialidad)
        val Nrocol=findViewById<EditText>(R.id.tv_nro_colegiatura)

        //  RegisterService.enviarWs(nombres,apellidos,correo,contrasena,esp,nrocol);
        initViewModel()
        registro.setOnClickListener(){
            if(TextUtils.isEmpty(Firstname.text.toString()) &&
                TextUtils.isEmpty(Lastname.text.toString()) &&
                TextUtils.isEmpty(Email.text.toString()) &&
                TextUtils.isEmpty(Password.text.toString())  &&
                TextUtils.isEmpty(ConfirmPassword.text.toString()) &&
                TextUtils.isEmpty(Esp.text.toString()) &&
                TextUtils.isEmpty(Nrocol.text.toString())
            )
            {
                Toast.makeText(this,"Complete los datos", Toast.LENGTH_SHORT).show()
            }
            else if (Password.text.toString() != ConfirmPassword.text.toString()){
                Toast.makeText(this,"Contraseña debe ser igual", Toast.LENGTH_SHORT).show()
            }
            else if(Password.length()<6){
                Toast.makeText(this,"La contraseña debe ser mayor a 6 carácteres", Toast.LENGTH_SHORT).show()
            }
            else{
                createSpecialist()
                /*val intent = Intent(this, SelectorActivity::class.java).apply {
                    //putExtra("Username",user.name)
                }
                startActivity(intent)*/
            }
        }
    }
    private fun createSpecialist(){

        val Firstname: EditText=findViewById<EditText>(R.id.tv_nombre1)
        val Lastname: EditText=findViewById<EditText>(R.id.tv_apellido1)
        val Email: EditText=findViewById<EditText>(R.id.tv_correo1)
        val Password: EditText=findViewById<EditText>(R.id.tv_contraseña1)
        val ConfirmPassword: EditText=findViewById<EditText>(R.id.tv_confirm_password)
        val Esp: EditText=findViewById<EditText>(R.id.tv_especialidad)
        val Nrocol: EditText=findViewById<EditText>(R.id.tv_nro_colegiatura)
        val  specialist = Specialist(Firstname.text.toString().replace("       ",""),
            Lastname.text.toString().replace("       ",""),
            Email.text.toString().replace("       ",""),
            Password.text.toString().replace("       ",""),
            Esp.text.toString().replace("       ",""),
            Nrocol.text.toString().replace("       ",""),
            ConfirmPassword.text.toString().replace("       ",""))

        //viewModel.createNewSpecialist(specialist)

        //TODO: AHORA -> registro con usuario especialista
        FirebaseAuth.getInstance()
            .createUserWithEmailAndPassword(specialist.Email,specialist.Password)
            .addOnCompleteListener{

                val mAuth = FirebaseAuth.getInstance()
                if (it.isSuccessful){

                    val uid = mAuth.currentUser?.uid
                    //TODO: ESTA VEZ, EL MAIL SERÁ LA CLAVE PARA LA BASE DE DATOS(NOSQL)
                    db.collection("specialists").document(uid.toString()).set(
                        hashMapOf(
                            "specialistId" to uid,
                            "specialistName" to specialist.Firstname,
                            "specialistLastname" to specialist.Lastname,
                            "specialistEmail" to specialist.Email,
                            "specialistArea" to specialist.Area,
                            "specialistTuitionNumber" to specialist.TuitionNumber,
                            "specialistRol" to "specialist",
                        )
                    )
                    Toast.makeText(this, "Registro de usuario especialista completado", Toast.LENGTH_LONG).show()
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
        viewModel = ViewModelProvider(this).get(CreateSpecialistVM::class.java)
        viewModel.getCreateNewSpecialistObserver().observe(this, Observer <SpecialistResponse?>{

            if(it  == null) {
                Toast.makeText(this, "Failed to create User", Toast.LENGTH_LONG).show()
            } else {
                //{"code":201,"meta":null,"data":{"id":2877,"name":"xxxxxaaaaabbbbb","email":"xxxxxaaaaabbbbb@gmail.com","gender":"male","status":"active"}}
                Toast.makeText(this, "Successfully created User", Toast.LENGTH_LONG).show()
            }
        })
    }
}