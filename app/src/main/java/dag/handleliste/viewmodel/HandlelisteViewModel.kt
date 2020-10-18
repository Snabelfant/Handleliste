package dag.handleliste.viewmodel

import androidx.lifecycle.ViewModel
import dag.handleliste.repository.Repository

class HandlelisteViewModel() : ViewModel() {
    val handlelisteLive
        get() = Repository.handlelisteLive

}