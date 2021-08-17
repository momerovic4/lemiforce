package com.example.lemiforce

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.lemiforce.data.staticdata.PitanjaRepo
import com.example.lemiforce.model.Pitanje
import org.json.JSONArray

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var pitanja = mutableListOf<Pitanje>()
        val tacniOdg = this.assets.open("demo.txt").bufferedReader().use { it.readText() }.split('\n')
        val jsonString = this.assets.open("pitanja.json").bufferedReader().use { it.readText() }
        val jsonObjekti = JSONArray(jsonString)
        for (i in 0 until jsonObjekti.length()){
            val listobject = jsonObjekti.getJSONArray(i)
            var textPitanja = ""
            var odgovori = listOf<String>()
            var splitani = listOf<Int>()
            splitani = tacniOdg[i].split(',').map { it.trim().toInt() }
            val tacni = mutableListOf<Int>()
            splitani.forEach { broj -> tacni.add(broj-1) }
            var kategorija = ""
            for (j in 0 until listobject.length()){
                val name = listobject.getJSONObject(j).getString("name")
                when (name) {
                    "kategorije" -> {kategorija = listobject.getJSONObject(j).getString("ocr_text")}
                    "tekst_pitanja" -> {textPitanja = listobject.getJSONObject(j).getString("ocr_text")}
                    "odgovori" -> {
                        odgovori = parseOdgovore(listobject.getJSONObject(j).getString("ocr_text"))
                    }
                }
            }
            pitanja.add(Pitanje(textPitanja,odgovori,tacni,kategorija = kategorija))
        }

        PitanjaRepo.pitanja = pitanja
    }

    private fun parseOdgovore(odgovori: String) : List<String> {
        var parsed = odgovori.split(';','\n').filter { s -> s.isNotBlank() && s.isNotEmpty() }
        return parsed
    }

    fun odaberiKategoriju(view: View){
        var intent = Intent(this,OdabirKategorije::class.java)
        startActivity(intent)
    }
}