package com.example.lemiforce

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.lemiforce.data.staticdata.PitanjaRepo
import com.example.lemiforce.model.Pitanje
import org.json.JSONArray
import org.json.JSONObject
import java.io.File

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var pitanja = mutableListOf<Pitanje>()

        val jsonString = this.assets.open("xmltojson.json").bufferedReader().use { it.readText() }
        var json = JSONObject(jsonString)
        var listAnot = json.getJSONArray("annotation")
        for (i in 0..listAnot.length()-1){
            var listobject = listAnot.getJSONObject(i).getJSONArray("object")
            var textPitanja = ""
            var odgovori = mutableListOf<String>()
            var tacni = mutableListOf<Int>(1)
            var kategorija = ""
            var odgtxt = ""
            for (j in 0..listobject.length()-1){
                var name = listobject.getJSONObject(j).getString("name")
                when (name) {
                    "kategorije" -> {kategorija = listobject.getJSONObject(j).getString("ocr_text")}
                    "tekst_pitanja" -> {textPitanja = listobject.getJSONObject(j).getString("ocr_text")}
                    "odgovori" -> {odgtxt = listobject.getJSONObject(j).getString("ocr_text")}
                }
            }

            textPitanja.trim().replace("\n"," ")
            odgtxt.replace("\n"," ").trim()
            odgovori = odgtxt.split(";") as MutableList<String>

            var noviOdg = mutableListOf<String>()

            for(odg in odgovori){
                if(!odg.trim().equals("")){
                    var novi = odg.replace("\n"," ").trim()
                    println("Odgovor je:'"+novi+"'")
                    noviOdg.add(novi)
                }
            }
            pitanja.add(Pitanje(textPitanja,noviOdg,tacni,kategorija = kategorija))
        }

        PitanjaRepo.pitanja = pitanja

        for (i in 0..10) print(pitanja[i].ponudjeniOdgovori)
    }


    fun odaberiKategoriju(view: View){
        var intent = Intent(this,OdabirKategorije::class.java)
        startActivity(intent)
    }
}