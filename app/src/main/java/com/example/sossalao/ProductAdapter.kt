package com.example.sossalao

import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView

// define o construtor que recebe a lista de Product e o evento de clique
class DisciplinaAdapter (
    val Product: List<Product>,
    val onClick: (Product) -> Unit): RecyclerView.Adapter<DisciplinaAdapter.ProductViewHolder>() {

    // ViewHolder com os elemetos da tela
    class ProductViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val cardName: TextView
        val cardMake : TextView
        var cardDescription: TextView
        var cardView: CardView

        init {
            cardName = view.findViewById<TextView>(R.id.produto_titulo)
            cardMake = view.findViewById<TextView>(R.id.make)
            cardDescription = view.findViewById<TextView>(R.id.produto_descricao)
            cardView = view.findViewById<CardView>(R.id.card_produtos)

        }

    }

    // Quantidade de Product na lista

    override fun getItemCount() = this.Product.size

    // inflar layout do adapter

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        // infla view no adapter
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_product_list, parent, false)

        // retornar ViewHolder
        val holder = ProductViewHolder(view)
        return holder
    }

    // bind para atualizar Views com os dados

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val context = holder.itemView.context

        // recuperar objeto disciplina
        val disciplina = Product[position]

        // atualizar dados de disciplina

        holder.cardName.text = disciplina.name


        // adiciona evento de clique
        holder.itemView.setOnClickListener {onClick(disciplina)}
    }
}

