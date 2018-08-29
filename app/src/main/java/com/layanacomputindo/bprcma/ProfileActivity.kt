package com.layanacomputindo.bprcma

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.layanacomputindo.bprcma.model.Result
import com.layanacomputindo.bprcma.model.User
import com.layanacomputindo.bprcma.rest.RestClient
import com.layanacomputindo.bprcma.util.Config
import kotlinx.android.synthetic.main.activity_profile.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.log

class ProfileActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var back: ImageButton
    lateinit var logout: TextView
    lateinit var about: TextView
    private var sharedPreferences: SharedPreferences? = null
    private var pDialog: ProgressDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_profile)
        sharedPreferences = getSharedPreferences(Config.PREF_NAME,
                Activity.MODE_PRIVATE)
        back = back_profile
        logout = txt_log_out
        about = txt_about

        back.setOnClickListener(this)
        logout.setOnClickListener(this)
        about.setOnClickListener(this)

        pDialog = ProgressDialog.show(this,
                "",
                "Tunggu Sebentar!")

        getData()
    }

    private fun getData() {
        val service by lazy {
            RestClient.getClient(this)
        }
        val call = service.getUser()
        call.enqueue(object : Callback<Result<User>>{
            override fun onFailure(call: Call<Result<User>>?, t: Throwable?) {
                pDialog!!.dismiss()
                Log.e("on Failure", t.toString())
                Toast.makeText(applicationContext, R.string.cekkoneksi, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<Result<User>>, response: Response<Result<User>>) {
                Log.d("tmp tggl", "Status Code = " + response.code())
                if(response.isSuccessful){
                    pDialog!!.dismiss()
                    val result = response.body()
                    if (result != null) {
                        if (result.getStatus()!!) {
                            tv_user_name.text = result.getData()!!.getName()
                            tv_user_email.text = result.getData()!!.getEmail()
                            tv_user_alamat.text = ""
                        } else {
                            Log.e("debitur", response.raw().toString())
                            Toast.makeText(baseContext, result.getMessage(), Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

        })
    }

    override fun onClick(p0: View) {
        when(p0.id) {
            R.id.back_profile -> {
                onBackPressed()
            }
            R.id.txt_log_out -> {
                val editor = sharedPreferences!!.edit()
                editor.putBoolean(Config.IS_LOGIN, false)
                editor.apply()
                val i = Intent(this,SpalshActivity::class.java)
                startActivity(i)
                finishAffinity()
            }
            R.id.txt_about -> {
                val i = Intent(this,AboutActivity::class.java)
                startActivity(i)
            }
        }
    }
}
