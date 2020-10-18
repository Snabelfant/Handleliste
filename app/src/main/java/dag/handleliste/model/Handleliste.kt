package dag.handleliste.model

import dag.handleliste.model.Varestatus.KJØPT
import dag.handleliste.model.Varestatus.UTGÅR

class Handleliste(private val varerMutable: MutableList<Vare>) {
    val varer
        get() = varerMutable.sortedWith(Vare.comparator)

    val count
        get() = varerMutable.size

    fun add(vare: Vare) = varerMutable.add(vare)

    fun count(status: Varestatus) = varerMutable.filter { it.status == status }.size

    fun tøm() = varerMutable.removeIf { setOf(UTGÅR, KJØPT).contains(it.status) }

    fun update(index: Int, vare: Vare) {
        varerMutable[index] = vare
    }
}