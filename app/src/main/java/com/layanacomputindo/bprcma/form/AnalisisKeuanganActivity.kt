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
import com.layanacomputindo.bprcma.R
import com.layanacomputindo.bprcma.util.RupiahTextWatcher
import kotlinx.android.synthetic.main.activity_analisis_keuangan.*
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import com.layanacomputindo.bprcma.model.Result
import com.layanacomputindo.bprcma.model.UserId
import com.layanacomputindo.bprcma.rest.RestClient
import com.layanacomputindo.bprcma.util.Config
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AnalisisKeuanganActivity : AppCompatActivity(), View.OnClickListener, View.OnFocusChangeListener {

    private lateinit var sharedPreferences: SharedPreferences

    private var pDialog: ProgressDialog? = null

    var idKredit = 0

    var subTotal: Int = 0
    var subTotal2: Int = 0
    var total: Int = 0
    var etKas: Int = 0
    var etBank: Int = 0
    var etPiutang: Int = 0
    var etPersediaan: Int = 0
    var etTanah: Int = 0
    var etBangunan: Int = 0
    var etKendaraan: Int = 0
    var etInvLain: Int = 0
    var etAkuDepre: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_analisis_keuangan)
        setSupportActionBar(toolbar as Toolbar?)
        supportActionBar?.run {
            title = getString(R.string.analisis_keuangan)
            setDisplayHomeAsUpEnabled(true)
        }

        setPreferences()

        if(sharedPreferences.getString("from", "") == "repeat"){
            setDummy()
        }

        et_kas.addTextChangedListener(RupiahTextWatcher(et_kas))
        et_bank.addTextChangedListener(RupiahTextWatcher(et_bank))
        et_piutang.addTextChangedListener(RupiahTextWatcher(et_piutang))
        et_persediaan.addTextChangedListener(RupiahTextWatcher(et_persediaan))

        et_sub_jumlah.addTextChangedListener(RupiahTextWatcher(et_sub_jumlah))

        et_kas.setOnFocusChangeListener(this)
        et_bank.setOnFocusChangeListener(this)
        et_piutang.setOnFocusChangeListener(this)
        et_persediaan.setOnFocusChangeListener(this)

        et_tanah.addTextChangedListener(RupiahTextWatcher(et_tanah))
        et_bangunan.addTextChangedListener(RupiahTextWatcher(et_bangunan))
        et_kendaraan.addTextChangedListener(RupiahTextWatcher(et_kendaraan))
        et_inv_lain.addTextChangedListener(RupiahTextWatcher(et_inv_lain))
        et_aku_depre.addTextChangedListener(RupiahTextWatcher(et_aku_depre))

        et_sub_jumlah2.addTextChangedListener(RupiahTextWatcher(et_sub_jumlah2))
        et_total_aktiva.addTextChangedListener(RupiahTextWatcher(et_total_aktiva))

        et_tanah.setOnFocusChangeListener(this)
        et_bangunan.setOnFocusChangeListener(this)
        et_kendaraan.setOnFocusChangeListener(this)
        et_inv_lain.setOnFocusChangeListener(this)
        et_aku_depre.setOnFocusChangeListener(this)

        btn_next_an_keuangan1.setOnClickListener(this)
    }

    private fun setDummy() {
        et_kas.setText("20.000.000")
        et_bank.setText("200.000.000")
        et_piutang.setText("50.000.000")
        et_persediaan.setText("0")
        et_sub_jumlah.setText("270.000.000")

        et_tanah.setText("300.000.000")
        et_bangunan.setText("200.000.000")
        et_kendaraan.setText("75.000.000")
        et_inv_lain.setText("1.000.000.000")
        et_aku_depre.setText("25.000.000")
        et_sub_jumlah2.setText("1.600.000.000")

        et_total_aktiva.setText("1.870.000.000")
    }

    private fun setPreferences() {
        sharedPreferences = getSharedPreferences(Config.PREF_NAME,
                Activity.MODE_PRIVATE)
        idKredit = sharedPreferences.getInt(Config.KREDIT_ID, Config.EMPTY_INT)
    }

    override fun onFocusChange(p0: View, p1: Boolean) {
        when(p0.id){
            R.id.et_kas -> {
                setSubTotal()
            }
            R.id.et_bank -> {
                setSubTotal()
            }
            R.id.et_piutang -> {
                setSubTotal()
            }
            R.id.et_persediaan -> {
                setSubTotal()
            }
            R.id.et_tanah -> {
                setSubTotal2()
            }
            R.id.et_bangunan -> {
                setSubTotal2()
            }
            R.id.et_kendaraan -> {
                setSubTotal2()
            }
            R.id.et_inv_lain -> {
                setSubTotal2()
            }
            R.id.et_aku_depre -> {
                setSubTotal2()
            }
        }
    }

    private fun setSubTotal2() {
        if(et_tanah.text.isNotEmpty()){
            etTanah = et_tanah.getText().toString().replace("[$,.]".toRegex(), "").toInt()
        }else{
            etTanah = 0
        }
        if( et_bangunan.text.isNotEmpty()){
            etBangunan = et_bangunan.getText().toString().replace("[$,.]".toRegex(), "").toInt()
        }else{
            etBangunan = 0
        }
        if (et_kendaraan.text.isNotEmpty()){
            etKendaraan = et_kendaraan.getText().toString().replace("[$,.]".toRegex(), "").toInt()
        }else{
            etKendaraan = 0
        }
        if (et_inv_lain.text.isNotEmpty()){
            etInvLain = et_inv_lain.getText().toString().replace("[$,.]".toRegex(), "").toInt()
        }
        else{
            etInvLain = 0
        }
        if (et_aku_depre.text.isNotEmpty()){
            etAkuDepre = et_aku_depre.getText().toString().replace("[$,.]".toRegex(), "").toInt()
        }
        else{
            etAkuDepre = 0
        }
        subTotal2 = etTanah + etBangunan + etKendaraan + etInvLain + etAkuDepre
        total = subTotal + subTotal2
        et_sub_jumlah2.setText(subTotal2.toString())
        et_total_aktiva.setText(total.toString())
    }

    private fun setSubTotal() {
        if(et_kas.text.isNotEmpty()){
            etKas = et_kas.getText().toString().replace("[$,.]".toRegex(), "").toInt()
        }else{
            etKas = 0
        }
        if( et_bank.text.isNotEmpty()){
            etBank = et_bank.getText().toString().replace("[$,.]".toRegex(), "").toInt()
        }else{
            etBank = 0
        }
        if (et_piutang.text.isNotEmpty()){
            etPiutang = et_piutang.getText().toString().replace("[$,.]".toRegex(), "").toInt()
        }else{
            etPiutang = 0
        }
        if (et_persediaan.text.isNotEmpty()){
            etPersediaan = et_persediaan.getText().toString().replace("[$,.]".toRegex(), "").toInt()
        }
        else{
            etPersediaan = 0
        }
        subTotal = etKas + etBank + etPiutang + etPersediaan
        total = subTotal + subTotal2
        et_sub_jumlah.setText(subTotal.toString())
        et_total_aktiva.setText(total.toString())
    }

    override fun onClick(p0: View) {
        when(p0.id){
            R.id.btn_next_an_keuangan1 -> {
//                pDialog = ProgressDialog.show(this,
//                        "",
//                        "Tunggu Sebentar!")
//                submitData()
                startActivity(Intent(this@AnalisisKeuanganActivity, AnalisisKeuangan2Activity::class.java))
            }
        }
    }

    private fun submitData() {
        val service by lazy {
            RestClient.getClient(this)
        }
        val call = service.sendAktivaLancar(idKredit, etKas, etBank, etPiutang, etPersediaan, subTotal)
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
                            submitAktivaTetap()
                        } else {
                            Log.e("debitur", response.raw().toString())
                            Toast.makeText(baseContext, result.getMessage(), Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

        })
    }

    private fun submitAktivaTetap() {
        val service by lazy {
            RestClient.getClient(this)
        }
        val call = service.sendAktivaTetap(idKredit, etTanah, etBangunan, etKendaraan, etInvLain, etAkuDepre, subTotal2)
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
                            submitTotalAktiva()
                        } else {
                            Log.e("debitur", response.raw().toString())
                            Toast.makeText(baseContext, result.getMessage(), Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

        })
    }

    private fun submitTotalAktiva() {
        val service by lazy {
            RestClient.getClient(this)
        }
        val call = service.sendKeuanganAktivaDebitur(idKredit, et_periode.text.toString() , total)
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
                            startActivity(Intent(this@AnalisisKeuanganActivity, AnalisisKeuangan2Activity::class.java))
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
