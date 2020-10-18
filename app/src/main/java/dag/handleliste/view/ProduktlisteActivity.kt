package dag.handleliste.view;

import android.os.Bundle
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import dag.handleliste.R
import dag.handleliste.repository.Repository
import dag.handleliste.view.YesNoCancel.EMPTY
import dag.handleliste.viewmodel.ProduktlisteViewModel
import kotlinx.android.synthetic.main.produktlisteactivity.*

class ProduktlisteActivity() : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.produktlisteactivity)

        val viewModel: ProduktlisteViewModel =
            ViewModelProvider(this).get(ProduktlisteViewModel::class.java)

        viewModel.produktlisteLive.observe(this, Observer { produktliste ->
            produktlisteactivity_resyme.text = "Antall varer: ${produktliste.count}"
            produktlisteactivity_produktliste.adapter = ProduktlisteAdapter(this)
        })

        produktlisteactivity_produktliste.onItemLongClickListener =
            AdapterView.OnItemLongClickListener { _, _, _, index ->
                val produkt = Repository.produkter[index.toInt()]
                YesNoCancel.show(
                    this,
                    "Slette ${produkt.betegnelse}?",
                    { Repository.removeProdukt(index) },
                    EMPTY
                )

                true
            }

    }
}
