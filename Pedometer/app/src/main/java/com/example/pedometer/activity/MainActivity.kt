package com.example.pedometer.activity

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import android.Manifest
import com.example.pedometer.BaseApplication
import com.example.pedometer.R
import com.example.pedometer.databinding.ActivityMainBinding
import com.example.pedometer.fragment.CommunityFragment
import com.example.pedometer.fragment.HistoryFragment
import com.example.pedometer.fragment.HomeFragment
import com.example.pedometer.fragment.SettingFragment
import com.example.pedometer.service.PedometerService
import com.example.pedometer.util.REQUEST_CODE_STEP_COUNT
import com.example.pedometer.util.TEXT_IS_LOGIN

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val TAG = this::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT >= 26) {
            startForegroundService(Intent(this, PedometerService::class.java))
        } else {
            startService(Intent(this, PedometerService::class.java))
        }

        init()
    }

    private fun init() {
        if (checkActivityPermission(this)) {
            setFragment(HomeFragment.getInstance())
        }

        binding.btnSetting.setOnClickListener {
            val popup = PopupMenu(this, binding.btnSetting)
            popup.menu.add(resources.getString(R.string.text_update_goal))
            popup.menu.add(resources.getString(R.string.text_logout))
            popup.setOnMenuItemClickListener {
                Log.e(TAG, "it.title: ${it.title}")
                when (it.title) {
                    resources.getString(R.string.text_update_goal) -> {
                        val intent = Intent(this, UpdateGoalActivity::class.java)
                        intent.putExtra("flag", resources.getString(R.string.text_update_goal))
                        startActivity(intent)
                    }
                    resources.getString(R.string.text_logout) -> {
                        getSharedPreferences(TEXT_IS_LOGIN, Context.MODE_PRIVATE).edit().putBoolean(
                            TEXT_IS_LOGIN, false
                        ).apply()
                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                        stopService(Intent(this, PedometerService::class.java))
                        finish()
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
                R.id.community -> {
                    setFragment(CommunityFragment.getInstance())
                }
                R.id.setting -> {
                    setFragment(SettingFragment.getInstance())
                }
            }
            true
        }
    }

    override fun onResume() {
        super.onResume()
    }

    private fun setFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(binding.frameLayout.id, fragment)
            commit()
        }
    }

    private fun checkActivityPermission(context: Context): Boolean {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACTIVITY_RECOGNITION
                ) == PackageManager.PERMISSION_DENIED
            ) {
                requestPermissions(
                    arrayOf(Manifest.permission.ACTIVITY_RECOGNITION),
                    REQUEST_CODE_STEP_COUNT
                )
                return false
            }
        }
        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_STEP_COUNT) {
            if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                (application as BaseApplication).processCommunityId()
                setFragment(HomeFragment.getInstance())
            }
        }
    }

}

