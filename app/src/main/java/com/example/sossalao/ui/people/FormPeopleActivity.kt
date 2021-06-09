package com.example.sossalao.ui.people

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sossalao.Prefs
import com.example.sossalao.R
import kotlinx.android.synthetic.main.activity_form_people.*
import kotlinx.android.synthetic.main.toolbar.*

class FormPeopleActivity : AppCompatActivity() {
    var peoplePut: People? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_people)

        val args = intent.extras
        val title = args?.getString("view")
        val mode = args?.getString("mode")

        setSupportActionBar(toolbar)
        if (mode == "post"){
            supportActionBar?.title = "Cadastrar $title"
        }
        if (mode == "put"){
            if (intent.getSerializableExtra("people") is People)
                peoplePut = intent.getSerializableExtra("people") as People
            supportActionBar?.title = "Editar $title"
            form_people_name.editText?.setText(peoplePut?.name)
            form_people_email.editText?.setText(peoplePut?.email)
            form_people_phone.editText?.setText(peoplePut?.phoneNumber)
            form_people_send.text = "Editar"
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        form_people_send.setOnClickListener { selectMode(mode, title) }
        form_people_cancel.setOnClickListener { finish() }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true;
    }

    fun selectMode(mode: String?, title: String?){
        if (mode == "post"){
            postPeople(title)
        }
        if (mode == "put"){
            updatePeople(peoplePut)
        }
    }

    private fun updatePeople(people: People?) {
        people?.name = form_people_name.editText?.text.toString()
        people?.email = form_people_email.editText?.text.toString()
        people?.phoneNumber = form_people_phone.editText?.text.toString()
        taskUpdate(people as People)

    }

    fun postPeople(title: String?){
        val people = People()
        people.name = form_people_name.editText?.text.toString()
        people.email = form_people_email.editText?.text.toString()
        people.phoneNumber = form_people_phone.editText?.text.toString()
        if (title == "Funcionário"){
            people.typePeople = 1
        }
        if (title == "Cliente"){
            people.typePeople = 0
        }

        taskSave(people)
    }

    private fun taskSave(people: People) {
        Thread {
            PeopleService.save(people)
            runOnUiThread {
                finish()
            }
        }.start()
    }

    private fun taskUpdate(people: People) {
        Thread {
            PeopleService.update(people)
            Prefs.setString("people_name", people.name)
            Prefs.setString("people_email", people.email)
            Prefs.setString("people_phone", people.phoneNumber)
            runOnUiThread {
                finish()
            }
        }.start()
    }

}