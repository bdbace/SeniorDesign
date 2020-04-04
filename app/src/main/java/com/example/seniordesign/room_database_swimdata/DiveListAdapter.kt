package com.example.seniordesign.room_database_swimdata

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.seniordesign.R
import com.example.seniordesign.ui.home.HomeFragment

class DiveListAdapter internal constructor(context: Context) : RecyclerView.Adapter<DiveListAdapter.DiveViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var dives = emptyList<Dive>() // Cached copy of words

    inner class DiveViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val diveTimeView: TextView = itemView.findViewById(R.id.time)
        val diveDateView: TextView = itemView.findViewById(R.id.date)
        val diveNumberView: TextView = itemView.findViewById(R.id.dive_number)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiveViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_item2, parent, false)
        return DiveViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: DiveViewHolder, position: Int) {
        val current = dives[position]
        holder.diveTimeView.text = current.dive_time
        holder.diveDateView.text = current.dive_date
        holder.diveNumberView.text = current.dive_num

    }

    internal fun setDives(dives: List<Dive>) {
        this.dives = dives
        notifyDataSetChanged()
    }

    override fun getItemCount() = dives.size
}