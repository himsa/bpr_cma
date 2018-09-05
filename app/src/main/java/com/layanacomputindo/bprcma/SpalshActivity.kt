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
import android.widget.Toast
import com.google.firebase.iid.FirebaseInstanceId
import com.google.gson.Gson
import com.layanacomputindo.bprcma.model.Result
import com.layanacomputindo.bprcma.model.Token
import com.layanacomputindo.bprcma.rest.RestClient
import com.layanacomputindo.bprcma.util.Config
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
                    val refreshedToken = FirebaseInstanceId.getInstance().token
                    Log.e("Token", "Refreshed token: " + refreshedToken!!)
                    sendFCM(refreshedToken)
                    startActivity(Intent(applicationContext, AprovalListActivity::class.java))
                    finish()
                }else{
                    sendFCM(" ")
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

    private fun sendFCM(token: String) {
        val service by lazy {
            RestClient.getClient(this)
        }
        val call = service.updateFCM(token)

        call.enqueue(object : Callback<Result<Token>> {
            override fun onResponse(call: Call<Result<Token>>, response: Response<Result<Token>>) {
                Log.d("FCM", "Status Code = " + response.code())
                if (response.isSuccessful()) {
                    val result = response.body()
                    Log.e("FCM", "response = " + Gson().toJson(result))

                } else {
                    Log.e("FCM", response.raw().toString())
                }
            }

            override fun onFailure(call: Call<Result<Token>>, t: Throwable) {
                Log.e("on Failure", t.toString())
                Toast.makeText(applicationContext, R.string.cekkoneksi, Toast.LENGTH_LONG).show()
            }
        })
    }
}
