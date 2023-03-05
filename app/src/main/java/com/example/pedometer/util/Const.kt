package com.example.pedometer.util

import android.content.Context

const val DEFAULT_GOAL = 10000
const val SIZE_GOAL_LINE = 24f
const val SIZE_GOAL_LINE_WIDTH = 8f
const val SIZE_AVERAGE_LINE_WIDTH = 5f
const val SIZE_SPACE = 0.5f
const val TEXT_SIZE_X_AXIS = 12f
const val EXTRA_BOTTOM_OFFSET = 12f
const val SIZE_X_RANGE_MAXIMUM = 6f
const val SIZE_BAR_WIDTH = 0.25f
const val TEXT_SIZE_AVERAGE_LINE = 24f
const val SIZE_RADIUS = 20

const val DURATION_ANIMATION_Y = 1000
const val TEXT_SIZE_PIE_CHART_CENTER_TITLE_TEXT = 6f
const val TEXT_SIZE_PIE_CHART_CENTER_SUB_TEXT = 3f
const val PIE_DATA_SET_SLICE_SPACE = 2f
const val TEXT_SIZE_PIE_DATA_SET_VALUE = 12f
const val HOLE_RADIUS_PIE_CHART = 80f
const val TRANSPARENT_CIRCLE_RADIUS_PIE_CHART = 79f
const val LINE_LENGTH_DASHED_LINE = 25f
const val SPACE_LENGTH_DASHED_LINE = 10f
const val REQUEST_CODE_STEP_COUNT = 999
const val MICROSECONDS_IN_ONE_MINUTE: Long = 60000000
const val PEDOMETER_NOTIFICATION_ID = 1
const val PEDOMETER_NOTIFICATION_CHANNEL_ID = "Pedometer Notification"
const val TEXT_PEDOMETER = "pedometer"
const val FLAG_HOME = 0
const val FLAG_HISTORY = 1
const val FLAG_SETTING = 2
const val STATUS_REACHED = 0
const val STATUS_RUN = 1
const val STATUS_NOT_YET = 2

const val TEXT_INIT = "init"
const val TEXT_NOTI_REPEAT = "notirepeat"
const val TEXT_VALUE = "value"
const val TEXT_ONOFF = "onoff"

const val DEFAULT_VALUE_TEN_MINUTES = 10 * 60 * 1000
const val STATUS_SUCCESS = 0
const val STATUS_FAIL = 1

const val VALUE_THREE_SECONDS = 3000L

// distance per 1 steps(0.5m, 50cm)
const val DISTANCE_PER_STEPS = 0.5
// steps per 1 minutes(100steps)
const val MINUTES_PER_STEPS = 100
const val CALORIES_PER_STEPS = 400.0 / 10000.0
const val TEXT_REBOOT = "reboot"
const val TEXT_REBOOT_TIME = "reboottime"
const val TEXT_REBOOT_BEFORE_STEPS = "rebootbeforesteps"