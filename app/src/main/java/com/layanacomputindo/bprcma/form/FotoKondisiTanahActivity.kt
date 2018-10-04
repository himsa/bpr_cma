package com.layanacomputindo.bprcma.form

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
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
import com.layanacomputindo.bprcma.model.FotoTanah
import com.layanacomputindo.bprcma.model.Result
import com.layanacomputindo.bprcma.model.UserId
import com.layanacomputindo.bprcma.rest.RestClient
import com.layanacomputindo.bprcma.util.Config
import com.squareup.picasso.Picasso
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.activity_foto_kondisi_tanah.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream

class FotoKondisiTanahActivity : AppCompatActivity(), View.OnClickListener {
    var flagPhoto = 0

    private lateinit var sharedPreferences: SharedPreferences

    var idJaminan = 0

    var strImage1 = ""
    var strImage2 = ""
    var strImage3 = ""
    var strImage4 = ""
    var strImage5 = ""
    var strImage6 = ""
    var strImage7 = ""
    var strImage8 = ""

    private var pDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_foto_kondisi_tanah)
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

        et_rmh_muka.setOnClickListener(this)
        et_rmh_blkng.setOnClickListener(this)
        et_rmh_kanan.setOnClickListener(this)
        et_rmh_kiri.setOnClickListener(this)
        et_rmh_dalam.setOnClickListener(this)
        et_rmh_lain.setOnClickListener(this)
        et_rmh_sktr1.setOnClickListener(this)
        et_rmh_sktr2.setOnClickListener(this)

        btn_next_foto_tanah.setOnClickListener(this)
    }

    private fun getData() {
        val service by lazy {
            RestClient.getClient(this)
        }
        val call = service.getJaminanTanahFoto(idJaminan)
        call.enqueue(object: Callback<Result<FotoTanah>>{
            override fun onFailure(call: Call<Result<FotoTanah>>?, t: Throwable?) {
                pDialog!!.dismiss()
                Log.e("on Failure", t.toString())
                Toast.makeText(applicationContext, R.string.cekkoneksi, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<Result<FotoTanah>>, response: Response<Result<FotoTanah>>) {
                Log.d("debitur", "Status Code = " + response.code())
                if(response.isSuccessful){
                    val result = response.body()
                    if (result != null) {
                        if (result.getStatus()!!) {
                            val data = result.getData()
                            pDialog!!.dismiss()
                            if (data != null){
                                Picasso.with(this@FotoKondisiTanahActivity)
                                        .load(data.getTampakMuka())
                                        .error(android.R.drawable.stat_notify_error).into(et_rmh_muka)
                                Picasso.with(this@FotoKondisiTanahActivity)
                                        .load(data.getTampakBelakang())
                                        .error(android.R.drawable.stat_notify_error).into(et_rmh_blkng)
                                Picasso.with(this@FotoKondisiTanahActivity)
                                        .load(data.getTampakSisiKanan())
                                        .error(android.R.drawable.stat_notify_error).into(et_rmh_kanan)
                                Picasso.with(this@FotoKondisiTanahActivity)
                                        .load(data.getTampakSisiKiri())
                                        .error(android.R.drawable.stat_notify_error).into(et_rmh_kiri)
                                Picasso.with(this@FotoKondisiTanahActivity)
                                        .load(data.getTampakBelakang())
                                        .error(android.R.drawable.stat_notify_error).into(et_rmh_blkng)
                                Picasso.with(this@FotoKondisiTanahActivity)
                                        .load(data.getTampakDalam())
                                        .error(android.R.drawable.stat_notify_error).into(et_rmh_dalam)
                                Picasso.with(this@FotoKondisiTanahActivity)
                                        .load(data.getTampakLain())
                                        .error(android.R.drawable.stat_notify_error).into(et_rmh_lain)
                                Picasso.with(this@FotoKondisiTanahActivity)
                                        .load(data.getTampakSekitar1())
                                        .error(android.R.drawable.stat_notify_error).into(et_rmh_sktr1)
                                Picasso.with(this@FotoKondisiTanahActivity)
                                        .load(data.getTampakSekitar2())
                                        .error(android.R.drawable.stat_notify_error).into(et_rmh_sktr2)
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

    override fun onClick(p0: View) {
        when(p0.id){
            R.id.et_rmh_muka -> {
                flagPhoto = 0
                selectImage()
            }
            R.id.et_rmh_blkng -> {
                flagPhoto = 1
                selectImage()
            }
            R.id.et_rmh_kanan -> {
                flagPhoto = 2
                selectImage()
            }
            R.id.et_rmh_kiri -> {
                flagPhoto = 3
                selectImage()
            }
            R.id.et_rmh_dalam -> {
                flagPhoto = 4
                selectImage()
            }
            R.id.et_rmh_lain -> {
                flagPhoto = 5
                selectImage()
            }
            R.id.et_rmh_sktr1 -> {
                flagPhoto = 6
                selectImage()
            }
            R.id.et_rmh_sktr2 -> {
                flagPhoto = 7
                selectImage()
            }
            R.id.btn_next_foto_tanah -> {
                if(sharedPreferences.getString(Config.ROLE, "")== "komite"||sharedPreferences.getString(Config.ROLE, "")== "supervisor"){
                    startActivity(Intent(applicationContext, FaktorPenilaianActivity::class.java))
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
        val call = service.sendJaminanTanahFoto(idJaminan, strImage1, strImage2, strImage3, strImage4, strImage5, strImage6, strImage7, strImage8)
        call.enqueue(object : Callback<Result<UserId>>{
            override fun onFailure(call: Call<Result<UserId>>?, t: Throwable?) {
                pDialog!!.dismiss()
                Log.e("on Failure", t.toString())
                Toast.makeText(applicationContext, R.string.cekkoneksi, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<Result<UserId>>, response: Response<Result<UserId>>) {
                Log.d("fotoTanah", "Status Code = " + response.code())
                if(response.isSuccessful){
                    val result = response.body()
                    if (result != null) {
                        if (result.getStatus()!!) {
                            pDialog!!.dismiss()
                            startActivity(Intent(applicationContext, FaktorPenilaianActivity::class.java))
                        } else {
                            Log.e("fotoTanah", response.raw().toString())
                            Toast.makeText(baseContext, result.getMessage(), Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

        })
    }

    private fun setDummy() {
        et_rmh_muka.setImageResource(R.drawable.dummy_rumah)
        et_rmh_blkng.setImageResource(R.drawable.dummy_rumah)
        et_rmh_kanan.setImageResource(R.drawable.dummy_rumah)
        et_rmh_kiri.setImageResource(R.drawable.dummy_rumah)
        et_rmh_dalam.setImageResource(R.drawable.dummy_rumah)
        et_rmh_lain.setImageResource(R.drawable.dummy_rumah)
    }

    private fun setPreferences() {
        sharedPreferences = getSharedPreferences(Config.PREF_NAME,
                Activity.MODE_PRIVATE)
        idJaminan = sharedPreferences.getInt(Config.JAMINAN_ID, Config.EMPTY_INT)
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
                et_rmh_muka.setImageBitmap(selectedImage)
            }else if(flagPhoto == 1){
                strImage2 = getStringImage(selectedImage)
                et_rmh_blkng.setImageBitmap(selectedImage)
            }else if(flagPhoto == 2){
                strImage3 = getStringImage(selectedImage)
                et_rmh_kanan.setImageBitmap(selectedImage)
            }else if(flagPhoto == 3){
                strImage4 = getStringImage(selectedImage)
                et_rmh_kiri.setImageBitmap(selectedImage)
            }else if(flagPhoto == 4){
                strImage5 = getStringImage(selectedImage)
                et_rmh_dalam.setImageBitmap(selectedImage)
            }else if(flagPhoto == 5){
                strImage6 = getStringImage(selectedImage)
                et_rmh_lain.setImageBitmap(selectedImage)
            }else if(flagPhoto == 6){
                strImage7 = getStringImage(selectedImage)
                et_rmh_sktr1.setImageBitmap(selectedImage)
            }else if(flagPhoto == 7){
                strImage8 = getStringImage(selectedImage)
                et_rmh_sktr2.setImageBitmap(selectedImage)
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
