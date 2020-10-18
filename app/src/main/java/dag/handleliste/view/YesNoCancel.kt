package dag.handleliste.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

typealias Action = (Unit) -> Unit

object YesNoCancel {
    val EMPTY: Action = {}

    fun show(context: Context, message: String, yes: Action?, no: Action?, cancel: Action? = null) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder
            .setMessage(message)
            .setIcon(android.R.drawable.ic_dialog_alert);

        if (yes != null) {
            builder.setPositiveButton("Ja") { dialog: DialogInterface, _: Int ->
                yes(Unit)
                dialog.dismiss();
            }
        }

        if (no != null) {
            builder.setNegativeButton("Nei") { dialog: DialogInterface, _: Int ->
                no(Unit)
                dialog.dismiss();
            }
        }

        if (cancel != null) {
            builder.setNegativeButton("Avbryt") { dialog: DialogInterface, _: Int ->
                cancel(Unit)
                dialog.dismiss();
            }
        }

        builder.show();
    }

}
