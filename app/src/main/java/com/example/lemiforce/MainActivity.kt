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
            var odgovori = listOf<String>()
            var tacni = mutableListOf<Int>(1)
            var kategorija = ""
            for (i in 0..listobject.length()-1){
                var name = listobject.getJSONObject(i).getString("name")
                when (name) {
                    "kategorije" -> {kategorija = listobject.getJSONObject(i).getString("ocr_text")}
                    "tekst_pitanja" -> {textPitanja = listobject.getJSONObject(i).getString("ocr_text")}
                    "odgovori" -> {odgovori = listobject.getJSONObject(i).getString("ocr_text").split(";")
                    }
                }
            }
            pitanja.add(Pitanje(textPitanja,odgovori,tacni,kategorija = kategorija))
        }

        PitanjaRepo.pitanja = pitanja
    }


    fun odaberiKategoriju(view: View){
        var intent = Intent(this,OdabirKategorije::class.java)
        startActivity(intent)
    }
}