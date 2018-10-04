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
import android.widget.RadioButton
import android.widget.RadioGroup
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
import kotlinx.android.synthetic.main.activity_info_usaha_pkrjn_part1.*
import kotlinx.android.synthetic.main.toolbar_usaha.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream

class InfoUsahaPkrjnPart1Activity : AppCompatActivity(), View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    var flagPhoto = 0
    var uploadJenis = ""
    private var lat: Double = 0.0
    private var long: Double = 0.0
    private lateinit var location: SimpleLocation

    private lateinit var sharedPreferences: SharedPreferences
    var idDebitur = 0
    var strImage1 = ""
    var strImage2 = ""
    var strImage3 = ""
    var strImage4 = ""

    private var pDialog: ProgressDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_usaha_pkrjn_part1)
        setSupportActionBar(toolbar as Toolbar?)
        supportActionBar?.run {
            title = getString(R.string.informasi_usaha)
            setDisplayHomeAsUpEnabled(true)
        }

        setPreferences()

        if(sharedPreferences.getString("from", "") == "repeat"){
            pDialog = ProgressDialog.show(this,
                    "",
                    "Tunggu Sebentar!")
            getData()
        }

        // construct a new instance of SimpleLocation
        location = SimpleLocation(this)

        // if we can't access the location yet
        if (!location.hasLocationEnabled()) {
            // ask the user to enable location access
            SimpleLocation.openSettings(this)
        }

        btn_next_inf_ush_pkrj_1.setOnClickListener(this)
        rg_jenis_utama_pekerjaan.setOnCheckedChangeListener(this)
        pick_location.setOnClickListener(this)
        img_tempat.setOnClickListener(this)
        img_tempat2.setOnClickListener(this)
        img_dokumen.setOnClickListener(this)
        img_dokumen2.setOnClickListener(this)
    }

    private fun getFoto() {
        val service by lazy {
            RestClient.getClient(this)
        }
        val call = service.getFotoPekerjaan(idDebitur)
        call.enqueue(object : Callback<Results<FotoPekerjaan>>{
            override fun onFailure(call: Call<Results<FotoPekerjaan>>?, t: Throwable?) {
                pDialog!!.dismiss()
                Log.e("on Failure", t.toString())
                Toast.makeText(applicationContext, R.string.cekkoneksi, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<Results<FotoPekerjaan>>, response: Response<Results<FotoPekerjaan>>) {
                Log.d("debitur", "Status Code = " + response.code())
                if(response.isSuccessful){
                    val result = response.body()
                    if (result != null) {
                        if (result.getStatus()!!) {
                            val data = result.getData()
                            pDialog!!.dismiss()
                            if (data != null){
                                if(data.lastIndex >=3){
                                    Picasso.with(this@InfoUsahaPkrjnPart1Activity)
                                            .load(data[0].getFoto())
                                            .error(android.R.drawable.stat_notify_error).into(img_tempat)
                                    Picasso.with(this@InfoUsahaPkrjnPart1Activity)
                                            .load(data[1].getFoto())
                                            .error(android.R.drawable.stat_notify_error).into(img_tempat2)
                                    Picasso.with(this@InfoUsahaPkrjnPart1Activity)
                                            .load(data[2].getFoto())
                                            .error(android.R.drawable.stat_notify_error).into(img_dokumen)
                                    Picasso.with(this@InfoUsahaPkrjnPart1Activity)
                                            .load(data[3].getFoto())
                                            .error(android.R.drawable.stat_notify_error).into(img_dokumen2)
                                }else if(data.lastIndex ==2){
                                    Picasso.with(this@InfoUsahaPkrjnPart1Activity)
                                            .load(data[0].getFoto())
                                            .error(android.R.drawable.stat_notify_error).into(img_tempat)
                                    Picasso.with(this@InfoUsahaPkrjnPart1Activity)
                                            .load(data[1].getFoto())
                                            .error(android.R.drawable.stat_notify_error).into(img_tempat2)
                                    Picasso.with(this@InfoUsahaPkrjnPart1Activity)
                                            .load(data[2].getFoto())
                                            .error(android.R.drawable.stat_notify_error).into(img_dokumen)
                                    Picasso.with(this@InfoUsahaPkrjnPart1Activity)
                                }else if(data.lastIndex ==1){
                                    Picasso.with(this@InfoUsahaPkrjnPart1Activity)
                                            .load(data[0].getFoto())
                                            .error(android.R.drawable.stat_notify_error).into(img_tempat)
                                    Picasso.with(this@InfoUsahaPkrjnPart1Activity)
                                            .load(data[1].getFoto())
                                            .error(android.R.drawable.stat_notify_error).into(img_tempat2)
                                }else if(data.lastIndex == 0){
                                    Picasso.with(this@InfoUsahaPkrjnPart1Activity)
                                            .load(data[0].getFoto())
                                            .error(android.R.drawable.stat_notify_error).into(img_tempat)
                                }
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

    private fun getData() {
        val service by lazy {
            RestClient.getClient(this)
        }
        val call = service.getPekerjaan(idDebitur)
        call.enqueue(object: Callback<Result<Pekerjaan>>{
            override fun onFailure(call: Call<Result<Pekerjaan>>?, t: Throwable?) {
                pDialog!!.dismiss()
                Log.e("on Failure", t.toString())
                Toast.makeText(applicationContext, R.string.cekkoneksi, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<Result<Pekerjaan>>, response: Response<Result<Pekerjaan>>) {
                Log.d("debitur", "Status Code = " + response.code())
                if(response.isSuccessful){
                    val result = response.body()
                    if (result != null) {
                        if (result.getStatus()!!) {
                            val data = result.getData()
                            pDialog!!.dismiss()
                            if (data != null){
                                when(data.getJenisUtamaPekerjaan()){
                                    "usaha" -> {
                                        rb_usaha.isChecked = true
                                    }
                                    "karyawan" -> {
                                        rb_karyawan.isChecked = true
                                    }
                                    else -> {
                                        rb_pensiunan.isChecked = true
                                    }
                                }
                                Picasso.with(this@InfoUsahaPkrjnPart1Activity)
                                        .load(data.getFoto1())
                                        .error(android.R.drawable.stat_notify_error).into(img_tempat)
                                Picasso.with(this@InfoUsahaPkrjnPart1Activity)
                                        .load(data.getFoto2())
                                        .error(android.R.drawable.stat_notify_error).into(img_tempat2)
                                Picasso.with(this@InfoUsahaPkrjnPart1Activity)
                                        .load(data.getFotoPendukung1())
                                        .error(android.R.drawable.stat_notify_error).into(img_dokumen)
                                Picasso.with(this@InfoUsahaPkrjnPart1Activity)
                                        .load(data.getFotoPendukung2())
                                        .error(android.R.drawable.stat_notify_error).into(img_dokumen2)
                                if(data.getLatitude().isNullOrEmpty() && data.getLongitude().isNullOrEmpty()){

                                }else{
                                    lat = data.getLatitude()!!.toDouble()
                                    long = data.getLongitude()!!.toDouble()
                                    tv_location.text = resources.getText(R.string.lokasi_disimpan)
                                    pick_location.isClickable = false
                                    pick_location.setImageResource(R.drawable.icon_lokasi_grey)
                                }
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
        rb_karyawan.isChecked = true
        img_tempat.setImageResource(R.drawable.dummy_kantor)
        img_tempat2.setImageResource(R.drawable.dummy_kantor)
        img_dokumen.setImageResource(R.drawable.dummy_sk)
        img_dokumen2.setImageResource(R.drawable.dummy_sk)
        tv_location.text = resources.getText(R.string.lokasi_disimpan)
        pick_location.setImageResource(R.drawable.icon_lokasi_grey)

    }

    private fun setPreferences() {
        sharedPreferences = getSharedPreferences(Config.PREF_NAME,
                Activity.MODE_PRIVATE)
        idDebitur = sharedPreferences.getInt(Config.DEBITUR_ID, Config.EMPTY_INT)
    }

    override fun onCheckedChanged(p0: RadioGroup?, p1: Int) {
        when(p1){
            R.id.rb_usaha -> {
                val radioButton = findViewById(R.id.rb_usaha) as RadioButton

                uploadJenis = radioButton.text.toString()
            }
            R.id.rb_karyawan -> {
                val radioButton = findViewById(R.id.rb_karyawan) as RadioButton

                uploadJenis = radioButton.text.toString()
            }
            R.id.rb_pensiunan -> {
                val radioButton = findViewById(R.id.rb_pensiunan) as RadioButton

                uploadJenis = radioButton.text.toString()
            }
        }
    }

    override fun onClick(p0: View) {
        when(p0.id){
            R.id.btn_next_inf_ush_pkrj_1 -> {
                next()
            }
            R.id.img_tempat -> {
                flagPhoto = 0
                selectImage()
            }
            R.id.img_tempat2 -> {
                flagPhoto = 1
                selectImage()
            }
            R.id.img_dokumen -> {
                flagPhoto = 2
                selectImage()
            }
            R.id.img_dokumen2 -> {
                flagPhoto = 3
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
            if(img_tempat.drawable != null && img_tempat2.drawable != null && img_dokumen.drawable != null && img_dokumen2.drawable != null){
                strImage1 = getStringImage((img_tempat.getDrawable() as BitmapDrawable).bitmap)
                strImage2 = getStringImage((img_tempat2.getDrawable() as BitmapDrawable).bitmap)
                strImage3 = getStringImage((img_dokumen.getDrawable() as BitmapDrawable).bitmap)
                strImage4 = getStringImage((img_dokumen2.getDrawable() as BitmapDrawable).bitmap)
            }
        }

        pDialog = ProgressDialog.show(this,
                "",
                "Tunggu Sebentar!")

        if(uploadJenis != "" && strImage1 != "" && strImage2 != "" && strImage3 != "" && strImage4 != "" && lat.toString() != "" &&
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
        val call = service.sendPekerjaanDebitur(idDebitur, lat.toString(), long.toString(), uploadJenis, strImage1, strImage2, strImage3, strImage4)
        call.enqueue(object : Callback<Result<UserId>>{
            override fun onFailure(call: Call<Result<UserId>>?, t: Throwable?) {
                pDialog!!.dismiss()
                Log.e("on Failure", t.toString())
                Toast.makeText(applicationContext, R.string.cekkoneksi, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<Result<UserId>>, response: Response<Result<UserId>>) {
                Log.d("tmp tggl", "Status Code = " + response.code())
                if(response.isSuccessful){
                    val result = response.body()
                    if (result != null) {
                        if (result.getStatus()!!) {
                            pDialog!!.dismiss()
                            startActivity(Intent(this@InfoUsahaPkrjnPart1Activity, PermohonanKreditActivity::class.java))
                        } else {
                            pDialog!!.dismiss()
                            Log.e("tmp tggl", response.raw().toString())
                            Toast.makeText(baseContext, result.getMessage(), Toast.LENGTH_SHORT).show()
                        }
                    }else{
                        pDialog!!.dismiss()
                        Log.e("tmp tggl", response.raw().toString())
                        Toast.makeText(baseContext, response.message(), Toast.LENGTH_SHORT).show()
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
                img_tempat.setImageBitmap(selectedImage)
            }else if(flagPhoto == 1){
                strImage2 = getStringImage(selectedImage)
                img_tempat2.setImageBitmap(selectedImage)
            }else if(flagPhoto == 2){
                strImage3 = getStringImage(selectedImage)
                img_dokumen.setImageBitmap(selectedImage)
            }else if(flagPhoto == 3){
                strImage4 = getStringImage(selectedImage)
                img_dokumen2.setImageBitmap(selectedImage)
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
