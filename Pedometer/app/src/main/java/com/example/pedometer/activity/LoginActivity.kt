package com.example.pedometer.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.example.pedometer.BaseApplication
import com.example.pedometer.retrofit.APIHelper
import com.example.pedometer.databinding.ActivityLoginBinding
import com.example.pedometer.util.*
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        setClickEvent()
    }

    private fun init() {
        // is login
        if(getSharedPreferences(TEXT_IS_LOGIN, Context.MODE_PRIVATE).getBoolean(TEXT_IS_LOGIN, false)) goToMainActivity()
        // is not login
        else {
            binding.showExistId = false
            (application as BaseApplication).processCommunityId()
            val pref = getSharedPreferences(TEXT_COMMUNITY_ID, Context.MODE_PRIVATE)
            if (pref.getString(TEXT_COMMUNITY_ID, null) != null) {
                val communityId = pref.getString(TEXT_COMMUNITY_ID, null)!!
                binding.etIdNew.setText(communityId)
            }
        }

    }

    private fun setClickEvent() {
        binding.btnLogin.setOnClickListener {
            if (!validate() || !checkIdPwd()) AppMsgUtil.showErrorMsg(
                "Please check your input id & pwd",
                STATUS_FAIL,
                this
            )
            else {
                val pref = getSharedPreferences(TEXT_IS_LOGIN, Context.MODE_PRIVATE)
                pref.edit().putBoolean(TEXT_IS_LOGIN, true).apply()
                goToMainActivity()
            }
        }
        binding.swiChangeIdType.setOnCheckedChangeListener { _, isChecked ->
            binding.showExistId = isChecked
        }

        binding.layoutWrap.setOnClickListener {
            if (it !is TextInputEditText && it !is TextInputLayout) {
                hideKeyboard()
            }
        }

    }

    private fun goToMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun validate(): Boolean {
        // new id
        if (!binding.showExistId!!) {
            if (binding.etIdNew.text.isNullOrEmpty()) return false
        }
        // exist id
        if (binding.showExistId!!) {
            if (binding.etIdExist.text.isNullOrEmpty()) return false
        }
        // password validate
        if (binding.etPassword.text.isNullOrEmpty()) return false
        if (binding.etPassword.text!!.length < MINIMUM_LENGTH_PASSWORD) return false
        return true
    }

    private fun checkIdPwd(): Boolean {
        val id = if (!binding.showExistId!!) binding.etIdNew.text.toString() // new id
        else binding.etIdExist.text.toString() // exist id
        val pwd = binding.etPassword.text.toString()
        return APIHelper.isAbleLogin(id, pwd, this)
    }

    private fun hideKeyboard() {
        val im =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        im.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        currentFocus?.clearFocus()
    }

}