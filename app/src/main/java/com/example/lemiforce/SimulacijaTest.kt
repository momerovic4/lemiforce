package com.example.lemiforce

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.children
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
    private var brTacnih = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.simulacija_test)

        txtPitanje = findViewById(R.id.txtPolozili)
        txtBrojPitanja = findViewById(R.id.txtBrojPitanja)
        //btnSimulacija = findViewById(R.id.btnSimulacija)
        recyclerView = findViewById(R.id.rwOdgovori)

        findViewById<Button>(R.id.btnProslo).isEnabled = false

        setUpPitanje()
    }

    @SuppressLint("ResourceType")
    fun pokupiOdgovore() {
        var odgovoreni = mutableListOf<Int>()
        for (child in recyclerView.children) {
            if (odgovorAdapter.PitanjeViewHolder(child).isChecked()){
                val index = recyclerView.getChildAdapterPosition(child)
                odgovoreni.add(index)
//                child.background = Drawable.createFromXml(resources, resources.getXml(R.drawable.red))
            }
        }
//        for(tacno in pitanja[brojPitanja].tacaniOdgovri) {
//            recyclerView.get(tacno).background = Drawable.createFromXml(resources, resources.getXml(R.drawable.green))
//        }
        pitanja[brojPitanja].odgovoreni = odgovoreni
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

        odgovorAdapter = PitanjeAdapter(odgovori as MutableList<String>,pitanja[brojPitanja].odgovoreni,pitanja[brojPitanja].tacniOdgovori,this,!zavrsenPokusaj)
        recyclerView.adapter = odgovorAdapter
    }

    fun iducePitanje(view: View) {
        pokupiOdgovore()
        if(brojPitanja != pitanja.size-1){
            if (brojPitanja == pitanja.size-2) findViewById<Button>(R.id.btnIduce).text = "Predaj test"
            else findViewById<Button>(R.id.btnIduce).text = "iduce"
            brojPitanja++
            setUpPitanje()
            if(pitanja[brojPitanja].odgovoreni == null) enableClicableAndEnabled()
        }else{
            zavrsenPokusaj = true
            prikupiIPrikaziRezultat()
        }
        findViewById<Button>(R.id.btnProslo).isEnabled = true
    }

    fun prosloPitanje(view: View){
        if(brojPitanja!=0){
            findViewById<Button>(R.id.btnProslo).isEnabled = brojPitanja != 1
            findViewById<Button>(R.id.btnIduce).text = "iduce"
            brojPitanja--
            setUpPitanje()
            //disableClickableAndEnabled()
        }
    }

    var intentLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            if(data!!.hasExtra("PRVOPITANJE")) {
                brojPitanja = data!!.getIntExtra("PRVOPITANJE",0)
                setUpPitanje()
                findViewById<Button>(R.id.btnProslo).isEnabled = false
                findViewById<Button>(R.id.btnIduce).text = "iduce"
            } else {
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
        } else {
            setResult(Activity.RESULT_CANCELED, intent)
            finish()
        }
    }

    fun prikupiIPrikaziRezultat() {
        var tacnih = 0
        for(pitanje in pitanja){
            if(pitanje.odgovoreni!!.containsAll(pitanje.tacniOdgovori) && pitanje.tacniOdgovori.containsAll(pitanje.odgovoreni!!)) tacnih++
        }
        var intent = Intent(this,PrikazRezultata::class.java)
        intent.putExtra("OSTVARENIBODOVI",(tacnih.toDouble()/pitanja.size.toDouble())*100)
        intent.putExtra("BROJPITANJA",pitanja.size)
        intentLauncher.launch(intent)
    }
}