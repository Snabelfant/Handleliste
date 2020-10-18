package dag.handleliste.model

import java.lang.Integer.max

class Plassering(val beskrivelse: String, val plassering: String) {
    override fun toString() = "$beskrivelse ($plassering)"
}

object Plasseringer {
    fun index(p: String?) = max(0, plasseringer.indexOfFirst { it.plassering == p })

    val plasseringer = listOf(
        Plassering("Frukt & grønt", "F&g"),
        Plassering("Bakeri", "Bkr"),
        Plassering("Bakevarer", "Bvr"),
        Plassering("Dessert", "Dsr"),
        Plassering("Drikke", "Drk"),
        Plassering("Dyr", "Dyr"),
        Plassering("Egg", "Egg"),
        Plassering("Etnisk", "Etn"),
        Plassering("Fisk", "Fsk"),
        Plassering("Frokost", "Frk"),
        Plassering("Fryst", "Fry"),
        Plassering("Godteri & snaks", "G&s"),
        Plassering("Hus & hjem", "H&h"),
        Plassering("Kjeks", "Kks"),
        Plassering("Kjøtt", "Kjt"),
        Plassering("Krydder", "Krd"),
        Plassering("Meieri", "Mri"),
        Plassering("Middagstilbehør", "Mtb"),
        Plassering("Ost", "Ost"),
        Plassering("Pålegg", "Plg"),
        Plassering("Personlig", "Prs"),
        Plassering("Syltetøy", "Sty"),
        Plassering("Annet", "A")
    )
}
