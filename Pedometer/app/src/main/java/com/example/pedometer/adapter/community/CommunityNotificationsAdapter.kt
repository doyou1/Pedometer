package com.example.pedometer.adapter.community

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.pedometer.databinding.RvItemCommunityFriendsBinding
import com.example.pedometer.databinding.RvItemCommunityNotificationsBinding
import com.example.pedometer.domain.CommunityFriends
import com.example.pedometer.domain.CommunityNotifications

class CommunityNotificationsAdapter(_list: List<CommunityNotifications>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val list = _list

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = RvItemCommunityNotificationsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CommunityNotificationsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as CommunityNotificationsViewHolder).bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class CommunityNotificationsViewHolder(private val binding: RvItemCommunityNotificationsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CommunityNotifications) {
            binding.ivIsNew.visibility = if (item.isNew) View.VISIBLE else View.INVISIBLE
            Glide.with(itemView.context)
                .load(item.profileUrl)
                .into(binding.ivProfile)
            binding.tvName.text = item.name
            binding.tvContent.text = item.content
            binding.tvDate.text = item.date
        }

    }


}