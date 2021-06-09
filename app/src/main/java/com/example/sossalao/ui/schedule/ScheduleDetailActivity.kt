package com.example.sossalao.ui.schedule

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.example.sossalao.Prefs
import com.example.sossalao.R
import com.example.sossalao.ui.people.FormPeopleActivity
import com.example.sossalao.ui.people.People
import com.example.sossalao.ui.people.PeopleService
import kotlinx.android.synthetic.main.activity_people_detail.*
import kotlinx.android.synthetic.main.activity_schedule_detail.*
import kotlinx.android.synthetic.main.toolbar.*

class ScheduleDetailActivity : AppCompatActivity() {

    private val context: Context get() = this
    var schedule: Schedule? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule_detail)

        if (intent.getSerializableExtra("schedule") is Schedule)
            schedule = intent.getSerializableExtra("schedule") as Schedule

        setSupportActionBar(toolbar)
        supportActionBar?.title = "Agendamento"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setFields()

        detail_schedule_edit.setOnClickListener { onClickEdit(schedule) }
        detail_schedule_delete.setOnClickListener { onClickDelete(schedule) }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true;
    }

    private fun onClickDelete(schedule: Schedule?) {
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
        if (this.schedule != null && this.schedule is Schedule) {

            Thread {
                ScheduleService.delete(this.schedule as Schedule)
                runOnUiThread {
                    finish()
                }
            }.start()
        }
    }

    fun onClickEdit(schedule: Schedule?) {
        intent = Intent(context, FormScheduleActivity::class.java)
        intent.putExtra("schedule", schedule)
        intent.putExtra("mode", "put")

        startActivity(intent)
    }

    private fun setFields() {
        detail_date.text = "${ScheduleDate.dayNum(schedule?.checkIn)} de ${ScheduleDate.month(schedule?.checkIn)} de ${ScheduleDate.year(schedule?.checkIn)}"
        var client = PeopleService.getPeopleById(context, schedule?.clientId)
        detail_client.text = client.name
        var employee = PeopleService.getPeopleById(context, schedule?.employeeId)
        detail_employee.text = employee.name
        detail_status_edit.text = schedule?.status?.let { StatusSchedule.getSchedulevalue(it) }
    }

    override fun onRestart() {
        super.onRestart()
        val date = Prefs.getString("schedule_date")
        val clientId = Prefs.getString("schedule_clientId")
        val employeeId = Prefs.getString("schedule_employeeId")
        val status = Prefs.getString("schedule_status")?.let { StatusSchedule.getSchedulevalue(it.toInt()) }


        detail_date.text = "${ScheduleDate.dayNum(date)} de ${ScheduleDate.month(date)} de ${ScheduleDate.year(date)}"
        var client = PeopleService.getPeopleById(context, clientId?.toInt())
        detail_client.text = client.name
        var employee = PeopleService.getPeopleById(context, employeeId?.toInt())
        detail_employee.text = employee.name
        detail_status_edit.text = status
    }
}