package com.mindoverflow.scoutshub.adapter

import android.R.id
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import bit.linux.tinyspacex.Helpers
import com.mindoverflow.scoutshub.R
import com.mindoverflow.scoutshub.models.Perfil
import com.mindoverflow.scoutshub.models.RecyclerItem
import com.mindoverflow.scoutshub.ui.ProfileViewActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.lang.String


// The classe used as an adapter for the RecylerView
class RecycleViewAdapter internal constructor(recyclerList: MutableList<RecyclerItem>) :
    RecyclerView.Adapter<RecycleViewAdapter.RecyclerViewHolder>(),
    Filterable {

    // recyclerList not full
    private val recyclerList: List<RecyclerItem>
    private var recyclerListFull: List<RecyclerItem> = ArrayList<RecyclerItem>(recyclerList)
    //private var mListener : RecycleViewAdapter.OnItemClickListener? = null

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
        //The current row correspondent to the position in the search view that has been clicked
        val currentItem: RecyclerItem = recyclerList[position]

        holder.imageView.setImageResource(currentItem.imageResource)
        holder.textView.text = currentItem.text

        holder.itemView.setOnClickListener{
            // get context of itemView
            val context =  holder.itemView.context

            println("name")
            println(holder.textView.text)

            var perfil : Perfil? = null

            GlobalScope.launch(Dispatchers.IO) {
                perfil = UserNameVerification(holder.textView.text.toString())
                println("perfil")
                println(perfil)
                GlobalScope.launch(Dispatchers.Main) {
                    val intent = Intent(context, ProfileViewActivity::class.java)
                    intent.putExtra("User", perfil!!.toJson().toString())
                    context.startActivity(intent)
                }
            }
        }
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

    /*class EnquiryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView : TextView
        var textView: TextView

        init {
            imageView = itemView.findViewById(R.id.imageViewPesquisa)
            textView = itemView.findViewById(R.id.textViewPesquisa)
            itemView.setOnClickListener {
                val lister = setOnItemClickListener(listener)
                if (mListener != null) {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        cursor.moveToPosition(position)
                        id = cursor.getInt(cursor.getColumnIndex(FormContract.FormEntry._ID))
                        Log.d("ID", String.valueOf(id))
                        mListner.onItemClick(position)
                    }
                }
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) : RecycleViewAdapter.OnItemClickListener? {
        mListener = listener

        return mListener
    }*/

    private fun UserNameVerification(user : kotlin.String) : Perfil? {
        //val users = ArrayList<Perfil>()

        var userToReturn : Perfil? = null

        val client = OkHttpClient()

        val url = Helpers.getURL()

        val request =
            Request.Builder().url("$url/perfil/")
                .get()
                .build()

        client.newCall(request).execute().use { response ->

            val jsStr = (response.body!!.string())
            val jsonArray = JSONObject(jsStr).getJSONArray("perfis")

            for (index in 0 until jsonArray.length()) {
                val userCompare = Perfil.fromJson(jsStr, index)

                if(user.equals(userCompare.nome!!, ignoreCase = true)){
                    userToReturn = userCompare
                }
            }
        }
        return userToReturn
    }


    init {
        this.recyclerList = recyclerList
        recyclerListFull = ArrayList<RecyclerItem>(recyclerList)
    }
}