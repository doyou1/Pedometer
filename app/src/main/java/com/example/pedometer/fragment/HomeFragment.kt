package com.example.pedometer.fragment

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pedometer.R
import com.example.pedometer.adapter.WeekGoalAdapter
import com.example.pedometer.databinding.FragmentHomeBinding
import com.example.pedometer.util.*
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import kotlin.random.Random

class HomeFragment : BaseFragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val TAG = this::class.java.simpleName

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        setWeekGoal()
        setChart()
        setText()
    }

    private fun setWeekGoal() {
        binding.rvWeekGoal.adapter = WeekGoalAdapter(Util.getDataWeekGoal(requireContext()))
    }

    private fun setChart() {
        binding.chartStep.description.isEnabled = false
        // radius of the center hole in percent of maximum radius
        binding.chartStep.holeRadius = HOLE_RADIUS_PIE_CHART
        binding.chartStep.transparentCircleRadius = TRANSPARENT_CIRCLE_RADIUS_PIE_CHART
        binding.chartStep.legend.isEnabled = false

        // disable drag
        binding.chartStep.isRotationEnabled = false

        binding.chartStep.centerText = getCenterText()
        binding.chartStep.data = getData()
        binding.chartStep.animateY(DURATION_ANIMATION_Y)
        binding.chartStep.invalidate()
    }

    private fun setText() {
        binding.tvDistanceData.text = "12.7"
        binding.tvMinutesData.text = "152"
        binding.tvCaloriesData.text = "6913"
    }

    private fun getCenterText(): SpannableString {
        val centerText = "8513\nsteps"
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

    private fun getData(): PieData {
        val goal = if (activity != null && activity?.getSharedPreferences(
                activity?.getString(R.string.text_goal),
                Context.MODE_PRIVATE
            ) != null
        ) {
            activity?.getSharedPreferences(activity?.getString(R.string.text_goal), Context.MODE_PRIVATE)!!
                .getInt(activity?.getString(R.string.text_goal), DEFAULT_GOAL)
        } else {
            DEFAULT_GOAL
        }
        val entries = arrayListOf<PieEntry>()
//        val steps = 8513f
        val steps = Random.nextInt(0, DEFAULT_GOAL).toFloat()
        entries.add(PieEntry(steps, resources.getString(R.string.text_current_steps) ))
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