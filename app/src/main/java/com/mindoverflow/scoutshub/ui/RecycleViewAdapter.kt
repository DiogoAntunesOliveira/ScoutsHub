package com.mindoverflow.scoutshub.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mindoverflow.scoutshub.R
import com.mindoverflow.scoutshub.models.RecyclerItem
import kotlin.collections.ArrayList

// The classe used as an adapter for the RecylerView
class RecycleViewAdapter internal constructor(recyclerList: MutableList<RecyclerItem>) :
    RecyclerView.Adapter<RecycleViewAdapter.RecyclerViewHolder>(),
    Filterable {

    // recyclerList not full
    private val recyclerList: List<RecyclerItem>

    private var recyclerListFull: List<RecyclerItem> = ArrayList<RecyclerItem>(recyclerList)

    inner class RecyclerViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView
        var textView: TextView

        init {
            imageView = itemView.findViewById(R.id.imageViewPesquisa)
            textView = itemView.findViewById(R.id.textViewPesquisa)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val v: View = LayoutInflater.from(parent.context).inflate(
            R.layout.row_pesquisa,
            parent, false
        )
        return RecyclerViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val currentItem: RecyclerItem = recyclerList[position]
        holder.imageView.setImageResource(currentItem.imageResource)
        holder.textView.text = currentItem.text
    }

    override fun getItemCount(): Int {
        return recyclerList.size
    }

    override fun getFilter(): Filter {
        return exampleFilter
    }

    private val exampleFilter: Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence): FilterResults {
            val filteredList: MutableList<RecyclerItem> = ArrayList<RecyclerItem>()
            if (constraint.isEmpty()) {
                filteredList.addAll(recyclerListFull)
            } else {
                val filterPattern = constraint.toString().toLowerCase().trim { it <= ' ' }
                for (item in recyclerListFull) {
                    if (item.text.toLowerCase().contains(filterPattern)) {
                        filteredList.add(item)
                    }
                }
            }
            val results = FilterResults()
            results.values = filteredList
            return results
        }

        override fun publishResults(constraint: CharSequence, results: FilterResults) {
            recyclerList.clear()
            recyclerList.addAll((results.values as Collection<RecyclerItem>))
            notifyDataSetChanged()
        }
    }

    init {
        this.recyclerList = recyclerList
        recyclerListFull = ArrayList<RecyclerItem>(recyclerList)
    }
}