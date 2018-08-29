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
import com.layanacomputindo.bprcma.MenuActivity
import com.layanacomputindo.bprcma.R
import com.layanacomputindo.bprcma.model.Result
import com.layanacomputindo.bprcma.model.UserId
import com.layanacomputindo.bprcma.rest.RestClient
import com.layanacomputindo.bprcma.util.Config
import com.layanacomputindo.bprcma.util.RupiahTextWatcher
import kotlinx.android.synthetic.main.activity_penilaian_kesimpulan.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PenilaianKesimpulanActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var sharedPreferences: SharedPreferences
    var idJaminan = 0

    private var pDialog: ProgressDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_penilaian_kesimpulan)
        setSupportActionBar(toolbar as Toolbar?)
        supportActionBar?.run {
            title = getString(R.string.pemr_jaminan)
            setDisplayHomeAsUpEnabled(true)
        }

        setPreferences()

        et_nilai.addTextChangedListener(RupiahTextWatcher(et_nilai))
        et_nilai_psr.addTextChangedListener(RupiahTextWatcher(et_nilai_psr))
        et_luas_sesuai_fisik.addTextChangedListener(RupiahTextWatcher(et_luas_sesuai_fisik))
        et_nilai_psr_ssuai_imb.addTextChangedListener(RupiahTextWatcher(et_nilai_psr_ssuai_imb))
        et_nilai_psr_ssuai_fisik.addTextChangedListener(RupiahTextWatcher(et_nilai_psr_ssuai_fisik))
        et_sesuai_fisik_tnh.addTextChangedListener(RupiahTextWatcher(et_sesuai_fisik_tnh))
        et_sesuai_imb_tnh.addTextChangedListener(RupiahTextWatcher(et_sesuai_imb_tnh))
        et_sesuai_fisik.addTextChangedListener(RupiahTextWatcher(et_sesuai_fisik))
        et_sesuai_imb.addTextChangedListener(RupiahTextWatcher(et_sesuai_imb))
        et_sesuai_fisik_lkuidasi.addTextChangedListener(RupiahTextWatcher(et_sesuai_fisik_lkuidasi))
        et_sesuai_imb_likuidasi.addTextChangedListener(RupiahTextWatcher(et_sesuai_imb_likuidasi))

        btn_selesai_tanah.setOnClickListener(this)
    }

    private fun setPreferences() {
        sharedPreferences = getSharedPreferences(Config.PREF_NAME,
                Activity.MODE_PRIVATE)
        idJaminan = sharedPreferences.getInt(Config.JAMINAN_ID, Config.EMPTY_INT)
    }

    override fun onClick(p0: View) {
        when(p0.id){
            R.id.btn_selesai_tanah -> {
//                pDialog = ProgressDialog.show(this,
//                        "",
//                        "Tunggu Sebentar!")
//                submitData()
                startActivity(Intent(applicationContext, MenuActivity::class.java))
                finishAffinity()
            }
        }
    }

    private fun submitData() {
        val service by lazy {
            RestClient.getClient(this)
        }
        val call = service.sendPenilaianKesimpulan(idJaminan,
                Integer.parseInt(et_luas_tanah.text.toString()),
                et_nilai.getText().toString().replace("[$,.]".toRegex(), "").toInt(),
                et_nilai_psr.getText().toString().replace("[$,.]".toRegex(), "").toInt(),
                Integer.parseInt(et_luas_sesuai_imb.text.toString()),
                et_luas_sesuai_fisik.getText().toString().replace("[$,.]".toRegex(), "").toInt(),
                Integer.parseInt(et_nilai_bngn.text.toString()),
                Integer.parseInt(et_pnysutan.text.toString()),
                et_nilai_psr_ssuai_imb.getText().toString().replace("[$,.]".toRegex(), "").toInt(),
                et_nilai_psr_ssuai_fisik.getText().toString().replace("[$,.]".toRegex(), "").toInt(),
                et_sesuai_fisik_tnh.getText().toString().replace("[$,.]".toRegex(), "").toInt(),
                et_sesuai_imb_tnh.getText().toString().replace("[$,.]".toRegex(), "").toInt(),
                Integer.parseInt(et_sesuai_fisik.text.toString()),
                Integer.parseInt(et_sesuai_imb.text.toString()),
                et_detail_keterangan.text.toString(),
                et_fktr_pos.text.toString(),
                et_fktr_neg.text.toString())

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
                            startActivity(Intent(applicationContext, MenuActivity::class.java))
                            finishAffinity()
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
