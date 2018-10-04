package com.layanacomputindo.bprcma.form

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import android.util.Base64
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.layanacomputindo.bprcma.MenuActivity
import com.layanacomputindo.bprcma.R
import com.layanacomputindo.bprcma.SpalshActivity
import com.layanacomputindo.bprcma.model.Result
import com.layanacomputindo.bprcma.model.Tabungan
import com.layanacomputindo.bprcma.model.UserId
import com.layanacomputindo.bprcma.rest.RestClient
import com.layanacomputindo.bprcma.util.Config
import com.layanacomputindo.bprcma.util.RupiahTextWatcher
import com.squareup.picasso.Picasso
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.activity_tabungan_deposito.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream

class TabunganDepositoActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var sharedPreferences: SharedPreferences
    var idKredit = 0
    var idJaminan = 0
    var strImageTabungan = ""
    var flag = 0
    private var pDialog: ProgressDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tabungan_deposito)
        setSupportActionBar(toolbar as Toolbar?)
        supportActionBar?.run {
            title = getString(R.string.tabungan_deposito)
            setDisplayHomeAsUpEnabled(true)
        }

        setPreferences()

        if(sharedPreferences.getString("from", "") == "repeat"){
            pDialog = ProgressDialog.show(this,
                    "",
                    "Tunggu Sebentar!")
            getData()
        }

        et_nominal_rekening.addTextChangedListener(RupiahTextWatcher(et_nominal_rekening))

        btn_tambah_jaminan.setOnClickListener(this)
        btn_selesai_tabungan.setOnClickListener(this)
        btn_setuju.setOnClickListener(this)
        btn_tolak.setOnClickListener(this)
        img_tabungan.setOnClickListener(this)
    }

    private fun getData() {
        val service by lazy {
            RestClient.getClient(this)
        }
        val call = service.getJaminanTabungan(idJaminan)
        call.enqueue(object: Callback<Result<Tabungan>>{
            override fun onFailure(call: Call<Result<Tabungan>>?, t: Throwable?) {
                pDialog!!.dismiss()
                Log.e("on Failure", t.toString())
                Toast.makeText(applicationContext, R.string.cekkoneksi, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<Result<Tabungan>>, response: Response<Result<Tabungan>>) {
                Log.d("debitur", "Status Code = " + response.code())
                if(response.isSuccessful){
                    val result = response.body()
                    if (result != null) {
                        if (result.getStatus()!!) {
                            val data = result.getData()
                            pDialog!!.dismiss()
                            if (data != null){
                                et_nama_rekening.setText(data.getNama())
                                if (data.getNominal() == null || data.getNominal() == 0){

                                }else{
                                    et_nominal_rekening.setText(data.getNominal().toString())
                                }
                                et_nomor_rekening.setText(data.getNomorRekening())
                                et_nomor_bilyet.setText(data.getNomorBilyet())
                                Picasso.with(applicationContext)
                                        .load(data.getFotoDokumen())
                                        .error(android.R.drawable.stat_notify_error).into(img_tabungan)
                            }

                        } else {
                            pDialog!!.dismiss()
                            Log.e("debitur", response.raw().toString())
                            Toast.makeText(baseContext, result.getMessage(), Toast.LENGTH_SHORT).show()
                        }
                    }
                }else {
                    pDialog!!.dismiss()
                    Log.e("debitur", response.raw().toString())
                    Toast.makeText(baseContext, response.message(), Toast.LENGTH_SHORT).show()
                }
            }

        })
    }

    private fun setDummy() {
        et_nomor_rekening.setText("1234567890")
        et_nomor_bilyet.setText("987654")
        et_nama_rekening.setText("Himsa Yudhistira")
        et_nominal_rekening.setText("56.000.000")
        img_tabungan.setImageResource(R.drawable.dummy_tabungan)
    }

    override fun onClick(p0: View) {
        when(p0.id){
            R.id.btn_tambah_jaminan -> {
                flag = 0
                next()
            }
            R.id.btn_selesai_tabungan -> {
                flag = 1
                next()
            }
            R.id.img_tabungan -> {
                selectImage()
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
        if(sharedPreferences.getString("from", "") == "repeat"){
            if(img_tabungan.drawable != null){
                strImageTabungan = getStringImage((img_tabungan.getDrawable() as BitmapDrawable).bitmap)
            }
        }
        pDialog = ProgressDialog.show(this,
                "",
                "Tunggu Sebentar!")
        if(et_nomor_rekening.text.toString() != "" &&
        et_nomor_bilyet.text.toString() != "" && et_nama_rekening.text.toString() !="" &&
        et_nominal_rekening.getText().toString().replace("[$,.]".toRegex(), "").toInt() !=0 &&
        strImageTabungan != ""){
            if(sharedPreferences.getString("from", "") == "repeat"){
                updateData()
            }else{
                submitData()
            }
        }else{
            pDialog!!.dismiss()
            Toast.makeText(applicationContext, R.string.cekData, Toast.LENGTH_LONG).show()
        }
    }

    private fun updateData() {
        val service by lazy {
            RestClient.getClient(this)
        }
        val call = service.updateJaminanTabungan(idJaminan, et_nomor_rekening.text.toString(),
                et_nomor_bilyet.text.toString(), et_nama_rekening.text.toString(),
                et_nominal_rekening.getText().toString().replace("[$,.]".toRegex(), "").toInt(),
                strImageTabungan)
        call.enqueue(object : Callback<Result<UserId>>{
            override fun onFailure(call: Call<Result<UserId>>?, t: Throwable?) {
                pDialog!!.dismiss()
                Log.e("on Failure", t.toString())
                Toast.makeText(applicationContext, R.string.cekkoneksi, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<Result<UserId>>, response: Response<Result<UserId>>) {
                Log.d("tabungan", "Status Code = " + response.code())
                if(response.isSuccessful){
                    val result = response.body()
                    if (result != null) {
                        if (result.getStatus()!!) {
                            pDialog!!.dismiss()
                            if(flag==0){
                                startActivity(Intent(applicationContext, PemeriksaanJaminanActivity::class.java))
                            }else{
                                startActivity(Intent(applicationContext, SpalshActivity::class.java))
                                finishAffinity()
                            }
                        } else {
                            pDialog!!.dismiss()
                            Log.e("tabungan", response.raw().toString())
                            Toast.makeText(baseContext, result.getMessage(), Toast.LENGTH_SHORT).show()
                        }
                    }
                }else {
                    pDialog!!.dismiss()
                    Log.e("tabungan", response.raw().toString())
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
                    val result = response.body()
                    if (result != null) {
                        if (result.getStatus()!!) {
                            pDialog!!.dismiss()
                            Toast.makeText(baseContext, result.getMessage(), Toast.LENGTH_SHORT).show()
                            startActivity(Intent(applicationContext, SpalshActivity::class.java))
                            finishAffinity()
                        } else {
                            pDialog!!.dismiss()
                            Log.e("aproval", response.raw().toString())
                            Toast.makeText(baseContext, result.getMessage(), Toast.LENGTH_SHORT).show()
                        }
                    }
                }else {
                    pDialog!!.dismiss()
                    Log.e("aproval", response.raw().toString())
                    Toast.makeText(baseContext, response.message(), Toast.LENGTH_SHORT).show()
                }
            }

        })
    }

    private fun submitData() {
        val service by lazy {
            RestClient.getClient(this)
        }
        val call = service.sendJaminanTabungan(idKredit, et_nomor_rekening.text.toString(),
                et_nomor_bilyet.text.toString(), et_nama_rekening.text.toString(),
                et_nominal_rekening.getText().toString().replace("[$,.]".toRegex(), "").toInt(),
                strImageTabungan)
        call.enqueue(object : Callback<Result<UserId>>{
            override fun onFailure(call: Call<Result<UserId>>?, t: Throwable?) {
                pDialog!!.dismiss()
                Log.e("on Failure", t.toString())
                Toast.makeText(applicationContext, R.string.cekkoneksi, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<Result<UserId>>, response: Response<Result<UserId>>) {
                Log.d("tabungan", "Status Code = " + response.code())
                if(response.isSuccessful){
                    val result = response.body()
                    if (result != null) {
                        if (result.getStatus()!!) {
                            pDialog!!.dismiss()
                            val editor = sharedPreferences.edit()
                            editor.putInt(Config.JAMINAN_ID, result.getData()!!.id)
                            editor.apply()
                            if(flag==0){
                                startActivity(Intent(applicationContext, PemeriksaanJaminanActivity::class.java))
                            }else{
                                startActivity(Intent(applicationContext, SpalshActivity::class.java))
                                finishAffinity()
                            }
                            finishAffinity()
                        } else {
                            pDialog!!.dismiss()
                            Log.e("tabungan", response.raw().toString())
                            Toast.makeText(baseContext, result.getMessage(), Toast.LENGTH_SHORT).show()
                        }
                    }
                }else {
                    pDialog!!.dismiss()
                    Log.e("tabungan", response.raw().toString())
                    Toast.makeText(baseContext, response.message(), Toast.LENGTH_SHORT).show()
                }
            }

        })
    }

    private fun selectImage() {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val result = CropImage.getActivityResult(data)
            val imageUri = result.getUri()
            val imageStream = contentResolver.openInputStream(imageUri)
            val selectedImage = BitmapFactory.decodeStream(imageStream)
            strImageTabungan = getStringImage(selectedImage)
            img_tabungan.setImageBitmap(selectedImage)
        } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
            val error = CropImage.getActivityResult(data).getError()
            Log.e("error", error.getLocalizedMessage())
        }
    }

    fun getStringImage(bmp: Bitmap): String {
        val baos = ByteArrayOutputStream()
        bmp.compress(Bitmap.CompressFormat.JPEG, 30, baos)
        val imageBytes = baos.toByteArray()
        return Base64.encodeToString(imageBytes, Base64.DEFAULT)
    }

    private fun setPreferences() {
        sharedPreferences = getSharedPreferences(Config.PREF_NAME,
                Activity.MODE_PRIVATE)
        idKredit = sharedPreferences.getInt(Config.KREDIT_ID, Config.EMPTY_INT)
        idJaminan = sharedPreferences.getInt(Config.JAMINAN_ID, Config.EMPTY_INT)
        if(sharedPreferences.getString(Config.ROLE, "")== "komite"||sharedPreferences.getString(Config.ROLE, "")== "supervisor"){
            btn_selesai_tabungan.visibility = View.GONE
            btn_tambah_jaminan.visibility = View.GONE
            ll_aproval.visibility = View.VISIBLE
        }else{
            btn_selesai_tabungan.visibility = View.VISIBLE
            btn_tambah_jaminan.visibility = View.VISIBLE
            ll_aproval.visibility = View.GONE
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return true
    }
}
