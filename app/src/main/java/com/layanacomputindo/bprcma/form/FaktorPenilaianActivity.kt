package com.layanacomputindo.bprcma.form

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.layanacomputindo.bprcma.R
import com.layanacomputindo.bprcma.model.FaktorPenialian
import com.layanacomputindo.bprcma.model.Result
import com.layanacomputindo.bprcma.model.UserId
import com.layanacomputindo.bprcma.rest.RestClient
import com.layanacomputindo.bprcma.util.Config
import kotlinx.android.synthetic.main.activity_faktor_penilaian.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FaktorPenilaianActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var sharedPreferences: SharedPreferences
    var idJaminan = 0

    private var pDialog: ProgressDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_faktor_penilaian)
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

        btn_next_faktor_penilaian.setOnClickListener(this)
    }

    private fun getData() {
        val service by lazy {
            RestClient.getClient(this)
        }
        val call = service.getJaminanTanahFaktor(idJaminan)
        call.enqueue(object: Callback<Result<FaktorPenialian>>{
            override fun onFailure(call: Call<Result<FaktorPenialian>>?, t: Throwable?) {
                pDialog!!.dismiss()
                Log.e("on Failure", t.toString())
                Toast.makeText(applicationContext, R.string.cekkoneksi, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<Result<FaktorPenialian>>, response: Response<Result<FaktorPenialian>>) {
                Log.d("tanah", "Status Code = " + response.code())
                if(response.isSuccessful){
                    val result = response.body()
                    if (result != null) {
                        if (result.getStatus()!!) {
                            val data = result.getData()
                            pDialog!!.dismiss()
                            if (data != null){
                                sw_ada_jalan.isChecked = set(data.getAdaJalanAksesMasuk())
                                sw_dpt_dll.isChecked = set(data.getAksesJalanRoda4())
                                sw_jln_batu.isChecked = set(data.getAksesBerupaTanahBatu())
                                sw_sungai.isChecked = set(data.getDekatSungai())
                                sw_ibadah.isChecked = set(data.getJauhTempatIbadah())
                                sw_banjir.isChecked = set(data.getDekatLokasiBanjir())
                                sw_listrik.isChecked = set(data.getDekatTeganganListrikTinggi())
                                sw_urug.isChecked = set(data.getTanahPerluDiurug())
                                sw_pemda.isChecked = set(data.getMengikutiMasterPlanPemda())
                                sw_tusuk.isChecked = set(data.getLokasiTusukSate())
                                sw_tmp_ibadah.isChecked = set(data.getLokasiTempatIbadah())
                                sw_usaha.isChecked = set(data.getLokasiTempatUsaha())
                                sw_makam.isChecked = set(data.getDekatMakam())
                                sw_spbu.isChecked = set(data.getDekatSpbu())
                                sw_tpa.isChecked = set(data.getDekatTpaSampah())
                                sw_sawah.isChecked = set(data.getDekatSawah())
                                sw_industri.isChecked = set(data.getDekatPerindustrian())
                                sw_longsor.isChecked = set(data.getDekatBahayaLongsor())
                                et_kondisi_lingkungan.setText(data.getKondisiLingkunganProspek())
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

    private fun set(data: Any?): Boolean {
        return data.toString().equals("1", true)
    }

    private fun setdata(data: Boolean): Int {
        return if (data)  1 else 0
    }

    override fun onClick(p0: View) {
        when(p0.id){
            R.id.btn_next_faktor_penilaian -> {
                if(sharedPreferences.getString(Config.ROLE, "")== "komite"||sharedPreferences.getString(Config.ROLE, "")== "supervisor"){
                    startActivity(Intent(applicationContext, SumberHargaTanahActivity::class.java))
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
        val call = service.sendTanahFaktorPenilaian(idJaminan, setdata(sw_ada_jalan.isChecked), setdata(sw_dpt_dll.isChecked),
                setdata(sw_jln_batu.isChecked), setdata(sw_sungai.isChecked), setdata(sw_ibadah.isChecked), setdata(sw_banjir.isChecked),
                setdata(sw_listrik.isChecked), setdata(sw_urug.isChecked), setdata(sw_pemda.isChecked), setdata(sw_tusuk.isChecked), setdata(sw_tmp_ibadah.isChecked),
                setdata(sw_usaha.isChecked), setdata(sw_makam.isChecked), setdata(sw_spbu.isChecked), setdata(sw_tpa.isChecked), setdata(sw_sawah.isChecked),
                setdata(sw_industri.isChecked), setdata(sw_longsor.isChecked), et_kondisi_lingkungan.text.toString())
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
                            startActivity(Intent(applicationContext, SumberHargaTanahActivity::class.java))
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return true
    }
}
