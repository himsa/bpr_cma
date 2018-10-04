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
import android.widget.Toast
import com.layanacomputindo.bprcma.R
import com.layanacomputindo.bprcma.model.Fasilitas
import com.layanacomputindo.bprcma.model.Result
import com.layanacomputindo.bprcma.model.TataRuang
import com.layanacomputindo.bprcma.model.UserId
import com.layanacomputindo.bprcma.rest.RestClient
import com.layanacomputindo.bprcma.util.Config
import kotlinx.android.synthetic.main.activity_tata_ruang.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.layanacomputindo.bprcma.R.id.b



class TataRuangActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var sharedPreferences: SharedPreferences
    var idJaminan = 0
    var int = 0
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
        if(sharedPreferences.getString("from", "") == "repeat"){
            pDialog = ProgressDialog.show(this,
                    "",
                    "Tunggu Sebentar!")
            getData()
            getSaluranListrik()
        }

        btn_next_tata_ruang.setOnClickListener(this)
    }

    private fun getSaluranListrik() {
        val service by lazy {
            RestClient.getClient(this)
        }
        val call = service.getJaminanTanahFasilitas(idJaminan)
        call.enqueue(object: Callback<Result<Fasilitas>>{
            override fun onFailure(call: Call<Result<Fasilitas>>?, t: Throwable?) {
                pDialog!!.dismiss()
                Log.e("on Failure", t.toString())
                Toast.makeText(applicationContext, R.string.cekkoneksi, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<Result<Fasilitas>>, response: Response<Result<Fasilitas>>) {
                Log.d("tanah", "Status Code = " + response.code())
                if(response.isSuccessful){
                    val result = response.body()
                    if (result != null) {
                        if (result.getStatus()!!) {
                            val data = result.getData()
                            pDialog!!.dismiss()
                            if (data != null){
                                et_slrn_lstrk.setText(data.getSaluranListrikWatt().toString())
                                et_slrn_lstrk_v.setText(data.getSaluranListrikVolt().toString())
                                et_slrn_tlp.setText(data.getSaluranTelepon())
                                if(data.getSumberAir()!!.contains("pompa listrik", true)){
                                    cb_pmp_lstrk.isChecked = true
                                }else{
                                    cb_pdam.isChecked = true
                                }
                            }
                        } else {
                            pDialog!!.dismiss()
                            Log.e("tanah", response.raw().toString())
                            Toast.makeText(baseContext, result.getMessage(), Toast.LENGTH_SHORT).show()
                        }
                    }
                }else {
                    pDialog!!.dismiss()
                    Log.e("tanah", response.raw().toString())
                    Toast.makeText(baseContext, response.message(), Toast.LENGTH_SHORT).show()
                }
            }

        })
    }

    private fun getData() {
        val service by lazy {
            RestClient.getClient(this)
        }
        val call = service.getJaminanTanahTataRuang(idJaminan)
        call.enqueue(object: Callback<Result<TataRuang>>{
            override fun onFailure(call: Call<Result<TataRuang>>?, t: Throwable?) {
                pDialog!!.dismiss()
                Log.e("on Failure", t.toString())
                Toast.makeText(applicationContext, R.string.cekkoneksi, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<Result<TataRuang>>, response: Response<Result<TataRuang>>) {
                Log.d("tanah", "Status Code = " + response.code())
                if(response.isSuccessful){
                    val result = response.body()
                    if (result != null) {
                        if (result.getStatus()!!) {
                            val data = result.getData()
                            pDialog!!.dismiss()
                            if (data != null){
                                cb_1_kmr_tdr.isChecked = set(data.getKamarTidurLantai1())
                                cb_2_kmr_tdr.isChecked = set(data.getKamarTidurLantai2())
                                cb_3_kmr_tdr.isChecked = set(data.getKamarTidurLantai3())
                                cb_1_kmr_mnd.isChecked = set(data.getKamarMandiLantai1())
                                cb_2_kmr_mnd.isChecked = set(data.getKamarMandiLantai2())
                                cb_3_kmr_mnd.isChecked = set(data.getKamarMandiLantai3())
                                cb_1_r_mkn.isChecked = set(data.getRuangMakanKeluargaLantai1())
                                cb_2_r_mkn.isChecked = set(data.getRuangMakanKeluargaLantai2())
                                cb_3_r_mkn.isChecked = set(data.getRuangMakanKeluargaLantai3())
                                cb_1_r_tm.isChecked = set(data.getRuangTamuLantai1())
                                cb_2_r_tm.isChecked = set(data.getRuangTamuLantai2())
                                cb_3_r_tm.isChecked = set(data.getRuangTamuLantai3())
                                cb_1_dpr.isChecked = set(data.getDapurLantai1())
                                cb_2_dpr.isChecked = set(data.getDapurLantai2())
                                cb_3_dpr.isChecked = set(data.getDapurLantai3())
                                cb_1_grs.isChecked = set(data.getGarasiLantai1())
                                cb_2_grs.isChecked = set(data.getGarasiLantai2())
                                cb_3_grs.isChecked = set(data.getGarasiLantai3())
                                cb_1_k_pmbnt.isChecked = set(data.getKamarPembantuLantai1())
                                cb_2_k_pmbnt.isChecked = set(data.getKamarPembantuLantai2())
                                cb_3_k_pmbnt.isChecked = set(data.getKamarPembantuLantai3())
                            }
                        } else {
                            pDialog!!.dismiss()
                            Log.e("tanah", response.raw().toString())
                            Toast.makeText(baseContext, result.getMessage(), Toast.LENGTH_SHORT).show()
                        }
                    }
                }else {
                    pDialog!!.dismiss()
                    Log.e("tanah", response.raw().toString())
                    Toast.makeText(baseContext, response.message(), Toast.LENGTH_SHORT).show()
                }
            }

        })
    }

    private fun set(data: Any?): Boolean {
        return data.toString().equals("1", true)
    }

    private fun setdata(data: Boolean): Int {
        return if (data)  1 else 0
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
                    next()
                }
            }
        }
    }

    private fun next() {
        pDialog = ProgressDialog.show(this,
                "",
                "Tunggu Sebentar!")
        if (et_slrn_lstrk.text.toString() != "" && et_slrn_lstrk_v.text.toString() !=""){
            submitData()
        }else{
            pDialog!!.dismiss()
            Toast.makeText(applicationContext, R.string.cekData, Toast.LENGTH_LONG).show()
        }

    }

    private fun submitData() {
        val service by lazy {
            RestClient.getClient(this)
        }
        val call = service.sendTataRuang(idJaminan, setdata(cb_1_kmr_tdr.isChecked), setdata(cb_2_kmr_tdr.isChecked), setdata(cb_3_kmr_tdr.isChecked),
                setdata(cb_1_kmr_mnd.isChecked), setdata(cb_2_kmr_mnd.isChecked), setdata(cb_3_kmr_mnd.isChecked), setdata(cb_1_r_mkn.isChecked),
                setdata(cb_2_r_mkn.isChecked), setdata(cb_3_r_mkn.isChecked), setdata(cb_1_r_tm.isChecked), setdata(cb_2_r_tm.isChecked),
                setdata(cb_3_r_tm.isChecked), setdata(cb_1_dpr.isChecked), setdata(cb_2_dpr.isChecked), setdata(cb_3_dpr.isChecked),
                setdata(cb_1_grs.isChecked), setdata(cb_2_grs.isChecked), setdata(cb_3_grs.isChecked),
                setdata(cb_1_k_pmbnt.isChecked), setdata(cb_2_k_pmbnt.isChecked), setdata(cb_3_k_pmbnt.isChecked),
                Integer.parseInt(et_slrn_lstrk.text.toString()), Integer.parseInt(et_slrn_lstrk_v.text.toString()),
                setdata(cb_pdam.isChecked), setdata(cb_pmp_lstrk.isChecked))
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
                            pDialog!!.dismiss()
                            Log.e("TataRuang", response.raw().toString())
                            Toast.makeText(baseContext, result.getMessage(), Toast.LENGTH_SHORT).show()
                        }
                    }
                }else {
                    pDialog!!.dismiss()
                    Log.e("TataRuang", response.raw().toString())
                    Toast.makeText(baseContext, response.message(), Toast.LENGTH_SHORT).show()
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
