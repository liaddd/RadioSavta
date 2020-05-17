package com.liad.radiosavta.adapters

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.liad.radiosavta.R
import com.liad.radiosavta.models.RecordedShow
import com.liad.radiosavta.utils.extension.clearAndAddAll

class RecordedShowsAdapter : RecyclerView.Adapter<RecordedShowsAdapter.ViewHolder>() {

    private var recordedShowsList = mutableListOf<RecordedShow>()

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
        return recordedShowsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val show = recordedShowsList[position]

        holder.numberTV.text = "${(position + 1)}"
        holder.nameTV.text = show.name
        holder.durationTV.text = show.duration
        holder.dateTV.text = show.recordedAt

        holder.imageView.setOnClickListener {
            val uri = Uri.parse(show.url)
            startActivity(holder.itemView.context, Intent(Intent.ACTION_VIEW, uri), null)
        }
    }

    fun setRecordedShow(newShows: List<RecordedShow>) {
        recordedShowsList.clearAndAddAll(newShows)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val numberTV: TextView = itemView.findViewById(R.id.recorded_list_item_number)
        val nameTV: TextView = itemView.findViewById(R.id.recorded_list_item_name)
        val durationTV: TextView = itemView.findViewById(R.id.recorded_list_item_duration)
        val dateTV: TextView = itemView.findViewById(R.id.recorded_list_item_date)
        val imageView: ImageView = itemView.findViewById(R.id.recorded_list_item_image_view)
    }
}