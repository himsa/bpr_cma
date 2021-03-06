package com.layanacomputindo.bprcma.form

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.layanacomputindo.bprcma.R
import com.layanacomputindo.bprcma.model.Result
import com.layanacomputindo.bprcma.model.TanahBangunan
import com.layanacomputindo.bprcma.model.UserId
import com.layanacomputindo.bprcma.rest.RestClient
import com.layanacomputindo.bprcma.util.Config
import kotlinx.android.synthetic.main.activity_keadaan_bangunan.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class KeadaanBangunanActivity : AppCompatActivity(), RadioGroup.OnCheckedChangeListener, View.OnClickListener {
    private lateinit var sharedPreferences: SharedPreferences
    var idJaminan = 0
    private var pDialog: ProgressDialog? = null

    var uploadJenis = ""
    var uploadKonstruksi = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_keadaan_bangunan)
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

        m2.setText(Html.fromHtml("m<sup><small>2</small></sup>"))

        rg_jns_bngn.setOnCheckedChangeListener(this)
        rg_konstruksi.setOnCheckedChangeListener(this)

        btn_next_keadaan_bangunan.setOnClickListener(this)
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
                                et_th_dibangun.setText(data.getTahunDibangun().toString())
                                et_luas_bangunan.setText(data.getLuasBangunan())
                                if(data.getJenisBangunan()!!.contains("permanen", true)){
                                    rb_prmn.isChecked = true
                                }else{
                                    rb_sm_prmn.isChecked = true
                                }
                                if(data.getKonstruksi()!!.contains("beton tertulang", true)){
                                    rb_btn_trtlng.isChecked = true
                                }else if(data.getKonstruksi()!!.contains("besi baja", true)){
                                    rb_bs_bj.isChecked = true
                                }else{
                                    rb_kayu.isChecked = true
                                }
                                et_klts_bngn.setText(data.getKualitasBangunan())
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

    private fun setPreferences() {
        sharedPreferences = getSharedPreferences(Config.PREF_NAME,
                Activity.MODE_PRIVATE)
        idJaminan = sharedPreferences.getInt(Config.JAMINAN_ID, Config.EMPTY_INT)
    }

    override fun onClick(p0: View) {
        when(p0.id){
            R.id.btn_next_keadaan_bangunan -> {
                if(sharedPreferences.getString(Config.ROLE, "")== "komite"||sharedPreferences.getString(Config.ROLE, "")== "supervisor"){
                    startActivity(Intent(applicationContext, TataRuangActivity::class.java))
                }else{
                    next()
                }
            }
        }
    }

    private fun next() {
        if(sharedPreferences.getString("from", "") == "repeat"){
            val radioButton = findViewById(rg_jns_bngn.checkedRadioButtonId) as RadioButton
            uploadJenis = radioButton.text.toString()
            val radioButton1 = findViewById(rg_konstruksi.checkedRadioButtonId) as RadioButton
            uploadKonstruksi = radioButton1.text.toString()
        }
        pDialog = ProgressDialog.show(this,
                "",
                "Tunggu Sebentar!")
        if (et_th_dibangun.text.toString() != "" && et_luas_bangunan.text.toString() !="" && uploadJenis !="" &&
                uploadKonstruksi !="" && et_klts_bngn.text.toString() !=""){
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
        val call = service.sendJaminanTanah4(idJaminan, Integer.parseInt(et_th_dibangun.text.toString()),
                et_luas_bangunan.text.toString().toDouble(), uploadJenis, uploadKonstruksi,
                et_klts_bngn.text.toString())
        call.enqueue(object : Callback<Result<UserId>> {
            override fun onFailure(call: Call<Result<UserId>>?, t: Throwable?) {
                pDialog!!.dismiss()
                Log.e("on Failure", t.toString())
                Toast.makeText(applicationContext, R.string.cekkoneksi, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<Result<UserId>>, response: Response<Result<UserId>>) {
                Log.d("KeadaanBangunan", "Status Code = " + response.code())
                if(response.isSuccessful){
                    val result = response.body()
                    if (result != null) {
                        if (result.getStatus()!!) {
                            pDialog!!.dismiss()
                            startActivity(Intent(applicationContext, TataRuangActivity::class.java))
                        } else {
                            Log.e("KeadaanBangunan", response.raw().toString())
                            Toast.makeText(baseContext, result.getMessage(), Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

        })
    }

    override fun onCheckedChanged(p0: RadioGroup?, p1: Int) {
        when(p1){
            R.id.rb_prmn -> {
                val radioButton = findViewById(R.id.rb_prmn) as RadioButton

                uploadJenis = radioButton.text.toString()
            }
            R.id.rb_sm_prmn -> {
                val radioButton = findViewById(R.id.rb_sm_prmn) as RadioButton

                uploadJenis = radioButton.text.toString()
            }
            R.id.rb_btn_trtlng -> {
                val radioButton = findViewById(R.id.rb_btn_trtlng) as RadioButton

                uploadKonstruksi = radioButton.text.toString()
            }
            R.id.rb_bs_bj -> {
                val radioButton = findViewById(R.id.rb_bs_bj) as RadioButton

                uploadKonstruksi = radioButton.text.toString()
            }
            R.id.rb_kayu -> {
                val radioButton = findViewById(R.id.rb_kayu) as RadioButton

                uploadKonstruksi = radioButton.text.toString()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return true
    }
}
