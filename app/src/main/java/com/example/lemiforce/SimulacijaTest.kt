package com.example.lemiforce

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
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
    private lateinit var pitanja: MutableList<Pitanje>
    private lateinit var txtPitanje: TextView
    private lateinit var txtBrojPitanja: TextView
    private lateinit var imgSlika: ImageView
    private var brTacnih = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.simulacija_test)

        txtPitanje = findViewById(R.id.txtPolozili)
        txtBrojPitanja = findViewById(R.id.txtBrojPitanja)
        imgSlika = findViewById(R.id.imgSlika)
        pitanja = viewmodel.getPitanjaZaKategoriju(intent.getStringExtra("KATEGORIJA")) as MutableList<Pitanje>
        pitanja.addAll(viewmodel.getPitanjaZnakove(10))
        pitanja.addAll(viewmodel.getPitanjaRaskrsnice(10))
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

        if(pitanja.get(brojPitanja).kategorija.equals("R") || pitanja.get(brojPitanja).kategorija.equals("Z")){
            val PACKAGE_NAME = applicationContext.packageName
            val imgId = resources.getIdentifier("$PACKAGE_NAME:drawable/${pitanja.get(brojPitanja).slika}", null, null)
            imgSlika.setImageBitmap(BitmapFactory.decodeResource(resources, imgId))
        }

        var manager = GridLayoutManager(this,1)
        manager.reverseLayout = true
        recyclerView.layoutManager = manager

        odgovorAdapter = PitanjeAdapter(odgovori as MutableList<String>,pitanja[brojPitanja].odgovoreni,pitanja[brojPitanja].tacniOdgovori,this,!zavrsenPokusaj)
        recyclerView.adapter = odgovorAdapter
    }

    fun getResourceId(pVariableName: String?, pResourcename: String?, pPackageName: String?): Int {
        return try {
            resources.getIdentifier(pVariableName, pResourcename,packageName)
        } catch (e: Exception) {
            e.printStackTrace()
            -1
        }
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

    @Override
    override fun onBackPressed() {
        finish();
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