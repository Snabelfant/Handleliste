package dag.handleliste.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import dag.handleliste.R
import dag.handleliste.model.Varestatus
import dag.handleliste.repository.Repository
import dag.handleliste.view.YesNoCancel.EMPTY
import dag.handleliste.viewmodel.HandlelisteViewModel
import kotlinx.android.synthetic.main.handlelisteactivity.*


class HandlelisteActivity() : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.handlelisteactivity)

        this.supportActionBar?.setLogo(R.drawable.elefant6);
        this.supportActionBar?.setDisplayUseLogoEnabled(true);
        this.supportActionBar?.setDisplayShowHomeEnabled(true);

        Repository.open(this)

        val viewModel: HandlelisteViewModel =
            ViewModelProvider(this).get(HandlelisteViewModel::class.java)

        viewModel.handlelisteLive.observe(this, Observer { handleliste ->
            handlelisteactivity_resyme.text =
                "${handleliste.count} varer, ${
                    handleliste.count(Varestatus.NÅ) + handleliste.count(
                        Varestatus.SENERE
                    )
                } igjen"
            handlelisteactivity_handleliste.adapter = HandlelisteAdapter(this)
        })

        handlelisteactivity_handleliste.onItemLongClickListener =
            AdapterView.OnItemLongClickListener { _, _, _, index ->
                Vareeditor.newInstance(index).show(supportFragmentManager, "")
                true
            }

        handlelisteactivity_handleliste.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, _, index ->
                Varestatuseditor.newInstance(index).show(supportFragmentManager, "")
            }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.handlelisteactivitymenu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.handlelisteactivitymenu_legg_til -> Vareeditor.newInstance()
                .show(supportFragmentManager, "")

            R.id.handlelisteactivitymenu_ny_handleliste -> YesNoCancel.show(
                this,
                "Ny handleliste?",
                { Repository.tøm() },
                EMPTY
            )

            R.id.handlelisteactivitymenu_produktliste -> startActivity(
                Intent(
                    this,
                    ProduktlisteActivity::class.java
                )
            )

            R.id.handlelisteactivitymenu_send_sms -> {
                val smsIntent = Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", "40484743", null))
                    .putExtra(
                        Intent.EXTRA_TEXT,
                        Repository.varer.joinToString(separator = "\n") { it -> "${it.mengde}  ${it.betegnelse}" }
                    )

                startActivity(smsIntent)
            }

            R.id.handlelisteactivitymenu_send_epost -> {
                val emailIntent = Intent(
                    Intent.ACTION_SENDTO, Uri.fromParts("mailto", "loemar@start.no", null)
                ).apply {
                    putExtra(Intent.EXTRA_SUBJECT, "Handleliste")
                    putExtra(
                        Intent.EXTRA_TEXT,
                        Repository.varer.joinToString(separator = "\n") { it -> "${it.mengde}  ${it.betegnelse}" }
                    )
                }
                startActivity(Intent.createChooser(emailIntent, "Send e-post..."))
            }
        }

        return true
    }
}
