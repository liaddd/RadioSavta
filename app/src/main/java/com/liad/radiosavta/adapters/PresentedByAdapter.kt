package com.liad.radiosavta.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.liad.radiosavta.R

class PresentedByAdapter : RecyclerView.Adapter<PresentedByAdapter.ViewHolder>() {

    private val presentedByList = mutableListOf(
        R.mipmap.ic_launcher,
        R.mipmap.ic_launcher,
        R.mipmap.ic_launcher,
        R.mipmap.ic_launcher,
        R.mipmap.ic_launcher,
        R.mipmap.ic_launcher,
        R.mipmap.ic_launcher
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.presented_by_list_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return presentedByList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val icon = presentedByList[position]

        holder.imageView.setBackgroundResource(icon)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.presented_by_item_image_view)
    }
}