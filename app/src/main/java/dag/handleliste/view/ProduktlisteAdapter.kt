package dag.handleliste.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import dag.handleliste.R
import dag.handleliste.model.Produkt
import dag.handleliste.repository.Repository

class ProduktlisteAdapter(context: Context) :
    ArrayAdapter<Produkt>(context, R.layout.produktlisterad, Repository.produkter) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val produkt = Repository.produkter[position]
        val inflater = super.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val rowView = convertView ?: inflater.inflate(R.layout.produktlisterad, parent, false);
        val betegnelseView = rowView.findViewById(R.id.produktliste_betegnelse) as TextView
        val plasseringView = rowView.findViewById(R.id.produktliste_plassering) as TextView

        betegnelseView.text = produkt.betegnelse
        plasseringView.text = produkt.plassering

        return rowView
    }

}
