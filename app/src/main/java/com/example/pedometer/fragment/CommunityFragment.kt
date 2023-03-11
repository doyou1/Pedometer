package com.example.pedometer.fragment

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
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
import com.google.android.material.badge.BadgeDrawable
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
        val notificationsBadge = binding.tabLayout.getTabAt(1)?.orCreateBadge
        notificationsBadge?.badgeGravity = BadgeDrawable.TOP_END
        notificationsBadge?.number = 10
        notificationsBadge?.backgroundColor = resources.getColor(R.color.app_red)
    }

    private fun setFragment(position: Int?) {
        position?.let {
            binding.showFrameLayout = false
            val handler = Handler(Looper.getMainLooper())
            handler.postDelayed({
                val transaction = childFragmentManager.beginTransaction()
                val fragment = when (it) {
                    0 -> CommunityFriendsFragment.getInstance()
                    1 -> CommunityNotificationsFragment.getInstance()
                    else -> throw NotImplementedError()
                }
                transaction.replace(binding.frameLayout.id, fragment)
                transaction.addToBackStack(null)
                transaction.commit()
                binding.showFrameLayout = true
            }, DELAY_SHOW_FRAME_LAYOUT)
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