package com.layanacomputindo.bprcma

import android.app.Activity
import android.util.Log
import android.widget.Toast
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService
import com.google.gson.Gson
import com.layanacomputindo.bprcma.model.Result
import com.layanacomputindo.bprcma.model.Token
import com.layanacomputindo.bprcma.rest.RestClient
import com.layanacomputindo.bprcma.util.Config
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MyFirebaseInstanceIDService : FirebaseInstanceIdService() {

    override fun onTokenRefresh() {

        val refreshedToken = FirebaseInstanceId.getInstance().token
        Log.e("Token", "Refreshed token: " + refreshedToken!!)

        sendRegistrationToServer(refreshedToken)

    }

    private fun sendRegistrationToServer(token: String) {
        val sharedPreferences = getSharedPreferences(Config.PREF_NAME,
                Activity.MODE_PRIVATE)
        val islogin = sharedPreferences.getBoolean(Config.IS_LOGIN, false)
        val role = sharedPreferences.getString(Config.ROLE, "")
        Log.d("statuslogin", islogin.toString())
        if (islogin) {
            if(role == "komite"||role == "supervisor"){
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
            }else {
                val service by lazy {
                    RestClient.getClient(this)
                }
                val call = service.updateFCM(" ")

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
        } else {

        }
    }
}