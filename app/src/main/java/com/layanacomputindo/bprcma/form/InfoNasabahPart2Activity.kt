package com.layanacomputindo.bprcma.form

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.text.InputType
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import com.layanacomputindo.bprcma.R
import kotlinx.android.synthetic.main.activity_info_nasabah_part2.*
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.widget.Toast
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import java.io.FileNotFoundException
import android.util.Base64
import android.util.Log
import com.layanacomputindo.bprcma.model.FotoPasangan
import com.layanacomputindo.bprcma.model.Pasangan
import com.layanacomputindo.bprcma.model.Result
import com.layanacomputindo.bprcma.model.UserId
import com.layanacomputindo.bprcma.rest.RestClient
import com.layanacomputindo.bprcma.util.Config
import com.squareup.picasso.Picasso
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.util.*


class InfoNasabahPart2Activity : AppCompatActivity(), View.OnClickListener, DatePickerDialog.OnDateSetListener {
    val REQUEST_IMAGE_ISTRI_GALERY = 3
    private lateinit var sharedPreferences: SharedPreferences
    var idDebitur = 0
    var strImagePasangan = ""

    private var pDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_nasabah_part2)
        setSupportActionBar(toolbar as Toolbar?)
        supportActionBar?.run {
            title = getString(R.string.informasi_nasabah)
            setDisplayHomeAsUpEnabled(true)
        }

        setPreferences()
        if(sharedPreferences.getString("from", "") == "repeat"){
            pDialog = ProgressDialog.show(this,
                    "",
                    "Tunggu Sebentar!")
            add_telp.visibility = View.GONE
            getData()
        }

//        et_alamat.setImeOptions(EditorInfo.IME_ACTION_DONE)
//        et_alamat.setRawInputType(InputType.TYPE_CLASS_TEXT)

        btn_next_inf_nas_2.setOnClickListener(this)
        btn_skip_inf_nas_2.setOnClickListener(this)
        img_istri.setOnClickListener(this)
        tv_dob.setOnClickListener(this)
        add_telp.setOnClickListener (this)
    }

    private fun next() {
        if(sharedPreferences.getString("from", "") == "repeat"){
            if(strImagePasangan != ""){
                strImagePasangan = getStringImage((img_istri.getDrawable() as BitmapDrawable).bitmap)
            }
        }

        pDialog = ProgressDialog.show(this,
                "",
                "Tunggu Sebentar!")
        if(et_nama.text.toString() != "" && et_tmp_lhr.text.toString() != "" &&
                tv_dob.text.toString() != "" && et_no_ktp.text.toString() != "" &&
                et_telp_1.text.toString() != "" && et_alamat.text.toString() != "" &&
                strImagePasangan != ""){
            submitPasanganDebitur()
        }else{
            pDialog!!.dismiss()
            Toast.makeText(applicationContext, R.string.cekData, Toast.LENGTH_LONG).show()
        }
    }

    private fun getData() {
        val service by lazy {
            RestClient.getClient(this)
        }
        val call = service.getPasangan(idDebitur)
        call.enqueue(object: Callback<Result<Pasangan>>{
            override fun onFailure(call: Call<Result<Pasangan>>?, t: Throwable?) {
                pDialog!!.dismiss()
                Log.e("on Failure", t.toString())
                Toast.makeText(applicationContext, R.string.cekkoneksi, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<Result<Pasangan>>, response: Response<Result<Pasangan>>) {
                Log.d("debitur", "Status Code = " + response.code())
                if(response.isSuccessful){
                    val result = response.body()
                    if (result != null) {
                        if (result.getStatus()!!) {
                            val data = result.getData()
                            pDialog!!.dismiss()
                            if (data != null){
                                et_nama.setText(data.getNama())
                                et_tmp_lhr.setText(data.getTempatLahir())
                                tv_dob.text = data.getTanggalLahir()
                                et_no_ktp.setText(data.getNoKtp())
//                                et_telp_1.setText(data.getT)
                                et_alamat.setText(data.getAlamat())
                                getFoto(idDebitur)
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

    private fun getFoto(idDebitur: Int) {
        val service by lazy {
            RestClient.getClient(this)
        }
        val call = service.getFotoPasangan(idDebitur)
        call.enqueue(object: Callback<Result<FotoPasangan>>{
            override fun onFailure(call: Call<Result<FotoPasangan>>?, t: Throwable?) {
                pDialog!!.dismiss()
                Log.e("on Failure", t.toString())
                Toast.makeText(applicationContext, R.string.cekkoneksi, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<Result<FotoPasangan>>, response: Response<Result<FotoPasangan>>) {
                Log.d("debitur", "Status Code = " + response.code())
                if(response.isSuccessful){
                    val result = response.body()
                    if (result != null) {
                        if (result.getStatus()!!) {
                            val data = result.getData()
                            pDialog!!.dismiss()
                            if (data != null){
                                Picasso.with(this@InfoNasabahPart2Activity)
                                        .load(data.getFoto())
                                        .error(android.R.drawable.stat_notify_error).into(img_istri)
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

    private fun setPreferences() {
        sharedPreferences = getSharedPreferences(Config.PREF_NAME,
                Activity.MODE_PRIVATE)
        idDebitur = sharedPreferences.getInt(Config.DEBITUR_ID, Config.EMPTY_INT)
    }

    override fun onClick(p0: View) {
        when (p0.id) {
            R.id.btn_skip_inf_nas_2 -> {
                val intent = Intent(this, InfoNasabahPart3Activity::class.java)
                startActivity(intent)
            }
            R.id.btn_next_inf_nas_2 -> {
                next()
            }
            R.id.img_istri -> {
                selectImage(REQUEST_IMAGE_ISTRI_GALERY)
            }
            R.id.tv_dob -> {
                selectDob()
            }
            R.id.add_telp -> {
                cv_telp2.visibility = View.VISIBLE
                add_telp.visibility = View.GONE
            }
        }
    }

    private fun submitPasanganDebitur() {
        pasanganDebitur(idDebitur)
    }

    private fun pasanganDebitur(idDebitur: Int) {
        val service by lazy {
            RestClient.getClient(this)
        }
        val call = service.sendPasanganDebitur(idDebitur, et_nama.text.toString(), et_tmp_lhr.text.toString(),
                tv_dob.text.toString(), et_no_ktp.text.toString(), et_alamat.text.toString())
        call.enqueue(object : Callback<Result<UserId>> {
            override fun onResponse(call: Call<Result<UserId>>, response: Response<Result<UserId>>) {
                Log.d("debitur", "Status Code = " + response.code())
                if(response.isSuccessful){
                    val result = response.body()
                    if (result != null) {
                        if (result.getStatus()!!) {
                            sendTelpPasangan(idDebitur)
                        } else {
                            Log.e("debitur", response.raw().toString())
                            Toast.makeText(baseContext, result.getMessage(), Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

            override fun onFailure(call: Call<Result<UserId>>, t: Throwable?) {
                pDialog!!.dismiss()
                Log.e("on Failure", t.toString())
                Toast.makeText(applicationContext, R.string.cekkoneksi, Toast.LENGTH_LONG).show()
            }

        })
    }

    private fun sendTelpPasangan(idDebitur: Int) {
        val service by lazy {
            RestClient.getClient(this)
        }
        val call = service.sendTelpPasanganDebitur(idDebitur, et_telp_1.text.toString())
        call.enqueue(object: Callback<Result<UserId>>{
            override fun onResponse(call: Call<Result<UserId>>, response: Response<Result<UserId>>) {
                Log.d("telp1", "Status Code = " + response.code())
                if(response.isSuccessful){
                    val result = response.body()
                    if (result != null) {
                        if (result.getStatus()!!) {
                            sendImg(strImagePasangan)
                        } else {
                            Log.e("telp1", response.raw().toString())
                            Toast.makeText(baseContext, result.getMessage(), Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

            override fun onFailure(call: Call<Result<UserId>>, t: Throwable?) {
                pDialog!!.dismiss()
                Log.e("on Failure", t.toString())
                Toast.makeText(applicationContext, R.string.cekkoneksi, Toast.LENGTH_LONG).show()
            }

        })
    }

    private fun sendTelpPasangan2(idDebitur: Int) {
        val service by lazy {
            RestClient.getClient(this)
        }
        val call = service.sendTelpPasanganDebitur(idDebitur, et_telp_2.text.toString())
        call.enqueue(object: Callback<Result<UserId>>{
            override fun onResponse(call: Call<Result<UserId>>, response: Response<Result<UserId>>) {
                Log.d("telp1", "Status Code = " + response.code())
                if(response.isSuccessful){
                    val result = response.body()
                    if (result != null) {
                        if (result.getStatus()!!) {
                            sendImg(strImagePasangan)
                        } else {
                            Log.e("telp1", response.raw().toString())
                            Toast.makeText(baseContext, result.getMessage(), Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

            override fun onFailure(call: Call<Result<UserId>>, t: Throwable?) {
                pDialog!!.dismiss()
                Log.e("on Failure", t.toString())
                Toast.makeText(applicationContext, R.string.cekkoneksi, Toast.LENGTH_LONG).show()
            }

        })
    }

    private fun sendImg(strImagePasangan: String) {
        val service by lazy {
            RestClient.getClient(this)
        }
        var call = service.sendFotoPasanganDebitur(idDebitur, strImagePasangan)
        call.enqueue(object : Callback<Result<UserId>>{
            override fun onResponse(call: Call<Result<UserId>>, response: Response<Result<UserId>>) {
                Log.d("foto", "Status Code = " + response.code())
                if(response.isSuccessful){
                    val result = response.body()
                    if (result != null) {
                        if (result.getStatus()!!) {
                            pDialog!!.dismiss()
                            startActivity(Intent(this@InfoNasabahPart2Activity, InfoNasabahPart3Activity::class.java))

                        } else {
                            Log.e("foto", response.raw().toString())
                            Toast.makeText(baseContext, result.getMessage(), Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

            override fun onFailure(call: Call<Result<UserId>>, t: Throwable?) {
                pDialog!!.dismiss()
                Log.e("on Failure", t.toString())
                Toast.makeText(applicationContext, R.string.cekkoneksi, Toast.LENGTH_LONG).show()
            }

        })
    }

    private fun selectDob() {
        val now = Calendar.getInstance()
        val dpd = DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        )
        dpd.setOkColor(Color.parseColor("#000000"))
        dpd.setCancelColor(Color.parseColor("#000000"))
        dpd.show(fragmentManager, "Datepickerdialog")
    }

    override fun onDateSet(view: DatePickerDialog?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        val date = "" + dayOfMonth + "/" + (monthOfYear + 1) + "/" + year
        tv_dob.setText(date)
    }

    private fun selectImage(i: Int) {
        val intent = Intent(Intent.ACTION_PICK)
        intent.setType("image/*")
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            try {
                val result = CropImage.getActivityResult(data)
                val imageUri = result.getUri()
                val imageStream = contentResolver.openInputStream(imageUri)
                val selectedImage = BitmapFactory.decodeStream(imageStream)
                strImagePasangan = getStringImage(selectedImage)
                img_istri.setImageBitmap(selectedImage)
                Log.d("img upload : ", strImagePasangan)
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show()
            }
        }else {
            Toast.makeText(this, "You haven't picked Image", Toast.LENGTH_LONG).show()
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

