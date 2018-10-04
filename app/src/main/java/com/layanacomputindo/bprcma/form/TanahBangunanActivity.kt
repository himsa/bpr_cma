package com.layanacomputindo.bprcma.form

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import com.layanacomputindo.bprcma.R
import com.layanacomputindo.bprcma.model.Result
import com.layanacomputindo.bprcma.model.TanahBangunan
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
    var idJaminan = 0
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
        if(sharedPreferences.getString("from", "") == "repeat"){
            pDialog = ProgressDialog.show(this,
                    "",
                    "Tunggu Sebentar!")
            getData()
        }

        rg_hk_ats_tnh.setOnCheckedChangeListener(this)

        btn_next_tanah_bangunan.setOnClickListener(this)
    }

    private fun getData() {
        val service by lazy {
            RestClient.getClient(this)
        }
        val call = service.getJaminanTanah(idJaminan)
        call.enqueue(object: Callback<Result<TanahBangunan>>{
            override fun onFailure(call: Call<Result<TanahBangunan>>?, t: Throwable?) {
                pDialog!!.dismiss()
                Log.e("on Failure", t.toString())
                Toast.makeText(applicationContext, R.string.cekkoneksi, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<Result<TanahBangunan>>, response: Response<Result<TanahBangunan>>) {
                Log.d("tanah", "Status Code = " + response.code())
                if(response.isSuccessful){
                    val result = response.body()
                    if (result != null) {
                        if (result.getStatus()!!) {
                            val data = result.getData()
                            pDialog!!.dismiss()
                            if (data != null){
                                if(data.getHakAtasTanah().equals("SHGM", true)){
                                    rb_shgm.isChecked = true
                                }else{
                                    rb_shm.isChecked = true
                                }
                                et_nomor_hak.setText(data.getNomorHak())
                                et_nomor_su.setText(data.getNoSuTanggalSu())
                                et_an_pmg_hak.setText(data.getAtasNamaPemegangHak())
                                et_tgl_brkr_hak.setText(data.getTanggalBerakhirHak())
                                et_nmr_si_m.setText(data.getNoSuratIzinMembangun())
                                et_nmr_nib_tanah.setText(data.getNoNibTanah())
                                et_asl_hk_ats_tnh.setText(data.getAsalHakAtasTanah())
                                et_rwt_sngkt_tnh.setText(data.getRiwayatSingkatTanah())
                            }

                        } else {
                            Log.e("tanah", response.raw().toString())
                            Toast.makeText(baseContext, result.getMessage(), Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

        })
    }

    private fun setPreferences() {
        sharedPreferences = getSharedPreferences(Config.PREF_NAME,
                Activity.MODE_PRIVATE)
        idKredit = sharedPreferences.getInt(Config.KREDIT_ID, Config.EMPTY_INT)
        idJaminan = sharedPreferences.getInt(Config.JAMINAN_ID, Config.EMPTY_INT)
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
                if(sharedPreferences.getString(Config.ROLE, "")== "komite"||sharedPreferences.getString(Config.ROLE, "")== "supervisor"){
                    startActivity(Intent(applicationContext, KeadaanTanahActivity::class.java))
                }else{
                    next()
                }
            }
        }
    }

    private fun next() {
        if(sharedPreferences.getString("from", "") == "repeat"){
            val radioButton = findViewById(rg_hk_ats_tnh.checkedRadioButtonId) as RadioButton
            uploadHkAtsTnh = radioButton.text.toString()
            if(rg_hk_ats_tnh.checkedRadioButtonId == R.id.rb_shgm){
                uploadHkAtsTnh = "SHGM"
            }else{
                uploadHkAtsTnh = "SHM"
            }
        }
        pDialog = ProgressDialog.show(this,
                "",
                "Tunggu Sebentar!")
        if(uploadHkAtsTnh !="" && et_nomor_hak.text.toString() != "" && et_nomor_su.text.toString() != "" &&
                et_an_pmg_hak.text.toString() != "" && et_tgl_brkr_hak.text.toString() != "" &&
                et_nmr_si_m.text.toString() != "" && et_nmr_nib_tanah.text.toString() != "" && et_asl_hk_ats_tnh.text.toString() != "" &&
                et_rwt_sngkt_tnh.text.toString() != ""){
            if(sharedPreferences.getString("from", "") == "repeat"){
                updateData()
            }else{
                submitData()
            }
        }else{
            pDialog!!.dismiss()
            Toast.makeText(applicationContext, R.string.cekData, Toast.LENGTH_LONG).show()
        }
    }

    private fun updateData() {
        val service by lazy {
            RestClient.getClient(this)
        }
        val call = service.updateJaminanTanah(idJaminan, uploadHkAtsTnh, et_nomor_hak.text.toString(),
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
                            startActivity(Intent(applicationContext, KeadaanTanahActivity::class.java))
                        } else {
                            pDialog!!.dismiss()
                            Log.e("TanahBangunan", response.raw().toString())
                            Toast.makeText(baseContext, result.getMessage(), Toast.LENGTH_SHORT).show()
                        }
                    }else{
                        pDialog!!.dismiss()
                        Log.e("TanahBangunan", response.raw().toString())
                        Toast.makeText(baseContext, response.message(), Toast.LENGTH_SHORT).show()
                    }
                }
            }

        })
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
