package com.example.lemiforce.viewmodel

import com.example.lemiforce.data.staticdata.PitanjaRepo
import com.example.lemiforce.model.Pitanje

class ViewModel {
    fun getPitanjaZaKategoriju(kategorija: String?) : List<Pitanje> {
        return PitanjaRepo.pitanja.filter { p -> p.kategorija.contains(kategorija!!) }.shuffled().take(20)
    }
}