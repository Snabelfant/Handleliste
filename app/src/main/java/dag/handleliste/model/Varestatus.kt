package dag.handleliste.model

enum class Varestatus(val displayName: String, val handlelisteOrder: Int) {
    KJØPT("Kjøpt", 4),
    UTGÅR("Utgår", 5),
    SENERE("Senere", 2),
    NÅRDETPASSER("Når det passer", 3),
    NÅ("Nå", 1),
    HARRYTUR("Harrytur", 6);

    override fun toString() = displayName

    companion object {
        val vareeditorOrder = listOf(NÅ, SENERE, NÅRDETPASSER, HARRYTUR, KJØPT, UTGÅR)
        val varestatusOrder = listOf(KJØPT, SENERE, UTGÅR, NÅRDETPASSER, NÅ, HARRYTUR)
        fun vareeditorIndexOf(status: Varestatus): Int = vareeditorOrder.indexOf(status)
        fun vareeditorStatusOf(index: Long): Varestatus = vareeditorOrder[index.toInt()]
    }
}
