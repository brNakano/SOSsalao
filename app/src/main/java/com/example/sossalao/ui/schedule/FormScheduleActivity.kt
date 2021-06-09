package com.example.sossalao.ui.schedule

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.sossalao.Prefs
import com.example.sossalao.R
import com.example.sossalao.ui.people.PeopleService
import com.example.sossalao.ui.people.client.ClientActivity
import com.example.sossalao.ui.people.employee.EmployeeActivity
import kotlinx.android.synthetic.main.activity_form_schedule.*
import kotlinx.android.synthetic.main.toolbar.*

class FormScheduleActivity : AppCompatActivity() {
    var schedulePut: Schedule? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_schedule)

        val args = intent.extras
        val mode = args?.getString("mode")

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (mode == "post"){
            supportActionBar?.title = "Cadastrar Agendamento"
        }
        if (mode == "put"){
            if (intent.getSerializableExtra("schedule") is Schedule)
                schedulePut = intent.getSerializableExtra("schedule") as Schedule
            supportActionBar?.title = "Editar Agendamento"

            form_schedule_day.editText?.setText(ScheduleDate.dayNum(schedulePut?.checkIn))
            form_schedule_month.editText?.setText(ScheduleDate.monthValue(schedulePut?.checkIn))
            form_schedule_year.editText?.setText(ScheduleDate.year(schedulePut?.checkIn).toString())
            form_schedule_hour.editText?.setText(ScheduleDate.hour(schedulePut?.checkIn).toString())
            form_schedule_minute.editText?.setText(ScheduleDate.minute(schedulePut?.checkIn).toString())
            val clientName = PeopleService.getPeopleById(this, schedulePut?.clientId)
            val employeeName = PeopleService.getPeopleById(this, schedulePut?.employeeId)
            form_client_name.text = clientName.name
            form_employee_name.text = employeeName.name
            form_schedule_send.text = "Editar"
        }

        client_select.setOnClickListener { addClient() }
        employee_select.setOnClickListener { addEmployee() }

        form_schedule_cancel.setOnClickListener { finish() }
        form_schedule_send.setOnClickListener { selectMode(mode) }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true;
    }

    private fun selectMode(mode: String?) {
        if(checkFields()){
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_LONG).show()
            return
        }
        if (mode == "post"){
            postSchedule()
        }
        if (mode == "put"){
            updateSchedule(schedulePut)
        }
    }

    private fun postSchedule() {
        val schedule = Schedule()
        val date = "${form_schedule_year.editText?.text.toString()}-${form_schedule_month.editText?.text.toString()}-${form_schedule_day.editText?.text.toString()}T${form_schedule_hour.editText?.text.toString()}:${form_schedule_minute.editText?.text.toString()}:00"

        schedule.checkIn = date
        schedule.checkOut = date
        schedule.clientId = Prefs.getString("clientId")?.toInt()
        schedule.employeeId = Prefs.getString("employeeId")?.toInt()
        val status = statusRadio(form_status.checkedRadioButtonId)
        schedule.status = status
        taskSave(schedule)
    }

    private fun taskSave(schedule: Schedule) {
        Thread {
            ScheduleService.save(schedule)
            runOnUiThread {
                finish()
            }
        }.start()
    }

    private fun updateSchedule(schedule: Schedule?) {
        val date = "${form_schedule_year.editText?.text.toString()}-${form_schedule_month.editText?.text.toString()}-${form_schedule_day.editText?.text.toString()}T${form_schedule_hour.editText?.text.toString()}:${form_schedule_minute.editText?.text.toString()}:00"

        schedule?.checkIn = date
        schedule?.checkOut = date
        schedule?.clientId = Prefs.getString("clientId")?.toInt()
        schedule?.employeeId = Prefs.getString("employeeId")?.toInt()
        val status = statusRadio(form_status.checkedRadioButtonId)
        schedule?.status = status

        taskUpdate(schedule as Schedule)
    }

    fun taskUpdate(schedule: Schedule){
        Thread {
            ScheduleService.update(schedule)
            Prefs.setString("schedule_date", schedule.checkIn)
            Prefs.setString("schedule_clientId", schedule.clientId.toString())
            Prefs.setString("schedule_employeeId", schedule.employeeId.toString())
            Prefs.setString("schedule_status", schedule.status.toString())
            runOnUiThread {
                finish()
            }
        }.start()
    }

    private fun addEmployee() {
        Prefs.setString("select", "add")
        var intent = Intent(this, EmployeeActivity::class.java)
        startActivity(intent)
    }

    private fun addClient() {
        Prefs.setString("select", "add")
        var intent = Intent(this, ClientActivity::class.java)
        startActivity(intent)
    }

    fun statusRadio(id: Int): Int {
        when(id){
            R.id.form_Adiado -> {
                return 0
            }
            R.id.form_cancelado -> {
                return 2
            }
            R.id.form_atendido -> {
                return 1
            }
            R.id.form_marcado -> {
                return 3
            }
        }
        return 3
    }

    fun checkFields(): Boolean {
        var fields = listOf<String?>()
        fields += form_schedule_day.editText?.text.toString()
        fields += form_schedule_month.editText?.text.toString()
        fields += form_schedule_year.editText?.text.toString()
        fields += form_schedule_hour.editText?.text.toString()
        fields += form_schedule_minute.editText?.text.toString()
        fields += Prefs.getString("clientName")
        fields += Prefs.getString("employeeName")

        for (field in fields) {
            if (field == "" || field == null){
                return true
            }
        }
        return false

    }

    override fun onRestart() {
        super.onRestart()
        val clientName = Prefs.getString("clientName")

        if (clientName != ""){
            form_client_name.setText(clientName)
        }
        val employeeName = Prefs.getString("employeeName")
        if (employeeName != ""){
            form_employee_name.setText(employeeName)
        }

    }
}