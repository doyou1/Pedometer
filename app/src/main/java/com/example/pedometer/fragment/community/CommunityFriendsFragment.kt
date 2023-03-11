package com.example.pedometer.fragment.community

import TEMP_LIST_COMMUNITY_FRIENDS
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pedometer.adapter.community.CommunityFriendsAdapter
import com.example.pedometer.databinding.FragmentCommunityFriendsBinding
import com.example.pedometer.domain.CommunityFriends
import com.example.pedometer.util.*
import com.github.mikephil.charting.data.*

class CommunityFriendsFragment : Fragment() {

    private var _binding: FragmentCommunityFriendsBinding? = null
    private val binding get() = _binding!!
    private val TAG = this::class.java.simpleName

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCommunityFriendsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        val dividerItemDecoration = DividerItemDecoration(
            requireContext(),
            LinearLayoutManager(requireContext()).orientation
        )
        binding.recyclerView.addItemDecoration(dividerItemDecoration)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = CommunityFriendsAdapter(TEMP_LIST_COMMUNITY_FRIENDS)
    }

    companion object {
        private var instance: CommunityFriendsFragment? = null
        fun getInstance(): CommunityFriendsFragment {
            if (instance == null) {
                instance = CommunityFriendsFragment()
            }
            return instance!!
        }
    }
}