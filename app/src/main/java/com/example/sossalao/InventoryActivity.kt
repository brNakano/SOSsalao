package com.example.sossalao

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_inventory.*
import kotlinx.android.synthetic.main.lateral_menu_header.view.*
import kotlinx.android.synthetic.main.toolbar.*


open class InventoryActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    val context: Context get() = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inventory)
        Log.i("Informacao: ", {Prefs.getString("API_TOKEN")}.toString())

        setSupportActionBar(toolbar)
        supportActionBar?.title = "Produtos"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        configuraMenuLateral()


    }
/*    private fun loading(){
        GlobalScope.launch(context = Dispatchers.Main) {
            progressBar.visibility = View.VISIBLE
            delay(10000)
            progressBar.visibility = View.GONE
        }
    }*/

    private fun configuraMenuLateral() {

        var toogle = ActionBarDrawerToggle(this, drawerMenuLateral, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)

        drawerMenuLateral.addDrawerListener(toogle)
        toogle.syncState()

        val header = lateral_menu.getHeaderView(0)
        header.appHeaderTittle.text = "Produtos"

        lateral_menu.setNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_schedule -> {
                startActivity(Intent(this, ScheduleActivity::class.java))
            }

            R.id.nav_people -> {
                Toast.makeText(this, "Pessoas ainda não implementado", Toast.LENGTH_SHORT).show()
            }

            R.id.nav_products -> {
                startActivity(Intent(this, InventoryActivity::class.java))
            }

            R.id.nav_config -> {
                Toast.makeText(this, "Config ainda não implementado", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_logout -> {
                finish()
            }
        }

        drawerMenuLateral.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val id = item?.itemId

        when (id) {
            R.id.action_logout -> {
                finish()
            }
            R.id.action_refresh -> {
                Toast.makeText(context, "Botão de atualizar", Toast.LENGTH_LONG).show()
            }
            R.id.action_config -> {
                Toast.makeText(context, "Botão de configuracoes", Toast.LENGTH_LONG).show()
            }
            R.id.action_search -> {
                Toast.makeText(context, "Botão de buscar", Toast.LENGTH_LONG).show()
            }
        }

        return super.onOptionsItemSelected(item)
    }
}
