package com.liad.radiosavta.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.liad.radiosavta.R
import com.liad.radiosavta.models.Program
import com.liad.radiosavta.utils.extension.clearAndAddAll

class ProgramsAdapter : RecyclerView.Adapter<ProgramsAdapter.ViewHolder>() {

    private val programsList = mutableListOf<Program>()
    var listener: IProgramListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.program_list_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return programsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val program = programsList[position]

        holder.nameTV.text = program.nameEn
        //holder.descriptionTV.text = program.description

        val firstUser = program.users?.let { it[0] }
        Glide.with(holder.itemView.context)
            .load(program.getCover() ?: firstUser?.getProfileImg())
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .centerCrop()
            .placeholder(
                ResourcesCompat.getDrawable(
                    holder.itemView.resources,
                    R.mipmap.ic_launcher,
                    null
                )
            )
            .into(holder.coverImage)

        holder.itemView.setOnClickListener {
            listener?.onClick(program)
        }
    }

    fun setPrograms(data: List<Program>) {
        programsList.clearAndAddAll(data)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTV: TextView = itemView.findViewById(R.id.program_list_item_name_text_view)
        //val descriptionTV: TextView = itemView.findViewById(R.id.program_list_item_description_text_view)
        val coverImage: ImageView = itemView.findViewById(R.id.program_list_item_cover_image_view)
    }

    interface IProgramListener {
        fun onClick(program: Program)
    }
}