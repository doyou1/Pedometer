package com.example.pedometer.activity

import TEMP_LIST_ADD_FRIENDS
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pedometer.adapter.AddFriendsAdapter
import com.example.pedometer.adapter.community.CommunityFriendsAdapter
import com.example.pedometer.databinding.ActivityAddFriendsDialogBinding
import com.example.pedometer.fragment.community.CommunityFriendsFragment
import com.example.pedometer.fragment.community.CommunityNotificationsFragment
import com.example.pedometer.util.DELAY_SHOW_FRAME_LAYOUT
import com.example.pedometer.util.DELAY_SHOW_RECYCLER_VIEW
import com.example.pedometer.util.FLAG_CLOSE
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout


class AddFriendsDialogActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddFriendsDialogBinding
    private val TAG = this::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddFriendsDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setClickEvent()
        setRecyclerView()
    }

    private fun setClickEvent() {
        binding.layoutContent.setOnClickListener {
            if (it !is TextInputEditText && it !is TextInputLayout) {
                hideKeyboard()
            }
        }

        binding.btnClose.setOnClickListener {
            closeActivity()
        }
    }

    private fun setRecyclerView() {
        binding.showRecyclerView = false
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            val dividerItemDecoration = DividerItemDecoration(
                this,
                LinearLayoutManager(this).orientation
            )
            binding.recyclerView.addItemDecoration(dividerItemDecoration)
            binding.recyclerView.layoutManager = LinearLayoutManager(this)
            binding.recyclerView.adapter = AddFriendsAdapter(TEMP_LIST_ADD_FRIENDS, this)
            binding.showRecyclerView = true
        }, DELAY_SHOW_RECYCLER_VIEW)
    }

    private fun hideKeyboard() {
        val im =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        im.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        currentFocus?.clearFocus()
    }

    // when touch transparent area, call finish() function like dialog
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
                closeActivity()
                return true
            }
        }
        // If the user touches inside the dialog, let the event be handled normally
        return super.onTouchEvent(event)
    }

    private fun closeActivity() {
        val intent = Intent()
        intent.putExtra("flag", FLAG_CLOSE)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}