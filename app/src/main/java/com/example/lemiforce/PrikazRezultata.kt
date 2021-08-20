package com.example.lemiforce

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class PrikazRezultata: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.prikaz_rezultata)

        var procenat = intent.getDoubleExtra("OSTVARENIBODOVI", 0.0)
        var brojPitanja = intent.getIntExtra("BROJPITANJA",40)
        var potrebanProcenat = intent.getDoubleExtra("POTREBANPROCENAT", 90.0)

        if (procenat >= potrebanProcenat) findViewById<TextView>(R.id.txtPolozili).text = "Položili ste"
        findViewById<TextView>(R.id.txtBrPitanja).text = "Broj pitanja: $brojPitanja"
        findViewById<TextView>(R.id.txtPostotak).text = "Vaš postotak: " + (Math.round(procenat * 100.0) / 100.0).toString() + "%"
        findViewById<TextView>(R.id.txtPotrebno).text = "Potrebno za položiti: " + potrebanProcenat.toString() + "%"
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