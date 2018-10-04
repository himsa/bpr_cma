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
import android.util.Base64
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.layanacomputindo.bprcma.MenuActivity
import com.layanacomputindo.bprcma.R
import com.layanacomputindo.bprcma.SpalshActivity
import com.layanacomputindo.bprcma.model.FotoKendaraan
import com.layanacomputindo.bprcma.model.Result
import com.layanacomputindo.bprcma.model.UserId
import com.layanacomputindo.bprcma.rest.RestClient
import com.layanacomputindo.bprcma.util.Config
import com.squareup.picasso.Picasso
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.activity_foto_kendaraan2.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream

class FotoKendaraan2Activity : AppCompatActivity(), View.OnClickListener {
    var flagPhoto = 0

    private lateinit var sharedPreferences: SharedPreferences
    private var pDialog: ProgressDialog? = null

    var idJaminan = 0
    var idKredit = 0
    var flag = 0
    var strImage5 = ""
    var strImage6 = ""
    var strImage7 = ""
    var strImage8 = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_foto_kendaraan2)
        setSupportActionBar(toolbar as Toolbar?)
        supportActionBar?.run {
            title = getString(R.string.txt_kendaraan)
            setDisplayHomeAsUpEnabled(true)
        }

        setPreferences()

        if(sharedPreferences.getString("from", "") == "repeat"){
            pDialog = ProgressDialog.show(this,
                    "",
                    "Tunggu Sebentar!")
            getData()
        }

        btn_selesai_kendaraan.setOnClickListener(this)
        btn_tambah_jaminan.setOnClickListener(this)
        btn_setuju.setOnClickListener(this)
        btn_tolak.setOnClickListener(this)
        img_nmr_rangka.setOnClickListener(this)
        img_nmr_mesin.setOnClickListener(this)
        img_bpkb.setOnClickListener(this)
        img_stnk.setOnClickListener(this)
    }

    private fun getData() {
        val service by lazy {
            RestClient.getClient(this)
        }
        val call = service.getJaminanKendaraanFoto(idJaminan)
        call.enqueue(object: Callback<Result<FotoKendaraan>> {
            override fun onFailure(call: Call<Result<FotoKendaraan>>?, t: Throwable?) {
                pDialog!!.dismiss()
                Log.e("on Failure", t.toString())
                Toast.makeText(applicationContext, R.string.cekkoneksi, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<Result<FotoKendaraan>>, response: Response<Result<FotoKendaraan>>) {
                Log.d("kendaraan", "Status Code = " + response.code())
                if(response.isSuccessful){
                    val result = response.body()
                    if (result != null) {
                        if (result.getStatus()!!) {
                            val data = result.getData()
                            pDialog!!.dismiss()
                            if (data != null){
                                Picasso.with(applicationContext)
                                        .load(data.getFotoNomorRangka())
                                        .error(android.R.drawable.stat_notify_error).into(img_nmr_rangka)
                                Picasso.with(applicationContext)
                                        .load(data.getFotoNomorMesin())
                                        .error(android.R.drawable.stat_notify_error).into(img_nmr_mesin)
                                Picasso.with(applicationContext)
                                        .load(data.getFotoBpkb())
                                        .error(android.R.drawable.stat_notify_error).into(img_bpkb)
                                Picasso.with(applicationContext)
                                        .load(data.getFotoStnk())
                                        .error(android.R.drawable.stat_notify_error).into(img_stnk)
                            }
                        } else {
                            pDialog!!.dismiss()
                            Log.e("kendaraan", response.raw().toString())
                            Toast.makeText(baseContext, result.getMessage(), Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

        })
    }

    override fun onClick(p0: View) {
        when(p0.id){
            R.id.btn_tambah_jaminan -> {
                flag = 0
                next()
            }
            R.id.btn_selesai_kendaraan -> {
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
            R.id.img_nmr_rangka -> {
                flagPhoto = 4
                selectImage()
            }
            R.id.img_nmr_mesin -> {
                flagPhoto = 5
                selectImage()
            }
            R.id.img_bpkb -> {
                flagPhoto = 6
                selectImage()
            }
            R.id.img_stnk -> {
                flagPhoto = 7
                selectImage()
            }
        }
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
                            startActivity(Intent(applicationContext, SpalshActivity::class.java))
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

    private fun selectImage() {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(this)
    }

    private fun next() {
        if(sharedPreferences.getString("from", "") == "repeat"){
            if(img_nmr_rangka.drawable != null && img_nmr_mesin.drawable != null &&
                    img_bpkb.drawable != null && img_stnk.drawable != null){
                strImage5 = getStringImage((img_nmr_rangka.getDrawable() as BitmapDrawable).bitmap)
                strImage6 = getStringImage((img_nmr_mesin.getDrawable() as BitmapDrawable).bitmap)
                strImage7 = getStringImage((img_bpkb.getDrawable() as BitmapDrawable).bitmap)
                strImage8 = getStringImage((img_stnk.getDrawable() as BitmapDrawable).bitmap)
            }
        }
        pDialog = ProgressDialog.show(this,
                "",
                "Tunggu Sebentar!")
        if(strImage5 != "" &&
                strImage6 != "" &&strImage7 != "" && strImage8 != ""){
            submitData()
        }else{
            pDialog!!.dismiss()
            Toast.makeText(applicationContext, R.string.cekData, Toast.LENGTH_LONG).show()
        }
    }

    private fun submitData() {
        Log.d("fotoKendaraan", "IdJaminan = " + idJaminan)
        val service by lazy {
            RestClient.getClient(this)
        }
        val call = service.sendJaminanKendaraanFoto2(idJaminan, strImage5, strImage6, strImage7, strImage8)
        call.enqueue(object : Callback<Result<UserId>>{
            override fun onFailure(call: Call<Result<UserId>>?, t: Throwable?) {
                pDialog!!.dismiss()
                Log.e("on Failure", t.toString())
                Toast.makeText(applicationContext, R.string.cekkoneksi, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<Result<UserId>>, response: Response<Result<UserId>>) {
                pDialog!!.dismiss()
                Log.d("fotoKendaraan", "Status Code = " + response.code())
                if(response.isSuccessful){
                    val result = response.body()
                    if (result != null) {
                        if (result.getStatus()!!) {
                            if(flag==0){
                                startActivity(Intent(applicationContext, PemeriksaanJaminanActivity::class.java))
                            }else{
                                startActivity(Intent(applicationContext, SpalshActivity::class.java))
                                finishAffinity()
                            }
                        } else {
                            Log.e("fotoKendaraan", response.raw().toString())
                            Toast.makeText(applicationContext, result.getMessage(), Toast.LENGTH_SHORT).show()
                        }
                    }
                }else{
                    Log.e("fotoKendaraan", response.raw().toString())
                    Toast.makeText(applicationContext, response.message(), Toast.LENGTH_SHORT).show()
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
            btn_selesai_kendaraan.visibility = View.GONE
            btn_tambah_jaminan.visibility = View.GONE
            ll_aproval.visibility = View.VISIBLE
        }else{
            btn_selesai_kendaraan.visibility = View.VISIBLE
            btn_tambah_jaminan.visibility = View.VISIBLE
            ll_aproval.visibility = View.GONE
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val result = CropImage.getActivityResult(data)
            val imageUri = result.getUri()
            val imageStream = contentResolver.openInputStream(imageUri)
            val selectedImage = BitmapFactory.decodeStream(imageStream)

            if(flagPhoto == 4){
                strImage5 = getStringImage(selectedImage)
                img_nmr_rangka.setImageBitmap(selectedImage)
            }else if(flagPhoto == 5){
                strImage6 = getStringImage(selectedImage)
                img_nmr_mesin.setImageBitmap(selectedImage)
            }else if(flagPhoto == 6){
                strImage7 = getStringImage(selectedImage)
                img_bpkb.setImageBitmap(selectedImage)
            }else if(flagPhoto == 7){
                strImage8 = getStringImage(selectedImage)
                img_stnk.setImageBitmap(selectedImage)
            }
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return true
    }
}
