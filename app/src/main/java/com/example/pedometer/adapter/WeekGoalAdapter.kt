package com.example.pedometer.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pedometer.databinding.RvItemWeekGoalBinding
import com.example.pedometer.domain.WeekGoal

class WeekGoalAdapter(private val _list: List<WeekGoal>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TAG = this::class.java.simpleName
    private val list = _list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = RvItemWeekGoalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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
            binding.model = item
        }
    }

}