package com.example.pedometer.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pedometer.R
import com.example.pedometer.databinding.RvItemWeekGoalBinding
import com.example.pedometer.domain.WeekGoal
import com.example.pedometer.util.STATUS_NOT_YET
import com.example.pedometer.util.STATUS_REACHED
import com.example.pedometer.util.STATUS_RUN

class WeekGoalAdapter(private val _list: List<WeekGoal>, private val context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TAG = this::class.java.simpleName
    private val list = _list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            RvItemWeekGoalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        binding.root.layoutParams.width = parent?.measuredWidth / 7
        return WeekGoalViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = list[position]
        (holder as WeekGoalViewHolder).bind(item)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class WeekGoalViewHolder(private val binding: RvItemWeekGoalBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: WeekGoal) {

            when (item.status) {
                STATUS_REACHED -> {
                    binding.ivWeekGoal.setColorFilter(context.resources.getColor(R.color.app_color))
                    binding.tvWeekGoal.setTextColor(context.resources.getColor(R.color.app_color))
                }
                STATUS_RUN -> {
                    binding.ivWeekGoal.setColorFilter(context.resources.getColor(R.color.run))
                    binding.tvWeekGoal.setTextColor(context.resources.getColor(R.color.run))
                }
                STATUS_NOT_YET -> {
                    binding.ivWeekGoal.setColorFilter(context.resources.getColor(R.color.not_yet))
                    binding.tvWeekGoal.setTextColor(context.resources.getColor(R.color.not_yet))
                }
            }

            binding.model = item
        }
    }

}