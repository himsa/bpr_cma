package com.layanacomputindo.bprcma

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.layanacomputindo.bprcma.model.Result
import com.layanacomputindo.bprcma.model.Token
import com.layanacomputindo.bprcma.rest.RestClient
import com.layanacomputindo.bprcma.util.Config
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var btnLogin : Button
    private var pDialog: ProgressDialog? = null
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_main)

        sharedPreferences = getSharedPreferences(Config.PREF_NAME,
                Activity.MODE_PRIVATE)

        btnLogin = btn_login
        btnLogin.setOnClickListener(this)
    }

    override fun onClick(p0: View) {
        when(p0.id){
            R.id.btn_login -> {
                pDialog = ProgressDialog.show(this,
                        "",
                        "Tunggu Sebentar!")
                doLogin(et_username.text.toString(), et_password.text.toString())
            }
        }
    }

    private fun doLogin(mEmail: String, mPassword: String) {
        val service by lazy {
            RestClient.getClient(this)
        }
        val call = service.getToken(mEmail, mPassword)
        call.enqueue(object : Callback<Result<Token>> {
            override fun onResponse(call: Call<Result<Token>>, response: Response<Result<Token>>) {
                Log.d("Login", "Status Code = " + response.code())
                if(response.isSuccessful){
                    pDialog!!.dismiss()
                    val result = response.body()
                    if (result != null) {
                        if (result.getStatus()!!) {
                            val data = result.getData()
                            Log.d("Login", "response = " + Gson().toJson(result))
                            val editor = sharedPreferences.edit()
                            editor.putString(Config.USER_TOKEN, data!!.token)
                            editor.putInt(Config.USER_ID, data.user_id)
                            editor.putString(Config.ROLE, data.role)
                            editor.putBoolean(Config.IS_LOGIN, true)
                            editor.apply()
                            if (data.role == "komite"||data.role == "supervisor"){
                                startActivity(Intent(this@MainActivity, AprovalListActivity::class.java))
                                finish()
                            }else{
                                startActivity(Intent(this@MainActivity, MenuActivity::class.java))
                                finish()
                            }
                            Log.d("Login", "Token: " + data.token)
                            Log.d("Login", "saveToken: " + sharedPreferences.getString(Config.USER_TOKEN, Config.EMPTY))

                        } else {
                            Log.e("Login", response.raw().toString())
                            Toast.makeText(baseContext, "Username/Password anda salah", Toast.LENGTH_SHORT).show()
                        }
                    }
                }else{
                    pDialog!!.dismiss()
                    Log.e("Login", response.raw().toString())
                    Toast.makeText(baseContext, "Username/Password anda salah", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Result<Token>>, t: Throwable) {
                pDialog!!.dismiss()
                Log.e("on Failure", t.toString())
                Toast.makeText(applicationContext, R.string.cekkoneksi, Toast.LENGTH_LONG).show()
            }
        })
    }
}
