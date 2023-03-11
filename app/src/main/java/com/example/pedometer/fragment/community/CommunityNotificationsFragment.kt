package com.example.pedometer.fragment.community

import TEMP_THIS_WEEK_LIST_COMMUNITY_NOTIFICATIONS
import TEMP_TODAY_LIST_COMMUNITY_NOTIFICATIONS
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pedometer.adapter.community.CommunityFriendsAdapter
import com.example.pedometer.adapter.community.CommunityNotificationsAdapter
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
        val dividerItemDecoration = DividerItemDecoration(
            requireContext(),
            LinearLayoutManager(requireContext()).orientation
        )
        binding.recyclerViewToday.addItemDecoration(dividerItemDecoration)
        binding.recyclerViewToday.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewToday.adapter = CommunityNotificationsAdapter(TEMP_TODAY_LIST_COMMUNITY_NOTIFICATIONS)

        binding.recyclerViewThisWeek.addItemDecoration(dividerItemDecoration)
        binding.recyclerViewThisWeek.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewThisWeek.adapter = CommunityNotificationsAdapter(TEMP_THIS_WEEK_LIST_COMMUNITY_NOTIFICATIONS)
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