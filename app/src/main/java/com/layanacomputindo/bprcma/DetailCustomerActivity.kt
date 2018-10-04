package com.layanacomputindo.bprcma

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.layanacomputindo.bprcma.model.Debitur
import com.layanacomputindo.bprcma.model.Result
import com.layanacomputindo.bprcma.rest.RestClient
import com.layanacomputindo.bprcma.util.Config
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_customer.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailCustomerActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var btnBack: ImageButton

    private lateinit var btnSearch: ImageButton

    private lateinit var sharedPreferences: SharedPreferences
    var idDebitur = 0
    private var pDialog: ProgressDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_customer)
        setSupportActionBar(toolbar as Toolbar?)
        supportActionBar?.run {
            title = getString(R.string.action_informasi_kredit)
            setDisplayHomeAsUpEnabled(true)
        }
        pDialog = ProgressDialog.show(this,
                "",
                "Tunggu Sebentar!")
        sharedPreferences = getSharedPreferences(Config.PREF_NAME,
                Activity.MODE_PRIVATE)
        idDebitur = sharedPreferences.getInt(Config.DEBITUR_ID, Config.EMPTY_INT)
        getData(idDebitur)
        btn_dftr_kredit.setOnClickListener(this)
    }

    private fun getData(idDebitur: Int) {
        val service by lazy {
            RestClient.getClient(this)
        }
        val call = service.getDebiturDetail(idDebitur)
        call.enqueue(object: Callback<Result<Debitur>>{
            override fun onFailure(call: Call<Result<Debitur>>?, t: Throwable?) {
                pDialog!!.dismiss()
                Log.e("on Failure", t.toString())
                Toast.makeText(applicationContext, R.string.cekkoneksi, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<Result<Debitur>>?, response: Response<Result<Debitur>>?) {
                if (response != null) {
                    pDialog!!.dismiss()
                    if (response.isSuccessful){
                        val result = response.body().getData()
                        if (result != null) {
                            Picasso.with(this@DetailCustomerActivity)
                                    .load(result.getFotoDebitur()?.getFotoKtp())
                                    .error(android.R.drawable.stat_notify_error).into(img_profile_customer)
                            txt_nama_debitur.text = result.getNama()
                            val list = result.getTeleponDebitur()
                            if (list != null) {
                                if (list.isNotEmpty()){
                                    val phoneNumber = list[0].getNoTelepon()
                                    txt_telp_debitur.text = phoneNumber

                                    btn_hub.setOnClickListener {
                                        val dialPhoneIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
                                        startActivity(dialPhoneIntent)
                                    }
                                }
                            }
                            txt_alamat_debitur.text = result.getAlamat()
                            txt_ttl_debitur.text = result.getTanggalLahir()
                        }
                    }else{
                        Toast.makeText(applicationContext, response.body().getMessage(), Toast.LENGTH_LONG).show()
                    }
                }
            }

        })
    }

    override fun onClick(p0: View) {
        when(p0.id){
            R.id.btn_dftr_kredit -> {
                startActivity(Intent(this, ListKreditActivity::class.java))
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
