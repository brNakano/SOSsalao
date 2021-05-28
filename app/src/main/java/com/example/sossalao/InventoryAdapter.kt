package com.example.sossalao

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView


class InventoryAdapter (
    val inventoryList: List<Inventory>,
    val onClick: (Inventory) -> Unit): RecyclerView.Adapter<InventoryAdapter.InventoryViewHolder>() {


    class InventoryViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val productName: TextView
        val productMake: TextView
        val productDescription: TextView
        var cardView: CardView

        init {
            productName = view.findViewById<TextView>(R.id.product_name)
            productMake = view.findViewById<TextView>(R.id.product_make)
            productDescription = view.findViewById<TextView>(R.id.product_description)
            cardView = view.findViewById<CardView>(R.id.card_inventory)

        }

    }



    override fun getItemCount() = this.inventoryList.size


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InventoryViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_inventory, parent, false)


        val holder = InventoryViewHolder(view)
        return holder
    }



    override fun onBindViewHolder(holder: InventoryViewHolder, position: Int) {
        val context = holder.itemView.context


        val inventory = inventoryList[position]


        holder.productName.text = inventory.name.capitalize()
        holder.productMake.text = "Marca: ${inventory.make.capitalize()}"
        holder.productDescription.text = "Descrição: ${inventory.description.capitalize()}"





        holder.itemView.setOnClickListener {onClick(inventory)}
    }
}