package com.layanacomputindo.bprcma.form

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.text.InputType
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import com.layanacomputindo.bprcma.R
import com.layanacomputindo.bprcma.model.Result
import com.layanacomputindo.bprcma.model.UserId
import com.layanacomputindo.bprcma.rest.RestClient
import com.layanacomputindo.bprcma.util.Config
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import kotlinx.android.synthetic.main.activity_analisis_usaha_part2.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class AnalisisUsahaPart2Activity : AppCompatActivity(), RadioGroup.OnCheckedChangeListener, View.OnClickListener, DatePickerDialog.OnDateSetListener {
    private lateinit var sharedPreferences: SharedPreferences

    private var pDialog: ProgressDialog? = null

    var idKredit = 0
    var uploadStatus = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_analisis_usaha_part2)
        setSupportActionBar(toolbar as Toolbar?)
        supportActionBar?.run {
            title = getString(R.string.analisis_usaha)
            setDisplayHomeAsUpEnabled(true)
        }

        setPreferences()

        et_deskripsi_usaha.setImeOptions(EditorInfo.IME_ACTION_DONE)
        et_deskripsi_usaha.setRawInputType(InputType.TYPE_CLASS_TEXT)

        et_detail_keterangan.setImeOptions(EditorInfo.IME_ACTION_DONE)
        et_detail_keterangan.setRawInputType(InputType.TYPE_CLASS_TEXT)

        et_deskripsi_usaha.setImeOptions(EditorInfo.IME_ACTION_DONE)
        et_deskripsi_usaha.setRawInputType(InputType.TYPE_CLASS_TEXT)

        rg_status.setOnCheckedChangeListener(this)

        btn_next_an_usaha2.setOnClickListener(this)
        tv_spesifikasi_usaha.setOnClickListener(this)
    }

    private fun setPreferences() {
        sharedPreferences = getSharedPreferences(Config.PREF_NAME,
                Activity.MODE_PRIVATE)
        idKredit = sharedPreferences.getInt(Config.KREDIT_ID, Config.EMPTY_INT)
    }

    override fun onClick(p0: View) {
        when(p0.id){
            R.id.btn_next_an_usaha2 -> {
//                pDialog = ProgressDialog.show(this,
//                        "",
//                        "Tunggu Sebentar!")
//                submitData()
                startActivity(Intent(this@AnalisisUsahaPart2Activity, SumberPengembalianKreditActivity::class.java))
            }
            R.id.tv_spesifikasi_usaha -> {
                selectDob()
            }
        }
    }

    private fun submitData() {
        val service by lazy {
            RestClient.getClient(this)
        }
        val call = service.sendUsahaPasanganDebitur(idKredit, et_detail_keterangan.text.toString(), et_detail_keterangan.text.toString(),
                tv_spesifikasi_usaha.text.toString(), uploadStatus, et_deskripsi_usaha.text.toString(), et_des_pnghsln_smpngn.text.toString())
        call.enqueue(object: Callback<Result<UserId>> {
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
                            startActivity(Intent(this@AnalisisUsahaPart2Activity, SumberPengembalianKreditActivity::class.java))
                        } else {
                            Log.e("debitur", response.raw().toString())
                            Toast.makeText(baseContext, result.getMessage(), Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

        })
    }

    override fun onCheckedChanged(p0: RadioGroup?, p1: Int) {
        when(p1){
            R.id.rb_milik_sendiri -> {
                val radioButton = findViewById(R.id.rb_milik_sendiri) as RadioButton

                Toast.makeText(this,
                        radioButton.getText(), Toast.LENGTH_SHORT).show()
            }
            R.id.rb_keluarga -> {
                val radioButton = findViewById(R.id.rb_keluarga) as RadioButton

                Toast.makeText(this,
                        radioButton.getText(), Toast.LENGTH_SHORT).show()
            }
            R.id.rb_kerjasama -> {
                val radioButton = findViewById(R.id.rb_kerjasama) as RadioButton

                Toast.makeText(this,
                        radioButton.getText(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun selectDob() {
        val now = Calendar.getInstance()
        val dpd = DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        )
        dpd.setOkColor(Color.parseColor("#000000"))
        dpd.setCancelColor(Color.parseColor("#000000"))
        dpd.show(fragmentManager, "Datepickerdialog")
    }

    override fun onDateSet(view: DatePickerDialog?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        val date = "" + dayOfMonth + "/" + (monthOfYear + 1) + "/" + year
        tv_spesifikasi_usaha.setText(date)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return true
    }
}
