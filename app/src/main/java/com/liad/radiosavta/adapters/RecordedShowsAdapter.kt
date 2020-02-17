package com.liad.radiosavta.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.liad.radiosavta.R

class RecordedShowsAdapter : RecyclerView.Adapter<RecordedShowsAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.recorded_show_list_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return 10
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.numberTV.text = "${(position + 1)}"
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val numberTV: TextView = itemView.findViewById(R.id.recorded_list_item_number)
    }
}