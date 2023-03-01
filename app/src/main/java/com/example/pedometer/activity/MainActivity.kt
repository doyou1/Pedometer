package com.example.pedometer.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import com.example.pedometer.R
import com.example.pedometer.databinding.ActivityMainBinding
import com.example.pedometer.fragment.HistoryFragment
import com.example.pedometer.fragment.HomeFragment
import com.example.pedometer.fragment.SettingFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val TAG = this::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
//        if (checkActivityPermission(this)) {
            setFragment(HomeFragment.getInstance())
//        }

        binding.btnSetting.setOnClickListener {
            val popup = PopupMenu(this, binding.btnSetting)
            popup.menu.add(resources.getString(R.string.text_update_goal))
            popup.setOnMenuItemClickListener {
                Log.e(TAG, "it.title: ${it.title}")
                when(it.title) {
                    resources.getString(R.string.text_update_goal) -> {
                        val intent = Intent(this, SettingActivity::class.java)
                        intent.putExtra("flag", resources.getString(R.string.text_update_goal))
                        startActivity(intent)

                    }
                }
                false
            }
            popup.show()
        }

        binding.bnv.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    setFragment(HomeFragment.getInstance())
                }
                R.id.history -> {
                    setFragment(HistoryFragment.getInstance())
                }
                R.id.setting -> {
                    setFragment(SettingFragment.getInstance())
                }
            }
            true
        }
    }

    private fun setFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(binding.frameLayout.id, fragment)
            commit()
        }
    }
}

