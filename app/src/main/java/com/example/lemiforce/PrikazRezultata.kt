package com.example.lemiforce

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class PrikazRezultata: AppCompatActivity() {
    private lateinit var btnPregled : Button
    private lateinit var btnNoviTest : Button
    private lateinit var btnNazad : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.prikaz_rezultata)
        btnPregled = findViewById(R.id.btnPregled)
        btnNoviTest = findViewById(R.id.btnNoviTest)
        btnNazad = findViewById(R.id.btnNazad)

        var procenat = intent.getDoubleExtra("OSTVARENIBODOVI", 0.0)
        var brojPitanja = intent.getIntExtra("BROJPITANJA",40)
        var potrebanProcenat = intent.getDoubleExtra("POTREBANPROCENAT", 90.0)

        if (procenat >= potrebanProcenat) findViewById<TextView>(R.id.txtPolozili).text = "Polozili ste"
        findViewById<TextView>(R.id.txtBrPitanja).text = "Broj pitanja: $brojPitanja"
        findViewById<TextView>(R.id.txtPostotak).text = "Vas postotak: " + procenat.toString() + "%"
        findViewById<TextView>(R.id.txtPotrebno).text = "Potrebno za poloziti: " + potrebanProcenat.toString() + "%"
    }

    fun vratiNaPregled(view: View) {
        intent.putExtra("PRVOPITANJE", 0)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    fun zapocniNovi(view: View) {
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    fun vratiNaIzbor(view: View) {
        setResult(Activity.RESULT_CANCELED, intent)
        finish()
    }
}