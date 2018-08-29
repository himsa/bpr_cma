package com.layanacomputindo.bprcma

import android.app.Activity
import android.util.Log
import android.widget.Toast
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService
import com.google.gson.Gson
import com.layanacomputindo.bprcma.rest.RestClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MyFirebaseInstanceIDService : FirebaseInstanceIdService() {

    override fun onTokenRefresh() {

        val refreshedToken = FirebaseInstanceId.getInstance().token
        Log.e("Token", "Refreshed token: " + refreshedToken!!)

        sendRegistrationToServer(refreshedToken)

    }

    private fun sendRegistrationToServer(token: String?) {
//        val sharedPreferences = getSharedPreferences("shared", Activity.MODE_PRIVATE)
//        val islogin = sharedPreferences.getBoolean("islogin", false)
//
//        Log.d("statuslogin", islogin.toString())
//        if (islogin) {
//            val service by lazy {
//                RestClient.getClient(this)
//            }
//            val call = service.updateFcm(token)
//
//            call.enqueue(object : Callback<Result> {
//                override fun onResponse(call: Call<Result>, response: Response<Result>) {
//                    Log.d("FCM", "Status Code = " + response.code())
//                    if (response.isSuccessful()) {
//                        val result = response.body()
//                        Log.e("FCM", "response = " + Gson().toJson(result))
//
//                    } else {
//                        Log.e("FCM", response.raw().toString())
//                    }
//                }
//
//                override fun onFailure(call: Call<Result>, t: Throwable) {
//                    Log.e("on Failure", t.toString())
//                    Toast.makeText(applicationContext, R.string.cekkoneksi, Toast.LENGTH_LONG).show()
//                }
//            })
//        } else {
//
//        }
    }
}