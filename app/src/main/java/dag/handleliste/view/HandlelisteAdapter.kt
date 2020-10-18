package dag.handleliste.view

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import dag.handleliste.R
import dag.handleliste.model.Vare
import dag.handleliste.model.Varestatus
import dag.handleliste.repository.Repository

class HandlelisteAdapter(context: Context) :
    ArrayAdapter<Vare>(context, R.layout.handlelisterad, Repository.varer) {

    companion object {
        private val colorMap: Map<Varestatus, Int> = mapOf(
            Varestatus.NÅ to Color.parseColor("#FFFFCC"),
            Varestatus.SENERE to Color.parseColor("#FFFF99"),
            Varestatus.NÅRDETPASSER to Color.parseColor("#FFCC99"),
            Varestatus.HARRYTUR to Color.parseColor("#C0C0C0"),
            Varestatus.KJØPT to Color.parseColor("#CCFF99"),
            Varestatus.UTGÅR to Color.parseColor("#FF9999")
        )
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val vare = Repository.varer[position]
        val inflater = super.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val rowView = convertView ?: inflater.inflate(R.layout.handlelisterad, parent, false)
        val mengdeView = rowView.findViewById(R.id.handleliste_varemengde) as TextView
        val betegnelseView = rowView.findViewById(R.id.handleliste_varebetegnelse) as TextView
        val plasseringView = rowView.findViewById(R.id.handleliste_vareplassering) as TextView

        mengdeView.text = vare.mengde
        betegnelseView.text = vare.betegnelse
        plasseringView.text = vare.plassering

        rowView.setBackgroundColor(colorMap[vare.status]!!)
        return rowView

    }

}
