package dag.handleliste.model

class Produktliste(private val produkterMutable: MutableList<Produkt>) {
    val produkter
        get() = produkterMutable.sortedWith(Produkt.betegnelseComparator)

    val count
        get() = produkter.size

    fun add(produkt: Produkt) = produkterMutable.add(produkt)

    fun has(betegnelse: String) = produkterMutable.contains(Produkt(betegnelse))

    fun removeAt(index: Int) {
        produkterMutable.removeAt(index)
    }

    fun finn(betegnelse: String) = produkter.firstOrNull { it.betegnelse == betegnelse }
    fun forslag(prefix: String, varerÅUtelukke: List<String>): List<String> {
        val startsWith = produkterMutable
            .filter { it.betegnelse.startsWith(prefix, true) }
            .map { it.betegnelse }
            .sorted()

        val contains = produkterMutable
            .filter { !it.betegnelse.startsWith(prefix, true) && it.betegnelse.contains(prefix, true) }
            .map { it.betegnelse }
            .sorted()

        return (startsWith + contains).filter { forslag -> varerÅUtelukke.none { utelukke -> utelukke.equals(forslag, true)  } }
    }

}