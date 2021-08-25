package ba.sss.lemiforce.data.staticdata

import ba.sss.lemiforce.model.Pitanje

class PitanjaRepo {
    companion object {
        var pitanja = mutableListOf<Pitanje>()
    }
}