package com.example.lemiforce.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.lemiforce.R


class PitanjeAdapter (
    var odgovori: MutableList<String>,
    var odgovoreni: List<Int>?,
    var tacni: List<Int>,
    var context: Context,
    var simulacija: Boolean = false
) : RecyclerView.Adapter<PitanjeAdapter.PitanjeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PitanjeViewHolder {
        var layoutinflater = LayoutInflater.from(parent.context)
        var view = layoutinflater.inflate(R.layout.odgovor_item, parent, false)
        return PitanjeViewHolder(view)
    }
    override fun getItemCount(): Int = odgovori.size

    @SuppressLint("ResourceType")
    override fun onBindViewHolder(holder: PitanjeViewHolder, position: Int) {
        if(odgovoreni!=null) {
            if(!simulacija){
                if(odgovoreni!!.contains(position))
                    holder.cbCl.background = Drawable.createFromXml(context.resources, context.resources.getXml(R.drawable.red))
                if(tacni.contains(position))
                    holder.cbCl.background = Drawable.createFromXml(context.resources, context.resources.getXml(R.drawable.green))
                holder.cbOdgovor.isClickable = false
            }
            if(odgovoreni!!.contains(position)) holder.cbOdgovor.isChecked = true
        }
        holder.cbOdgovor.text = odgovori[position].toString()
    }
    inner class PitanjeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cbOdgovor: CheckBox = itemView.findViewById(R.id.cb)
        val cbCl : ConstraintLayout = itemView.findViewById(R.id.cbcl)
        fun isChecked() : Boolean = cbOdgovor.isChecked
    }
}

