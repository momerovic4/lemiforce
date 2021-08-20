package com.example.lemiforce.viewmodel

import com.example.lemiforce.data.staticdata.PitanjaRepo
import com.example.lemiforce.model.Pitanje

class ViewModel {
    fun getPitanjaZaKategoriju(kategorija: String?) : List<Pitanje> {
        return PitanjaRepo.pitanja.filter { p -> p.kategorija.contains(kategorija!!) }.shuffled().take(20)
    }

    fun getPitanjaZnakove(broj: Int): List<Pitanje> {
        return PitanjaRepo.pitanja.filter { p -> p.kategorija.contains("Z") }.shuffled().take(broj)
    }

    fun getPrvaPomoc(): List<Pitanje> {
        return PitanjaRepo.pitanja.filter { p -> p.kategorija.contains("P") }.shuffled().take(10)
    }

    fun getPitanjaRaskrsnice(broj: Int): List<Pitanje> {
        return PitanjaRepo.pitanja.filter { p -> p.kategorija.contains("R") }.shuffled().take(broj)
    }

    fun refreshPitanja() {
        for (pitanje in PitanjaRepo.pitanja) {
            pitanje.odgovoreni = null
        }
    }
}