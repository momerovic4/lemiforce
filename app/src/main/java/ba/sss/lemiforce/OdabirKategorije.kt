package ba.sss.lemiforce

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import ba.sss.lemiforce.R
import ba.sss.lemiforce.data.staticdata.PitanjaRepo
import ba.sss.lemiforce.views.AboutUsDialogFragment

class OdabirKategorije : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.odabir_kategorije)

        val tridot = findViewById<ImageView>(R.id.moreImg)
        tridot.setOnClickListener { showAboutUs(it) }
        var slike = mutableListOf<View>()
        var textovi  = mutableListOf<View>()

        var imgKatA: View = findViewById(R.id.imgKatA)
        var imgKatB: View = findViewById(R.id.imgKatB)
        var imgKatC: View = findViewById(R.id.imgKatC)
        var imgKatD: View = findViewById(R.id.imgKatD)
        var imgKatT: View = findViewById(R.id.imgKatT)
        var imgPrva: View = findViewById(R.id.imgPrva)

        slike.addAll(listOf(imgKatA,imgKatB,imgKatC,imgKatD,imgKatT,imgPrva))

        var txtKatA: View = findViewById(R.id.txtKatA)
        var txtKatB: View = findViewById(R.id.txtKatB)
        var txtKatC: View = findViewById(R.id.txtKatC)
        var txtKatD: View = findViewById(R.id.txtKatD)
        var txtKatT: View = findViewById(R.id.txtKatT)
        var txtPrva: View = findViewById(R.id.txtPrva)

        textovi.addAll(listOf(txtKatA,txtKatB,txtKatC,txtKatD,txtKatT,txtPrva))

        for (slika in slike) slika.setOnClickListener { izaberiKategoriju(getNameForView(slika))}
        for (text in textovi) text.setOnClickListener { izaberiKategoriju(getNameForView(text))}
    }

    fun getNameForView(view :View): String {
        return view.getResources().getResourceName(view.getId());
    }

    fun izaberiKategoriju(kategorija: String) {
        var kat = kategorija.takeLast(4)
        var intent = Intent(this, OdabirTest::class.java)
        intent.putExtra("KATEGORIJA",kat)
        var uzorak = kat
        if(uzorak.contains("Prva")) {
            uzorak = "P"
        }else{
            uzorak = kat.takeLast(1).toString()
        }
        intent.putExtra("BRTESTOVA",PitanjaRepo.pitanja.filter { p -> p.kategorija.contains(uzorak) }.chunked(20).size)
        startActivity(intent)
    }

    fun showAboutUs(view: View) {
        val dialog = AboutUsDialogFragment()
        dialog.show(supportFragmentManager, null)
    }
}