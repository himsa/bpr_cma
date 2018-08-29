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
import android.widget.Toast
import com.layanacomputindo.bprcma.R
import com.layanacomputindo.bprcma.model.Result
import com.layanacomputindo.bprcma.model.UserId
import com.layanacomputindo.bprcma.rest.RestClient
import com.layanacomputindo.bprcma.util.Config
import kotlinx.android.synthetic.main.activity_sumber_pengembalian_kredit.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SumberPengembalianKreditActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var sharedPreferences: SharedPreferences
    private var pDialog: ProgressDialog? = null

    var idKredit = 0
    var uploadSumberPengembalian = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sumber_pengembalian_kredit)
        setSupportActionBar(toolbar as Toolbar?)
        supportActionBar?.run {
            title = getString(R.string.smbr_pengembalian)
            setDisplayHomeAsUpEnabled(true)
        }

        setPreferences()

        if(sharedPreferences.getString("from", "") == "repeat"){
            setDummy()
        }

        val smbrPngmblnAdapter = ArrayAdapter.createFromResource(this, R.array.smbrPngmbln, android.R.layout.simple_spinner_item)
        sp_sumber_pengembalian.setAdapter(smbrPngmblnAdapter, 0)
        sp_sumber_pengembalian.setOnSpinnerItemClickListener({ position, itemAtPosition ->
            uploadSumberPengembalian = itemAtPosition
        })

        et_detail_keterangan.setImeOptions(EditorInfo.IME_ACTION_DONE)
        et_detail_keterangan.setRawInputType(InputType.TYPE_CLASS_TEXT)

        btn_next_smbr_pngmbln.setOnClickListener(this)
    }

    private fun setDummy() {
        sp_sumber_pengembalian.setSelection(1)
        et_detail_keterangan.setText("dari penghasilan bulanan")
    }

    private fun setPreferences() {
        sharedPreferences = getSharedPreferences(Config.PREF_NAME,
                Activity.MODE_PRIVATE)
        idKredit = sharedPreferences.getInt(Config.KREDIT_ID, Config.EMPTY_INT)
    }

    override fun onClick(p0: View) {
        when(p0.id){
            R.id.btn_next_smbr_pngmbln -> {
//                pDialog = ProgressDialog.show(this,
//                        "",
//                        "Tunggu Sebentar!")
//                submitData()
                startActivity(Intent(this@SumberPengembalianKreditActivity, AnalisisKeuanganActivity::class.java))
            }
        }
    }

    private fun submitData() {
        val service by lazy {
            RestClient.getClient(this)
        }
        val call = service.sendPengembalianKredit(idKredit, uploadSumberPengembalian, et_detail_keterangan.text.toString())
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
                            startActivity(Intent(this@SumberPengembalianKreditActivity, AnalisisKeuanganActivity::class.java))
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
