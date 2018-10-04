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
import kotlinx.android.synthetic.main.activity_foto_kondisi_kendaraan.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream

class FotoKondisiKendaraanActivity : AppCompatActivity(), View.OnClickListener {
    var flagPhoto = 0

    private lateinit var sharedPreferences: SharedPreferences
    var idJaminan = 0
    var idKredit = 0
    var flag = 0
    var strImage1 = ""
    var strImage2 = ""
    var strImage3 = ""
    var strImage4 = ""

    private var pDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_foto_kondisi_kendaraan)
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
        img_tmp_muka.setOnClickListener(this)
        img_tmp_belakang.setOnClickListener(this)
        img_tmp_kanan.setOnClickListener(this)
        img_tmp_kiri.setOnClickListener(this)
    }

    private fun getData() {
        val service by lazy {
            RestClient.getClient(this)
        }
        val call = service.getJaminanKendaraanFoto(idJaminan)
        call.enqueue(object: Callback<Result<FotoKendaraan>>{
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
                                Picasso.with(this@FotoKondisiKendaraanActivity)
                                        .load(data.getTampakMuka())
                                        .error(android.R.drawable.stat_notify_error).into(img_tmp_muka)
                                Picasso.with(this@FotoKondisiKendaraanActivity)
                                        .load(data.getTampakBelakang())
                                        .error(android.R.drawable.stat_notify_error).into(img_tmp_belakang)
                                Picasso.with(this@FotoKondisiKendaraanActivity)
                                        .load(data.getTampakSisiKanan())
                                        .error(android.R.drawable.stat_notify_error).into(img_tmp_kanan)
                                Picasso.with(this@FotoKondisiKendaraanActivity)
                                        .load(data.getTampakSisiKiri())
                                        .error(android.R.drawable.stat_notify_error).into(img_tmp_kiri)
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

    private fun setDummy() {
        img_tmp_muka.setImageResource(R.drawable.dummy_kendaraan)
        img_tmp_belakang.setImageResource(R.drawable.dummy_kendaraan)
        img_tmp_kanan.setImageResource(R.drawable.dummy_kendaraan)
        img_tmp_kiri.setImageResource(R.drawable.dummy_kendaraan)
    }

    private fun setPreferences() {
        sharedPreferences = getSharedPreferences(Config.PREF_NAME,
                Activity.MODE_PRIVATE)
        idJaminan = sharedPreferences.getInt(Config.JAMINAN_ID, Config.EMPTY_INT)
        idKredit = sharedPreferences.getInt(Config.KREDIT_ID, Config.EMPTY_INT)
    }

    override fun onClick(p0: View) {
        when(p0.id){
            R.id.btn_selesai_kendaraan -> {
                flag = 1
                next()
            }
            R.id.img_tmp_muka -> {
                flagPhoto = 0
                selectImage()
            }
            R.id.img_tmp_belakang -> {
                flagPhoto = 1
                selectImage()
            }
            R.id.img_tmp_kanan -> {
                flagPhoto = 2
                selectImage()
            }
            R.id.img_tmp_kiri -> {
                flagPhoto = 3
                selectImage()
            }
        }
    }

    private fun next() {
        if(sharedPreferences.getString("from", "") == "repeat"){
            if(img_tmp_muka.drawable != null && img_tmp_belakang.drawable != null && img_tmp_kanan.drawable != null &&
                    img_tmp_kiri.drawable != null ){
                strImage1 = getStringImage((img_tmp_muka.getDrawable() as BitmapDrawable).bitmap)
                strImage2 = getStringImage((img_tmp_belakang.getDrawable() as BitmapDrawable).bitmap)
                strImage3 = getStringImage((img_tmp_kanan.getDrawable() as BitmapDrawable).bitmap)
                strImage4 = getStringImage((img_tmp_kiri.getDrawable() as BitmapDrawable).bitmap)
            }
        }
        pDialog = ProgressDialog.show(this,
                "",
                "Tunggu Sebentar!")
        if(strImage1 != "" && strImage2 != "" && strImage3 != "" && strImage4 != "" ){
            submitData()
        }else{
            pDialog!!.dismiss()
            Toast.makeText(applicationContext, R.string.cekData, Toast.LENGTH_LONG).show()
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
                            if(flag==0){
                                startActivity(Intent(this@FotoKondisiKendaraanActivity, PemeriksaanJaminanActivity::class.java))
                            }else{
                                startActivity(Intent(this@FotoKondisiKendaraanActivity, SpalshActivity::class.java))
                                finishAffinity()
                            }
                        } else {
                            Log.e("aproval", response.raw().toString())
                            Toast.makeText(baseContext, result.getMessage(), Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

        })
    }

    private fun submitData() {
        Log.d("fotoKendaraan", "IdJaminan = " + idJaminan)
        val service by lazy {
            RestClient.getClient(this)
        }
        val call = service.sendJaminanKendaraanFoto(idJaminan, strImage1, strImage2, strImage3, strImage4)
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
                            startActivity(Intent(applicationContext, FotoKendaraan2Activity::class.java))
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

            if (flagPhoto == 0){
                strImage1 = getStringImage(selectedImage)
                img_tmp_muka.setImageBitmap(selectedImage)
            }else if(flagPhoto == 1){
                strImage2 = getStringImage(selectedImage)
                img_tmp_belakang.setImageBitmap(selectedImage)
            }else if(flagPhoto == 2){
                strImage3 = getStringImage(selectedImage)
                img_tmp_kanan.setImageBitmap(selectedImage)
            }else if(flagPhoto == 3){
                strImage4 = getStringImage(selectedImage)
                img_tmp_kiri.setImageBitmap(selectedImage)
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
