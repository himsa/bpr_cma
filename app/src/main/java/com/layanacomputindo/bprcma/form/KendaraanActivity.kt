package com.layanacomputindo.bprcma.form

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
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

    var uploadJenis = ""
    var uploadPenggunaan = ""
    var uploadGesek = false
    var uploadGesek1 = false
    var uploadSesuai = false

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
            setDummy()
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
    }

    override fun onClick(p0: View) {
        when(p0.id){
            R.id.btn_next_kendaraan -> {
//                pDialog = ProgressDialog.show(this,
//                        "",
//                        "Tunggu Sebentar!")
//                submitData()
                startActivity(Intent(applicationContext, FotoKondisiKendaraanActivity::class.java))
            }
            R.id.et_tgl_pmriksaan -> {
                selectDob()
            }
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
                uploadGesek1, et_nomor_polisi.text.toString(), et_nomor_bpkb.text.toString(), et_tipe_warna.text.toString(),
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
                Log.d("tmp tggl", "Status Code = " + response.code())
                if(response.isSuccessful){
                    val result = response.body()
                    if (result != null) {
                        if (result.getStatus()!!) {
                            val editor = sharedPreferences.edit()
                            editor.putInt(Config.JAMINAN_ID, result.getData()!!.id)
                            editor.apply()
                            startActivity(Intent(applicationContext, FotoKondisiKendaraanActivity::class.java))
                        } else {
                            Log.e("debitur", response.raw().toString())
                            Toast.makeText(baseContext, result.getMessage(), Toast.LENGTH_SHORT).show()
                        }
                    }
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

                uploadGesek = true
            }
            R.id.rb_bukti_gesek_tdk -> {
                val radioButton = findViewById(R.id.rb_bukti_gesek_tdk) as RadioButton

                uploadGesek = false
            }
            R.id.rb_bukti_gesek_ada1 -> {
                val radioButton = findViewById(R.id.rb_bukti_gesek_ada1) as RadioButton

                uploadGesek1 = true
            }
            R.id.rb_bukti_gesek_tdk1 -> {
                val radioButton = findViewById(R.id.rb_bukti_gesek_tdk1) as RadioButton

                uploadGesek1 = false
            }
            R.id.rb_sesuai -> {
                val radioButton = findViewById(R.id.rb_bukti_gesek_tdk1) as RadioButton

                uploadSesuai = true
            }
            R.id.rb_tidak -> {
                val radioButton = findViewById(R.id.rb_bukti_gesek_tdk1) as RadioButton

                uploadSesuai = false
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
