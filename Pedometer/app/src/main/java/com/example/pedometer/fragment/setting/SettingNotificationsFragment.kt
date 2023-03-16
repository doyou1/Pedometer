package com.example.pedometer.fragment.setting

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.Navigation
import com.example.pedometer.R
import com.example.pedometer.databinding.FragmentSettingNotificationsBinding
import com.example.pedometer.util.*
import java.lang.Exception

class SettingNotificationsFragment : SettingChildBaseFragment() {

    private var _binding: FragmentSettingNotificationsBinding? = null
    private val binding get() = _binding!!
    private val TAG = this::class.java.simpleName

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingNotificationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // This callback will only be called when MyFragment is at least Started.
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true /* enabled by default */) {
                override fun handleOnBackPressed() {
                    // Handle the back button event
                    Navigation.findNavController(requireView()).navigateUp()
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    override fun onResume() {
        super.onResume()
        init()
        setEvent()
    }

    private fun init() {
        val pref = requireContext().getSharedPreferences(TEXT_NOTI_REPEAT, Context.MODE_PRIVATE)
        val notiOnoff = pref.getBoolean(TEXT_ONOFF, true)
        binding.swiNotificationsOnOff.isChecked = notiOnoff
        val notiRepeatPeriod = pref.getInt(TEXT_VALUE, DEFAULT_VALUE_TEN_MINUTES)
        binding.etNotificationsPeriod.setText("${notiRepeatPeriod / (60 * 1000)}")
    }

    private fun setEvent() {
        binding.btnNotificationsPeriod.setOnClickListener {
            binding.btnNotificationsPeriod.isEnabled = false
            if (validate()) {
                val value = binding.etNotificationsPeriod.text.toString().toInt()
                val pref =
                    requireContext().getSharedPreferences(TEXT_NOTI_REPEAT, Context.MODE_PRIVATE)
                pref.edit().putInt(TEXT_VALUE, value * (60 * 1000)).apply()
                AppMsgUtil.showInfoMsg(
                    requireContext().resources.getString(R.string.text_success_change_noti_repeat_period),
                    STATUS_SUCCESS,
                    requireActivity()
                )
            } else {
                AppMsgUtil.showErrorMsg(
                    requireContext().resources.getString(R.string.text_fail_change_noti_repeat_period),
                    STATUS_FAIL,
                    requireActivity()
                )
            }
            Handler(Looper.getMainLooper()).postDelayed({
                binding.btnNotificationsPeriod.isEnabled = true
            }, VALUE_THREE_SECONDS)
        }

        binding.swiNotificationsOnOff.setOnCheckedChangeListener { _, isChecked ->
            binding.swiNotificationsOnOff.isEnabled = false

            val pref = requireContext().getSharedPreferences(TEXT_NOTI_REPEAT, Context.MODE_PRIVATE)
            pref.edit().putBoolean(TEXT_ONOFF, isChecked).apply()
            AppMsgUtil.showInfoMsg(
                requireContext().resources.getString(R.string.text_success_change_noti_onoff),
                STATUS_SUCCESS,
                requireActivity()
            )
            Handler(Looper.getMainLooper()).postDelayed({
                binding.swiNotificationsOnOff.isEnabled = true
            }, VALUE_THREE_SECONDS)
        }

        binding.btnBack.setOnClickListener {
            Navigation.findNavController(requireView()).navigateUp()
        }
    }

    private fun validate(): Boolean {

        // null check
        if (binding.etNotificationsPeriod.text == null) return false

        // empty check (length=0)
        if (binding.etNotificationsPeriod.text.toString().isEmpty()) return false

        // int value check
        try {
            binding.etNotificationsPeriod.text.toString().toInt()
        } catch (e: Exception) {
            return false
        }

        return true
    }

    companion object {
        private var instance: SettingChildBaseFragment? = null

        fun getInstance(): SettingChildBaseFragment {
            if (instance == null) {
                instance = SettingNotificationsFragment()
            }
            return instance!!
        }
    }
}