package dag.handleliste.repository

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import dag.handleliste.model.*
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.io.StringReader

class FileStorage(private val dir: File) {
    private val handlelisteFilnavn = "handleliste.json"
    private val produktlisteFilnavn = "produktliste.json"
    private val mapper = ObjectMapper()
    val handleliste: Handleliste
    val produktliste: Produktliste

    init {
        val varerMutable: MutableList<Vare> = mapper.readValue(createReader(handlelisteFilnavn))
        handleliste = Handleliste(varerMutable)

        val produkterMutable: MutableList<Produkt> = mapper.readValue(createReader(produktlisteFilnavn))
        produktliste = Produktliste(produkterMutable)
    }

    fun saveHandleliste() = mapper.writeValue(createWriter(handlelisteFilnavn), handleliste.varer)

    fun saveProduktliste() = mapper.writeValue(createWriter(produktlisteFilnavn), produktliste.produkter)

    fun addVare(vare: Vare) {
        handleliste.add(vare)
        saveHandleliste()
    }

    fun addProdukt(produkt: Produkt) {
        produktliste.add(produkt)
        saveProduktliste()
    }

    fun removeProdukt(index: Long) {
        produktliste.removeAt(index.toInt())
        saveProduktliste()
    }

    fun tøm() = apply { handleliste.tøm() }.also { saveHandleliste() }

    fun hasProdukt(betegnelse: String) = produktliste.has(betegnelse)

    private fun createWriter(filnavn: String) = FileWriter(File(dir, filnavn))

    private fun createReader(filnavn: String) =
        with(File(dir, filnavn)) { if (exists()) FileReader(this) else StringReader("[]") }

    fun updateVare(index: Int, vare: Vare) = handleliste.update(index, vare)

    fun finnProdukt(betegnelse: String) = produktliste.finn(betegnelse)

    fun forslag(prefix: String) = produktliste.forslag(prefix, handleliste.varebetegnelser )

    fun setVarestatus(index: Int, nyVarestatus: Varestatus) {
        handleliste.varer[index].status = nyVarestatus
        saveHandleliste()
    }
}

