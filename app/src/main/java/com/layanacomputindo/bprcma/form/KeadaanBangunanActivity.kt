package com.layanacomputindo.bprcma.form

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.text.Html
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

        m2.setText(Html.fromHtml("m<sup><small>2</small></sup>"))

        rg_jns_bngn.setOnCheckedChangeListener(this)
        rg_konstruksi.setOnCheckedChangeListener(this)

        btn_next_keadaan_bangunan.setOnClickListener(this)
    }

    private fun setPreferences() {
        sharedPreferences = getSharedPreferences(Config.PREF_NAME,
                Activity.MODE_PRIVATE)
        idJaminan = sharedPreferences.getInt(Config.JAMINAN_ID, Config.EMPTY_INT)
    }

    override fun onClick(p0: View) {
        when(p0.id){
            R.id.btn_next_keadaan_bangunan -> {
//                pDialog = ProgressDialog.show(this,
//                        "",
//                        "Tunggu Sebentar!")
//                submitData()
                startActivity(Intent(applicationContext, TataRuangActivity::class.java))
            }
        }
    }

    private fun submitData() {
        val service by lazy {
            RestClient.getClient(this)
        }
        val call = service.sendJaminanTanah4(idJaminan, Integer.parseInt(et_th_dibangun.text.toString()),
                Integer.parseInt(et_luas_bangunan.text.toString()), uploadJenis, uploadKonstruksi,
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
