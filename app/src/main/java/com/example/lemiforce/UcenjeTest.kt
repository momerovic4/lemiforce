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

class UcenjeTest : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var odgovorAdapter : PitanjeAdapter
    private lateinit var btnSimulacija : Button
    private val viewmodel = ViewModel()
    private var brojPitanja: Int = 0
    private var pitanja: List<Pitanje> = viewmodel.getPitanja()
    private lateinit var txtPitanje: TextView
    private lateinit var txtBrojPitanja: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ucenje_test)

        txtPitanje = findViewById(R.id.txtPolozili)
        txtBrojPitanja = findViewById(R.id.txtBrojPitanja)
        btnSimulacija = findViewById(R.id.btnNazad)
        recyclerView = findViewById(R.id.rwOdgovori)

        findViewById<Button>(R.id.btnProslo).isEnabled = false

        setUpPitanje()
    }

    @SuppressLint("ResourceType")
    fun pokupiOdgovore(view: View) {
        var odgovoreni = mutableListOf<Int>()
        for (child in recyclerView.children) {
            if (odgovorAdapter.PitanjeViewHolder(child).isChecked()){
                val index = recyclerView.getChildAdapterPosition(child)
                odgovoreni.add(index)
                child.background = Drawable.createFromXml(resources, resources.getXml(R.drawable.red))
            }
        }
        for(tacno in pitanja[brojPitanja].tacniOdgovori) {
            recyclerView.get(tacno).background = Drawable.createFromXml(resources, resources.getXml(R.drawable.green))
        }
        pitanja[brojPitanja].odgovoreni = odgovoreni
        disableClickableAndEnabled()
    }

    fun disableClickableAndEnabled() {
        for (child in recyclerView.children) {
            odgovorAdapter.PitanjeViewHolder(child).cbOdgovor.isClickable = false
        }
        findViewById<Button>(R.id.btnNazad).isEnabled = false
    }

    fun enableClicableAndEnabled() {
        for (child in recyclerView.children) {
            odgovorAdapter.PitanjeViewHolder(child).cbOdgovor.isClickable = true
        }
        findViewById<Button>(R.id.btnNazad).isEnabled = true

    }

    fun setUpPitanje() {
        txtPitanje.text = pitanja.get(brojPitanja).text
        txtBrojPitanja.text = "${brojPitanja+1}."

        var odgovori = pitanja.get(brojPitanja).ponudjeniOdgovori

        var manager = GridLayoutManager(this,1)
        manager.reverseLayout = true
        recyclerView.layoutManager = manager

        odgovorAdapter = PitanjeAdapter(odgovori as MutableList<String>,pitanja.get(brojPitanja).odgovoreni,pitanja[brojPitanja].tacniOdgovori,this)
        recyclerView.adapter = odgovorAdapter
    }

    fun iducePitanje(view: View) {
        if(pitanja[brojPitanja].odgovoreni == null){
            Toast.makeText(applicationContext,"Prvo provjerite odgovor",Toast.LENGTH_SHORT).show()
        }else{
            //TODO promijeniti brojeve na 20 pitanja
            if(brojPitanja != 3){
                if (brojPitanja == 2) findViewById<Button>(R.id.btnIduce).text = "Zavrsi pokusaj"
                else findViewById<Button>(R.id.btnIduce).text = "iduce"
                brojPitanja++
                setUpPitanje()
                if(pitanja[brojPitanja].odgovoreni == null) enableClicableAndEnabled()
            }else{
                onBackPressed()
            }
            findViewById<Button>(R.id.btnProslo).isEnabled = true
        }
    }

    fun prosloPitanje(view: View){
        if(brojPitanja!=0){
            findViewById<Button>(R.id.btnProslo).isEnabled = brojPitanja != 1
            findViewById<Button>(R.id.btnIduce).text = "iduce"
            brojPitanja--
            setUpPitanje()
            disableClickableAndEnabled()
        }
    }
}