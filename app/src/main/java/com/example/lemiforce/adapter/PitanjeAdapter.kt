package com.example.lemiforce.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.example.lemiforce.R


class PitanjeAdapter (
    var odgovori: MutableList<String>,
    private val onItemClicked: (odgovor: String)->Unit
) : RecyclerView.Adapter<PitanjeAdapter.PitanjeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PitanjeViewHolder {
        var layoutinflater = LayoutInflater.from(parent.context)
        var view = layoutinflater.inflate(R.layout.odgovor_item, parent, false)
        return PitanjeViewHolder(view)
    }
    override fun getItemCount(): Int = odgovori.size

    override fun onBindViewHolder(holder: PitanjeViewHolder, position: Int) {
        holder.cbOdgovor.text = odgovori[position].toString()
    }

    fun UpdateOdgovore(odgovori: MutableList<String>) {
       this.odgovori = odgovori
        notifyDataSetChanged()
    }
    inner class PitanjeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cbOdgovor: CheckBox = itemView.findViewById(R.id.cb)
    }
}

