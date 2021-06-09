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
import com.example.sossalao.ui.people.*
import kotlinx.android.synthetic.main.activity_client.*
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
            this.clientList = listOf<People>()
            for (people in peopleList) {
                if (people.typePeople == 0){
                    this.clientList += people
                }
            }
            this.clientList = this.clientList.sortedByDescending { it.idPeople }
            runOnUiThread {

                recyclerClient?.adapter = PeopleAdapter(this.clientList) { onClickClient(it) }
            }
        }.start()

    }

    fun onClickClient(people: People) {
        val select = Prefs.getString("select")
        if (select == "normal"){
            val intent = Intent(context, PeopleDetailActivity::class.java)
            intent.putExtra("people", people)
            startActivityForResult(intent, REQUEST_REMOVE)
        }
        if(select == "add"){
            Prefs.setString("clientId", people.idPeople.toString())
            Prefs.setString("clientName", people.name)
            finish()
        }
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
                intent = Intent(this, FormPeopleActivity::class.java)
                intent.putExtra("view", "Cliente")
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