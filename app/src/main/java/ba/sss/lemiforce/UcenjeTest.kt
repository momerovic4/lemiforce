package ba.sss.lemiforce

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.core.view.get
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ba.sss.lemiforce.R
import ba.sss.lemiforce.adapter.PitanjeAdapter
import ba.sss.lemiforce.model.Pitanje
import ba.sss.lemiforce.viewmodel.ViewModel

class UcenjeTest : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var odgovorAdapter : PitanjeAdapter
    private lateinit var btnSimulacija : Button
    private val viewmodel = ViewModel()
    private var brojPitanja: Int = 0
    private lateinit var pitanja: MutableList<Pitanje>
    private lateinit var txtPitanje: TextView
    private lateinit var txtBrojPitanja: TextView
    private lateinit var imgSlika: ImageView

    override fun onBackPressed() {
        super.onBackPressed()
        viewmodel.refreshPitanja()
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ucenje_test)

        txtPitanje = findViewById(R.id.txtPolozili)
        txtBrojPitanja = findViewById(R.id.txtBrojPitanja)
        btnSimulacija = findViewById(R.id.btnNazad)
        recyclerView = findViewById(R.id.rwOdgovori)
        imgSlika = findViewById(R.id.imgSlika)
        if(intent.getStringExtra("KATEGORIJA").equals("P")){
            pitanja = viewmodel.getPrvaPomoc(intent.getIntExtra("TEST",0)) as MutableList<Pitanje>
        }else{
            pitanja = viewmodel.getPitanjaZaKategoriju(intent.getStringExtra("KATEGORIJA"),intent.getIntExtra("TEST",0)) as MutableList<Pitanje>
            pitanja.addAll(viewmodel.getPitanjaZnakove(intent.getIntExtra("TEST",0)))
            pitanja.addAll(viewmodel.getPitanjaRaskrsnice(intent.getIntExtra("TEST",0)))
        }
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
            recyclerView.get(tacno).background = Drawable.createFromXml(resources, resources.getXml(
                R.drawable.green
            ))
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

        if(pitanja.get(brojPitanja).kategorija.equals("R") || pitanja.get(brojPitanja).kategorija.equals("Z")){
            imgSlika.isEnabled = true;
            imgSlika.visibility = View.VISIBLE
            val PACKAGE_NAME = applicationContext.packageName
            val imgId = resources.getIdentifier("$PACKAGE_NAME:drawable/${pitanja.get(brojPitanja).slika}", null, null)
            imgSlika.setImageBitmap(BitmapFactory.decodeResource(resources, imgId))
        }else{
            imgSlika.isEnabled = false;
            imgSlika.visibility = View.INVISIBLE
        }

        var manager = GridLayoutManager(this,1)
        manager.reverseLayout = true
        recyclerView.layoutManager = manager

        odgovorAdapter = PitanjeAdapter(odgovori as MutableList<String>,pitanja.get(brojPitanja).odgovoreni,pitanja[brojPitanja].tacniOdgovori,this)
        recyclerView.adapter = odgovorAdapter
        manager.scrollToPosition(odgovori.size-1)
    }

    fun iducePitanje(view: View) {
        if(pitanja[brojPitanja].odgovoreni == null){
            Toast.makeText(applicationContext,"Prvo provjerite odgovor",Toast.LENGTH_SHORT).show()
        }else{
            if(brojPitanja != pitanja.size-1){
                if (brojPitanja == pitanja.size-2) findViewById<Button>(R.id.btnIduce).text = "Zavrsi pokusaj"
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

    fun prosloPitanje(view: View) {
        if (brojPitanja != 0) {
            findViewById<Button>(R.id.btnProslo).isEnabled = brojPitanja != 1
            findViewById<Button>(R.id.btnIduce).text = "iduce"
            brojPitanja--
            setUpPitanje()
            disableClickableAndEnabled()
        }
    }
}