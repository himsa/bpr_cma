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
import com.layanacomputindo.bprcma.util.RupiahTextWatcher
import kotlinx.android.synthetic.main.activity_sumber_harga_tanah.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SumberHargaTanahActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var sharedPreferences: SharedPreferences
    var idJaminan = 0

    private var pDialog: ProgressDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sumber_harga_tanah)
        setSupportActionBar(toolbar as Toolbar?)
        supportActionBar?.run {
            title = getString(R.string.pemr_jaminan)
            setDisplayHomeAsUpEnabled(true)
        }

        setPreferences()

        et_kntr_pmsrn.addTextChangedListener(RupiahTextWatcher(et_kntr_pmsrn))
        et_kntr_ntrs_stmpt.addTextChangedListener(RupiahTextWatcher(et_kntr_ntrs_stmpt))
        et_masy_sktr.addTextChangedListener(RupiahTextWatcher(et_masy_sktr))
        et_njob.addTextChangedListener(RupiahTextWatcher(et_njob))
        et_tksrn_pnilai.addTextChangedListener(RupiahTextWatcher(et_tksrn_pnilai))
        et_pngmbng.addTextChangedListener(RupiahTextWatcher(et_pngmbng))
        et_tksrn_pnilai_bank.addTextChangedListener(RupiahTextWatcher(et_tksrn_pnilai_bank))

        btn_next_sbr_hrg_tnh.setOnClickListener(this)
    }

    private fun setPreferences() {
        sharedPreferences = getSharedPreferences(Config.PREF_NAME,
                Activity.MODE_PRIVATE)
        idJaminan = sharedPreferences.getInt(Config.JAMINAN_ID, Config.EMPTY_INT)
    }

    override fun onClick(p0: View) {
        when(p0.id){
            R.id.btn_next_sbr_hrg_tnh -> {
                if(sharedPreferences.getString(Config.ROLE, "")== "komite"||sharedPreferences.getString(Config.ROLE, "")== "supervisor"){
                    startActivity(Intent(applicationContext, PenilaianKesimpulanActivity::class.java))
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
        val call = service.sendSumberHargaTanah(idJaminan, et_kntr_pmsrn.getText().toString().replace("[$,.]".toRegex(), "").toInt(),
                et_kntr_ntrs_stmpt.getText().toString().replace("[$,.]".toRegex(), "").toInt(),
                et_masy_sktr.getText().toString().replace("[$,.]".toRegex(), "").toInt(),
                et_njob.getText().toString().replace("[$,.]".toRegex(), "").toInt(),
                et_tksrn_pnilai.getText().toString().replace("[$,.]".toRegex(), "").toInt())
        call.enqueue(object : Callback<Result<UserId>> {
            override fun onFailure(call: Call<Result<UserId>>?, t: Throwable?) {
                pDialog!!.dismiss()
                Log.e("on Failure", t.toString())
                Toast.makeText(applicationContext, R.string.cekkoneksi, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<Result<UserId>>, response: Response<Result<UserId>>) {
                Log.d("FaktorPenilaian", "Status Code = " + response.code())
                if(response.isSuccessful){
                    val result = response.body()
                    if (result != null) {
                        if (result.getStatus()!!) {
                            sendHargaBangunan()
                        } else {
                            Log.e("FaktorPenilaian", response.raw().toString())
                            Toast.makeText(baseContext, result.getMessage(), Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

        })
    }

    private fun sendHargaBangunan() {
        val service by lazy {
            RestClient.getClient(this)
        }
        val call = service.sendSumberHargaBangunan(idJaminan, et_pngmbng.getText().toString().replace("[$,.]".toRegex(), "").toInt(),
                et_tksrn_pnilai_bank.getText().toString().replace("[$,.]".toRegex(), "").toInt())
        call.enqueue(object : Callback<Result<UserId>> {
            override fun onFailure(call: Call<Result<UserId>>?, t: Throwable?) {
                pDialog!!.dismiss()
                Log.e("on Failure", t.toString())
                Toast.makeText(applicationContext, R.string.cekkoneksi, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<Result<UserId>>, response: Response<Result<UserId>>) {
                Log.d("FaktorPenilaian", "Status Code = " + response.code())
                if(response.isSuccessful){
                    val result = response.body()
                    if (result != null) {
                        if (result.getStatus()!!) {
                            pDialog!!.dismiss()
                            startActivity(Intent(applicationContext, PenilaianKesimpulanActivity::class.java))
                        } else {
                            Log.e("FaktorPenilaian", response.raw().toString())
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
