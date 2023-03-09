package com.example.pedometer.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.pedometer.R
import com.example.pedometer.databinding.FragmentCommunityBinding
import com.example.pedometer.databinding.FragmentHistoryBinding
import com.example.pedometer.fragment.community.CommunityFriendsFragment
import com.example.pedometer.fragment.community.CommunityNotificationsFragment
import com.example.pedometer.room.DBHelper
import com.example.pedometer.room.Pedometer
import com.example.pedometer.util.*
import com.github.mikephil.charting.components.LimitLine
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.model.GradientColor
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CommunityFragment : BaseFragment() {

    private var _binding: FragmentCommunityBinding? = null
    private val binding get() = _binding!!
    private val TAG = this::class.java.simpleName
    override val currentView: Int = FLAG_COMMUNITY

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCommunityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()

        setFragment(0)

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                setFragment(tab?.position)
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }
            override fun onTabReselected(tab: TabLayout.Tab?) {
                setFragment(tab?.position)
            }
        })
    }

    private fun setFragment(position: Int?) {
        position?.let {
            val transaction = childFragmentManager.beginTransaction()
            val fragment = when (it) {
                0 -> CommunityFriendsFragment.getInstance()
                1 -> CommunityNotificationsFragment.getInstance()
                else -> throw NotImplementedError()
            }
            transaction.replace(binding.frameLayout.id, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }


    companion object {
        private var instance: CommunityFragment? = null
        fun getInstance(): CommunityFragment {
            if (instance == null) {
                instance = CommunityFragment()
            }
            return instance!!
        }
    }
}