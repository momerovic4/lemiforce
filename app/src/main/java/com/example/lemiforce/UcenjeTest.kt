package com.example.lemiforce

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lemiforce.adapter.PitanjeAdapter
import com.example.lemiforce.model.Pitanje

class UcenjeTest : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var odgovorAdapter : PitanjeAdapter
    private var brojPitanja: Int = 1
    private var pitanje: Pitanje? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ucenje_test)

        var odgovori = listOf<String>("Odgovor broj jedan","Odgovor broj dva koji je malo duzi nego inace pa da vidim kako ce ovo raditi","Jos jedan odgovor","E sad je ovaj odgovor drama dug, tako da ako ovo bude rega bilo, onda ce sve biti rega jer je ovaj haos dug, brand new Richard milli cost a milly shoutout to my boy pacci")

        recyclerView = findViewById(R.id.rwOdgovori)

        var manager = GridLayoutManager(this,1)
        manager.reverseLayout = true
        recyclerView.layoutManager = manager

        odgovorAdapter = PitanjeAdapter(odgovori as MutableList<String>){}
        recyclerView.adapter = odgovorAdapter
    }
}