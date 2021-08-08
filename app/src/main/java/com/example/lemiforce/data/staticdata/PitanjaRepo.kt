package com.example.lemiforce.data.staticdata

import com.example.lemiforce.model.Pitanje

class PitanjaRepo {
    companion object {
        fun getPitanja(): List<Pitanje>{
            return listOf(
                Pitanje("Pitanje broj jedan?", listOf("odgovor a","odgovor b", "odgovor c"), listOf(0,2)),
                Pitanje("Pitanje broj dva?", listOf("haza","mook", "penc"), listOf(1)),
                Pitanje("Pitanje broj tri?", listOf("odgovor d","odgovor e", "odgovor f"), listOf(0)),
                Pitanje("Pitanje broj cetiri?", listOf("a","b", "c"), listOf(0,2))
            )
        }
    }
}