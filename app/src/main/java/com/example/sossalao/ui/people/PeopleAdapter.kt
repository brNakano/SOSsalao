package com.example.sossalao.ui.people

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.sossalao.R

class PeopleAdapter (
    val peopleList: List<People>,
    val onClick: (People) -> Unit): RecyclerView.Adapter<PeopleAdapter.PeopleViewHolder>() {


    class PeopleViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val peopleName: TextView
        val peoplePhone: TextView
        val peopleEmail: TextView
        var cardView: CardView

        init {
            peopleName = view.findViewById<TextView>(R.id.people_name)
            peoplePhone = view.findViewById<TextView>(R.id.people_phone)
            peopleEmail = view.findViewById<TextView>(R.id.people_email)
            cardView = view.findViewById<CardView>(R.id.card_people)

        }

    }



    override fun getItemCount() = this.peopleList.size


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeopleViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_people, parent, false)


        val holder = PeopleViewHolder(view)
        return holder
    }



    override fun onBindViewHolder(holder: PeopleViewHolder, position: Int) {
        val context = holder.itemView.context


        val people = peopleList[position]

        holder.peopleName.text = "Nome: ${people.name}"
        holder.peopleEmail.text = "Email: ${people.email}"
        holder.peoplePhone.text = "Telefone: ${people.phoneNumber}"





        holder.itemView.setOnClickListener {onClick(people)}
    }
}