package com.example.sossalao.ui.schedule

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.sossalao.R
import com.example.sossalao.ui.people.PeopleService

class ScheduleAdapter (
    val scheduleList: List<Schedule>,
    val onClick: (Schedule) -> Unit): RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder>() {


        class ScheduleViewHolder(view: View): RecyclerView.ViewHolder(view) {
            val scheduleCheckIn: TextView
            val scheduleEmployee: TextView
            val scheduleClient: TextView
            val scheduleStatus: TextView
            val scheduleDayNum: TextView
            val scheduleDayWeek: TextView
            val fullDate: TextView
            var cardView: CardView

            init {
                scheduleCheckIn = view.findViewById<TextView>(R.id.schedule_checkIn)
                scheduleEmployee = view.findViewById<TextView>(R.id.schedule_employee)
                scheduleClient = view.findViewById<TextView>(R.id.schedule_client)
                scheduleStatus = view.findViewById<TextView>(R.id.detail_status)
                scheduleDayNum = view.findViewById<TextView>(R.id.day_num)
                scheduleDayWeek = view.findViewById<TextView>(R.id.week_day)
                fullDate = view.findViewById<TextView>(R.id.full_date)
                cardView = view.findViewById<CardView>(R.id.card_schedule)

            }

        }



        override fun getItemCount() = this.scheduleList.size


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleViewHolder {

            val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_schedule, parent, false)


            val holder = ScheduleViewHolder(view)
            return holder
        }



        override fun onBindViewHolder(holder: ScheduleViewHolder, position: Int) {
            val context = holder.itemView.context


            val schedule = scheduleList[position]

/*            val simpleDate = SimpleDateFormat("dd.E")
            val data = simpleDate.format(schedule.checkIn)*/

            val cliente = PeopleService.getPeopleById(context, schedule.clientId)
            val employee = PeopleService.getPeopleById(context, schedule.employeeId)

            val dateTime = "${ScheduleDate.hour(schedule.checkIn)}:${ScheduleDate.minute(schedule.checkIn)}"
            val fullDateTime = "${ScheduleDate.dayNum(schedule.checkIn)} de ${ScheduleDate.month(schedule.checkIn)} de ${ScheduleDate.year(schedule.checkIn)}"
            holder.scheduleDayNum.text = ScheduleDate.dayNum(schedule.checkIn).toString()
            holder.scheduleDayWeek.text = ScheduleDate.dayWeek(schedule.checkIn)
            holder.fullDate.text = fullDateTime
            holder.scheduleCheckIn.text = dateTime

            holder.scheduleStatus.text = StatusSchedule.getSchedulevalue(schedule.status)
            val color = StatusSchedule.getStatusColor(StatusSchedule.getSchedulevalue(schedule.status))
            holder.scheduleStatus.setBackgroundColor(ContextCompat.getColor(context, color))
            holder.scheduleEmployee.text = "Funcionário: ${employee.name}"
            holder.scheduleClient.text = "Cliente: ${cliente.name}"





            holder.itemView.setOnClickListener {onClick(schedule)}
        }

}