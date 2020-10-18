package dag.handleliste.view

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.*
import androidx.fragment.app.DialogFragment
import dag.handleliste.R
import dag.handleliste.model.*
import dag.handleliste.repository.Repository

class Vareeditor : DialogFragment() {
    companion object {
        fun newInstance(repositoryIndex: Long): Vareeditor {
            val vareeditor = Vareeditor()
            val args = Bundle()
            args.putInt("repositoryIndex", repositoryIndex.toInt())
            Log.i("til vareeditor", "$repositoryIndex")
            vareeditor.arguments = args
            return vareeditor
        }

        fun newInstance() = Vareeditor()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)
        val inflater = activity!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val vareeditorView = inflater.inflate(dag.handleliste.R.layout.vareeditor, null)

        val builder = AlertDialog.Builder(activity)
        builder.setView(vareeditorView)

        val mengde = vareeditorView.findViewById(R.id.vareeditor_mengde) as TextView
        val plasseringAdapter =
            ArrayAdapter<Plassering>(this.activity!!, dag.handleliste.R.layout.spinnertext, Plasseringer.plasseringer)
        val plassering = vareeditorView.findViewById(R.id.vareeditor_plassering) as Spinner
        plassering.adapter = plasseringAdapter
        val betegnelse = vareeditorView.findViewById(R.id.vareeditor_betegnelse) as AutoCompleteTextView
        val varestatusAdapter =
            ArrayAdapter(this.activity!!, dag.handleliste.R.layout.spinnertext, Varestatus.vareeditorOrder)
        val status = vareeditorView.findViewById(R.id.vareeditor_status) as Spinner
        status.adapter = varestatusAdapter

        val index = arguments?.getInt("repositoryIndex")
        if (index != null) {
            val vare = Repository.varer[index]
            mengde.text = vare.mengde
            plassering.setSelection(Plasseringer.index(vare.plassering))
            betegnelse.setText(vare.betegnelse)
            status.setSelection(Varestatus.vareeditorIndexOf(vare.status))
        } else {
            status.setSelection(Varestatus.vareeditorIndexOf(Varestatus.NÅ))
        }

        betegnelse.onItemClickListener = AdapterView.OnItemClickListener { _, _, _, _ ->
            plassering.setSelection(Plasseringer.index(Repository.finnProdukt(betegnelse.text.toString())?.plassering))
        }

        val forslagslisteAdapter = ForslagslisteAdapter(this.context)
        betegnelse.threshold = 1
        betegnelse.setAdapter(forslagslisteAdapter)

        builder.setPositiveButton("OK", DialogInterface.OnClickListener { _, _ ->
            val vare = Vare(
                betegnelse.text.toString(),
                mengde.text.toString(),
                (plassering.selectedItem as Plassering).plassering,
                Varestatus.vareeditorStatusOf(status.selectedItemId)
            )

            try {
                vare.sjekk()
            } catch (uve: UgyldigVareException) {
                YesNoCancel.show(this.activity!!, "Ugyldig vare: ${uve.message}", YesNoCancel.EMPTY, null)
                return@OnClickListener
            }

            if (index != null) {
                Repository.updateVare(index, vare)
            } else {
                Repository.addVare(vare)
            }

            if (!Repository.hasProdukt(vare.betegnelse)) {
                YesNoCancel.show(
                    this.activity!!,
                    "Legge til ny vare ${vare.betegnelse}" + if (vare.plassering == "") "?" else "/${vare.plassering}?",
                    { Repository.addProdukt(Produkt.from(vare)) },
                    YesNoCancel.EMPTY,
                    null
                )
            }
        }).setNegativeButton("Avbryt")
        { _, _ -> }

        return builder.create()
    }
}
