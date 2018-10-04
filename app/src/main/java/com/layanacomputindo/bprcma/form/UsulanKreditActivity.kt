package com.layanacomputindo.bprcma.form

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import android.util.Base64
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import com.layanacomputindo.bprcma.R
import com.layanacomputindo.bprcma.model.Result
import com.layanacomputindo.bprcma.model.UserId
import com.layanacomputindo.bprcma.rest.RestClient
import com.layanacomputindo.bprcma.util.Config
import com.layanacomputindo.bprcma.util.RupiahTextWatcher
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.activity_usulan_kredit.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream

class UsulanKreditActivity : AppCompatActivity(), RadioGroup.OnCheckedChangeListener, View.OnClickListener {
    private lateinit var sharedPreferences: SharedPreferences

    private var pDialog: ProgressDialog? = null

    var idKredit = 0
    var uploadJenis: String = ""
    var uploadCaraBayar: String = ""
    private var flagPhoto = 0
    private var strImage1 = ""
    private var strImage2 = ""
    private var strImage3 = ""
    private var strImage4 = ""
    private var strImage5 = ""
    val arrayFoto: ArrayList<String> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_usulan_kredit)
        setSupportActionBar(toolbar as Toolbar?)
        supportActionBar?.run {
            title = getString(R.string.usulan_kredit)
            setDisplayHomeAsUpEnabled(true)
        }

        setPreferences()

        rg_jnis_kredit.setOnCheckedChangeListener(this)
        rg_tata_cara_bayar.setOnCheckedChangeListener(this)

        et_plafon_kredit.addTextChangedListener(RupiahTextWatcher(et_plafon_kredit))
        et_angs_per_bulan.addTextChangedListener(RupiahTextWatcher(et_angs_per_bulan))
        et_total_kredit.addTextChangedListener(RupiahTextWatcher(et_total_kredit))
        et_total_angunan.addTextChangedListener(RupiahTextWatcher(et_total_angunan))

        img1.setOnClickListener(this)
        img2.setOnClickListener(this)
        img3.setOnClickListener(this)
        img4.setOnClickListener(this)
        img5.setOnClickListener(this)
        btn_next_usulan_kredit.setOnClickListener(this)
    }

    private fun setPreferences() {
        sharedPreferences = getSharedPreferences(Config.PREF_NAME,
                Activity.MODE_PRIVATE)
        idKredit = sharedPreferences.getInt(Config.KREDIT_ID, Config.EMPTY_INT)
    }

    override fun onClick(p0: View) {
        when(p0.id){
            R.id.btn_next_usulan_kredit -> {
                next()
            }
            R.id.img1 -> {
                flagPhoto = 0
                selectImage()
            }
            R.id.img2 -> {
                flagPhoto = 1
                selectImage()
            }
            R.id.img3 -> {
                flagPhoto = 2
                selectImage()
            }
            R.id.img4 -> {
                flagPhoto = 3
                selectImage()
            }
            R.id.img5 -> {
                flagPhoto = 4
                selectImage()
            }
        }
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
                img1.setImageBitmap(selectedImage)
            }else if(flagPhoto == 1){
                strImage2 = getStringImage(selectedImage)
                img2.setImageBitmap(selectedImage)
            }else if(flagPhoto == 2){
                strImage3 = getStringImage(selectedImage)
                img3.setImageBitmap(selectedImage)
            }else if(flagPhoto == 3){
                strImage4 = getStringImage(selectedImage)
                img4.setImageBitmap(selectedImage)
            }else if(flagPhoto == 4){
                strImage5 = getStringImage(selectedImage)
                img5.setImageBitmap(selectedImage)
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

    private fun next() {
        pDialog = ProgressDialog.show(this,
                "",
                "Tunggu Sebentar!")
        if(uploadJenis!= "" && et_plafon_kredit.text.toString() != "" && uploadCaraBayar != "" &&
                et_suku_bunga_per_bulan.text.toString() != "" && et_angs_per_bulan.text.toString() != "" &&
                et_angka_waktu.text.toString() != "" && et_provisi_adm.text.toString() != "" &&
                et_pengikatan_kredit.text.toString() != "" && et_total_kredit.text.toString() != "" &&
                et_pengikatan_angunan.text.toString() != "" && et_total_angunan.text.toString() != "" &&
                et_kesimpulan_angunan.text.toString() != "" && et_asuransi_angunan.text.toString() != "" &&
                et_usulan_deviasi.text.toString() != ""){
            submitData()
        }else{
            pDialog!!.dismiss()
            Toast.makeText(applicationContext, R.string.cekData, Toast.LENGTH_LONG).show()
        }
    }

    private fun submitData() {
        arrayFoto.add(strImage1)
        arrayFoto.add(strImage2)
        arrayFoto.add(strImage3)
        arrayFoto.add(strImage4)
        arrayFoto.add(strImage5)
        val service by lazy {
            RestClient.getClient(this)
        }
        val call = service.sendUsulanKredit(idKredit, uploadJenis,
                et_plafon_kredit.getText().toString().replace("[$,.]".toRegex(), "").toInt(),
                uploadCaraBayar, et_suku_bunga_per_bulan.text.toString(), et_angs_per_bulan.getText().toString().replace("[$,.]".toRegex(), "").toInt(),
                Integer.parseInt(et_angka_waktu.text.toString()), Integer.parseInt(et_provisi_adm.text.toString()), et_pengikatan_kredit.text.toString(),
                et_total_kredit.getText().toString().replace("[$,.]".toRegex(), "").toInt(), et_pengikatan_angunan.text.toString(),
                et_total_angunan.getText().toString().replace("[$,.]".toRegex(), "").toInt(),
                et_kesimpulan_angunan.text.toString(), et_asuransi_angunan.text.toString(), et_usulan_deviasi.text.toString(), arrayFoto)
        call.enqueue(object : Callback<Result<UserId>>{
            override fun onFailure(call: Call<Result<UserId>>?, t: Throwable?) {
                pDialog!!.dismiss()
                Log.e("on Failure2", t.toString())
                Toast.makeText(applicationContext, R.string.cekkoneksi, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<Result<UserId>>, response: Response<Result<UserId>>) {
                Log.d("usulan kredit", "Status Code = " + response.code())
                if(response.isSuccessful){
                    val result = response.body()
                    if (result != null) {
                        if (result.getStatus()!!) {
                            startActivity(Intent(this@UsulanKreditActivity, PemeriksaanJaminanActivity::class.java))
                        } else {
                            pDialog!!.dismiss()
                            Log.e("usulan kredit", response.raw().toString())
                            Toast.makeText(baseContext, result.getMessage(), Toast.LENGTH_SHORT).show()
                        }
                    }
                }else{
                    pDialog!!.dismiss()
                    Log.e("usulan kredit", response.raw().toString())
                    Toast.makeText(baseContext, response.message(), Toast.LENGTH_SHORT).show()
                }
            }

        })
    }

    override fun onCheckedChanged(p0: RadioGroup?, p1: Int) {
        when(p1){
            R.id.rb_baru -> {
                val radioButton = findViewById(R.id.rb_baru) as RadioButton

                uploadJenis = radioButton.text.toString()
            }
            R.id.rb_tambahan -> {
                val radioButton = findViewById(R.id.rb_tambahan) as RadioButton

                uploadJenis = radioButton.text.toString()
            }
            R.id.rb_pembaharuan -> {
                val radioButton = findViewById(R.id.rb_pembaharuan) as RadioButton

                uploadJenis = radioButton.text.toString()
            }
            R.id.rb_angsuran -> {
                val radioButton = findViewById(R.id.rb_angsuran) as RadioButton

                uploadCaraBayar = radioButton.text.toString()
            }
            R.id.rb_berjangka -> {
                val radioButton = findViewById(R.id.rb_berjangka) as RadioButton

                uploadCaraBayar = radioButton.text.toString()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return true
    }
}
