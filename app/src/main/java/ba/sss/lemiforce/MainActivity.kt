package ba.sss.lemiforce

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import ba.sss.lemiforce.R
import ba.sss.lemiforce.data.staticdata.PitanjaRepo
import ba.sss.lemiforce.model.Pitanje
import org.json.JSONArray

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadPitanja()
    }

    private fun parseOdgovore(odgovori: String) : List<String> {
        odgovori.replace("\n"," ")
        var parsed = odgovori.split(';').filter { s -> s.isNotBlank() && s.isNotEmpty() }
        for(i in 0 until parsed.size-1) parsed[i].trim()
        return parsed
    }

    private fun loadPitanja(){
        var pitanja = mutableListOf<Pitanje>()
        var tacniOdg = this.assets.open("pitanja_odg.txt").bufferedReader().use { it.readText() }.split('\n')
        var jsonString = this.assets.open("pitanja_obj.json").bufferedReader().use { it.readText() }
        var jsonObjekti = JSONArray(jsonString)
        for (i in 0 until jsonObjekti.length()){
            var listobject = jsonObjekti.getJSONArray(i)
            var textPitanja = ""
            var odgovori = listOf<String>()
            var splitani = tacniOdg[i].split(',').map { it.trim().toInt() }
            var tacni = mutableListOf<Int>()
            splitani.forEach { broj -> tacni.add(broj-1) }
            var kategorija = ""
            for (j in 0 until listobject.length()){
                var name = listobject.getJSONObject(j).getString("name")
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
        //Pitanja znakovi
        tacniOdg = this.assets.open("znakovi_odg.txt").bufferedReader().use { it.readText() }.split('\n')
        jsonString = this.assets.open("znakovi_obj.json").bufferedReader().use { it.readText() }
        jsonObjekti = JSONArray(jsonString)
        for (i in 0 until jsonObjekti.length()){
            var listobject = jsonObjekti.getJSONArray(i)
            var textPitanja = ""
            var odgovori = listOf<String>()
            var splitani = listOf<Int>()
            splitani = tacniOdg[i].split(',').map { it.trim().toInt() }
            var tacni = mutableListOf<Int>()
            splitani.forEach { broj -> tacni.add(broj-1) }
            for (j in 0 until listobject.length()){
                var name = listobject.getJSONObject(j).getString("name")
                when (name) {
                    "tekst_pitanja" -> {textPitanja = listobject.getJSONObject(j).getString("ocr_text")}
                    "odgovori" -> {
                        odgovori = parseOdgovore(listobject.getJSONObject(j).getString("ocr_text"))
                    }
                }
            }
            pitanja.add(Pitanje(textPitanja,odgovori,tacni,kategorija = "Z",slika = ("z"+(i+1))))
        }
        //Pitanja Raskrsnice
        tacniOdg = this.assets.open("raskrsnice_odgovori.txt").bufferedReader().use { it.readText() }.split('\n')
        jsonString = this.assets.open("raskrsnice_obj.json").bufferedReader().use { it.readText() }
        jsonObjekti = JSONArray(jsonString)
        for (i in 0 until jsonObjekti.length()){
            var listobject = jsonObjekti.getJSONArray(i)
            var textPitanja = ""
            var odgovori = listOf<String>()
            var splitani = listOf<Int>()
            splitani = tacniOdg[i].split(',').map { it.trim().toInt() }
            var tacni = mutableListOf<Int>()
            splitani.forEach { broj -> tacni.add(broj-1) }
            for (j in 0 until listobject.length()){
                var name = listobject.getJSONObject(j).getString("name")
                when (name) {
                    "tekst_pitanja" -> {textPitanja = listobject.getJSONObject(j).getString("ocr_text")}
                    "odgovori" -> {
                        odgovori = parseOdgovore(listobject.getJSONObject(j).getString("ocr_text"))
                    }
                }
            }
            pitanja.add(Pitanje(textPitanja,odgovori,tacni,kategorija = "R",slika = ("r"+(i+1))))
        }
        //Pitanja Prva pomoc
        tacniOdg = this.assets.open("prvapom_odg.txt").bufferedReader().use { it.readText() }.split('\n')
        jsonString = this.assets.open("prvapomoc_obj.json").bufferedReader().use { it.readText() }
        jsonObjekti = JSONArray(jsonString)
        for (i in 0 until jsonObjekti.length()){
            var listobject = jsonObjekti.getJSONArray(i)
            var textPitanja = ""
            var odgovori = listOf<String>()
            var splitani = listOf<Int>()
            splitani = tacniOdg[i].split(',').map { it.trim().toInt() }
            var tacni = mutableListOf<Int>()
            splitani.forEach { broj -> tacni.add(broj-1) }
            for (j in 0 until listobject.length()){
                var name = listobject.getJSONObject(j).getString("name")
                when (name) {
                    "tekst_pitanja" -> {textPitanja = listobject.getJSONObject(j).getString("ocr_text")}
                    "odgovori" -> {
                        odgovori = parseOdgovore(listobject.getJSONObject(j).getString("ocr_text"))
                    }
                }
            }
            pitanja.add(Pitanje(textPitanja,odgovori,tacni,kategorija = "P"))
        }
        PitanjaRepo.pitanja = pitanja
    }

    fun odaberiKategoriju(view: View){
        var intent = Intent(this, OdabirKategorije::class.java)
        startActivity(intent)
    }
}