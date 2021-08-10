package com.example.lemiforce

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.view.children
import androidx.core.view.get
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lemiforce.adapter.PitanjeAdapter
import com.example.lemiforce.model.Pitanje
import com.example.lemiforce.viewmodel.ViewModel

class SimulacijaTest : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var odgovorAdapter : PitanjeAdapter
    private var zavrsenPokusaj: Boolean = false
    private val viewmodel = ViewModel()
    private var brojPitanja: Int = 0
    private var pitanja: List<Pitanje> = viewmodel.getPitanja()
    private lateinit var txtPitanje: TextView
    private lateinit var txtBrojPitanja: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.simulacija_test)

        txtPitanje = findViewById(R.id.txtTextPitanja)
        txtBrojPitanja = findViewById(R.id.txtBrojPitanja)
        //btnSimulacija = findViewById(R.id.btnSimulacija)
        recyclerView = findViewById(R.id.rwOdgovori)

        findViewById<Button>(R.id.btnProslo).isEnabled = false

        setUpPitanje()
    }

    @SuppressLint("ResourceType")
    fun pokupiOdgovore() {
        var odgovreni = mutableListOf<Int>()
        for (child in recyclerView.children) {
            if (odgovorAdapter.PitanjeViewHolder(child).isChecked()){
                val index = recyclerView.getChildAdapterPosition(child)
                odgovreni.add(index)
//                child.background = Drawable.createFromXml(resources, resources.getXml(R.drawable.red))
            }
        }
//        for(tacno in pitanja[brojPitanja].tacaniOdgovri) {
//            recyclerView.get(tacno).background = Drawable.createFromXml(resources, resources.getXml(R.drawable.green))
//        }
        pitanja[brojPitanja].odgovoreni = odgovreni
//        disableClickableAndEnabled()
    }

    fun disableClickableAndEnabled() {
        for (child in recyclerView.children) {
            odgovorAdapter.PitanjeViewHolder(child).cbOdgovor.isClickable = false
        }
        //findViewById<Button>(R.id.btnSimulacija).isEnabled = false
    }

    fun enableClicableAndEnabled() {
        for (child in recyclerView.children) {
            odgovorAdapter.PitanjeViewHolder(child).cbOdgovor.isClickable = true
        }
        //findViewById<Button>(R.id.btnSimulacija).isEnabled = true
    }

    fun setUpPitanje() {
        txtPitanje.text = pitanja.get(brojPitanja).text
        txtBrojPitanja.text = "${brojPitanja+1}."

        var odgovori = pitanja.get(brojPitanja).ponudjeniOdgovori

        var manager = GridLayoutManager(this,1)
        manager.reverseLayout = true
        recyclerView.layoutManager = manager

        odgovorAdapter = PitanjeAdapter(odgovori as MutableList<String>,pitanja[brojPitanja].odgovoreni,pitanja[brojPitanja].tacaniOdgovri,this,!zavrsenPokusaj)
        recyclerView.adapter = odgovorAdapter
    }

    fun iducePitanje(view: View) {
        pokupiOdgovore()
        //TODO promijeniti brojeve na 20 pitanja
        if(brojPitanja != 3){
            if (brojPitanja == 2) findViewById<Button>(R.id.btnIduce).text = "Predaj test"
            else findViewById<Button>(R.id.btnIduce).text = "iduce"
            brojPitanja++
            setUpPitanje()
            if(pitanja[brojPitanja].odgovoreni == null) enableClicableAndEnabled()
        }else{
            //todo prikazati rezultate
            zavrsenPokusaj = true
            setUpPitanje()
        }
        findViewById<Button>(R.id.btnProslo).isEnabled = true
    }

    fun prosloPitanje(view: View){
        if(brojPitanja!=0){
            if (brojPitanja == 1) findViewById<Button>(R.id.btnProslo).isEnabled = false
            else findViewById<Button>(R.id.btnProslo).isEnabled = true
            findViewById<Button>(R.id.btnIduce).text = "iduce"
            brojPitanja--
            setUpPitanje()
            //disableClickableAndEnabled()
        }
    }
}