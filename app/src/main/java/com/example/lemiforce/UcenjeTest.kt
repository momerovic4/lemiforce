package com.example.lemiforce

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.children
import androidx.core.view.get
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lemiforce.adapter.PitanjeAdapter
import com.example.lemiforce.model.Pitanje
import com.example.lemiforce.viewmodel.ViewModel
import org.w3c.dom.Text

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

        txtPitanje = findViewById(R.id.txtTextPitanja)
        txtBrojPitanja = findViewById(R.id.txtBrojPitanja)
        btnSimulacija = findViewById(R.id.btnSimulacija)
        recyclerView = findViewById(R.id.rwOdgovori)

        setUpPitanje()
    }

    @SuppressLint("ResourceType")
    fun pokupiOdgovore(view: View) {
        var odgovreni = mutableListOf<Int>()
        for (child in recyclerView.children) {
            if (odgovorAdapter.PitanjeViewHolder(child).isChecked()){
                val index = recyclerView.getChildAdapterPosition(child)
                odgovreni.add(index)
                child.background = Drawable.createFromXml(resources, resources.getXml(R.drawable.red))
            }
        }
        for(tacno in pitanja[brojPitanja].tacaniOdgovri) {
            recyclerView.get(tacno).background = Drawable.createFromXml(resources, resources.getXml(R.drawable.green))
        }
        pitanja[brojPitanja].odgovreni = odgovreni
        disableClickableAndEnabled()
    }

    fun disableClickableAndEnabled() {
        for (child in recyclerView.children) {
            odgovorAdapter.PitanjeViewHolder(child).cbOdgovor.isClickable = false
        }
        findViewById<Button>(R.id.btnSimulacija).isEnabled = false
    }

    fun enableClicableAndEnabled() {
        for (child in recyclerView.children) {
            odgovorAdapter.PitanjeViewHolder(child).cbOdgovor.isClickable = true
        }
        findViewById<Button>(R.id.btnSimulacija).isEnabled = true

    }

    fun setOdgovoreni(odgovori: List<Int>?){
        if(odgovori != null){
            for (child in recyclerView.children) {
                val index = recyclerView.getChildAdapterPosition(child)
                if(odgovori.contains(index))
                odgovorAdapter.PitanjeViewHolder(child).cbOdgovor.isChecked = true
            }
        }
    }

    fun setUpPitanje() {
        txtPitanje.text = pitanja.get(brojPitanja).text
        txtBrojPitanja.text = "${brojPitanja+1}."

        var odgovori = pitanja.get(brojPitanja).ponudjeniOdgovori

        var manager = GridLayoutManager(this,1)
        manager.reverseLayout = true
        recyclerView.layoutManager = manager

        odgovorAdapter = PitanjeAdapter(odgovori as MutableList<String>){}
        recyclerView.adapter = odgovorAdapter
    }

    fun iducePitanje(view: View) {
        if(pitanja[brojPitanja].odgovreni == null){
            Toast.makeText(applicationContext,"Prvo provjerite odgovor",Toast.LENGTH_SHORT).show()
        }else{
            if(brojPitanja != 19){
                brojPitanja++
                setUpPitanje()
                if(pitanja[brojPitanja].odgovreni == null) enableClicableAndEnabled()
            }
        }

    }

    fun prosloPitanje(view: View){
        if(brojPitanja!=0){
            brojPitanja--
            setUpPitanje()
            disableClickableAndEnabled()
            setOdgovoreni(pitanja[brojPitanja].odgovreni)
        }
    }
}