package com.example.pedometer.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.example.pedometer.retrofit.APIHelper
import com.example.pedometer.databinding.ActivityLoginBinding
import com.example.pedometer.room.RoomDBHelper
import com.example.pedometer.util.*
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val TAG = this::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        setClickEvent()
    }

    private fun init() {
        // is login
        if (getSharedPreferences(TEXT_IS_LOGIN, Context.MODE_PRIVATE).getBoolean(
                TEXT_IS_LOGIN,
                false
            )
        ) goToMainActivity()
        // is not login
        else {
            binding.showExistId = false
            binding.isReadyNewId = false
            val context = this

            GlobalScope.launch(Dispatchers.IO) {
                val id =
                    APIHelper.processNewCommunityId(DataUtil.getDeviceUUID(context), context)
                GlobalScope.launch(Dispatchers.Main) {
                    id?.let {
                        setId(id)
                    }
                }
            }
        }

    }

    private fun setId(id: String) {
        binding.isReadyNewId = true
        binding.etIdNew.setText(id)
    }

    private fun setClickEvent() {
        binding.btnLogin.setOnClickListener {
            if (!validate()) return@setOnClickListener
            checkIdPwd()
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

    private fun checkIdPwd() {
        val id = if (!binding.showExistId!!) binding.etIdNew.text.toString() // new id
        else binding.etIdExist.text.toString() // exist id
        val pwd = binding.etPassword.text.toString()
        GlobalScope.launch(Dispatchers.IO) {
            val isAbleLogin = APIHelper.isAbleLogin(id, pwd, binding.showExistId)
            GlobalScope.launch(Dispatchers.Main) {
                if (isAbleLogin != null) {
                    if (isAbleLogin) successLogin(id)
                    else failLogin()
                } else failLogin()
            }
        }

    }

    private fun successLogin(id: String) {
        val pref = getSharedPreferences(TEXT_IS_LOGIN, Context.MODE_PRIVATE)
        pref.edit().putBoolean(TEXT_IS_LOGIN, true).apply()
        pref.edit().putString(TEXT_LOGIN_ID, id).apply()
        val context = this
        // when login with exist id
        // get exist server data, replace room db data
        if (binding.showExistId!!) {
            GlobalScope.launch(Dispatchers.IO) {
                val result = APIHelper.processExistData(id)
                if (result != null) {
                    RoomDBHelper.replaceUserData(result, context)
                    GlobalScope.launch(Dispatchers.Main) {
                        context.getSharedPreferences(
                            TEXT_REPLACE_USER_DATA,
                            Context.MODE_PRIVATE
                        ).edit().putBoolean(
                            DateUtil.getFullToday(), true
                        ).apply()
                        goToMainActivity()
                    }
                }
            }
        } else {
            goToMainActivity()
        }
    }

    private fun failLogin() {
        AppMsgUtil.showErrorMsg(
            TEXT_ERROR_MSG,
            STATUS_FAIL,
            this
        )
    }

    private fun hideKeyboard() {
        val im =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        im.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        currentFocus?.clearFocus()
    }
}