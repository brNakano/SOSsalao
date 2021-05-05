package com.example.sossalao

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.marginLeft
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.bottombar.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        action_add.setOnClickListener{
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        bottom_home.replaceMenu(R.menu.menu)

        bottom_home.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_search -> Toast.makeText(this@HomeActivity,"Deseja pesquisar algo?", Toast.LENGTH_SHORT).show()
                R.id.action_config -> startActivity(Intent(this, SettingsActivity::class.java))
                R.id.action_refresh -> loading()
                R.id.action_logout -> finish()
                R.id.menu_toggle -> layoutMenuLateral.openDrawer(GravityCompat.START)

            };   true

        }

        configuraMenuLateral()

    }

    // adiciona os atributos da view
    private fun configuraMenuLateral() {
        var toogle = ActionBarDrawerToggle(
            this,
            layoutMenuLateral,
            R.string.nav_open,
            R.string.nav_close)

        layoutMenuLateral.addDrawerListener(toogle)
        toogle.syncState()

        menu_lateral.setNavigationItemSelectedListener(this)
    }


    // tabs do menu lateral
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.scheduling -> {
                Toast.makeText(this, "Clicou agenda", Toast.LENGTH_SHORT).show()
            }

            R.id.user_management -> {
                Toast.makeText(this, "Clicou Usuários", Toast.LENGTH_SHORT).show()
            }

            R.id.clients -> {
                Toast.makeText(this, "Clicou Clientes", Toast.LENGTH_SHORT).show()
            }

            R.id.products -> {
                Toast.makeText(this, "Clicou Produtos", Toast.LENGTH_SHORT).show()
            }

            R.id.procedure -> {
                Toast.makeText(this, "Clicou procedure", Toast.LENGTH_SHORT).show()
            }
            R.id.lateral_logout -> {
                finish()
            }
        }

        layoutMenuLateral.closeDrawer(GravityCompat.START)
        return true
    }

    private fun loading(){
        GlobalScope.launch(context = Dispatchers.Main) {
            progressBar.visibility = View.VISIBLE
            delay(10000)
            progressBar.visibility = View.GONE
        }
    }
}
