package com.example.sossalao

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_schedule.*
import kotlinx.android.synthetic.main.lateral_menu_header.*
import kotlinx.android.synthetic.main.lateral_menu_header.view.*
import kotlinx.android.synthetic.main.toolbar.*


class ScheduleActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule)
        val it = intent
        val token = it.getStringExtra("tokenAuth")
        Log.i("Informacao: ", token.toString())

        setSupportActionBar(toolbar)
        supportActionBar?.title = "Agenda"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        configuraMenuLateral()
        mensagemInicial.text = "nada"


    }
/*    private fun loading(){
        GlobalScope.launch(context = Dispatchers.Main) {
            progressBar.visibility = View.VISIBLE
            delay(10000)
            progressBar.visibility = View.GONE
        }
    }*/

    private fun configuraMenuLateral() {
        // ícone de menu (hamburger) para mostrar o menu
        var toogle = ActionBarDrawerToggle(this, drawerMenuLateral, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)

        drawerMenuLateral.addDrawerListener(toogle)
        toogle.syncState()

        val header = lateral_menu.getHeaderView(0)
        header.appHeaderTittle.text = "Agenda"

        lateral_menu.setNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_diciplinas -> {
                Toast.makeText(this, "Clicou Disciplinas", Toast.LENGTH_SHORT).show()
            }

            R.id.nav_mensagens -> {
                Toast.makeText(this, "Clicou Mensagens", Toast.LENGTH_SHORT).show()
            }

            R.id.nav_forum -> {
                Toast.makeText(this, "Clicou Forum", Toast.LENGTH_SHORT).show()
            }

            R.id.nav_localizacao -> {
                Toast.makeText(this, "Clicou Localização", Toast.LENGTH_SHORT).show()
            }

            R.id.nav_config -> {
                Toast.makeText(this, "Clicou Config", Toast.LENGTH_SHORT).show()
            }
        }

        // fecha menu depois de tratar o evento
        drawerMenuLateral.closeDrawer(GravityCompat.START)
        return true
    }
}
