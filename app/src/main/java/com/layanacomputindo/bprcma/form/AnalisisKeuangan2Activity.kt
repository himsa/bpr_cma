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
import kotlinx.android.synthetic.main.activity_analisis_keuangan2.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AnalisisKeuangan2Activity : AppCompatActivity(), View.OnClickListener, View.OnFocusChangeListener {

    private lateinit var sharedPreferences: SharedPreferences

    private var pDialog: ProgressDialog? = null

    var idKredit = 0

    var subTotal: Int = 0
    var subTotal2: Int = 0
    var total: Int = 0
    var etHtgBank: Int = 0
    var etHtgDagang: Int = 0
    var etHtgJkPj: Int = 0
    var etKwMsh: Int = 0
    var etModal1: Int = 0
    var etMdlKrj: Int = 0
    var etCdLbDthn: Int = 0
    var etLbThBjln: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_analisis_keuangan2)
        setSupportActionBar(toolbar as Toolbar?)
        supportActionBar?.run {
            title = getString(R.string.analisis_keuangan)
            setDisplayHomeAsUpEnabled(true)
        }

        setPreferences()

        if(sharedPreferences.getString("from", "") == "repeat"){
            setDummy()
        }

        et_htg_bank.addTextChangedListener(RupiahTextWatcher(et_htg_bank))
        et_htg_dagang.addTextChangedListener(RupiahTextWatcher(et_htg_dagang))
        et_htg_jk_pj.addTextChangedListener(RupiahTextWatcher(et_htg_jk_pj))
        et_kw_msh_hrs_dbyr.addTextChangedListener(RupiahTextWatcher(et_kw_msh_hrs_dbyr))

        et_sub_jumlah.addTextChangedListener(RupiahTextWatcher(et_sub_jumlah))

        et_htg_bank.setOnFocusChangeListener(this)
        et_htg_dagang.setOnFocusChangeListener(this)
        et_htg_jk_pj.setOnFocusChangeListener(this)
        et_kw_msh_hrs_dbyr.setOnFocusChangeListener(this)

        et_modal1.addTextChangedListener(RupiahTextWatcher(et_modal1))
        et_mdl_kerja.addTextChangedListener(RupiahTextWatcher(et_mdl_kerja))
        et_cd_laba_dthn.addTextChangedListener(RupiahTextWatcher(et_cd_laba_dthn))
        et_lb_th_bjln.addTextChangedListener(RupiahTextWatcher(et_lb_th_bjln))

        et_sub_jumlah2.addTextChangedListener(RupiahTextWatcher(et_sub_jumlah2))
        et_total_aktiva.addTextChangedListener(RupiahTextWatcher(et_total_aktiva))

        et_modal1.setOnFocusChangeListener(this)
        et_mdl_kerja.setOnFocusChangeListener(this)
        et_cd_laba_dthn.setOnFocusChangeListener(this)
        et_lb_th_bjln.setOnFocusChangeListener(this)

        btn_next_an_keuangan2.setOnClickListener(this)
    }

    private fun setDummy() {
        et_htg_bank.setText("20.000.000")
        et_htg_dagang.setText("200.000.000")
        et_htg_jk_pj.setText("50.000.000")
        et_kw_msh_hrs_dbyr.setText("0")
        et_sub_jumlah.setText("270.000.000")

        et_modal1.setText("300.000.000")
        et_mdl_kerja.setText("200.000.000")
        et_cd_laba_dthn.setText("75.000.000")
        et_lb_th_bjln.setText("1.000.000.000")
        et_sub_jumlah2.setText("1.575.000.000")

        et_total_aktiva.setText("1.845.000.000")
    }

    private fun setPreferences() {
        sharedPreferences = getSharedPreferences(Config.PREF_NAME,
                Activity.MODE_PRIVATE)
        idKredit = sharedPreferences.getInt(Config.KREDIT_ID, Config.EMPTY_INT)
    }

    override fun onFocusChange(p0: View, p1: Boolean) {
        when(p0.id){
            R.id.et_htg_bank -> {
                setSubTotal()
            }
            R.id.et_htg_dagang -> {
                setSubTotal()
            }
            R.id.et_htg_jk_pj -> {
                setSubTotal()
            }
            R.id.et_kw_msh_hrs_dbyr -> {
                setSubTotal()
            }
            R.id.et_modal1 -> {
                setSubTotal2()
            }
            R.id.et_mdl_kerja -> {
                setSubTotal2()
            }
            R.id.et_cd_laba_dthn -> {
                setSubTotal2()
            }
            R.id.et_lb_th_bjln -> {
                setSubTotal2()
            }
        }
    }

    private fun setSubTotal2() {
        if(et_modal1.text.isNotEmpty()){
            etModal1 = et_modal1.getText().toString().replace("[$,.]".toRegex(), "").toInt()
        }else{
            etModal1 = 0
        }
        if( et_mdl_kerja.text.isNotEmpty()){
            etMdlKrj = et_mdl_kerja.getText().toString().replace("[$,.]".toRegex(), "").toInt()
        }else{
            etMdlKrj = 0
        }
        if (et_cd_laba_dthn.text.isNotEmpty()){
            etCdLbDthn = et_cd_laba_dthn.getText().toString().replace("[$,.]".toRegex(), "").toInt()
        }else{
            etCdLbDthn = 0
        }
        if (et_lb_th_bjln.text.isNotEmpty()){
            etLbThBjln = et_lb_th_bjln.getText().toString().replace("[$,.]".toRegex(), "").toInt()
        }
        else{
            etLbThBjln = 0
        }

        subTotal2 = etModal1 + etMdlKrj + etCdLbDthn + etLbThBjln
        total = subTotal + subTotal2
        et_sub_jumlah2.setText(subTotal2.toString())
        et_total_aktiva.setText(total.toString())
    }

    private fun setSubTotal() {
        if(et_htg_bank.text.isNotEmpty()){
            etHtgBank = et_htg_bank.getText().toString().replace("[$,.]".toRegex(), "").toInt()
        }else{
            etHtgBank = 0
        }
        if( et_htg_dagang.text.isNotEmpty()){
            etHtgDagang = et_htg_dagang.getText().toString().replace("[$,.]".toRegex(), "").toInt()
        }else{
            etHtgDagang = 0
        }
        if (et_htg_jk_pj.text.isNotEmpty()){
            etHtgJkPj = et_htg_jk_pj.getText().toString().replace("[$,.]".toRegex(), "").toInt()
        }else{
            etHtgJkPj = 0
        }
        if (et_kw_msh_hrs_dbyr.text.isNotEmpty()){
            etKwMsh = et_kw_msh_hrs_dbyr.getText().toString().replace("[$,.]".toRegex(), "").toInt()
        }
        else{
            etKwMsh = 0
        }
        subTotal = etHtgBank + etHtgDagang + etHtgJkPj + etKwMsh
        total = subTotal + subTotal2
        et_sub_jumlah.setText(subTotal.toString())
        et_total_aktiva.setText(total.toString())
    }

    override fun onClick(p0: View) {
        when(p0.id){
            R.id.btn_next_an_keuangan2 -> {
//                pDialog = ProgressDialog.show(this,
//                        "",
//                        "Tunggu Sebentar!")
//                submitData()
                startActivity(Intent(this@AnalisisKeuangan2Activity, PrfLabaRugiActivity::class.java))
            }
        }
    }

    private fun submitData() {
        val service by lazy {
            RestClient.getClient(this)
        }
        val call = service.sendPasivaHutang(idKredit, etHtgBank, etHtgDagang, etHtgJkPj, etKwMsh, subTotal)
        call.enqueue(object : Callback<Result<UserId>> {
            override fun onFailure(call: Call<Result<UserId>>?, t: Throwable?) {
                pDialog!!.dismiss()
                Log.e("on Failure", t.toString())
                Toast.makeText(applicationContext, R.string.cekkoneksi, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<Result<UserId>>, response: Response<Result<UserId>>) {
                Log.d("tmp tggl", "Status Code = " + response.code())
                if(response.isSuccessful){
                    val result = response.body()
                    if (result != null) {
                        if (result.getStatus()!!) {
                            submitPasivaModal()
                        } else {
                            Log.e("debitur", response.raw().toString())
                            Toast.makeText(baseContext, result.getMessage(), Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

        })
    }

    private fun submitPasivaModal() {
        val service by lazy {
            RestClient.getClient(this)
        }
        val call = service.sendPasivaModal(idKredit, etModal1, etMdlKrj, etCdLbDthn, etLbThBjln, subTotal2)
        call.enqueue(object : Callback<Result<UserId>>{
            override fun onFailure(call: Call<Result<UserId>>?, t: Throwable?) {
                pDialog!!.dismiss()
                Log.e("on Failure", t.toString())
                Toast.makeText(applicationContext, R.string.cekkoneksi, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<Result<UserId>>, response: Response<Result<UserId>>) {
                Log.d("tmp tggl", "Status Code = " + response.code())
                if(response.isSuccessful){
                    val result = response.body()
                    if (result != null) {
                        if (result.getStatus()!!) {
                            submitTotalPasiva()
                        } else {
                            Log.e("debitur", response.raw().toString())
                            Toast.makeText(baseContext, result.getMessage(), Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

        })
    }

    private fun submitTotalPasiva() {
        val service by lazy {
            RestClient.getClient(this)
        }
        val call = service.sendKeuanganPasivaDebitur(idKredit, total)
        call.enqueue(object : Callback<Result<UserId>>{
            override fun onFailure(call: Call<Result<UserId>>?, t: Throwable?) {
                pDialog!!.dismiss()
                Log.e("on Failure", t.toString())
                Toast.makeText(applicationContext, R.string.cekkoneksi, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<Result<UserId>>, response: Response<Result<UserId>>) {
                Log.d("tmp tggl", "Status Code = " + response.code())
                if(response.isSuccessful){
                    pDialog!!.dismiss()
                    val result = response.body()
                    if (result != null) {
                        if (result.getStatus()!!) {
                            startActivity(Intent(this@AnalisisKeuangan2Activity, PrfLabaRugiActivity::class.java))
                        } else {
                            Log.e("debitur", response.raw().toString())
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
