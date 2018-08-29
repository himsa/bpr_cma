package com.layanacomputindo.bprcma.form

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.util.Base64
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.layanacomputindo.bprcma.MenuActivity
import com.layanacomputindo.bprcma.R
import com.layanacomputindo.bprcma.model.Result
import com.layanacomputindo.bprcma.model.UserId
import com.layanacomputindo.bprcma.rest.RestClient
import com.layanacomputindo.bprcma.util.Config
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
        setContentView(R.layout.activity_foto_kondisi_kendaraan)
        setSupportActionBar(toolbar as Toolbar?)
        supportActionBar?.run {
            title = getString(R.string.txt_kendaraan)
            setDisplayHomeAsUpEnabled(true)
        }

        setPreferences()

        if(sharedPreferences.getString("from", "") == "repeat"){
            setDummy()
        }

        btn_selesai_kendaraan.setOnClickListener(this)
        img_tmp_muka.setOnClickListener(this)
        img_tmp_belakang.setOnClickListener(this)
        img_tmp_kanan.setOnClickListener(this)
        img_tmp_kiri.setOnClickListener(this)
        img_nmr_rangka.setOnClickListener(this)
        img_nmr_mesin.setOnClickListener(this)
        img_bpkb.setOnClickListener(this)
        img_stnk.setOnClickListener(this)
    }

    private fun setDummy() {
        img_tmp_muka.setImageResource(R.drawable.dummy_kendaraan)
        img_tmp_belakang.setImageResource(R.drawable.dummy_kendaraan)
        img_tmp_kanan.setImageResource(R.drawable.dummy_kendaraan)
        img_tmp_kiri.setImageResource(R.drawable.dummy_kendaraan)
        img_nmr_mesin.setImageResource(R.drawable.dummy_nomor_mesin)
        img_nmr_rangka.setImageResource(R.drawable.dummy_nomor_rangka)
        img_bpkb.setImageResource(R.drawable.dummy_bpkb)
        img_stnk.setImageResource(R.drawable.dummy_kendaraan)
    }

    private fun setPreferences() {
        sharedPreferences = getSharedPreferences(Config.PREF_NAME,
                Activity.MODE_PRIVATE)
        idJaminan = sharedPreferences.getInt(Config.JAMINAN_ID, Config.EMPTY_INT)
    }

    override fun onClick(p0: View) {
        when(p0.id){
            R.id.btn_selesai_kendaraan -> {
//                pDialog = ProgressDialog.show(this,
//                        "",
//                        "Tunggu Sebentar!")
//                submitData()
                startActivity(Intent(applicationContext, MenuActivity::class.java))
                finishAffinity()
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

    private fun submitData() {
        val service by lazy {
            RestClient.getClient(this)
        }
        val call = service.sendJaminanKendaraanFoto(idJaminan, strImage1, strImage2, strImage3, strImage4, strImage5,
                strImage6, strImage7, strImage8)
        call.enqueue(object : Callback<Result<UserId>>{
            override fun onFailure(call: Call<Result<UserId>>?, t: Throwable?) {
                pDialog!!.dismiss()
                Log.e("on Failure", t.toString())
                Toast.makeText(applicationContext, R.string.cekkoneksi, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<Result<UserId>>, response: Response<Result<UserId>>) {
                Log.d("fotoKendaraan", "Status Code = " + response.code())
                if(response.isSuccessful){
                    val result = response.body()
                    if (result != null) {
                        if (result.getStatus()!!) {
                            pDialog!!.dismiss()
                            startActivity(Intent(applicationContext, MenuActivity::class.java))
                            finishAffinity()
                        } else {
                            Log.e("fotoKendaraan", response.raw().toString())
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
            }else if(flagPhoto == 4){
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
        bmp.compress(Bitmap.CompressFormat.JPEG, 50, baos)
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
