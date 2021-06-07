package com.example.sossalao.ui.schedule

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.sossalao.R
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class ScheduleAdapter (
    val scheduleList: List<Schedule>,
    val onClick: (Schedule) -> Unit): RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder>() {


        class ScheduleViewHolder(view: View): RecyclerView.ViewHolder(view) {
            val scheduleCheckIn: TextView
            val scheduleEmployee: TextView
            val scheduleClient: TextView
            var cardView: CardView

            init {
                scheduleCheckIn = view.findViewById<TextView>(R.id.schedule_checkIn)
                scheduleEmployee = view.findViewById<TextView>(R.id.schedule_employee)
                scheduleClient = view.findViewById<TextView>(R.id.schedule_client)
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

            holder.scheduleCheckIn.text = schedule.checkIn
            holder.scheduleEmployee.text = "Id do empregado: ${schedule.employeeId}"
            holder.scheduleClient.text = "Id do cliente: ${schedule.clientId}"





            holder.itemView.setOnClickListener {onClick(schedule)}
        }
}