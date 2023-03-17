package com.example.pedometer.fragment

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.pedometer.R
import com.example.pedometer.adapter.WeekGoalAdapter
import com.example.pedometer.databinding.FragmentHomeBinding
import com.example.pedometer.retrofit.APIHelper
import com.example.pedometer.room.RoomDBHelper
import com.example.pedometer.room.Pedometer
import com.example.pedometer.util.*
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeFragment : BaseFragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val TAG = this::class.java.simpleName
    override val currentView: Int = FLAG_HOME

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch(Dispatchers.IO) {
            val currentDaily = RoomDBHelper.getCurrentDaily(requireContext())
            lifecycleScope.launch(Dispatchers.Main) {
                setWeekGoal(currentDaily)
            }
        }

        binding.btnPost.setOnClickListener {
            Log.e(TAG, "btn post")
            APIHelper.post()
        }
    }

    override fun updateCurrentSteps(item: Pedometer?) {
        super.updateCurrentSteps(item)
        setChart(item)
        setText(item)
    }

    override fun updateCurrentDaily(item: List<Pedometer>) {
        super.updateCurrentDaily(item)
        setWeekGoal(item)
    }

    private fun setWeekGoal(item: List<Pedometer>) {
        binding.rvWeekGoal.adapter =
            WeekGoalAdapter(DataUtil.getDataWeekGoal(item, requireContext()), requireContext())
    }

    private fun setChart(item: Pedometer?) {
        binding.chartStep.description.isEnabled = false
        // radius of the center hole in percent of maximum radius
        binding.chartStep.holeRadius = HOLE_RADIUS_PIE_CHART
        binding.chartStep.transparentCircleRadius = TRANSPARENT_CIRCLE_RADIUS_PIE_CHART
        binding.chartStep.legend.isEnabled = false

        // disable drag
        binding.chartStep.isRotationEnabled = false

        if (item != null) {
            binding.chartStep.centerText = "${getCenterText(RoomDBUtil.computeSteps(item))}"
            binding.chartStep.data = getData(RoomDBUtil.computeSteps(item))
        }
        binding.chartStep.animateY(DURATION_ANIMATION_Y)
        binding.chartStep.invalidate()
    }

    private fun setText(item: Pedometer?) {
        if (item != null) {
            val steps = RoomDBUtil.computeSteps(item)

            // distance per 1steps
            binding.tvDistanceData.text = "${(steps * DISTANCE_PER_STEPS) / 1000.0}"
            binding.tvMinutesData.text = "${steps / MINUTES_PER_STEPS}"
            // 400 calories per 10,000 steps
            binding.tvCaloriesData.text = "${steps.toFloat() * CALORIES_PER_STEPS}"
        } else {
            binding.tvDistanceData.text = "0"
            binding.tvMinutesData.text = "0"
            binding.tvCaloriesData.text = "0"
        }
    }

    private fun getCenterText(steps: Int): SpannableString {
        val centerText = "$steps\nsteps"
        val index = centerText.indexOf("\n")
        val spannable = SpannableString(centerText)
        spannable.setSpan(
            ForegroundColorSpan(resources.getColor(R.color.app_orange)),
            0,
            index,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannable.setSpan(
            RelativeSizeSpan(TEXT_SIZE_PIE_CHART_CENTER_TITLE_TEXT),
            0,
            index,
            Spanned.SPAN_EXCLUSIVE_INCLUSIVE
        )
        spannable.setSpan(
            ForegroundColorSpan(resources.getColor(R.color.app_red)),
            index,
            centerText.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannable.setSpan(
            RelativeSizeSpan(TEXT_SIZE_PIE_CHART_CENTER_SUB_TEXT),
            index,
            centerText.length,
            Spanned.SPAN_EXCLUSIVE_INCLUSIVE
        )
        return spannable
    }

    private fun getData(value: Int): PieData {
        val goal = if (activity != null && activity?.getSharedPreferences(
                activity?.getString(R.string.text_goal),
                Context.MODE_PRIVATE
            ) != null
        ) {
            activity?.getSharedPreferences(
                activity?.getString(R.string.text_goal),
                Context.MODE_PRIVATE
            )!!
                .getInt(activity?.getString(R.string.text_goal), DEFAULT_GOAL)
        } else {
            DEFAULT_GOAL
        }
        val entries = arrayListOf<PieEntry>()
//        val steps = 8513f
        val steps = value.toFloat()
        entries.add(PieEntry(steps, resources.getString(R.string.text_current_steps)))
        val minus = goal - steps
        if (minus <= 0) {
            // not working
            // 목표 달성했기에 추가안함
        } else {
            entries.add(PieEntry(minus, resources.getString(R.string.text_remain_steps)))
        }

        val datasets = PieDataSet(entries, "Datasets")
        datasets.colors = listOf(
            resources.getColor(R.color.app_orange), resources.getColor(R.color.not_yet)
        )
        datasets.sliceSpace = PIE_DATA_SET_SLICE_SPACE
        datasets.valueTextColor = Color.WHITE
        datasets.valueTextSize = TEXT_SIZE_PIE_DATA_SET_VALUE

        return PieData(datasets)
    }

    companion object {
        private var instance: HomeFragment? = null
        fun getInstance(): HomeFragment {
            if (instance == null) {
                instance = HomeFragment()
            }
            return instance!!
        }
    }
}