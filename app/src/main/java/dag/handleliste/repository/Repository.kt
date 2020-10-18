package dag.handleliste.repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import dag.handleliste.model.*

object Repository {
    val handlelisteLive = MutableLiveData<Handleliste>()
    val produktlisteLive = MutableLiveData<Produktliste>()
    lateinit var fileStorage: FileStorage
    private var isOpened = false

    fun open(context: Context) {
        if (!isOpened) {
            fileStorage = FileStorage(context.getExternalFilesDir(null)!!)
            handlelisteLive.value = fileStorage.handleliste
            produktlisteLive.value = fileStorage.produktliste
            isOpened = true
        }
    }

    val produkter
        get() = fileStorage.produktliste.produkter
    val varer
        get() = fileStorage.handleliste.varer

    fun addVare(vare: Vare) {
        fileStorage.addVare(vare)
        handlelisteLive.value = fileStorage.handleliste
    }

    fun tøm() {
        fileStorage.tøm()
        handlelisteLive.value = fileStorage.handleliste
    }

    fun removeProdukt(index: Long) {
        fileStorage.removeProdukt(index)
        produktlisteLive.value = fileStorage.produktliste
    }

    fun addProdukt(produkt: Produkt) {
        fileStorage.addProdukt(produkt)
        produktlisteLive.value = fileStorage.produktliste
    }

    fun has(produkt: Produkt): Boolean = fileStorage.hasProdukt(produkt.betegnelse)
    fun hasProdukt(betegnelse: String) = fileStorage.hasProdukt(betegnelse)
    fun updateVare(index: Int, vare: Vare) {
        fileStorage.updateVare(index, vare)
        handlelisteLive.value = fileStorage.handleliste
    }

    fun finnProdukt(betegnelse: String) = fileStorage.finnProdukt(betegnelse)
    fun forslag(prefix: String) = fileStorage.forslag(prefix)
    fun setVarestatus(index: Int, nyVarestatus: Varestatus) {
        fileStorage.setVarestatus(index, nyVarestatus)
        handlelisteLive.value = fileStorage.handleliste
    }
}