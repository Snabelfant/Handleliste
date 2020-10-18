package dag.handleliste.viewmodel

import androidx.lifecycle.ViewModel
import dag.handleliste.repository.Repository

class ProduktlisteViewModel : ViewModel() {
    val produktlisteLive
        get() = Repository.produktlisteLive
}