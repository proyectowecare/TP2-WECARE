package com.example.wecareapp
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.wecareapp.databinding.ActivitySpetialistSelectorBinding

class SpetialistSelectorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        // Instancia del view binding
        val binding = ActivitySpetialistSelectorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val bottomNavigationView = binding.spetialistButtonNav
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_spetialist_fragment) as NavHostFragment
        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.spetialistHome,R.id.stadisticsFragment)
        )

        val navController = navHostFragment.navController

        setupActionBarWithNavController(navController,appBarConfiguration)
        // Sincronizacion del navController con nuesto bottom navigation
        bottomNavigationView.setupWithNavController(navController)


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_spetialist_options,menu)
        val textView: TextView = findViewById<TextView>(R.id.textView19)
        // PONER EL NOMBRE DEL USUARIO EN EL TÍTULO
        val bundle = intent.extras
        if (bundle != null){
            val nomPat = bundle?.getString("nameSpecialist")
            val welcomeText= "Buenos días\n${nomPat}"
            textView.text =welcomeText
        }
        println("      ")
        println("      ")
        println(textView.text)
        println("      ")
        println("      ")
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.spetialist_account) {
            Toast.makeText(this, "cuenta", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, SpetialistProfileActivity::class.java).apply {
            }
            startActivity(intent)
            return true
        }

        if (item.itemId == R.id.spetialist_pending_patients) {
            Toast.makeText(this, "pacientes", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, PendingPatients::class.java).apply {
            }
            startActivity(intent)
            return true
        }
        return super.onOptionsItemSelected(item)

    }



}