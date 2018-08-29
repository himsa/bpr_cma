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
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import com.layanacomputindo.bprcma.R
import com.layanacomputindo.bprcma.model.Result
import com.layanacomputindo.bprcma.model.UserId
import com.layanacomputindo.bprcma.rest.RestClient
import com.layanacomputindo.bprcma.util.Config
import im.delight.android.location.SimpleLocation
import kotlinx.android.synthetic.main.activity_tanah_bangunan.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TanahBangunanActivity : AppCompatActivity(), View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    var uploadHkAtsTnh = ""

    private lateinit var sharedPreferences: SharedPreferences
    var idKredit = 0
    private var pDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tanah_bangunan)
        setSupportActionBar(toolbar as Toolbar?)
        supportActionBar?.run {
            title = getString(R.string.pemr_jaminan)
            setDisplayHomeAsUpEnabled(true)
        }

        setPreferences()

        rg_hk_ats_tnh.setOnCheckedChangeListener(this)

        btn_next_tanah_bangunan.setOnClickListener(this)
    }

    private fun setPreferences() {
        sharedPreferences = getSharedPreferences(Config.PREF_NAME,
                Activity.MODE_PRIVATE)
        idKredit = sharedPreferences.getInt(Config.KREDIT_ID, Config.EMPTY_INT)
    }

    override fun onCheckedChanged(p0: RadioGroup?, p1: Int) {
        when(p1){
            R.id.rb_shm -> {
                val radioButton = findViewById(R.id.rb_shm) as RadioButton

                uploadHkAtsTnh = radioButton.text.toString()
            }
            R.id.rb_shgm -> {
                val radioButton = findViewById(R.id.rb_shgm) as RadioButton

                uploadHkAtsTnh = radioButton.text.toString()
            }
        }
    }

    override fun onClick(p0: View) {
        when(p0.id){
            R.id.btn_next_tanah_bangunan -> {
//                pDialog = ProgressDialog.show(this,
//                        "",
//                        "Tunggu Sebentar!")
//                submitData()
                startActivity(Intent(applicationContext, KeadaanTanahActivity::class.java))
            }
        }
    }

    private fun submitData() {
        val service by lazy {
            RestClient.getClient(this)
        }
        val call = service.sendJaminanTanah(idKredit, uploadHkAtsTnh, et_nomor_hak.text.toString(),
                et_nomor_su.text.toString(),et_an_pmg_hak.text.toString(),et_tgl_brkr_hak.text.toString(),
                et_nmr_si_m.text.toString(),et_nmr_nib_tanah.text.toString(), et_asl_hk_ats_tnh.text.toString(),
                et_rwt_sngkt_tnh.text.toString())
        call.enqueue(object : Callback<Result<UserId>>{
            override fun onFailure(call: Call<Result<UserId>>?, t: Throwable?) {
                pDialog!!.dismiss()
                Log.e("on Failure", t.toString())
                Toast.makeText(applicationContext, R.string.cekkoneksi, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<Result<UserId>>, response: Response<Result<UserId>>) {
                Log.d("TanahBangunan", "Status Code = " + response.code())
                if(response.isSuccessful){
                    val result = response.body()
                    if (result != null) {
                        if (result.getStatus()!!) {
                            pDialog!!.dismiss()
                            val editor = sharedPreferences.edit()
                            editor.putInt(Config.JAMINAN_ID, result.getData()!!.id)
                            editor.apply()
                            startActivity(Intent(applicationContext, KeadaanTanahActivity::class.java))
                        } else {
                            Log.e("TanahBangunan", response.raw().toString())
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
