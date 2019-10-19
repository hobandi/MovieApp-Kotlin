package pl.kfert.movie.ui.main

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.Filterable


class AutoCompleteAdapter(context: Context, resource: Int) :
    ArrayAdapter<String>(context, resource) , Filterable {

    private var listData : MutableList<String> = ArrayList()

    fun setData(list: List<String>) {
        listData.clear()
        listData.addAll(list)
        notifyDataSetChanged()
    }

    override fun getCount(): Int {
        return listData.size
    }

    override fun getItem(position: Int): String {
        return listData[position]
    }

    override fun getFilter() : Filter {

        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {

                val filterResults = FilterResults()

                constraint.let {
                    filterResults.values = listData
                    filterResults.count = listData.size
                }

                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                if (results != null && (results.count > 0)) {
                    notifyDataSetChanged()
                } else {
                    notifyDataSetInvalidated()
                }
            }
        }
    }
}
