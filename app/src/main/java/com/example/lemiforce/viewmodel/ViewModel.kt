package com.example.lemiforce.viewmodel

import com.example.lemiforce.data.staticdata.PitanjaRepo
import com.example.lemiforce.model.Pitanje

class ViewModel {
    fun getPitanja() : List<Pitanje> {
        return PitanjaRepo.pitanja
    }
}