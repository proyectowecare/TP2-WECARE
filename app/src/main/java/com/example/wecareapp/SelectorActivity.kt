package com.example.wecareapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AutoCompleteTextView
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.wecareapp.databinding.ActivitySelectorBinding

import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarItemView

class SelectorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        // Instancia del view binding
        val binding = ActivitySelectorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val bottomNavigationView = binding.buttonNav
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.homeFragment, R.id.feelingFragment2,R.id.statsFragment)
        )

        val navController = navHostFragment.navController

        setupActionBarWithNavController(navController,appBarConfiguration)
        actionBar?.title="Hogar"
        // Sincronizacion del navController con nuesto bottom navigation
        bottomNavigationView.setupWithNavController(navController)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_options,menu)
        // PONER EL NOMBRE DEL USUARIO EN EL TÍTULO
        val bundle = intent.extras
        val nomPat = bundle?.getString("namePatient")
        val textView: TextView = findViewById<TextView>(R.id.textView6)

        textView.text = "Buenos días\n${nomPat}"
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId==R.id.poner_iddecuenta){
            Toast.makeText(this,"cuenta",Toast.LENGTH_SHORT).show()
            val intent = Intent(this, PerfilActivity::class.java).apply {
                //putExtra("Username",user.name)
            }
            startActivity(intent)
            return true
        }
        if(item.itemId==R.id.poner_iddecuentaesp){
            Toast.makeText(this,"cuenta_ESP",Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MiEspActivity::class.java).apply {
                //putExtra("Username",user.name)
            }
            startActivity(intent)
            return true
        }
        if(item.itemId==R.id.poner_idsolic){
            Toast.makeText(this,"solicitud",Toast.LENGTH_SHORT).show()
            val intent = Intent(this, RelaxSelectorActivity::class.java).apply {
                //putExtra("Username",user.name)
            }
            startActivity(intent)
            return true
        }
        if(item.itemId==R.id.poner_idrelax){
            Toast.makeText(this,"Relajacion",Toast.LENGTH_SHORT).show()
            val intent = Intent(this, RelaxSelectorActivity::class.java).apply {
                //putExtra("Username",user.name)
            }
            startActivity(intent)
            return true
        }
        if(item.itemId==R.id.poner_idcerrarsesion){
            Toast.makeText(this,"cerrar sesion",Toast.LENGTH_SHORT).show()
            val intent = Intent(this, RegisterEventActivity::class.java).apply {
                //putExtra("Username",user.name)
            }
            startActivity(intent)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    // TODO: ASIGNAR LOS IMAGEBUTTON
    fun goToMisEmociones(view:View)
    {
        val intent = Intent(this,  R.id.feelingFragment2::class.java).apply {
            //putExtra("Username",user.name)
        }
        startActivity(intent)
    }
}
