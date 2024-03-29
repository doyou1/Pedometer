package com.example.pedometer.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.pedometer.R
import com.example.pedometer.databinding.FragmentHistoryBinding
import com.example.pedometer.room.RoomDBHelper
import com.example.pedometer.room.Pedometer
import com.example.pedometer.util.*
import com.github.mikephil.charting.components.LimitLine
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.model.GradientColor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HistoryFragment : BaseFragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private val TAG = this::class.java.simpleName
    override val currentView: Int = FLAG_HISTORY

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch(Dispatchers.IO) {
            val currentToday = RoomDBHelper.getCurrent(requireContext())
            val currentDaily = RoomDBHelper.getCurrentDaily(requireContext())
            val currentWeek = RoomDBHelper.getCurrentWeek(requireContext())
            lifecycleScope.launch(Dispatchers.Main) {
                when (currentView) {
                    FLAG_HISTORY -> {
                        if (currentToday != null) {
                            setChartToday(currentToday)
                            setChartTodayAverageLine()
                        } else {
                            setEmptyChartToday()
                            setChartTodayAverageLine()
                        }
                        setChartDaily(currentDaily)
                        setChartDailyAverageLine()
                        setChartWeek(currentWeek)
                        setChartWeekAverageLine()
                    }
                }
            }
        }

        binding.chartToday.setOnTouchListener { _, _ ->
            setChartTodayAverageLine()
            false
        }

        binding.chartDaily.setOnTouchListener { _, _ ->
            setChartDailyAverageLine()
            false
        }
        binding.chartWeek.setOnTouchListener { _, _ ->
            setChartWeekAverageLine()
            false
        }
    }

    private fun setChartToday(item: Pedometer) {
        val xvalue = LIST_CHART_TODAY_X_VALUES
        val barDataset = DataUtil.getChartTodayDataSet(xvalue, item, requireContext())
        // gradient bar color
        barDataset.gradientColors = listOf(
            GradientColor(
                resources.getColor(R.color.app_color),
                resources.getColor(R.color.app_orange)
            )
        )

        // barData text visible false because of lineData duplication
        val data = BarData(barDataset)
        data.barWidth = SIZE_BAR_WIDTH
        data.isHighlightEnabled = false
        data.setValueFormatter(object : IndexAxisValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return "${value.toInt()}"
            }
        })
        binding.chartToday.data = data

        binding.chartToday.description.isEnabled = false
        binding.chartToday.legend.isEnabled = false
        binding.chartToday.setScaleEnabled(false)
        binding.chartToday.isDoubleTapToZoomEnabled = false

        val goal = if (context != null && context?.getSharedPreferences(
                activity?.getString(R.string.text_goal),
                Context.MODE_PRIVATE
            ) != null
        ) {
            context?.getSharedPreferences(
                activity?.getString(R.string.text_goal),
                Context.MODE_PRIVATE
            )!!
                .getInt(activity?.getString(R.string.text_goal), DEFAULT_GOAL) / 24
        } else {
            DEFAULT_GOAL / 24
        }
        val goalLine = LimitLine(goal.toFloat(), activity?.getString(R.string.text_goal))
        goalLine.lineWidth = SIZE_GOAL_LINE_WIDTH
        goalLine.textSize = SIZE_GOAL_LINE
        goalLine.lineColor = resources.getColor(R.color.app_color)
        goalLine.textColor = resources.getColor(R.color.app_color)
        binding.chartToday.axisLeft.addLimitLine(goalLine)
        binding.chartToday.axisLeft.setDrawAxisLine(false)
        binding.chartToday.axisLeft.setDrawGridLines(false)
        binding.chartToday.axisLeft.axisMinimum = 0f
        binding.chartToday.axisLeft.axisMaximum =
            if (RoomDBUtil.getMaxSteps(item) >= (goal + (goal / 10))) (RoomDBUtil.getMaxSteps(item) + (RoomDBUtil.getMaxSteps(
                item
            ) / 10).toFloat())
            else (goal + (goal / 10)).toFloat()
        binding.chartToday.axisRight.isEnabled = false

        val chartRenderer = RoundBarChartRender(
            binding.chartToday,
            binding.chartToday.animator,
            binding.chartToday.viewPortHandler
        )
        chartRenderer.setRadius(SIZE_RADIUS)
        binding.chartToday.renderer = chartRenderer
        binding.chartToday.xAxis.textColor = resources.getColor(R.color.app_color)
        binding.chartToday.xAxis.spaceMin = SIZE_SPACE
        binding.chartToday.xAxis.spaceMax = SIZE_SPACE
        binding.chartToday.xAxis.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                val index = value.toInt()
                return xvalue[index]
            }
        }
        binding.chartToday.xAxis.setDrawGridLines(false)
        binding.chartToday.xAxis.setDrawAxisLine(false)
        binding.chartToday.xAxis.textSize = TEXT_SIZE_X_AXIS
        binding.chartToday.xAxis.position = XAxis.XAxisPosition.BOTTOM
        binding.chartToday.extraBottomOffset = EXTRA_BOTTOM_OFFSET
        // xAxis print 6 item of one time, add horizontal scroll
        binding.chartToday.setVisibleXRangeMaximum(SIZE_X_RANGE_MAXIMUM)
        // show current hour's item
        val currentHour = DateUtil.getCurrentHour().toInt()
        val xViewPosition =
            if (currentHour <= 3) 0f else if (currentHour > 3 && currentHour <= 21) (currentHour - 3).toFloat() else 24f
        binding.chartToday.moveViewToX(xViewPosition)
        binding.chartToday.animateY(DURATION_ANIMATION_Y)
        binding.chartToday.invalidate()
    }

    private fun setChartDaily(item: List<Pedometer>) {
        val xvalue = DataUtil.getChartDailyXValue(requireContext())
        val barDataset = DataUtil.getChartDailyDataSet(xvalue, item, requireContext())
        // gradient bar color
        barDataset.gradientColors = listOf(
            GradientColor(
                resources.getColor(R.color.app_color),
                resources.getColor(R.color.app_orange)
            )
        )

        // barData text visible false because of lineData duplication
        val data = BarData(barDataset)
        data.barWidth = SIZE_BAR_WIDTH
        data.isHighlightEnabled = false
        binding.chartDaily.data = data

        binding.chartDaily.description.isEnabled = false
        binding.chartDaily.legend.isEnabled = false
        binding.chartDaily.setScaleEnabled(false)
        binding.chartDaily.isDoubleTapToZoomEnabled = false

        val goal = if (context != null && context?.getSharedPreferences(
                activity?.getString(R.string.text_goal),
                Context.MODE_PRIVATE
            ) != null
        ) {
            context?.getSharedPreferences(
                activity?.getString(R.string.text_goal),
                Context.MODE_PRIVATE
            )!!
                .getInt(activity?.getString(R.string.text_goal), DEFAULT_GOAL)
        } else {
            DEFAULT_GOAL
        }
        val goalLine = LimitLine(goal.toFloat(), activity?.getString(R.string.text_goal))
        goalLine.lineWidth = SIZE_GOAL_LINE_WIDTH
        goalLine.textSize = SIZE_GOAL_LINE
        goalLine.lineColor = resources.getColor(R.color.app_color)
        goalLine.textColor = resources.getColor(R.color.app_color)
        binding.chartDaily.axisLeft.addLimitLine(goalLine)
        binding.chartDaily.axisLeft.setDrawAxisLine(false)
        binding.chartDaily.axisLeft.setDrawGridLines(false)
        binding.chartDaily.axisLeft.axisMinimum = 0f
        binding.chartDaily.axisLeft.axisMaximum =
            if (RoomDBUtil.computeMaxSteps(item) >= (goal + (goal / 10))) (RoomDBUtil.computeMaxSteps(
                item
            ) + (RoomDBUtil.computeMaxSteps(
                item
            ) / 10).toFloat()) else (goal + (goal / 10)).toFloat()
        binding.chartDaily.axisRight.isEnabled = false

        val chartRenderer = RoundBarChartRender(
            binding.chartDaily,
            binding.chartDaily.animator,
            binding.chartDaily.viewPortHandler
        )
        chartRenderer.setRadius(SIZE_RADIUS)
        binding.chartDaily.renderer = chartRenderer
        binding.chartDaily.xAxis.textColor = resources.getColor(R.color.app_color)
        binding.chartDaily.xAxis.spaceMin = SIZE_SPACE
        binding.chartDaily.xAxis.spaceMax = SIZE_SPACE
        binding.chartDaily.xAxis.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                val index = value.toInt()
                return xvalue[index]
            }
        }
        binding.chartDaily.xAxis.setDrawGridLines(false)
        binding.chartDaily.xAxis.setDrawAxisLine(false)
        binding.chartDaily.xAxis.textSize = TEXT_SIZE_X_AXIS
        binding.chartDaily.xAxis.position = XAxis.XAxisPosition.BOTTOM
        binding.chartDaily.extraBottomOffset = EXTRA_BOTTOM_OFFSET
        // xAxis print 6 item of one time, add horizontal scroll
        binding.chartDaily.setVisibleXRangeMaximum(SIZE_X_RANGE_MAXIMUM)
        // show most right, most last item
        binding.chartDaily.moveViewToX((xvalue.size).toFloat())

        binding.chartDaily.animateY(DURATION_ANIMATION_Y)
        binding.chartDaily.invalidate()
    }

    private fun setChartTodayAverageLine() {
        var average = 0
        val minIdx = binding.chartToday.lowestVisibleX.toInt()
        val maxIdx = binding.chartToday.highestVisibleX.toInt()
        for (i in minIdx..maxIdx) {
            val item = binding.chartToday.data.dataSets[0].getEntryForIndex(i)
            average += item.y.toInt()
        }
        average = (average / (maxIdx - minIdx + 1))
        val averageLine =
            LimitLine(average.toFloat(), requireContext().getString(R.string.text_average))
        averageLine.lineWidth = SIZE_AVERAGE_LINE_WIDTH
        averageLine.textSize = TEXT_SIZE_AVERAGE_LINE
        averageLine.lineColor = resources.getColor(R.color.light_blue)
        averageLine.textColor = resources.getColor(R.color.light_blue)
        averageLine.enableDashedLine(LINE_LENGTH_DASHED_LINE, SPACE_LENGTH_DASHED_LINE, 0f)


        // java.util.concurrentmodificationexception 問題で forEach文使わない
        for (ll in binding.chartToday.axisLeft.limitLines) {
            if (ll.label == requireContext().getString(R.string.text_average)) {
                binding.chartToday.axisLeft.removeLimitLine(ll)
                break
            }
        }

        binding.chartToday.axisLeft.addLimitLine(averageLine)
    }


    private fun setChartDailyAverageLine() {
        var average = 0
        val minIdx = binding.chartDaily.lowestVisibleX.toInt()
        val maxIdx = binding.chartDaily.highestVisibleX.toInt()
        for (i in minIdx..maxIdx) {
            val item = binding.chartDaily.data.dataSets[0].getEntryForIndex(i)
            average += item.y.toInt()
        }
        average = (average / (maxIdx - minIdx + 1))
        val averageLine =
            LimitLine(average.toFloat(), requireContext().getString(R.string.text_average))
        averageLine.lineWidth = SIZE_AVERAGE_LINE_WIDTH
        averageLine.textSize = TEXT_SIZE_AVERAGE_LINE
        averageLine.lineColor = resources.getColor(R.color.light_blue)
        averageLine.textColor = resources.getColor(R.color.light_blue)
        averageLine.enableDashedLine(LINE_LENGTH_DASHED_LINE, SPACE_LENGTH_DASHED_LINE, 0f)


        // java.util.concurrentmodificationexception 問題で forEach文使わない
        for (ll in binding.chartDaily.axisLeft.limitLines) {
            if (ll.label == requireContext().getString(R.string.text_average)) {
                binding.chartDaily.axisLeft.removeLimitLine(ll)
                break
            }
        }

        binding.chartDaily.axisLeft.addLimitLine(averageLine)
    }

    private fun setChartWeek(item: List<Pedometer>) {
        val periods = DataUtil.getChartWeekPeriods()
        val xvalue = arrayListOf<String>()
        for (item in periods) {
            xvalue.add(item.period)
        }

        val barDataset = DataUtil.getChartWeekDataSet(periods, item, requireContext())
        // gradient bar color
        barDataset.gradientColors = listOf(
            GradientColor(
                resources.getColor(R.color.app_color),
                resources.getColor(R.color.app_orange)
            )
        )

        // barData text visible false because of lineData duplication
        val data = BarData(barDataset)
        data.barWidth = SIZE_BAR_WIDTH
        data.isHighlightEnabled = false
        binding.chartWeek.data = data

        binding.chartWeek.description.isEnabled = false
        binding.chartWeek.legend.isEnabled = false
        binding.chartWeek.setScaleEnabled(false)
        binding.chartWeek.isDoubleTapToZoomEnabled = false

        val goal = if (context != null && context?.getSharedPreferences(
                activity?.getString(R.string.text_goal),
                Context.MODE_PRIVATE
            ) != null
        ) {
            context?.getSharedPreferences(
                activity?.getString(R.string.text_goal),
                Context.MODE_PRIVATE
            )!!
                .getInt(activity?.getString(R.string.text_goal), DEFAULT_GOAL) * 7
        } else {
            DEFAULT_GOAL * 7
        }
        val goalLine = LimitLine(goal.toFloat(), activity?.getString(R.string.text_goal))
        goalLine.lineWidth = SIZE_GOAL_LINE_WIDTH
        goalLine.textSize = SIZE_GOAL_LINE
        goalLine.lineColor = resources.getColor(R.color.app_color)
        goalLine.textColor = resources.getColor(R.color.app_color)
        binding.chartWeek.axisLeft.addLimitLine(goalLine)
        binding.chartWeek.axisLeft.setDrawAxisLine(false)
        binding.chartWeek.axisLeft.setDrawGridLines(false)
        binding.chartWeek.axisLeft.axisMinimum = 0f
        binding.chartWeek.axisLeft.axisMaximum =
            if (RoomDBUtil.computeMaxSteps(item) >= (goal + (goal / 10))) (RoomDBUtil.computeMaxSteps(
                item
            ) + (RoomDBUtil.computeMaxSteps(
                item
            ) / 10).toFloat()) else (goal + (goal / 10)).toFloat()

        binding.chartWeek.axisRight.isEnabled = false

        val chartRenderer = RoundBarChartRender(
            binding.chartWeek,
            binding.chartWeek.animator,
            binding.chartWeek.viewPortHandler
        )
        chartRenderer.setRadius(SIZE_RADIUS)
        binding.chartWeek.renderer = chartRenderer
        binding.chartWeek.xAxis.textColor = resources.getColor(R.color.app_color)
        binding.chartWeek.xAxis.spaceMin = SIZE_SPACE
        binding.chartWeek.xAxis.spaceMax = SIZE_SPACE
        binding.chartWeek.xAxis.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                val index = value.toInt()
                return xvalue[index]
            }
        }
        binding.chartWeek.xAxis.setDrawGridLines(false)
        binding.chartWeek.xAxis.setDrawAxisLine(false)
        binding.chartWeek.xAxis.textSize = TEXT_SIZE_X_AXIS
        binding.chartWeek.xAxis.position = XAxis.XAxisPosition.BOTTOM
        binding.chartWeek.extraBottomOffset = EXTRA_BOTTOM_OFFSET
        // xAxis print 6 item of one time, add horizontal scroll
        binding.chartWeek.setVisibleXRangeMaximum(SIZE_X_RANGE_MAXIMUM)
        // show most right, most last item
        binding.chartWeek.moveViewToX(xvalue.size.toFloat())
        binding.chartWeek.animateY(DURATION_ANIMATION_Y)
        binding.chartWeek.invalidate()
    }

    private fun setChartWeekAverageLine() {
        var average = 0
        val minIdx = binding.chartWeek.lowestVisibleX.toInt()
        val maxIdx = binding.chartWeek.highestVisibleX.toInt()
        for (i in minIdx..maxIdx) {
            val item = binding.chartWeek.data.dataSets[0].getEntryForIndex(i)
            average += item.y.toInt()
        }
        average = (average / (maxIdx - minIdx + 1))
        val averageLine =
            LimitLine(average.toFloat(), requireContext().getString(R.string.text_average))
        averageLine.lineWidth = SIZE_AVERAGE_LINE_WIDTH
        averageLine.textSize = TEXT_SIZE_AVERAGE_LINE
        averageLine.lineColor = resources.getColor(R.color.light_blue)
        averageLine.textColor = resources.getColor(R.color.light_blue)
        averageLine.enableDashedLine(LINE_LENGTH_DASHED_LINE, SPACE_LENGTH_DASHED_LINE, 0f)

        for (ll in binding.chartWeek.axisLeft.limitLines) {
            if (ll.label == requireContext().getString(R.string.text_average)) {
                binding.chartWeek.axisLeft.removeLimitLine(ll)
                break
            }
        }
        binding.chartWeek.axisLeft.addLimitLine(averageLine)
    }

    private fun setEmptyChartToday() {
        val xvalue = LIST_CHART_TODAY_X_VALUES
        val barDataset = DataUtil.getChartTodayEmptyDataSet(xvalue, requireContext())
        // gradient bar color
        barDataset.gradientColors = listOf(
            GradientColor(
                resources.getColor(R.color.app_color),
                resources.getColor(R.color.app_orange)
            )
        )

        // barData text visible false because of lineData duplication
        val data = BarData(barDataset)
        data.barWidth = SIZE_BAR_WIDTH
        data.isHighlightEnabled = false
        data.setValueFormatter(object : IndexAxisValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return "${value.toInt()}"
            }
        })
        binding.chartToday.data = data

        binding.chartToday.description.isEnabled = false
        binding.chartToday.legend.isEnabled = false
        binding.chartToday.setScaleEnabled(false)
        binding.chartToday.isDoubleTapToZoomEnabled = false

        val goal = if (context != null && context?.getSharedPreferences(
                activity?.getString(R.string.text_goal),
                Context.MODE_PRIVATE
            ) != null
        ) {
            context?.getSharedPreferences(
                activity?.getString(R.string.text_goal),
                Context.MODE_PRIVATE
            )!!
                .getInt(activity?.getString(R.string.text_goal), DEFAULT_GOAL) / 24
        } else {
            DEFAULT_GOAL / 24
        }
        val goalLine = LimitLine(goal.toFloat(), activity?.getString(R.string.text_goal))
        goalLine.lineWidth = SIZE_GOAL_LINE_WIDTH
        goalLine.textSize = SIZE_GOAL_LINE
        goalLine.lineColor = resources.getColor(R.color.app_color)
        goalLine.textColor = resources.getColor(R.color.app_color)
        binding.chartToday.axisLeft.addLimitLine(goalLine)
        binding.chartToday.axisLeft.setDrawAxisLine(false)
        binding.chartToday.axisLeft.setDrawGridLines(false)
        binding.chartToday.axisLeft.axisMinimum = 0f
//        binding.chartToday.axisLeft.axisMaximum =
//            if (RoomDBUtil.getMaxSteps(item) >= (goal + (goal / 10))) (RoomDBUtil.getMaxSteps(item) + (RoomDBUtil.getMaxSteps(
//                item
//            ) / 10).toFloat())
//            else (goal + (goal / 10)).toFloat()
        binding.chartToday.axisRight.isEnabled = false

//        val chartRenderer = RoundBarChartRender(
//            binding.chartToday,
//            binding.chartToday.animator,
//            binding.chartToday.viewPortHandler
//        )
//        chartRenderer.setRadius(SIZE_RADIUS)
//        binding.chartToday.renderer = chartRenderer
//        binding.chartToday.xAxis.textColor = resources.getColor(R.color.app_color)
//        binding.chartToday.xAxis.spaceMin = SIZE_SPACE
//        binding.chartToday.xAxis.spaceMax = SIZE_SPACE
//        binding.chartToday.xAxis.valueFormatter = object : ValueFormatter() {
//            override fun getFormattedValue(value: Float): String {
//                val index = value.toInt()
//                return xvalue[index]
//            }
//        }
        binding.chartToday.xAxis.setDrawGridLines(false)
        binding.chartToday.xAxis.setDrawAxisLine(false)
        binding.chartToday.xAxis.textSize = TEXT_SIZE_X_AXIS
        binding.chartToday.xAxis.position = XAxis.XAxisPosition.BOTTOM
        binding.chartToday.extraBottomOffset = EXTRA_BOTTOM_OFFSET
        // xAxis print 6 item of one time, add horizontal scroll
        binding.chartToday.setVisibleXRangeMaximum(SIZE_X_RANGE_MAXIMUM)
        // show current hour's item
        val currentHour = DateUtil.getCurrentHour().toInt()
        val xViewPosition =
            if (currentHour <= 3) 0f else if (currentHour > 3 && currentHour <= 21) (currentHour - 3).toFloat() else 24f
        binding.chartToday.moveViewToX(xViewPosition)
    }

    companion object {
        private var instance: HistoryFragment? = null
        fun getInstance(): HistoryFragment {
            if (instance == null) {
                instance = HistoryFragment()
            }
            return instance!!
        }
    }
}