package com.example.lemiforce.data.staticdata

import com.example.lemiforce.model.Pitanje

class PitanjaRepo {
    companion object {
        fun getPitanja(): List<Pitanje>{
            return listOf(
                Pitanje("Pomoću užeta ne smije da se vuče: (više tačnih odgovora)",
                    listOf("motorno vozilo na kojem su neispravni uređaji za osvjetljenje",
                        "motorno vozilo na kojem su neispravni uređaji za davanje znakova",
                        "motorno vozilo na kojem su neispravni uređaji za zaustavljanje",
                        "motorno vozilo na kojem su neispravni uređaji za upravljanje",
                        "teretno motorno vozilo i autobus"), listOf(2,3,4)),
                Pitanje("Ljekarsko uvjerenje o zdravstvenoj sposobnosti za upravljanje motornim vozilom\n" +
                        "važi?", listOf("12 mjeseci od dana izdavanja","6 mjeseci od dana izdavanja"), listOf(0)),
                Pitanje("Krutom vezom ne smije da se vuče motorno vozilo:", listOf("koje ima ispravan uređaj za upravljanje","teže od vučnog vozila-ako mu je neispravna pomoćna kočnica"), listOf(1)),
                Pitanje("Zaustavljanje vozila je:", listOf("svaki prekid kretanja vozila na putu u trajanju do 15 minuta, osim prekida koji se pravi da bi\n" +
                        "se postupilo po znaku ili pravilu kojim se reguliše saobraćaj","svaki prekid kretanja vozila na putu u trajanju do 5 minuta, osim prekida koji se pravi da bi\n" +
                        "se postupilo po znaku ili pravilu kojim se reguliše saobraćaj"), listOf(1))
            )
        }
    }
}