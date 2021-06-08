package com.example.sossalao.ui.people

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AlertDialog
import com.example.sossalao.Prefs
import com.example.sossalao.R

import kotlinx.android.synthetic.main.activity_people_detail.*

import kotlinx.android.synthetic.main.toolbar.*

class PeopleDetailActivity : AppCompatActivity() {

    private val context: Context get() = this
    var people: People? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_people_detail)

        if (intent.getSerializableExtra("people") is People)
            people = intent.getSerializableExtra("people") as People

        setSupportActionBar(toolbar)
        supportActionBar?.title = people?.name
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        detail_people_name.text = people?.name.toString()
        detail_people_type.text = people?.getType()
        detail_people_phone.text = people?.phoneNumber.toString()
        detail_people_email.text = people?.email.toString()

        detail_people_edit.setOnClickListener { onClickEdit(people) }
        detail_people_delete.setOnClickListener { onClickDelete(people) }
    }

    private fun onClickDelete(people: People?) {
        AlertDialog.Builder(this)
            .setTitle(R.string.app_name)
            .setMessage("Deseja excluir?")
            .setPositiveButton("Sim") {
                    dialog, which ->
                taskDelete()
            }.setNegativeButton("Não") {
                    dialog, which -> dialog.dismiss()
            }.create().show()
    }

    private fun taskDelete() {
        if (this.people != null && this.people is People) {

            Thread {
                PeopleService.delete(this.people as People)
                runOnUiThread {
                    finish()
                }
            }.start()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true;
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        return true
    }

    fun onClickEdit(people: People?) {
        intent = Intent(context, FormPeopleActivity::class.java)
        intent.putExtra("people", people)
        intent.putExtra("mode", "put")
        intent.putExtra("view", people?.getType())

        startActivity(intent)
    }

    override fun onRestart() {
        super.onRestart()

        val peopleName = Prefs.getString("people_name")
        val peopleEmail = Prefs.getString("people_email")
        val peoplePhone = Prefs.getString("people_phone")

        detail_people_name.text = peopleName
        detail_people_phone.text = peoplePhone
        detail_people_email.text = peopleEmail
    }
}