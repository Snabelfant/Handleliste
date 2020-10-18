package dag.handleliste.view

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import dag.handleliste.R
import dag.handleliste.model.Varestatus
import dag.handleliste.repository.Repository

class Varestatuseditor : DialogFragment() {
    companion object {
        fun newInstance(repositoryIndex: Long): Varestatuseditor {
            val varestatuseditor = Varestatuseditor()
            val args = Bundle()
            args.putInt("repositoryIndex", repositoryIndex.toInt())
            varestatuseditor.arguments = args
            return varestatuseditor
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)
        val index = arguments?.getInt("repositoryIndex")!!
        val vare = Repository.varer[index]
        val statusvalg = Varestatus.varestatusOrder.filter { it != vare.status }
        val builder = AlertDialog.Builder(activity)
        builder.setIcon(R.drawable.elefant5)
        builder.setTitle(vare.betegnelse);

        val adapter = ArrayAdapter<Varestatus>(activity!!, R.layout.spinnertext);
        adapter.addAll(statusvalg)

        builder.setNegativeButton("Avbryt") { dialog: DialogInterface, _ -> dialog.dismiss(); }

        builder.setAdapter(adapter) { _, which: Int ->
            val nyVarestatus = statusvalg[which]
            Repository.setVarestatus(index, nyVarestatus)
        }
        return builder.create()
    }
}

