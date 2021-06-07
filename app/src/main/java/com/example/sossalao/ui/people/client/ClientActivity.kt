package com.example.sossalao.ui.people.client

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sossalao.Prefs
import com.example.sossalao.R
import com.example.sossalao.ui.FormInventoryActivity
import com.example.sossalao.ui.ProductDetailActivity
import com.example.sossalao.ui.people.People
import com.example.sossalao.ui.people.PeopleAdapter
import com.example.sossalao.ui.people.PeopleService
import kotlinx.android.synthetic.main.activity_client.*
import kotlinx.android.synthetic.main.activity_employee.*
import kotlinx.android.synthetic.main.activity_employee.recyclerEmployee
import kotlinx.android.synthetic.main.toolbar.*

class ClientActivity : AppCompatActivity() {

    val context: Context get() = this
    private var clientList = listOf<People>()
    private var REQUEST_REMOVE= 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client)

        Log.i("Informacao: ", { Prefs.getString("API_TOKEN") }.toString())

        setSupportActionBar(toolbar)
        supportActionBar?.title = "Clientes"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        recyclerClient?.layoutManager = LinearLayoutManager(context)
        recyclerClient?.itemAnimator = DefaultItemAnimator()
        recyclerClient?.setHasFixedSize(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true;
    }

    override fun onResume() {
        super.onResume()
        taskSchedule()
    }

    fun taskSchedule() {
        Thread {
            val peopleList = PeopleService.getPeople(context)
            for (people in peopleList) {
                if (people.typePeople == 0){
                    this.clientList += people
                }
            }
            runOnUiThread {

                recyclerClient?.adapter = PeopleAdapter(this.clientList) { onClickClient(it) }
            }
        }.start()

    }

    fun onClickClient(people: People) {
        val intent = Intent(context, ProductDetailActivity::class.java)
        intent.putExtra("schedule", people)
        startActivityForResult(intent, REQUEST_REMOVE)
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
                startActivity(Intent(this, FormInventoryActivity::class.java))
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