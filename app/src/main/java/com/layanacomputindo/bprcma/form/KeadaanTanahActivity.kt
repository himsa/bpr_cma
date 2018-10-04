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
import kotlinx.android.synthetic.main.activity_keadaan_tanah.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class KeadaanTanahActivity : AppCompatActivity(), RadioGroup.OnCheckedChangeListener, View.OnClickListener {
    private lateinit var sharedPreferences: SharedPreferences
    var idJaminan = 0
    private var pDialog: ProgressDialog? = null

    var uploadPermukaan = ""
    var uploadPeruntukan = ""
    var uploadJenis = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_keadaan_tanah)
        setSupportActionBar(toolbar as Toolbar?)
        supportActionBar?.run {
            title = getString(R.string.pemr_jaminan)
            setDisplayHomeAsUpEnabled(true)
        }

        setPreferences()

        m2.setText(Html.fromHtml("m<sup><small>2</small></sup>"))

        setPreferences()
        if(sharedPreferences.getString("from", "") == "repeat"){
            pDialog = ProgressDialog.show(this,
                    "",
                    "Tunggu Sebentar!")
            getData()
        }

        rg_prm_tnh_jln.setOnCheckedChangeListener(this)
        rg_prntkn.setOnCheckedChangeListener(this)
        rg_jns_tnh.setOnCheckedChangeListener(this)

        btn_next_keadaan_tanah.setOnClickListener(this)
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
                                et_bntk_tnh.setText(data.getBentukTanah())
                                et_luas.setText(data.getLuasTanah())
                                et_panjang.setText(data.getPanjangTanah())
                                et_lebar.setText(data.getLebarTanah())
                                if (data.getPermukaanTanahDariJalan()!!.contains("sejajar", true)){
                                    rb_sejajar.isChecked = true
                                }else if(data.getPermukaanTanahDariJalan()!!.contains("lebih tinggi", true)){
                                    rb_tinggi.isChecked = true
                                }else{
                                    rb_rendah.isChecked = true
                                }
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
        idJaminan = sharedPreferences.getInt(Config.JAMINAN_ID, Config.EMPTY_INT)
    }

    override fun onClick(p0: View) {
       when(p0.id){
           R.id.btn_next_keadaan_tanah -> {
               if(sharedPreferences.getString(Config.ROLE, "")== "komite"||sharedPreferences.getString(Config.ROLE, "")== "supervisor"){
                   startActivity(Intent(applicationContext, AksesJalanActivity::class.java))
               }else{
                   next()
               }
           }
       }
    }

    private fun next() {
        if(sharedPreferences.getString("from", "") == "repeat"){
            val radioButton = findViewById(rg_prm_tnh_jln.checkedRadioButtonId) as RadioButton
            uploadPermukaan = radioButton.text.toString()
            val radioButton1 = findViewById(rg_prntkn.checkedRadioButtonId) as RadioButton
            uploadPeruntukan = radioButton1.text.toString()
            val radioButton2 = findViewById(rg_jns_tnh.checkedRadioButtonId) as RadioButton
            uploadJenis = radioButton2.text.toString()
        }
        pDialog = ProgressDialog.show(this,
                "",
                "Tunggu Sebentar!")
        if (et_bntk_tnh.text.toString() != "" && et_luas.text.toString() !="" && et_lebar.text.toString() !="" &&
                et_panjang.text.toString() !="" && uploadPermukaan !="" && uploadPeruntukan !="" && uploadJenis !=""){
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
        val call = service.sendJaminanTanah2(idJaminan, et_bntk_tnh.text.toString(), et_luas.text.toString().toDouble(),
                et_lebar.text.toString().toDouble(), et_panjang.text.toString().toDouble(),
                uploadPermukaan, uploadPeruntukan, uploadJenis)
        call.enqueue(object : Callback<Result<UserId>>{
            override fun onFailure(call: Call<Result<UserId>>?, t: Throwable?) {
                pDialog!!.dismiss()
                Log.e("on Failure", t.toString())
                Toast.makeText(applicationContext, R.string.cekkoneksi, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<Result<UserId>>, response: Response<Result<UserId>>) {
                Log.d("KeadaanTanah", "Status Code = " + response.code())
                if(response.isSuccessful){
                    val result = response.body()
                    if (result != null) {
                        if (result.getStatus()!!) {
                            pDialog!!.dismiss()
                            startActivity(Intent(applicationContext, AksesJalanActivity::class.java))
                        } else {
                            Log.e("KeadaanTanah", response.raw().toString())
                            Toast.makeText(baseContext, result.getMessage(), Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

        })
    }

    override fun onCheckedChanged(p0: RadioGroup?, p1: Int) {
        when(p1){
            R.id.rb_sejajar -> {
                val radioButton = findViewById(R.id.rb_sejajar) as RadioButton

                uploadPermukaan = radioButton.text.toString()
            }
            R.id.rb_tinggi -> {
                val radioButton = findViewById(R.id.rb_tinggi) as RadioButton

                uploadPermukaan = radioButton.text.toString()
            }
            R.id.rb_rendah -> {
                val radioButton = findViewById(R.id.rb_rendah) as RadioButton

                uploadPermukaan = radioButton.text.toString()
            }
            R.id.rb_rmh_tinggal -> {
                val radioButton = findViewById(R.id.rb_rmh_tinggal) as RadioButton

                uploadPeruntukan = radioButton.text.toString()
            }
            R.id.rb_rmh_toko -> {
                val radioButton = findViewById(R.id.rb_rmh_toko) as RadioButton

                uploadPeruntukan = radioButton.text.toString()
            }
            R.id.rb_tmp_usaha -> {
                val radioButton = findViewById(R.id.rb_tmp_usaha) as RadioButton

                uploadPeruntukan = radioButton.text.toString()
            }
            R.id.rb_pkrngn -> {
                val radioButton = findViewById(R.id.rb_pkrngn) as RadioButton

                uploadJenis = radioButton.text.toString()
            }
            R.id.rb_tegalan -> {
                val radioButton = findViewById(R.id.rb_tegalan) as RadioButton

                uploadJenis = radioButton.text.toString()
            }
            R.id.rb_sawah -> {
                val radioButton = findViewById(R.id.rb_sawah) as RadioButton

                uploadJenis = radioButton.text.toString()
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
