package com.layanacomputindo.bprcma.form

import android.Manifest
import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import android.util.Base64
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.layanacomputindo.bprcma.R
import com.layanacomputindo.bprcma.model.*
import com.layanacomputindo.bprcma.rest.RestClient
import com.layanacomputindo.bprcma.util.Config
import com.squareup.picasso.Picasso
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import im.delight.android.location.SimpleLocation
import kotlinx.android.synthetic.main.activity_info_nasabah_part3.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream

class InfoNasabahPart3Activity : AppCompatActivity(), View.OnClickListener {
    var flagPhoto = 0
    var uploadStatus = ""
    private var lat: Double = 0.0
    private var long: Double = 0.0
    private lateinit var location: SimpleLocation

    private lateinit var sharedPreferences: SharedPreferences
    var idDebitur = 0
    var strImage1 = ""
    var strImage2 = ""
    var strImage3 = ""

    private var pDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_nasabah_part3)
        setSupportActionBar(toolbar as Toolbar?)
        supportActionBar?.run {
            title = getString(R.string.informasi_nasabah)
            setDisplayHomeAsUpEnabled(true)
        }
        setPermission()
        setPreferences()


        // construct a new instance of SimpleLocation
        location = SimpleLocation(this)

        // if we can't access the location yet
        if (!location.hasLocationEnabled()) {
            // ask the user to enable location access
            SimpleLocation.openSettings(this)
        }

        val statusAdapter = ArrayAdapter.createFromResource(this, R.array.status, android.R.layout.simple_spinner_item)
        sp_status.setAdapter(statusAdapter, 0)
        sp_status.setOnSpinnerItemClickListener({ position, itemAtPosition ->
            uploadStatus = itemAtPosition
        })
        if(sharedPreferences.getString("from", "") == "repeat"){
            pDialog = ProgressDialog.show(this,
                    "",
                    "Tunggu Sebentar!")
            getData()
        }

        btn_next_inf_nas_3.setOnClickListener(this)
        img_st_tmp_tgl.setOnClickListener(this)
        img_tmp_tgl_1.setOnClickListener(this)
        img_tmp_tgl_2.setOnClickListener(this)
        pick_location.setOnClickListener(this)
    }

    private fun getData() {
        Log.d("debitur", "Id = " + idDebitur)
        val service by lazy {
            RestClient.getClient(this)
        }
        val call = service.getTempatTinggal(idDebitur)
        call.enqueue(object: Callback<Result<TempatTinggal>>{
            override fun onFailure(call: Call<Result<TempatTinggal>>?, t: Throwable?) {
                pDialog!!.dismiss()
                Log.e("on Failure", t.toString())
                Toast.makeText(applicationContext, R.string.cekkoneksi, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<Result<TempatTinggal>>, response: Response<Result<TempatTinggal>>) {
                Log.d("debitur", "Status Code = " + response.code())
                if(response.isSuccessful){
                    val result = response.body()
                    if (result != null) {
                        if (result.getStatus()!!) {
                            val data = result.getData()
                            pDialog!!.dismiss()
                            if (data != null){
                                when(data.getStatus()){
                                    "milik sendiri" -> {
                                        sp_status.setSelection(0)
                                    }
                                    "milik orang tua" -> {
                                        sp_status.setSelection(1)
                                    }
                                    "sewa" -> {
                                        sp_status.setSelection(2)
                                    }
                                    else -> {
                                        sp_status.setSelection(3)
                                    }
                                }
                                Picasso.with(this@InfoNasabahPart3Activity)
                                        .load(data.getFotoDokumen())
                                        .error(android.R.drawable.stat_notify_error).into(img_st_tmp_tgl)
                                Picasso.with(this@InfoNasabahPart3Activity)
                                        .load(data.getFoto1())
                                        .error(android.R.drawable.stat_notify_error).into(img_tmp_tgl_1)
                                Picasso.with(this@InfoNasabahPart3Activity)
                                        .load(data.getFoto2())
                                        .error(android.R.drawable.stat_notify_error).into(img_tmp_tgl_2)
                                if(data.getLatitude().isNullOrEmpty() && data.getLongitude().isNullOrEmpty()){

                                }else{
                                    lat = data.getLatitude()!!.toDouble()
                                    long = data.getLongitude()!!.toDouble()
                                }
                                tv_location.text = resources.getText(R.string.lokasi_disimpan)
                                pick_location.isClickable = false
                                pick_location.setImageResource(R.drawable.icon_lokasi_grey)
                            }

                        } else {
                            Log.e("debitur", response.raw().toString())
                            Toast.makeText(baseContext, result.getMessage(), Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

        })
    }

    private fun getFoto() {
        val service by lazy {
            RestClient.getClient(this)
        }
        val call = service.getFotoTempatTinggal(idDebitur)
        call.enqueue(object: Callback<Results<FotoTempatTinggal>>{
            override fun onFailure(call: Call<Results<FotoTempatTinggal>>?, t: Throwable?) {
                pDialog!!.dismiss()
                Log.e("on Failure", t.toString())
                Toast.makeText(applicationContext, R.string.cekkoneksi, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<Results<FotoTempatTinggal>>, response: Response<Results<FotoTempatTinggal>>) {
                Log.d("debitur", "Status Code = " + response.code())
                if(response.isSuccessful){
                    val result = response.body()
                    if (result != null) {
                        if (result.getStatus()!!) {
                            val data = result.getData()
                            pDialog!!.dismiss()
                            if (data != null){

                                Picasso.with(this@InfoNasabahPart3Activity)
                                        .load(data[0].getFoto())
                                        .error(android.R.drawable.stat_notify_error).into(img_st_tmp_tgl)
                                Picasso.with(this@InfoNasabahPart3Activity)
                                        .load(data[1].getFoto())
                                        .error(android.R.drawable.stat_notify_error).into(img_tmp_tgl_1)
                                Picasso.with(this@InfoNasabahPart3Activity)
                                        .load(data[2].getFoto())
                                        .error(android.R.drawable.stat_notify_error).into(img_tmp_tgl_2)
                            }

                        } else {
                            Log.e("debitur", response.raw().toString())
                            Toast.makeText(baseContext, result.getMessage(), Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

        })
    }

    private fun setDummy() {
        sp_status.setSelection(2)
        img_st_tmp_tgl.setImageResource(R.drawable.dummy_pbb)
        img_tmp_tgl_1.setImageResource(R.drawable.dummy_rumah)
        img_tmp_tgl_2.setImageResource(R.drawable.dummy_rumah)
        tv_location.text = resources.getText(R.string.lokasi_disimpan)
        tv_location.isClickable = false
        pick_location.setImageResource(R.drawable.icon_lokasi_grey)
    }

    private fun setPreferences() {
        sharedPreferences = getSharedPreferences(Config.PREF_NAME,
                Activity.MODE_PRIVATE)
        idDebitur = sharedPreferences.getInt(Config.DEBITUR_ID, Config.EMPTY_INT)
    }

    private fun setPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION),
                    101)
        }
    }

    override fun onClick(p0: View) {
        when(p0.id){
            R.id.back_nasabah -> {
                onBackPressed()
            }
            R.id.btn_next_inf_nas_3 -> {
                next()
            }
            R.id.img_st_tmp_tgl -> {
                flagPhoto = 0
                selectImage()
            }
            R.id.img_tmp_tgl_1 -> {
                flagPhoto = 1
                selectImage()
            }
            R.id.img_tmp_tgl_2 -> {
                flagPhoto = 2
                selectImage()
            }
            R.id.pick_location -> {
                lat  = location.latitude
                long = location.longitude
                tv_location.text = resources.getText(R.string.lokasi_disimpan)
                pick_location.setImageResource(R.drawable.icon_lokasi_grey)
            }
        }
    }

    private fun next() {
        if(sharedPreferences.getString("from", "") == "repeat"){
            if(img_st_tmp_tgl.drawable != null && img_tmp_tgl_1.drawable != null && img_tmp_tgl_2.drawable != null){
                strImage1 = getStringImage((img_st_tmp_tgl.getDrawable() as BitmapDrawable).bitmap)
                strImage2 = getStringImage((img_tmp_tgl_1.getDrawable() as BitmapDrawable).bitmap)
                strImage3 = getStringImage((img_tmp_tgl_2.getDrawable() as BitmapDrawable).bitmap)
            }
        }
        pDialog = ProgressDialog.show(this,
                "",
                "Tunggu Sebentar!")
        if(uploadStatus != "" && strImage1 != "" && strImage2 != "" && strImage3 !="" && lat.toString() != "" &&
                long.toString() != ""){
            submitData()
        }else{
            pDialog!!.dismiss()
            Toast.makeText(applicationContext, R.string.cekData, Toast.LENGTH_LONG).show()
        }
    }

    private fun submitData() {
        val service by lazy {
            RestClient.getClient(this)
        }
        val call = service.sendTempatTinggalDebitur(idDebitur, lat.toString(), long.toString(), uploadStatus, strImage1, strImage2, strImage3)
        call.enqueue(object : Callback<Result<UserId>>{
            override fun onResponse(call: Call<Result<UserId>>, response: Response<Result<UserId>>) {
                Log.d("tmp tggl", "Status Code = " + response.code())
                if(response.isSuccessful){
                    val result = response.body()
                    if (result != null) {
                        if (result.getStatus()!!) {
                            pDialog!!.dismiss()
                            startActivity(Intent(this@InfoNasabahPart3Activity, InfoUsahaPkrjnPart1Activity::class.java))
                        } else {
                            pDialog!!.dismiss()
                            Log.e("tmp tggl", response.raw().toString())
                            Toast.makeText(baseContext, result.getMessage(), Toast.LENGTH_SHORT).show()
                        }
                    }
                }else{
                    pDialog!!.dismiss()
                    Log.e("tmp tggl", response.raw().toString())
                    Toast.makeText(baseContext, response.message(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Result<UserId>>, t: Throwable?) {
                pDialog!!.dismiss()
                Log.e("on Failure", t.toString())
                Toast.makeText(applicationContext, R.string.cekkoneksi, Toast.LENGTH_LONG).show()
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
                img_st_tmp_tgl.setImageBitmap(selectedImage)
            }else if(flagPhoto == 1){
                strImage2 = getStringImage(selectedImage)
                img_tmp_tgl_1.setImageBitmap(selectedImage)
            }else if(flagPhoto == 2){
                strImage3 = getStringImage(selectedImage)
                img_tmp_tgl_2.setImageBitmap(selectedImage)
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
