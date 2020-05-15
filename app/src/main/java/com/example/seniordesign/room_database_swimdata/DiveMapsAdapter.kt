package com.example.seniordesign.room_database_swimdata


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.seniordesign.R

class DiveMapsAdapter internal constructor(context: Context) : RecyclerView.Adapter<DiveMapsAdapter.DiveViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var dives = emptyList<Dive>() // Cached copy of words

    inner class DiveViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val diveDateView: TextView = itemView.findViewById(R.id.date_maps)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiveViewHolder {
        val itemView = inflater.inflate(R.layout.cardview_item_maps, parent, false)
        return DiveViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: DiveViewHolder, position: Int) {
        val current = dives[position]
        holder.diveDateView.text = current.dive_date
    }

    internal fun setDives(dives: List<Dive>) {
        this.dives = dives
        notifyDataSetChanged()
    }

    override fun getItemCount() = dives.size
}