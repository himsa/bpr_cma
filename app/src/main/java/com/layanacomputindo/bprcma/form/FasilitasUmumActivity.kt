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
import com.layanacomputindo.bprcma.R
import com.layanacomputindo.bprcma.model.FasilitasUmum
import com.layanacomputindo.bprcma.model.Result
import com.layanacomputindo.bprcma.model.TanahBangunan
import com.layanacomputindo.bprcma.model.UserId
import com.layanacomputindo.bprcma.rest.RestClient
import com.layanacomputindo.bprcma.util.Config
import com.squareup.picasso.Picasso
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import im.delight.android.location.SimpleLocation
import kotlinx.android.synthetic.main.activity_fasilitas_umum.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream

class FasilitasUmumActivity : AppCompatActivity(), View.OnClickListener {
    var flagPhoto = 0
    var uploadStatus = ""
    private var lat: Double = 0.0
    private var long: Double = 0.0
    private lateinit var location: SimpleLocation

    private lateinit var sharedPreferences: SharedPreferences
    var idJaminan = 0
    var strImageDenah = ""

    private var pDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fasilitas_umum)
        setSupportActionBar(toolbar as Toolbar?)
        supportActionBar?.run {
            title = getString(R.string.pemr_jaminan)
            setDisplayHomeAsUpEnabled(true)
        }

        setPreferences()

        // construct a new instance of SimpleLocation
        location = SimpleLocation(this)

        // if we can't access the location yet
        if (!location.hasLocationEnabled()) {
            // ask the user to enable location access
            SimpleLocation.openSettings(this)
        }

        if(sharedPreferences.getString("from", "") == "repeat"){
            pDialog = ProgressDialog.show(this,
                    "",
                    "Tunggu Sebentar!")
            getData()
            getDenah()
        }

        btn_next_fasilitas_umum.setOnClickListener(this)
        pick_location.setOnClickListener(this)
        img_denah.setOnClickListener(this)
    }

    private fun getDenah() {
        val service by lazy {
            RestClient.getClient(this)
        }
        val call = service.getJaminanTanah(idJaminan)
        call.enqueue(object: Callback<Result<TanahBangunan>>{
            override fun onFailure(call: Call<Result<TanahBangunan>>?, t: Throwable?) {
                pDialog!!.dismiss()
                Log.e("on Failure", t.toString())
                Toast.makeText(applicationContext, R.string.cekkoneksi, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<Result<TanahBangunan>>, response: Response<Result<TanahBangunan>>) {
                Log.d("tanah", "Status Code = " + response.code())
                if(response.isSuccessful){
                    val result = response.body()
                    if (result != null) {
                        if (result.getStatus()!!) {
                            val data = result.getData()
                            pDialog!!.dismiss()
                            if (data != null){
                                Picasso.with(this@FasilitasUmumActivity)
                                        .load(data.getDenahLokasi())
                                        .error(android.R.drawable.stat_notify_error).into(img_denah)
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
                            Log.e("tanah", response.raw().toString())
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
        val call = service.getJaminanTanahFasilitasUmum(idJaminan)
        call.enqueue(object: Callback<Result<FasilitasUmum>>{
            override fun onFailure(call: Call<Result<FasilitasUmum>>?, t: Throwable?) {
                pDialog!!.dismiss()
                Log.e("on Failure", t.toString())
                Toast.makeText(applicationContext, R.string.cekkoneksi, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<Result<FasilitasUmum>>, response: Response<Result<FasilitasUmum>>) {
                Log.d("fasilitasumum", "Status Code = " + response.code())
                if(response.isSuccessful){
                    val result = response.body()
                    if (result != null) {
                        if (result.getStatus()!!) {
                            val data = result.getData()
                            pDialog!!.dismiss()
                            if (data != null){
                                sw_angktn_umum.isChecked = set(data.getAngkutanUmum())
                                sw_psr.isChecked = set(data.getPasar())
                                sw_sklh.isChecked = set(data.getSekolah())
                                sw_rs.isChecked = set(data.getRumahSakitPuskesmas())
                                sw_hbrn.isChecked = set(data.getHiburan())
                                sw_prkntrn.isChecked = set(data.getPerkantoran())
                                sw_olg.isChecked = set(data.getSaranaOlahRaga())
                                sw_tmp_ibdh.isChecked = set(data.getTempatIbadah())
                            }
                        } else {
                            pDialog!!.dismiss()
                            Log.e("fasilitasumum", response.raw().toString())
                            Toast.makeText(baseContext, result.getMessage(), Toast.LENGTH_SHORT).show()
                        }
                    }
                }else {
                    pDialog!!.dismiss()
                    Log.e("fasilitasumum", response.raw().toString())
                    Toast.makeText(baseContext, response.message(), Toast.LENGTH_SHORT).show()
                }
            }

        })
    }

    private fun set(data: Any?): Boolean {
        return data.toString().equals("1", true)
    }

    private fun setdata(data: Boolean): Int {
        return if (data)  1 else 0
    }

    private fun setPreferences() {
        sharedPreferences = getSharedPreferences(Config.PREF_NAME,
                Activity.MODE_PRIVATE)
        idJaminan = sharedPreferences.getInt(Config.JAMINAN_ID, Config.EMPTY_INT)
    }

    override fun onClick(p0: View) {
        when(p0.id){
            R.id.btn_next_fasilitas_umum -> {
                if(sharedPreferences.getString(Config.ROLE, "")== "komite"||sharedPreferences.getString(Config.ROLE, "")== "supervisor"){
                    startActivity(Intent(applicationContext, FotoKondisiTanahActivity::class.java))
                }else{
                    pDialog = ProgressDialog.show(this,
                            "",
                            "Tunggu Sebentar!")
                    submitData()
                }
            }
            R.id.img_denah -> {
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
        val call = service.sendTanahFasilitasUmum(idJaminan, setdata(sw_angktn_umum.isChecked), setdata(sw_psr.isChecked), setdata(sw_sklh.isChecked), setdata(sw_rs.isChecked),
                setdata(sw_hbrn.isChecked), setdata(sw_prkntrn.isChecked), setdata(sw_olg.isChecked), setdata(sw_tmp_ibdh.isChecked),
                strImageDenah, lat.toString(), long.toString())
        call.enqueue(object : Callback<Result<UserId>> {
            override fun onFailure(call: Call<Result<UserId>>?, t: Throwable?) {
                pDialog!!.dismiss()
                Log.e("on Failure", t.toString())
                Toast.makeText(applicationContext, R.string.cekkoneksi, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<Result<UserId>>, response: Response<Result<UserId>>) {
                Log.d("FasilitasUmum", "Status Code = " + response.code())
                if(response.isSuccessful){
                    val result = response.body()
                    if (result != null) {
                        if (result.getStatus()!!) {
                            pDialog!!.dismiss()
                            startActivity(Intent(applicationContext, FotoKondisiTanahActivity::class.java))
                        } else {
                            pDialog!!.dismiss()
                            Log.e("FasilitasUmum", response.raw().toString())
                            Toast.makeText(baseContext, result.getMessage(), Toast.LENGTH_SHORT).show()
                        }
                    }
                }else {
                    pDialog!!.dismiss()
                    Log.e("FasilitasUmum", response.raw().toString())
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
            strImageDenah = getStringImage(selectedImage)
            img_denah.setImageBitmap(selectedImage)
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
