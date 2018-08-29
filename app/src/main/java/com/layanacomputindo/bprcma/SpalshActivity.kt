package com.layanacomputindo.bprcma

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Window
import android.view.WindowManager
import com.layanacomputindo.bprcma.util.Config

class SpalshActivity : AppCompatActivity() {
    private var islogin: Boolean = false
    private var device_id: String? = null

    private var sharedPreferences: SharedPreferences? = null

    fun loadPreferences() {
        sharedPreferences = getSharedPreferences(Config.PREF_NAME,
                Activity.MODE_PRIVATE)
        if (sharedPreferences != null) {
            islogin = sharedPreferences!!.getBoolean(Config.IS_LOGIN, false)
        } else {
            islogin = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_spalsh)

        loadPreferences()

        Handler().postDelayed({
            // This method will be executed once the timer is over
            // Start your app main activity
            Log.d("statuslogin", islogin.toString())

            if (islogin) {
                if(sharedPreferences!!.getString(Config.ROLE, "") == "komite"||sharedPreferences!!.getString(Config.ROLE, "") == "supervisor"){
                    val edit = sharedPreferences!!.edit()
                    edit.putString("status", "cancel")
                    edit.apply()
                    startActivity(Intent(applicationContext, InformasiKreditActivity::class.java))
                    finish()
                }else{
                    val i = Intent(applicationContext, MenuActivity::class.java)
                    startActivity(i)
                    finish()
                }
            } else {
                val i = Intent(applicationContext, MainActivity::class.java)
                startActivity(i)

                finish()
            }
        }, 2000)
    }
}
