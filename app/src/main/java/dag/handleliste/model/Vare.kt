package dag.handleliste.model

import com.fasterxml.jackson.annotation.JsonProperty

class Vare(
    @JsonProperty("betegnelse")
    _betegnelse: String,
    @JsonProperty("mengde")
    var mengde: String = "",
    @JsonProperty("plassering")
    val plassering: String = "",
    @JsonProperty("status")
    var status: Varestatus = Varestatus.NÅ
) {
    fun sjekk() {
        if (betegnelse.length < 2) throw UgyldigVareException("Varebetegnelse mangler")
    }

    constructor(betegnelse: CharSequence, mengde: CharSequence, plassering: CharSequence, status: Varestatus)
            : this(betegnelse.toString(), mengde.toString(), plassering.toString(), status)

    val betegnelse = _betegnelse.capitalize()

    companion object {
        val comparator =
            Comparator { t: Vare, u: Vare -> t.status.handlelisteOrder.compareTo(u.status.handlelisteOrder) }
                .thenComparator { t: Vare, u: Vare -> t.plassering.compareTo(u.plassering, true) }
                .thenComparator { t: Vare, u: Vare -> t.betegnelse.compareTo(u.betegnelse, true) }
    }
}