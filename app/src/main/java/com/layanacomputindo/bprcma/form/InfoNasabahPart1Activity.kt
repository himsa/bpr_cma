package com.layanacomputindo.bprcma.form

import android.Manifest
import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import com.layanacomputindo.bprcma.R
import android.provider.MediaStore
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.layanacomputindo.bprcma.model.Debitur
import com.layanacomputindo.bprcma.model.Result
import com.layanacomputindo.bprcma.model.UserId
import com.layanacomputindo.bprcma.rest.RestClient
import com.layanacomputindo.bprcma.util.Config
import com.squareup.picasso.Picasso
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import kotlinx.android.synthetic.main.activity_info_nasabah_part1.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.util.*
import kotlin.collections.ArrayList


class InfoNasabahPart1Activity : AppCompatActivity(), View.OnClickListener, DatePickerDialog.OnDateSetListener {
    val REQUEST_IMAGE_KK_CAPTURE = 1

    val REQUEST_IMAGE_KTP_CAPTURE = 2

    val REQUEST_IMAGE_FOTO_CAPTURE = 3

    private var strImageKK = ""
    private var strImageKTP = ""
    private var strImageFoto = ""
    val arrayTelp: ArrayList<String> = ArrayList()

    private lateinit var sharedPreferences: SharedPreferences
    var idDebitur = 0
    var flagPhoto = 0

    private var pDialog: ProgressDialog? = null
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_nasabah_part1)
        setSupportActionBar(toolbar as Toolbar?)
        supportActionBar?.run {
            title = getString(R.string.informasi_nasabah)
            setDisplayHomeAsUpEnabled(true)
        }

        setPermission()
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

        btn_next_inf_nas_1.setOnClickListener(this)
        img_kk.setOnClickListener(this)
        img_ktp.setOnClickListener(this)
        img_foto_diri.setOnClickListener(this)
        tv_dob.setOnClickListener(this)
        add_telp.setOnClickListener (this)
    }

    private fun next() {
        if(sharedPreferences.getString("from", "") == "repeat"){
            if(img_kk.drawable != null && img_ktp.drawable != null && img_foto_diri.drawable != null){
                strImageKK = getStringImage((img_kk.getDrawable() as BitmapDrawable).bitmap)
                strImageKTP = getStringImage((img_ktp.getDrawable() as BitmapDrawable).bitmap)
                strImageFoto = getStringImage((img_foto_diri.getDrawable() as BitmapDrawable).bitmap)
            }
        }

        pDialog = ProgressDialog.show(this,
                "",
                "Tunggu Sebentar!")
        if(et_nama.text.toString() != "" && et_tmp_lhr.text.toString() != "" &&
                tv_dob.text.toString() != "" && et_no_ktp.text.toString() != "" &&
                et_telp_1.text.toString() != "" && et_alamat.text.toString() != "" &&
                strImageKK != "" && strImageKTP != "" && strImageFoto != ""){
            submitNewDebitur()
        }else{
            pDialog!!.dismiss()
            Toast.makeText(applicationContext, R.string.cekData, Toast.LENGTH_LONG).show()
        }
    }

    private fun getData() {
        val service by lazy {
            RestClient.getClient(this)
        }
        val call = service.getDebitur(idDebitur)
        call.enqueue(object : Callback<Result<Debitur>>{
            override fun onFailure(call: Call<Result<Debitur>>?, t: Throwable?) {
                pDialog!!.dismiss()
                Log.e("on Failure", t.toString())
                Toast.makeText(applicationContext, R.string.cekkoneksi, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<Result<Debitur>>, response: Response<Result<Debitur>>) {
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
                                et_telp_1.setText(data.getTeleponDebitur()!![0].getNoTelepon())
                                et_alamat.setText(data.getAlamat())
                                Picasso.with(this@InfoNasabahPart1Activity)
                                        .load(data.getFotoDebitur()?.getFotoKtp())
                                        .error(android.R.drawable.stat_notify_error).into(img_ktp)
                                Picasso.with(this@InfoNasabahPart1Activity)
                                        .load(data.getFotoDebitur()?.getFotoKartuKeluarga())
                                        .error(android.R.drawable.stat_notify_error).into(img_kk)
                                Picasso.with(this@InfoNasabahPart1Activity)
                                        .load(data.getFotoDebitur()?.getFotoDenganKtp())
                                        .error(android.R.drawable.stat_notify_error).into(img_foto_diri)
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
        et_nama.setText("Himsa Yudhistira")
        et_tmp_lhr.setText("Ponorogo")
        tv_dob.text = "27/3/1995"
        et_no_ktp.setText("1234567890")
        et_telp_1.setText("085658970892")
        et_alamat.setText("Pogung Lor RT 8 No 36, Sinduadi, Mlati, Sleman, Yogyakarta")
        img_ktp.setImageResource(R.drawable.dummy_ktp)
        img_kk.setImageResource(R.drawable.dummy_kk)
    }

    private fun setPreferences() {
        sharedPreferences = getSharedPreferences(Config.PREF_NAME,
                Activity.MODE_PRIVATE)
        idDebitur = sharedPreferences.getInt(Config.DEBITUR_ID, Config.EMPTY_INT)
    }

    private fun setPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.CAMERA),
                    100)
        }
    }

    override fun onClick(p0: View) {
        when(p0.id){
            R.id.btn_next_inf_nas_1 -> {
                next()
            }
            R.id.img_kk -> {
                selectImage(REQUEST_IMAGE_KK_CAPTURE)
            }
            R.id.img_ktp -> {
                selectImage(REQUEST_IMAGE_KTP_CAPTURE)
            }
            R.id.img_foto_diri -> {
                selectImage(REQUEST_IMAGE_FOTO_CAPTURE)
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

    private fun submitNewDebitur() {
        newDebitur(idDebitur)
    }

    private fun newDebitur(idDebitur: Int) {
        arrayTelp.add(et_telp_1.text.toString())
        arrayTelp.add(et_telp_2.text.toString())
        val service by lazy {
            RestClient.getClient(this)
        }
        val call = service.updateNewDebitur(idDebitur, et_nama.text.toString(), et_tmp_lhr.text.toString(),
                tv_dob.text.toString(), et_no_ktp.text.toString(), et_alamat.text.toString(),strImageKTP, strImageFoto, strImageKK, arrayTelp)
        call.enqueue(object : Callback<Result<UserId>>{
            override fun onResponse(call: Call<Result<UserId>>, response: Response<Result<UserId>>) {
                Log.d("debitur", "Status Code = " + response.code())
                if(response.isSuccessful){
                    val result = response.body()
                    if (result != null) {
                        if (result.getStatus()!!) {
                            pDialog!!.dismiss()
                            startActivity(Intent(applicationContext, InfoNasabahPart2Activity::class.java))
                        } else {
                            pDialog!!.dismiss()
                            Log.e("debitur", response.raw().toString())
                            Toast.makeText(baseContext, result.getMessage(), Toast.LENGTH_SHORT).show()
                        }
                    }
                }else{
                    pDialog!!.dismiss()
                    Log.e("debitur", response.raw().toString())
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
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            startActivityForResult(takePictureIntent, i)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_KK_CAPTURE && resultCode == Activity.RESULT_OK) {
            val extras = data!!.extras
            val imageBitmap = extras!!.get("data") as Bitmap
            strImageKK = getStringImage(imageBitmap)
            img_kk.setImageBitmap(imageBitmap)
        }else if (requestCode == REQUEST_IMAGE_KTP_CAPTURE && resultCode == Activity.RESULT_OK){
            val extras = data!!.extras
            val imageBitmap = extras!!.get("data") as Bitmap
            strImageKTP = getStringImage(imageBitmap)
            img_ktp.setImageBitmap(imageBitmap)
        }else if (requestCode == REQUEST_IMAGE_FOTO_CAPTURE && resultCode == Activity.RESULT_OK){
            val extras = data!!.extras
            val imageBitmap = extras!!.get("data") as Bitmap
            strImageFoto = getStringImage(imageBitmap)
            img_foto_diri.setImageBitmap(imageBitmap)
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
