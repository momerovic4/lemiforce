package com.example.lemiforce

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView

class OdabirTest : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.odabir_testa)

        var kategorija = intent.getStringExtra("KATEGORIJA")
        println("odabrano je: $kategorija")
        var txtKategorije: View = findViewById(R.id.txtPolozili)
        var imgKategorije: View = findViewById(R.id.imgKategorija)

        when (kategorija) {
            "KatA" -> {
                (txtKategorije as TextView).text = "Kategorija A"
                (imgKategorije as ImageView).setImageResource(R.drawable.kategorija_a)
            }
            "KatB" -> {
                (txtKategorije as TextView).text = "Kategorija B"
                (imgKategorije as ImageView).setImageResource(R.drawable.kategorija_b)
            }
            "KatC" -> {
                (txtKategorije as TextView).text = "Kategorija C"
                (imgKategorije as ImageView).setImageResource(R.drawable.kategorija_c)
            }
            "KatD" -> {
                (txtKategorije as TextView).text = "Kategorija D"
                (imgKategorije as ImageView).setImageResource(R.drawable.kategorija_d)
            }
            "KatT" -> {
                (txtKategorije as TextView).text = "Kategorija T"
                (imgKategorije as ImageView).setImageResource(R.drawable.kategorija_t)
            }
            "Prva" -> {
                (txtKategorije as TextView).text = "Prva pomoc"
                (imgKategorije as ImageView).setImageResource(R.drawable.prva_pomoc)
            }
        }

        var ucenjeBtn: View = findViewById(R.id.btnNoviTest)
        var simulacijaBtn: View = findViewById(R.id.btnNazad)

        ucenjeBtn.setOnClickListener {
            openTest()
        }

        simulacijaBtn.setOnClickListener {
            openSimulation()
        }
    }

    fun openTest() {
        var intent = Intent(this,UcenjeTest::class.java)
        startActivity(intent)
    }

    fun openSimulation() {
        var intent = Intent(this,SimulacijaTest::class.java)
        startActivity(intent)
    }
}