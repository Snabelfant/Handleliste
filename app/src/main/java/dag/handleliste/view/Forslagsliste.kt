package dag.handleliste.view

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import dag.handleliste.R
import dag.handleliste.repository.Repository

class ForslagslisteAdapter(context: Context?) : BaseAdapter(), Filterable {
    private val inflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private var forslag: List<String> = listOf()

    override fun getCount() = forslag.size

    override fun getItem(position: Int): String {
        return forslag[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup) =
        createViewFromResource(position, convertView, parent)

    private fun createViewFromResource(position: Int, convertView: View?, parent: ViewGroup): TextView {
        val view = (convertView ?: inflater.inflate(R.layout.spinnertext, parent, false)) as TextView
        view.text = getItem(position)
        return view
    }

    override fun getFilter(): Filter = ForslagFilter()

    private inner class ForslagFilter : Filter() {
        override fun performFiltering(prefix: CharSequence?): Filter.FilterResults {
            val results = Filter.FilterResults()

            Log.d("Filter", "filter cs='$prefix'")
            if (!prefix.isNullOrBlank()) {
                forslag = Repository.forslag(prefix as String)
                results.values = forslag
                results.count = forslag.size
            }
            return results
        }

        override fun publishResults(constraint: CharSequence?, results: Filter.FilterResults) {
            Log.d("Filter", "sortert=${results.values}")

            if (results.count > 0) {
                this@ForslagslisteAdapter.notifyDataSetChanged()
            } else {
                this@ForslagslisteAdapter.notifyDataSetInvalidated()
            }

        }


    }
}
