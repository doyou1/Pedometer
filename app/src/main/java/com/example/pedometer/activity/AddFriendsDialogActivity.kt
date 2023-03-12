package com.example.pedometer.activity

import android.app.Activity
import android.content.Intent
import android.graphics.RectF
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.pedometer.databinding.ActivityAddFriendsDialogBinding
import com.example.pedometer.util.FLAG_CLOSE


class AddFriendsDialogActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddFriendsDialogBinding
    private val TAG = this::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddFriendsDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        setClickEvent()
    }

    private fun setClickEvent() {

    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        // Get the action of the touch event
        val action = event.action

        // If the user touches outside of the dialog, close the activity
        if (action == MotionEvent.ACTION_DOWN) {
            val view: View = binding.layoutContent
            val location = IntArray(2)
            view.getLocationOnScreen(location)
            val x = event.rawX.toInt()
            val y = event.rawY.toInt()
            if ((x - location[0]) < 0 || view.width < (x - location[0])
                || (y - location[1]) < 0 || view.height < (y - location[1])
            ) {
                val intent = Intent()
                intent.putExtra("flag", FLAG_CLOSE)
                setResult(Activity.RESULT_OK, intent)
                finish()
                return true
            }
        }
        // If the user touches inside the dialog, let the event be handled normally
        return super.onTouchEvent(event)
    }
}