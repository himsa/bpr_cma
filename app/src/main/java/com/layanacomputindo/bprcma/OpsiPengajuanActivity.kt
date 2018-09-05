package com.layanacomputindo.bprcma

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.gson.Gson
import com.layanacomputindo.bprcma.form.InfoNasabahPart1Activity
import com.layanacomputindo.bprcma.model.Result
import com.layanacomputindo.bprcma.model.UserId
import com.layanacomputindo.bprcma.rest.RestClient
import com.layanacomputindo.bprcma.util.Config
import kotlinx.android.synthetic.main.activity_opsi_pengajuan.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OpsiPengajuanActivity : AppCompatActivity(), View.OnClickListener {
    private var pDialog: ProgressDialog? = null

    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_opsi_pengajuan)

        sharedPreferences = getSharedPreferences(Config.PREF_NAME,
                Activity.MODE_PRIVATE)

        op_repeat.setOnClickListener(this)
        op_new.setOnClickListener(this)
    }

    override fun onClick(p0: View) {
        when(p0.id){
            R.id.op_new -> {
                pDialog = ProgressDialog.show(this,
                        "",
                        "Tunggu Sebentar!")
                doNewUser()
                val editor = sharedPreferences.edit()
                editor.putString("from", "new")
                editor.apply()
            }
            R.id.op_repeat -> {
                val intent = Intent(this, InformasiKreditActivity::class.java)
                val editor = sharedPreferences.edit()
                editor.putString("from", "repeat")
                editor.putString("status", "disetujui")
                editor.apply()
                startActivity(intent)
            }
        }
    }

    private fun doNewUser() {
//        val idUser = sharedPreferences.getInt(Config.USER_ID, Config.EMPTY_INT)
        val service by lazy {
            RestClient.getClient(this)
        }
        val call = service.sendNewDebitur()
        call.enqueue(object : Callback<Result<UserId>> {
            override fun onResponse(call: Call<Result<UserId>>, response: Response<Result<UserId>>) {
                Log.d("userId", "Status Code = " + response.code())
                if(response.isSuccessful){
                    pDialog!!.dismiss()
                    val result = response.body()
                    if (result != null) {
                        if (result.getStatus()!!) {
                            val data = result.getData()
                            if (data != null){
                                Log.d("userId", "response = " + Gson().toJson(result))
                                val editor = sharedPreferences.edit()
                                editor.putInt(Config.DEBITUR_ID, data.id)
                                editor.putString("from", "main")
                                editor.apply()
                                startActivity(Intent(applicationContext, InfoNasabahPart1Activity::class.java))
                            } else {
                                Log.e("userId", response.raw().toString())
                                Toast.makeText(baseContext, R.string.no_data, Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Log.e("userId", response.raw().toString())
                            Toast.makeText(baseContext, result.getMessage(), Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

            override fun onFailure(call: Call<Result<UserId>>?, t: Throwable?) {
                pDialog!!.dismiss()
                Log.e("on Failure", t.toString())
                Toast.makeText(applicationContext, R.string.cekkoneksi, Toast.LENGTH_LONG).show()
            }

        })
    }
}
