package com.example.pedometer.fragment.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.pedometer.R
import com.example.pedometer.databinding.FragmentSettingHomeBinding

class SettingHomeFragment : SettingChildBaseFragment() {

    private var _binding: FragmentSettingHomeBinding? = null
    private val binding get() = _binding!!

    private var _navController: NavController? = null
    private val navController get() = _navController!!

    private val TAG = this::class.java.simpleName

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingHomeBinding.inflate(inflater, container, false)
        _navController = Navigation.findNavController(requireActivity(), R.id.fragment_container)

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        setClickEvent()
    }

    private fun setClickEvent() {
        binding.layoutNotifications.setOnClickListener {
            navController.navigate(R.id.action_home_to_notifications)
        }
        binding.btnNotifications.setOnClickListener {
            navController.navigate(R.id.action_home_to_notifications)
        }

        binding.layoutLanguage.setOnClickListener {
            navController.navigate(R.id.action_home_to_language)
        }
        binding.btnLanguage.setOnClickListener {
            navController.navigate(R.id.action_home_to_language)
        }

        binding.layoutPrivacySecurity.setOnClickListener {
            navController.navigate(R.id.action_home_to_privacy_security)
        }
        binding.btnPrivacySecurity.setOnClickListener {
            navController.navigate(R.id.action_home_to_privacy_security)
        }

        binding.layoutHelpSupport.setOnClickListener {
            navController.navigate(R.id.action_home_to_help_support)
        }
        binding.btnHelpSupport.setOnClickListener {
            navController.navigate(R.id.action_home_to_help_support)
        }

        binding.layoutAbout.setOnClickListener {
            navController.navigate(R.id.action_home_to_about)
        }
        binding.btnAbout.setOnClickListener {
            navController.navigate(R.id.action_home_to_about)
        }
    }

    companion object {
        private var instance: SettingChildBaseFragment? = null

        fun getInstance(): SettingChildBaseFragment {
            if (instance == null) {
                instance = SettingHomeFragment()
            }
            return instance!!
        }
    }
}