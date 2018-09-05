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
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import com.layanacomputindo.bprcma.R
import com.layanacomputindo.bprcma.model.Result
import com.layanacomputindo.bprcma.model.UserId
import com.layanacomputindo.bprcma.rest.RestClient
import com.layanacomputindo.bprcma.util.Config
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
            setDummy()
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
                pDialog = ProgressDialog.show(this,
                        "",
                        "Tunggu Sebentar!")
                submitData()
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

    private fun submitData() {
        val service by lazy {
            RestClient.getClient(this)
        }
        val call = service.sendPekerjaanDebitur(idDebitur, lat.toString(), long.toString(), uploadJenis)
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
                            sendFotoPekerjaan()
                        } else {
                            Log.e("debitur", response.raw().toString())
                            Toast.makeText(baseContext, result.getMessage(), Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

        })
    }

    private fun sendFotoPekerjaan() {
        val service by lazy {
            RestClient.getClient(this)
        }
        val call = service.sendFotoPekerjaanDebitur(idDebitur, strImage1, strImage2, strImage3, strImage4)
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
                            startActivity(Intent(this@InfoUsahaPkrjnPart1Activity, PermohonanKreditActivity::class.java))
                        } else {
                            Log.e("debitur", response.raw().toString())
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
