package com.example.pedometer.adapter.community

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.pedometer.databinding.RvItemCommunityFriendsBinding
import com.example.pedometer.domain.CommunityFriends

class CommunityFriendsAdapter(_list: List<CommunityFriends>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val list = _list

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = RvItemCommunityFriendsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CommunityFriendsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as CommunityFriendsViewHolder).bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class CommunityFriendsViewHolder(private val binding: RvItemCommunityFriendsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CommunityFriends) {

            Glide.with(itemView.context)
                .load(item.profileUrl)
//                .diskCacheStrategy(DiskCacheStrategy.NONE)
//                .skipMemoryCache(true)
                .into(binding.ivProfile)

            binding.tvName.text = item.name
            binding.tvSteps.text = item.steps
            binding.tvDate.text = item.date

        }

    }


}