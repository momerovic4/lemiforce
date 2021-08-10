package com.example.lemiforce

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class PrikazRezultata: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.prikaz_rezultata)

        var procenat = intent.getDoubleExtra("OSTVARENIBODOVI", 0.0)
        var brojPitanja = intent.getIntExtra("BROJPITANJA",40)
        var potrebanProcenat = intent.getDoubleExtra("POTREBANPROCENAT", 90.0)

        if (procenat >= potrebanProcenat) findViewById<TextView>(R.id.txtPolozili).text = "Polozili ste"
        findViewById<TextView>(R.id.txtBrPitanja).text = "Broj pitanja: $brojPitanja"
        findViewById<TextView>(R.id.txtPostotak).text = "Vas postotak: " + procenat.toString() + "%"
        findViewById<TextView>(R.id.txtPotrebno).text = "Potrebno za poloziti: " + potrebanProcenat.toString() + "%"
    }
}