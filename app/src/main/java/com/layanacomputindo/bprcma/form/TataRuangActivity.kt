package com.layanacomputindo.bprcma.form

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.layanacomputindo.bprcma.R
import com.layanacomputindo.bprcma.model.Result
import com.layanacomputindo.bprcma.model.UserId
import com.layanacomputindo.bprcma.rest.RestClient
import com.layanacomputindo.bprcma.util.Config
import kotlinx.android.synthetic.main.activity_tata_ruang.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TataRuangActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var sharedPreferences: SharedPreferences
    var idJaminan = 0
    private var pDialog: ProgressDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tata_ruang)
        setSupportActionBar(toolbar as Toolbar?)
        supportActionBar?.run {
            title = getString(R.string.pemr_jaminan)
            setDisplayHomeAsUpEnabled(true)
        }
        setPreferences()

        btn_next_tata_ruang.setOnClickListener(this)
    }

    private fun setPreferences() {
        sharedPreferences = getSharedPreferences(Config.PREF_NAME,
                Activity.MODE_PRIVATE)
        idJaminan = sharedPreferences.getInt(Config.JAMINAN_ID, Config.EMPTY_INT)
    }

    override fun onClick(p0: View) {
        when(p0.id){
            R.id.btn_next_tata_ruang -> {
                if(sharedPreferences.getString(Config.ROLE, "")== "komite"||sharedPreferences.getString(Config.ROLE, "")== "supervisor"){
                    startActivity(Intent(applicationContext, FasilitasUmumActivity::class.java))
                }else{
                    pDialog = ProgressDialog.show(this,
                            "",
                            "Tunggu Sebentar!")
                    submitData()
                }
            }
        }
    }

    private fun submitData() {
        val service by lazy {
            RestClient.getClient(this)
        }
        val call = service.sendTataRuang(idJaminan, cb_1_kmr_tdr.isChecked, cb_2_kmr_tdr.isChecked, cb_3_kmr_tdr.isChecked,
                cb_1_kmr_mnd.isChecked, cb_2_kmr_mnd.isChecked, cb_3_kmr_mnd.isChecked, cb_1_r_mkn.isChecked,
                cb_2_r_mkn.isChecked, cb_3_r_mkn.isChecked, cb_1_r_tm.isChecked, cb_2_r_tm.isChecked, cb_3_r_tm.isChecked,
                cb_1_dpr.isChecked, cb_2_dpr.isChecked, cb_3_dpr.isChecked, cb_1_grs.isChecked, cb_2_grs.isChecked, cb_3_grs.isChecked,
                cb_1_k_pmbnt.isChecked, cb_2_k_pmbnt.isChecked, cb_3_k_pmbnt.isChecked)
        call.enqueue(object : Callback<Result<UserId>> {
            override fun onFailure(call: Call<Result<UserId>>?, t: Throwable?) {
                pDialog!!.dismiss()
                Log.e("on Failure", t.toString())
                Toast.makeText(applicationContext, R.string.cekkoneksi, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<Result<UserId>>, response: Response<Result<UserId>>) {
                Log.d("TataRuang", "Status Code = " + response.code())
                if(response.isSuccessful){
                    val result = response.body()
                    if (result != null) {
                        if (result.getStatus()!!) {
                            sendFasilitas()
                        } else {
                            Log.e("TataRuang", response.raw().toString())
                            Toast.makeText(baseContext, result.getMessage(), Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

        })
    }

    private fun sendFasilitas() {
        val service by lazy {
            RestClient.getClient(this)
        }
        val call = service.sendTanahFasilitas(idJaminan, Integer.parseInt(et_slrn_lstrk.text.toString()), Integer.parseInt(et_slrn_lstrk_v.text.toString()),
                cb_pdam.isChecked, cb_pmp_lstrk.isChecked)
        call.enqueue(object : Callback<Result<UserId>> {
            override fun onFailure(call: Call<Result<UserId>>?, t: Throwable?) {
                pDialog!!.dismiss()
                Log.e("on Failure", t.toString())
                Toast.makeText(applicationContext, R.string.cekkoneksi, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<Result<UserId>>, response: Response<Result<UserId>>) {
                Log.d("TataRuang", "Status Code = " + response.code())
                if(response.isSuccessful){
                    val result = response.body()
                    if (result != null) {
                        if (result.getStatus()!!) {
                            pDialog!!.dismiss()
                            startActivity(Intent(applicationContext, FasilitasUmumActivity::class.java))
                        } else {
                            Log.e("TataRuang", response.raw().toString())
                            Toast.makeText(baseContext, result.getMessage(), Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return true
    }
}
