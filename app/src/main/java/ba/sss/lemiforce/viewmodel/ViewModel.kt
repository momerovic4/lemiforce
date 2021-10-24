package ba.sss.lemiforce.viewmodel

import ba.sss.lemiforce.data.staticdata.PitanjaRepo
import ba.sss.lemiforce.model.Pitanje

class ViewModel {
    fun getPitanjaZaKategoriju(kategorija: String?, index : Int) : List<Pitanje> {
        return if (index == 0)
            PitanjaRepo.pitanja.filter { p -> p.kategorija.contains(kategorija!!) }.shuffled().take(20)
        else
            PitanjaRepo.pitanja.filter { p -> p.kategorija.contains(kategorija!!) }.chunked(20)[index-1]
    }

    fun getPitanjaZnakove(index: Int): List<Pitanje> {
        var chunkan = PitanjaRepo.pitanja.filter { p -> p.kategorija.contains("Z") }.chunked(10)
        return if (index == 0)
            PitanjaRepo.pitanja.filter { p -> p.kategorija.contains("Z") }.shuffled().take(10)
        else {
            return if (index > chunkan.size)
                chunkan[(index-1)%10]
            else
                chunkan[index-1]
        }
    }

    fun getPrvaPomoc(index: Int): List<Pitanje> {
        return if (index == 0)
            PitanjaRepo.pitanja.filter { p -> p.kategorija.contains("P") }.shuffled().take(20)
        else
            PitanjaRepo.pitanja.filter { p -> p.kategorija.contains("P") }.chunked(20)[index-1]
    }

    fun getPitanjaRaskrsnice(index: Int): List<Pitanje> {
        var chunkan = PitanjaRepo.pitanja.filter { p -> p.kategorija.contains("R") }.chunked(10)
        return if (index == 0)
            PitanjaRepo.pitanja.filter { p -> p.kategorija.contains("R") }.shuffled().take(10)
        else {
            return if (index > chunkan.size)
                chunkan[(index-1)%10]
            else
                chunkan[index-1]
        }
    }

    fun refreshPitanja() {
        for (pitanje in PitanjaRepo.pitanja) {
            pitanje.odgovoreni = null
        }
    }
}