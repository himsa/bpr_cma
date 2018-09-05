package com.layanacomputindo.bprcma.form

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.text.InputType
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import com.layanacomputindo.bprcma.R
import com.layanacomputindo.bprcma.model.Result
import com.layanacomputindo.bprcma.model.Results
import com.layanacomputindo.bprcma.model.UserId
import com.layanacomputindo.bprcma.rest.RestClient
import com.layanacomputindo.bprcma.util.Config
import com.layanacomputindo.bprcma.util.RupiahTextWatcher
import kotlinx.android.synthetic.main.activity_permohonan_kredit.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PermohonanKreditActivity : AppCompatActivity(), RadioGroup.OnCheckedChangeListener, View.OnClickListener {

    private lateinit var sharedPreferences: SharedPreferences
    private var pDialog: ProgressDialog? = null

    var idDebitur = 0
    var uploadJenisKredit = ""
    var uploadTujuanKredit = ""
    var uploadSektorEkonomi = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permohonan_kredit)
        setSupportActionBar(toolbar as Toolbar?)
        supportActionBar?.run {
            title = getString(R.string.permohonan_kredit)
            setDisplayHomeAsUpEnabled(true)
        }

        setPreferences()

        if(sharedPreferences.getString("from", "") == "repeat"){
            setDummy()
        }

        et_nominal.addTextChangedListener(RupiahTextWatcher(et_nominal))
        et_detail_penggunaan_kredit.setImeOptions(EditorInfo.IME_ACTION_DONE)
        et_detail_penggunaan_kredit.setRawInputType(InputType.TYPE_CLASS_TEXT)
        rg_jenis_kredit.setOnCheckedChangeListener(this)
        btn_next_prm_kredit.setOnClickListener(this)

        val tjKreditAdapter = ArrayAdapter.createFromResource(this, R.array.tjKredit, android.R.layout.simple_spinner_item)
        val sktEkonomiAdapter = ArrayAdapter.createFromResource(this, R.array.sktEkonomi, android.R.layout.simple_spinner_item)

        sp_pilih_tujuan_kredit.setAdapter(tjKreditAdapter, 0)
        sp_sektor_ekonomi.setAdapter(sktEkonomiAdapter, 0)

        sp_pilih_tujuan_kredit.setOnSpinnerItemClickListener({ position, itemAtPosition ->
            uploadTujuanKredit = itemAtPosition
        })
        sp_sektor_ekonomi.setOnSpinnerItemClickListener({ position, itemAtPosition ->
            uploadSektorEkonomi = itemAtPosition
        })
    }

    private fun setDummy() {
        et_nominal.setText("100.000.000")
        et_jangka_waktu.setText("24")
        sp_pilih_tujuan_kredit.setSelection(1)
        sp_sektor_ekonomi.setSelection(3)
        et_detail_penggunaan_kredit.setText("Modal jualan")
    }

    private fun setPreferences() {
        sharedPreferences = getSharedPreferences(Config.PREF_NAME,
                Activity.MODE_PRIVATE)
        idDebitur = sharedPreferences.getInt(Config.DEBITUR_ID, Config.EMPTY_INT)
    }

    override fun onCheckedChanged(p0: RadioGroup?, p1: Int) {
        when(p1){
            R.id.rb_angsuran -> {
                val radioButton = findViewById(R.id.rb_angsuran) as RadioButton

                uploadJenisKredit = radioButton.text.toString()
            }
            R.id.rb_berkala -> {
                val radioButton = findViewById(R.id.rb_berkala) as RadioButton

                uploadJenisKredit = radioButton.text.toString()
            }
        }
    }

    override fun onClick(p0: View) {
        when(p0.id){
            R.id.btn_next_prm_kredit -> {
                pDialog = ProgressDialog.show(this,
                        "",
                        "Tunggu Sebentar!")
                submitData()
            }
        }
    }

    private fun submitData() {
        val service by lazy {
            RestClient.getClient(this)
        }
        val call = service.sendPermohonanKredit(idDebitur, et_jangka_waktu.text.toString(), uploadTujuanKredit, et_nominal.getText().toString().replace("[$,.]".toRegex(), "").toInt(),
                uploadSektorEkonomi, et_spesifikasi_usaha.text.toString(), et_detail_penggunaan_kredit.text.toString())
        call.enqueue(object: Callback<Result<UserId>>{
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
                            val editor = sharedPreferences.edit()
                            editor.putInt(Config.KREDIT_ID, result.getData()!!.id)
                            editor.apply()
                            startActivity(Intent(this@PermohonanKreditActivity, AnalisisUsahaPart1Activity::class.java))
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
