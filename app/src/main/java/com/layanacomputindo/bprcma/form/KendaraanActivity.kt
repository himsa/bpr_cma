package com.layanacomputindo.bprcma.form

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.layanacomputindo.bprcma.R
import com.layanacomputindo.bprcma.model.Kendaraan
import com.layanacomputindo.bprcma.model.Result
import com.layanacomputindo.bprcma.model.UserId
import com.layanacomputindo.bprcma.rest.RestClient
import com.layanacomputindo.bprcma.util.Config
import com.layanacomputindo.bprcma.util.RupiahTextWatcher
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import kotlinx.android.synthetic.main.activity_kendaraan.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class KendaraanActivity : AppCompatActivity(), RadioGroup.OnCheckedChangeListener, View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private lateinit var sharedPreferences: SharedPreferences

    var idKredit = 0
    var idJaminan = 0

    var uploadJenis = ""
    var uploadPenggunaan = ""
    var uploadGesek = 0
    var uploadGesek1 = 0
    var uploadSesuai = 0

    private var pDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kendaraan)
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

        et_nilai_market.addTextChangedListener(RupiahTextWatcher(et_nilai_market))
        et_nilai_likuidasi.addTextChangedListener(RupiahTextWatcher(et_nilai_likuidasi))

        rg_jnis_kendaraan.setOnCheckedChangeListener(this)
        rg_penggunaan_jaminan.setOnCheckedChangeListener(this)
        rg_bukti_gesek.setOnCheckedChangeListener(this)
        rg_bukti_gesek1.setOnCheckedChangeListener(this)
        rg_hasil.setOnCheckedChangeListener(this)

        et_tgl_pmriksaan.setOnClickListener(this)
        btn_next_kendaraan.setOnClickListener(this)
    }

    private fun getData() {
        val service by lazy {
            RestClient.getClient(this)
        }
        val call = service.getJaminanKendaraan(idJaminan)
        call.enqueue(object: Callback<Result<Kendaraan>>{
            override fun onFailure(call: Call<Result<Kendaraan>>?, t: Throwable?) {
                pDialog!!.dismiss()
                Log.e("on Failure", t.toString())
                Toast.makeText(applicationContext, R.string.cekkoneksi, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<Result<Kendaraan>>, response: Response<Result<Kendaraan>>) {
                Log.d("kendaraan", "Status Code = " + response.code())
                if(response.isSuccessful){
                    val result = response.body()
                    if (result != null) {
                        if (result.getStatus()!!) {
                            val data = result.getData()
                            pDialog!!.dismiss()
                            if (data != null){
                                et_tgl_pmriksaan.setText(data.getTanggalPemeriksaan())
                                when (data.getJenisKendaraan()){
                                    "motor" -> {
                                        rb_motor.isChecked = true
                                    }
                                    "mobil niaga" -> {
                                        rb_mobil_niaga.isChecked = true
                                    }
                                    "truck" -> {
                                        rb_truck.isChecked = true
                                    }
                                    "pick up" -> {
                                        rb_pick_up.isChecked = true
                                    }
                                    else -> {
                                        rb_lainnya.isChecked = true
                                    }
                                }

                                when (data.getPenggunaanJaminan()){
                                    "Kendaraan Pribadi" -> {
                                        rb_kend_pribadi.isChecked = true
                                    }
                                    "Kendaraan Operasional Usaha" -> {
                                        rb_kend_operasional_usaha.isChecked = true
                                    }
                                    else -> {
                                        rb_kend_usaha.isChecked = true
                                    }
                                }
                                et_dri_lok_usaha.setText(data.getDaerahOperasionalJaminan().toString())
                                et_nama_bpkb.setText(data.getNamaPemilikBpkb())
                                et_nama_saat_ini.setText(data.getNamaPemilik())
                                et_alamat_saat_ini.setText("apinya blm ada")
                                //bukti gesek tipe datanya apa? balikan apinya 1
                                if(data.getBuktiGesekMesin() == 1){
                                    rb_bukti_gesek_ada.isChecked = true
                                }else{
                                    rb_bukti_gesek_tdk.isChecked = true
                                }
                                if(data.getBuktiGesekRangka() == 1){
                                    rb_bukti_gesek_ada1.isChecked = true
                                }else{
                                    rb_bukti_gesek_tdk1.isChecked = true
                                }
                                if(data.getHasilKesesuaian() == 1){
                                    rb_sesuai.isChecked = true
                                }else{
                                    rb_tidak.isChecked = true
                                }
                                et_hub_pem_deb.setText(data.getHubunganPemilikDebitur())
                                et_nomor_faktur.setText(data.getNomorFaktur())
                                et_nomor_mesin.setText(data.getNomorMesin())
                                et_nomor_rangka.setText(data.getNomorRangka())
                                et_nomor_polisi.setText(data.getNomorPolisi())
                                et_nomor_bpkb.setText(data.getNomorBpkb())
                                et_nomor_stnk.setText(data.getNomorStnk())
                                et_tipe_kendaraan.setText(data.getTipeModelKendaraan())
                                et_tipe_warna.setText(data.getWarna())
                                et_th_pemb.setText(data.getTahunPembuatan().toString())
                                et_merk_kendaraan.setText(data.getMerkKendaraan())
                                et_check_samsat.setText(data.getCheckSamsat())
                                et_nama_samsat.setText(data.getNamaPetugasSamsat())
                                et_telp_samsat.setText(data.getNomorTeleponSamsat())
                                et_keterangan_kendaraan.setText(data.getKeteranganLain())
                                if (data.getNilaiMarket() == null || data.getNilaiMarket() == 0){

                                }else{
                                    et_nilai_market.setText(data.getNilaiMarket().toString())
                                }
                                if(data.getNilaiLikuidasi() == null || data.getNilaiLikuidasi() == 0){

                                }else{
                                    et_nilai_likuidasi.setText(data.getNilaiLikuidasi().toString())
                                }

                                et_inf_nama.setText(data.getNamaInformanHarga())
                                et_inf_alamat.setText(data.getAlamatInformanHarga())
                                et_inf_tlp.setText(data.getTeleponInformanHarga())
                                et_pendapat_pemeriksa.setText(data.getPendapatPemeriksa())
                            }

                        } else {
                            Log.e("kendaraan", response.raw().toString())
                            Toast.makeText(baseContext, result.getMessage(), Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

        })
    }

    private fun setDummy() {
        et_tgl_pmriksaan.setText("27/3/1995")
        et_nilai_market.setText("100.000.000")
        et_nilai_likuidasi.setText("70.000.000")
        rb_lainnya.isChecked = true
        et_dri_lok_usaha.setText("1")
        et_nama_bpkb.setText("Himsa Yudhistira")
        et_nama_saat_ini.setText("Himsa Yudhistira")
        et_alamat_saat_ini.setText("Pogung Lor RT 8 No 36, Sinduadi, Mlati, Sleman, Yogyakarta")
        et_hub_pem_deb.setText("Milik sendiri")
        et_nomor_faktur.setText("1324576809")
        et_nomor_mesin.setText("0897645312")
        et_nomor_rangka.setText("1029384756")
        et_nomor_polisi.setText("H  1  M")
        et_nomor_bpkb.setText("1324576809")
        et_nomor_stnk.setText("1324576809")
        et_tipe_kendaraan.setText("Pesawat Terbang")
        et_tipe_warna.setText("Putih biru")
        et_th_pemb.setText("2010")
        et_merk_kendaraan.setText("BOEING 737")
    }

    private fun setPreferences() {
        sharedPreferences = getSharedPreferences(Config.PREF_NAME,
                Activity.MODE_PRIVATE)
        idKredit = sharedPreferences.getInt(Config.KREDIT_ID, Config.EMPTY_INT)
        idJaminan = sharedPreferences.getInt(Config.JAMINAN_ID, Config.EMPTY_INT)
    }

    override fun onClick(p0: View) {
        when(p0.id){
            R.id.btn_next_kendaraan -> {
                if(sharedPreferences.getString(Config.ROLE, "")== "komite"||sharedPreferences.getString(Config.ROLE, "")== "supervisor"){
                    startActivity(Intent(applicationContext, FotoKondisiKendaraanActivity::class.java))
                }else{
                    next()
                }
            }
            R.id.et_tgl_pmriksaan -> {
                selectDob()
            }
        }
    }

    private fun next() {
        if(sharedPreferences.getString("from", "") == "repeat"){
            val radioButton = findViewById(rg_jnis_kendaraan.checkedRadioButtonId) as RadioButton
            uploadJenis = radioButton.text.toString()
            val radioButton1 = findViewById(rg_penggunaan_jaminan.checkedRadioButtonId) as RadioButton
            uploadPenggunaan = radioButton1.text.toString()
            if(rg_bukti_gesek.checkedRadioButtonId == R.id.rb_bukti_gesek_ada){
                uploadGesek = 1
            }else{
                uploadGesek = 0
            }
            if(rg_bukti_gesek1.checkedRadioButtonId == R.id.rb_bukti_gesek_ada1){
                uploadGesek1 = 1
            }else{
                uploadGesek1 = 0
            }
        }
        pDialog = ProgressDialog.show(this,
                "",
                "Tunggu Sebentar!")
        if(et_tgl_pmriksaan.text.toString() != "" &&
        uploadJenis != "" && uploadPenggunaan!="" && Integer.parseInt(et_dri_lok_usaha.text.toString())!=0 &&
        et_nama_bpkb.text.toString()!="" && et_nama_saat_ini.text.toString()!="" && et_hub_pem_deb.text.toString()!="" &&
        et_nomor_faktur.text.toString()!="" && et_nomor_mesin.text.toString()!="" && et_nomor_rangka.text.toString()!="" &&
        et_nomor_polisi.text.toString()!="" && et_nomor_bpkb.text.toString()!="" && et_nomor_stnk.text.toString()!="" && et_tipe_warna.text.toString()!="" &&
        et_th_pemb.text.toString()!="" && et_merk_kendaraan.text.toString()!="" && et_tipe_kendaraan.text.toString()!="" &&
        et_check_samsat.text.toString()!="" && et_nama_samsat.text.toString()!="" && et_telp_samsat.text.toString()!="" &&
        et_keterangan_kendaraan.text.toString()!="" &&
        et_nilai_market.getText().toString().replace("[$,.]".toRegex(), "").toInt()!=0 &&
        et_nilai_likuidasi.getText().toString().replace("[$,.]".toRegex(), "").toInt()!=0 &&
        et_inf_nama.text.toString()!="" && et_inf_tlp.text.toString()!="" && et_inf_alamat.text.toString()!="" &&
        et_pendapat_pemeriksa.text.toString()!=""){
            if(sharedPreferences.getString("from", "") == "repeat"){
                updateData()
            }else{
                submitData()
            }
        }else{
            pDialog!!.dismiss()
            Toast.makeText(applicationContext, R.string.cekData, Toast.LENGTH_LONG).show()
        }
    }

    private fun submitData() {
        val service by lazy {
            RestClient.getClient(this)
        }
        val call = service.sendJaminanKendaraan(idKredit, et_tgl_pmriksaan.text.toString(),
                uploadJenis, uploadPenggunaan, Integer.parseInt(et_dri_lok_usaha.text.toString()),
                et_nama_bpkb.text.toString(),et_nama_saat_ini.text.toString(), et_hub_pem_deb.text.toString(),
                et_nomor_faktur.text.toString(), et_nomor_mesin.text.toString(), uploadGesek, et_nomor_rangka.text.toString(),
                uploadGesek1, et_nomor_polisi.text.toString(), et_nomor_bpkb.text.toString(), et_nomor_stnk.text.toString(), et_tipe_warna.text.toString(),
                Integer.parseInt(et_th_pemb.text.toString()), et_merk_kendaraan.text.toString(), et_tipe_kendaraan.text.toString(),
                et_check_samsat.text.toString(), et_nama_samsat.text.toString(), et_telp_samsat.text.toString(),
                uploadSesuai, et_keterangan_kendaraan.text.toString(),
                et_nilai_market.getText().toString().replace("[$,.]".toRegex(), "").toInt(),
                et_nilai_likuidasi.getText().toString().replace("[$,.]".toRegex(), "").toInt(),
                et_inf_nama.text.toString(), "", et_inf_tlp.text.toString(), et_inf_alamat.text.toString(),
                et_pendapat_pemeriksa.text.toString())
        call.enqueue(object : Callback<Result<UserId>> {
            override fun onFailure(call: Call<Result<UserId>>?, t: Throwable?) {
                pDialog!!.dismiss()
                Log.e("on Failure", t.toString())
                Toast.makeText(applicationContext, R.string.cekkoneksi, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<Result<UserId>>, response: Response<Result<UserId>>) {
                Log.d("kendaraan", "Status Code = " + response.code())
                pDialog!!.dismiss()
                if(response.isSuccessful){
                    val result = response.body()
                    if (result != null) {
                        if (result.getStatus()!!) {
                            val editor = sharedPreferences.edit()
                            editor.putInt(Config.JAMINAN_ID, result.getData()!!.id)
                            editor.apply()
                            startActivity(Intent(applicationContext, FotoKondisiKendaraanActivity::class.java))
                        } else {
                            Log.e("kendaraan", response.raw().toString())
                            Toast.makeText(baseContext, result.getMessage(), Toast.LENGTH_SHORT).show()
                        }
                    }
                }else{
                    Log.e("kendaraan", response.raw().toString())
                    Toast.makeText(baseContext, response.message(), Toast.LENGTH_SHORT).show()
                }
            }

        })
    }

    private fun updateData(){
        val service by lazy {
            RestClient.getClient(this)
        }
        val call = service.updateJaminanKendaraan(idJaminan, et_tgl_pmriksaan.text.toString(),
                uploadJenis, uploadPenggunaan, Integer.parseInt(et_dri_lok_usaha.text.toString()),
                et_nama_bpkb.text.toString(),et_nama_saat_ini.text.toString(), et_hub_pem_deb.text.toString(),
                et_nomor_faktur.text.toString(), et_nomor_mesin.text.toString(), uploadGesek, et_nomor_rangka.text.toString(),
                uploadGesek1, et_nomor_polisi.text.toString(), et_nomor_bpkb.text.toString(), et_nomor_stnk.text.toString(), et_tipe_warna.text.toString(),
                Integer.parseInt(et_th_pemb.text.toString()), et_merk_kendaraan.text.toString(), et_tipe_kendaraan.text.toString(),
                et_check_samsat.text.toString(), et_nama_samsat.text.toString(), et_telp_samsat.text.toString(),
                uploadSesuai, et_keterangan_kendaraan.text.toString(),
                et_nilai_market.getText().toString().replace("[$,.]".toRegex(), "").toInt(),
                et_nilai_likuidasi.getText().toString().replace("[$,.]".toRegex(), "").toInt(),
                et_inf_nama.text.toString(), "", et_inf_tlp.text.toString(), et_inf_alamat.text.toString(),
                et_pendapat_pemeriksa.text.toString())
        call.enqueue(object : Callback<Result<UserId>> {
            override fun onFailure(call: Call<Result<UserId>>?, t: Throwable?) {
                pDialog!!.dismiss()
                Log.e("on Failure", t.toString())
                Toast.makeText(applicationContext, R.string.cekkoneksi, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<Result<UserId>>, response: Response<Result<UserId>>) {
                Log.d("kendaraanupdate", "Status Code = " + response.code())
                pDialog!!.dismiss()
                if(response.isSuccessful){
                    val result = response.body()
                    if (result != null) {
                        if (result.getStatus()!!) {
                            startActivity(Intent(applicationContext, FotoKondisiKendaraanActivity::class.java))
                        } else {
                            Log.e("kendaraan", response.raw().toString())
                            Toast.makeText(baseContext, result.getMessage(), Toast.LENGTH_SHORT).show()
                        }
                    }
                }else{
                    Log.e("kendaraanupdate", response.raw().toString())
                    Toast.makeText(baseContext, response.message(), Toast.LENGTH_SHORT).show()
                }
            }

        })
    }

    override fun onCheckedChanged(p0: RadioGroup?, p1: Int) {
        when(p1){
            R.id.rb_motor -> {
                val radioButton = findViewById(R.id.rb_motor) as RadioButton

                uploadJenis = radioButton.text.toString()
            }
            R.id.rb_mobil_niaga -> {
                val radioButton = findViewById(R.id.rb_mobil_niaga) as RadioButton

                uploadJenis = radioButton.text.toString()
            }
            R.id.rb_truck -> {
                val radioButton = findViewById(R.id.rb_truck) as RadioButton

                uploadJenis = radioButton.text.toString()
            }
            R.id.rb_pick_up -> {
                val radioButton = findViewById(R.id.rb_pick_up) as RadioButton

                uploadJenis = radioButton.text.toString()
            }
            R.id.rb_lainnya -> {
                val radioButton = findViewById(R.id.rb_lainnya) as RadioButton

                uploadJenis = radioButton.text.toString()
            }
            R.id.rb_kend_pribadi -> {
                val radioButton = findViewById(R.id.rb_kend_pribadi) as RadioButton

                uploadPenggunaan = radioButton.text.toString()
            }
            R.id.rb_kend_operasional_usaha -> {
                val radioButton = findViewById(R.id.rb_kend_operasional_usaha) as RadioButton

                uploadPenggunaan = radioButton.text.toString()
            }
            R.id.rb_kend_usaha -> {
                val radioButton = findViewById(R.id.rb_kend_usaha) as RadioButton

                uploadPenggunaan = radioButton.text.toString()
            }
            R.id.rb_bukti_gesek_ada -> {
                val radioButton = findViewById(R.id.rb_bukti_gesek_ada) as RadioButton

                uploadGesek = 1
            }
            R.id.rb_bukti_gesek_tdk -> {
                val radioButton = findViewById(R.id.rb_bukti_gesek_tdk) as RadioButton

                uploadGesek = 0
            }
            R.id.rb_bukti_gesek_ada1 -> {
                val radioButton = findViewById(R.id.rb_bukti_gesek_ada1) as RadioButton

                uploadGesek1 = 1
            }
            R.id.rb_bukti_gesek_tdk1 -> {
                val radioButton = findViewById(R.id.rb_bukti_gesek_tdk1) as RadioButton

                uploadGesek1 = 0
            }
            R.id.rb_sesuai -> {
                val radioButton = findViewById(R.id.rb_bukti_gesek_tdk1) as RadioButton

                uploadSesuai = 1
            }
            R.id.rb_tidak -> {
                val radioButton = findViewById(R.id.rb_bukti_gesek_tdk1) as RadioButton

                uploadSesuai = 0
            }
        }
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
        et_tgl_pmriksaan.setText(date)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return true
    }
}
