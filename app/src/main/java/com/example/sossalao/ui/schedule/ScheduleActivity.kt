package com.example.sossalao.ui.schedule

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
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sossalao.Prefs
import com.example.sossalao.R
import com.example.sossalao.ui.people.client.ClientActivity
import com.example.sossalao.ui.people.employee.EmployeeActivity
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_inventory.drawerMenuLateral
import kotlinx.android.synthetic.main.activity_inventory.lateral_menu
import kotlinx.android.synthetic.main.activity_schedule.*
import kotlinx.android.synthetic.main.lateral_menu_header.view.*
import kotlinx.android.synthetic.main.toolbar.*

class ScheduleActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    val context: Context get() = this
    private var scheduleList = listOf<Schedule>()
    private var REQUEST_REMOVE= 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule)

        Log.i("Informacao: ", { Prefs.getString("API_TOKEN") }.toString())

        setSupportActionBar(toolbar)
        supportActionBar?.title = "Agenda"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        configMenu()

        recyclerSchedule?.layoutManager = LinearLayoutManager(context)
        recyclerSchedule?.itemAnimator = DefaultItemAnimator()
        recyclerSchedule?.setHasFixedSize(true)


    }

    override fun onResume() {
        super.onResume()
        taskSchedule()
    }

    fun taskSchedule() {
        Thread {
            this.scheduleList = ScheduleService.getSchedule(context)
            this.scheduleList = this.scheduleList.sortedByDescending { it.idScheduling }
            Log.d("listSort", this.scheduleList.map { it.idScheduling }.toString())
            runOnUiThread {

                recyclerSchedule?.adapter = ScheduleAdapter(this.scheduleList) { onClickSchedule(it) }
            }
        }.start()

    }

    fun onClickSchedule(schedule: Schedule) {
        val intent = Intent(context, ScheduleDetailActivity::class.java)
        intent.putExtra("schedule", schedule)
        startActivityForResult(intent, REQUEST_REMOVE)
    }

/*    private fun loading(){
        GlobalScope.launch(context = Dispatchers.Main) {
            progressBar.visibility = View.VISIBLE
            delay(10000)
            progressBar.visibility = View.GONE
        }
    }*/

    private fun configMenu() {

        var toogle = ActionBarDrawerToggle(this, drawerMenuLateral, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )

        drawerMenuLateral.addDrawerListener(toogle)
        toogle.syncState()

        val header = lateral_menu.getHeaderView(0)
        header.appHeaderTittle.text = "Agenda"

        lateral_menu.setNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_schedule -> {
                if (this !is ScheduleActivity) {
                    startActivity(Intent(this, ScheduleActivity::class.java))
                }
            }

            R.id.nav_employee -> {
                Prefs.setString("select", "normal")
                val intent = Intent(context, EmployeeActivity::class.java)
                intent.putExtra("select", "normal")
                startActivity(intent)
            }

            R.id.nav_client -> {
                Prefs.setString("select", "normal")
                val intent = Intent(context, ClientActivity::class.java)
                startActivity(intent)
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
            R.id.action_add -> {
                var intent = Intent(this, FormScheduleActivity::class.java)
                intent.putExtra("view", "Agendamento")
                intent.putExtra("mode", "post")
                startActivity(intent)
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