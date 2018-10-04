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
import com.layanacomputindo.bprcma.MenuActivity
import com.layanacomputindo.bprcma.R
import com.layanacomputindo.bprcma.SpalshActivity
import com.layanacomputindo.bprcma.model.PenilaianKesimpulan
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
    var idKredit = 0
    var flag = 0

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
        if(sharedPreferences.getString("from", "") == "repeat"){
            pDialog = ProgressDialog.show(this,
                    "",
                    "Tunggu Sebentar!")
            getData()
        }

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

        btn_tambah_jaminan.setOnClickListener(this)
        btn_selesai_tanah.setOnClickListener(this)
        btn_setuju.setOnClickListener(this)
        btn_tolak.setOnClickListener(this)
    }

    private fun getData() {
        val service by lazy {
            RestClient.getClient(this)
        }
        val call = service.getJaminanPenilaian(idJaminan)
        call.enqueue(object: Callback<Result<PenilaianKesimpulan>>{
            override fun onFailure(call: Call<Result<PenilaianKesimpulan>>?, t: Throwable?) {
                pDialog!!.dismiss()
                Log.e("on Failure", t.toString())
                Toast.makeText(applicationContext, R.string.cekkoneksi, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<Result<PenilaianKesimpulan>>, response: Response<Result<PenilaianKesimpulan>>) {
                Log.d("FaktorPenilaian", "Status Code = " + response.code())
                if(response.isSuccessful){
                    val result = response.body()
                    if (result != null) {
                        if (result.getStatus()!!) {
                            val data = result.getData()
                            pDialog!!.dismiss()
                            if (data != null) {
                                et_luas_tanah.setText(data.getLuasTanah())
                                et_nilai.setText(data.getNilaiTanah().toString())
                                et_nilai_psr.setText(data.getNilaiPasarTanah().toString())
                                et_luas_sesuai_imb.setText(data.getLuasBangunanSesuaiImb().toString())
                                et_nilai_bngn.setText(data.getNilaiBangunan().toString())
                                et_luas_sesuai_fisik.setText(data.getLuasBangunanSesuaiFisik().toString())
                                et_pnysutan.setText(data.getPenyusutanBangunan().toString())
                                et_nilai_psr_ssuai_imb.setText(data.getNilaiPasarBangunanImb().toString())
                                et_nilai_psr_ssuai_fisik.setText(data.getNilaiPasarBangunanFisik().toString())
                                et_sesuai_imb_tnh.setText(data.getNilaiTanahBangunanImb().toString())
                                et_sesuai_fisik_tnh.setText(data.getNilaiTanahBangunanFisik().toString())
                                et_sesuai_fisik.setText(data.getPersenLikuidasiFisik().toString())
                                et_sesuai_fisik_lkuidasi.setText(data.getNilaiLikuidasiFisik().toString())
                                et_sesuai_imb.setText(data.getPersenLikuidasiImb().toString())
                                et_sesuai_imb_likuidasi.setText(data.getNilaiLikuidasiImb().toString())
                                et_detail_keterangan.setText(data.getKesimpulan().toString())
                                et_fktr_pos.setText(data.getFaktorPositif().toString())
                                et_fktr_neg.setText(data.getFaktorNegatif().toString())
                            }
                        } else {
                            pDialog!!.dismiss()
                            Log.e("FaktorPenilaian", response.raw().toString())
                            Toast.makeText(baseContext, result.getMessage(), Toast.LENGTH_SHORT).show()
                        }
                    }
                }else {
                    pDialog!!.dismiss()
                    Log.e("FaktorPenilaian", response.raw().toString())
                    Toast.makeText(baseContext, response.message(), Toast.LENGTH_SHORT).show()
                }
            }

        })
    }

    private fun setPreferences() {
        sharedPreferences = getSharedPreferences(Config.PREF_NAME,
                Activity.MODE_PRIVATE)
        idJaminan = sharedPreferences.getInt(Config.JAMINAN_ID, Config.EMPTY_INT)
        idKredit = sharedPreferences.getInt(Config.KREDIT_ID, Config.EMPTY_INT)
        if(sharedPreferences.getString(Config.ROLE, "")== "komite"||sharedPreferences.getString(Config.ROLE, "")== "supervisor"){
            btn_selesai_tanah.visibility = View.GONE
            btn_tambah_jaminan.visibility = View.GONE
            ll_aproval.visibility = View.VISIBLE
        }else{
            btn_selesai_tanah.visibility = View.VISIBLE
            btn_tambah_jaminan.visibility = View.VISIBLE
            ll_aproval.visibility = View.GONE
        }
    }

    override fun onClick(p0: View) {
        when(p0.id){
            R.id.btn_tambah_jaminan -> {
                flag = 0
                next()
            }
            R.id.btn_selesai_tanah -> {
                flag = 1
                next()
            }
            R.id.btn_setuju -> {
                pDialog = ProgressDialog.show(this,
                        "",
                        "Tunggu Sebentar!")
                aproval("disetujui")
            }
            R.id.btn_tolak -> {
                pDialog = ProgressDialog.show(this,
                        "",
                        "Tunggu Sebentar!")
                aproval("ditolak")
            }
        }
    }

    private fun next() {
        if (et_luas_tanah.text.toString() != "" &&
                et_nilai.text.toString() != "" &&
                et_nilai_psr.text.toString() != "" &&
                et_luas_sesuai_imb.text.toString() != "" &&
                et_nilai_bngn.text.toString() != "" &&
                et_luas_sesuai_fisik.text.toString() != "" &&
                et_pnysutan.text.toString() != "" &&
                et_nilai_psr_ssuai_imb.text.toString() != "" &&
                et_nilai_psr_ssuai_fisik.text.toString() != "" &&
                et_sesuai_imb_tnh.text.toString() != "" &&
                et_sesuai_fisik_tnh.text.toString() != "" &&
                et_sesuai_fisik.text.toString() != "" &&
                et_sesuai_fisik_lkuidasi.text.toString() != "" &&
                et_sesuai_imb.text.toString() != "" &&
                et_sesuai_imb_likuidasi.text.toString() != "" &&
                et_detail_keterangan.text.toString() != "" &&
                et_fktr_pos.text.toString() != "" &&
                et_fktr_neg.text.toString() != ""){
            submitData()
        }else{
            pDialog!!.dismiss()
            Toast.makeText(applicationContext, R.string.cekData, Toast.LENGTH_LONG).show()
        }
        pDialog = ProgressDialog.show(this,
                "",
                "Tunggu Sebentar!")
    }

    private fun submitData() {
        val service by lazy {
            RestClient.getClient(this)
        }
        val call = service.sendPenilaianKesimpulan(idJaminan,
                et_luas_tanah.text.toString().toDouble(),
                et_nilai.getText().toString().replace("[$,.]".toRegex(), "").toInt(),
                et_nilai_psr.getText().toString().replace("[$,.]".toRegex(), "").toInt(),
                et_luas_sesuai_imb.text.toString().toDouble(),
                et_nilai_bngn.text.toString().toDouble(),
                et_luas_sesuai_fisik.getText().toString().replace("[$,.]".toRegex(), "").toInt(),
                et_pnysutan.text.toString().toDouble(),
                et_nilai_psr_ssuai_imb.getText().toString().replace("[$,.]".toRegex(), "").toInt(),
                et_nilai_psr_ssuai_fisik.getText().toString().replace("[$,.]".toRegex(), "").toInt(),
                et_sesuai_imb_tnh.getText().toString().replace("[$,.]".toRegex(), "").toInt(),
                et_sesuai_fisik_tnh.getText().toString().replace("[$,.]".toRegex(), "").toInt(),
                et_sesuai_fisik.text.toString().toDouble(),
                et_sesuai_fisik_lkuidasi.getText().toString().replace("[$,.]".toRegex(), "").toInt(),
                et_sesuai_imb.text.toString().toDouble(),
                et_sesuai_imb_likuidasi.getText().toString().replace("[$,.]".toRegex(), "").toInt(),
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
                            if (flag == 0){
                                startActivity(Intent(this@PenilaianKesimpulanActivity, PemeriksaanJaminanActivity::class.java))
                            }else{
                                startActivity(Intent(applicationContext, MenuActivity::class.java))
                                finishAffinity()
                            }
                        } else {
                            pDialog!!.dismiss()
                            Log.e("FaktorPenilaian", response.raw().toString())
                            Toast.makeText(baseContext, result.getMessage(), Toast.LENGTH_SHORT).show()
                        }
                    }
                }else {
                    pDialog!!.dismiss()
                    Log.e("FaktorPenilaian", response.raw().toString())
                    Toast.makeText(baseContext, response.message(), Toast.LENGTH_SHORT).show()
                }
            }

        })
    }

    private fun aproval(s: String) {
        val service by lazy {
            RestClient.getClient(this)
        }
        val call = service.setKreditAproval(idKredit, s)
        call.enqueue(object: Callback<Result<UserId>>{
            override fun onFailure(call: Call<Result<UserId>>?, t: Throwable?) {
                pDialog!!.dismiss()
                Log.e("on Failure", t.toString())
                Toast.makeText(applicationContext, R.string.cekkoneksi, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<Result<UserId>>, response: Response<Result<UserId>>) {
                Log.d("aproval", "Status Code = " + response.code())
                if(response.isSuccessful){
                    pDialog!!.dismiss()
                    val result = response.body()
                    if (result != null) {
                        if (result.getStatus()!!) {
                            Toast.makeText(baseContext, result.getMessage(), Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this@PenilaianKesimpulanActivity, SpalshActivity::class.java))
                            finishAffinity()
                        } else {
                            Log.e("aproval", response.raw().toString())
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
