package com.example.pedometer.fragment.community

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.pedometer.databinding.FragmentCommunityFriendsBinding
import com.example.pedometer.databinding.FragmentCommunityNotificationsBinding
import com.example.pedometer.util.*
import com.github.mikephil.charting.data.*

class CommunityNotificationsFragment : Fragment() {

    private var _binding: FragmentCommunityNotificationsBinding? = null
    private val binding get() = _binding!!
    private val TAG = this::class.java.simpleName

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCommunityNotificationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()

    }

    companion object {
        private var instance: CommunityNotificationsFragment? = null
        fun getInstance(): CommunityNotificationsFragment {
            if (instance == null) {
                instance = CommunityNotificationsFragment()
            }
            return instance!!
        }
    }
}