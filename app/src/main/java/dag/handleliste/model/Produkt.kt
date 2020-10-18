package dag.handleliste.model

class Produkt(_betegnelse: String = "???", val plassering: String = "") {
    val betegnelse = _betegnelse.capitalize()

    override fun equals(other: Any?) = other is Produkt && other.betegnelse.equals(betegnelse, true)
    override fun hashCode() = 31 * betegnelse.hashCode() + plassering.hashCode()

    companion object {
        fun from(vare: Vare) = Produkt(vare.betegnelse, vare.plassering)
        val betegnelseComparator = Comparator { t: Produkt, u: Produkt -> t.betegnelse.compareTo(u.betegnelse, true) }
        val plasseringComparator =
            Comparator { t: Produkt, u: Produkt -> t.plassering.compareTo(u.plassering, true) }
                .thenComparator { t: Produkt, u: Produkt -> t.betegnelse.compareTo(u.betegnelse, true) }
    }
}


