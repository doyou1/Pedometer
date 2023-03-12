package com.example.pedometer.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pedometer.R
import com.example.pedometer.databinding.RvItemAddFriendsBinding
import com.example.pedometer.databinding.RvItemWeekGoalBinding
import com.example.pedometer.domain.AddFriends
import com.example.pedometer.domain.WeekGoal
import com.example.pedometer.util.*

class AddFriendsAdapter(private val _list: List<AddFriends>, private val context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TAG = this::class.java.simpleName
    private val list = _list

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            RvItemAddFriendsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AddFriendsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = list[position]
        (holder as AddFriendsViewHolder).bind(item)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class AddFriendsViewHolder(private val binding: RvItemAddFriendsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: AddFriends) {
            Glide.with(itemView.context)
                .load(item.profileUrl)
                .into(binding.ivProfile)

            binding.tvName.text = item.name
            when(item.status) {
                FLAG_STATUS_ADD -> {
                    binding.btnAction.text = context.resources.getString(R.string.text_add)
                    binding.btnAction.backgroundTintList = ColorStateList.valueOf(context.resources.getColor(R.color.app_red))
                    binding.btnAction.isEnabled = true
                }
                FLAG_STATUS_PENDING -> {
                    binding.btnAction.text = context.resources.getString(R.string.text_pending)
                    binding.btnAction.backgroundTintList = ColorStateList.valueOf(context.resources.getColor(R.color.run))
                    binding.btnAction.isEnabled = false
                }
                FLAG_STATUS_ALREADY -> {
                    binding.btnAction.text = context.resources.getString(R.string.text_already)
                    binding.btnAction.backgroundTintList = ColorStateList.valueOf(context.resources.getColor(R.color.run))
                    binding.btnAction.isEnabled = false
                }
            }

            binding.tvSteps.text = item.steps

            val content = SpannableString(item.communityId)
            content.setSpan(UnderlineSpan(), 0, content.length, 0)
            binding.tvCommunityId.text = content
        }
    }

}