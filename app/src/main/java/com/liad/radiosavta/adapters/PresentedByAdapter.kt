package com.liad.radiosavta.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.liad.radiosavta.R
import com.liad.radiosavta.models.User
import com.liad.radiosavta.utils.extension.clearAndAddAll

class PresentedByAdapter : RecyclerView.Adapter<PresentedByAdapter.ViewHolder>() {

    private val usersList = mutableListOf<User>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.users_list_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return usersList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val user = usersList[position]

        Glide.with(holder.itemView.context)
            .load(user.getProfileImg())
            .into(holder.imageView)
    }

    fun setUsers(data: List<User>) {
        usersList.clearAndAddAll(data)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.presented_by_item_image_view)
    }
}