package com.layanacomputindo.bprcma.form

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.layanacomputindo.bprcma.R
import com.layanacomputindo.bprcma.model.Result
import com.layanacomputindo.bprcma.model.UserId
import com.layanacomputindo.bprcma.rest.RestClient
import com.layanacomputindo.bprcma.util.Config
import com.layanacomputindo.bprcma.util.RupiahTextWatcher
import kotlinx.android.synthetic.main.activity_prf_laba_rugi.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PrfLabaRugiActivity : AppCompatActivity(), View.OnClickListener, View.OnFocusChangeListener {

    private lateinit var sharedPreferences: SharedPreferences

    private var pDialog: ProgressDialog? = null

    var idKredit = 0

    var sumBiaya: Int = 0
    var sumBiaya2: Int = 0
    var etGajiKyw: Int = 0
    var etSwTmpUsaha: Int = 0
    var etLstrkAirTelp: Int = 0
    var etAdmKemLing: Int = 0
    var etLainLain: Int = 0
    var etBiayaRt: Int = 0
    var etLstrkAirTelp2: Int = 0
    var etBPend: Int = 0
    var etBSewaLain: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prf_laba_rugi)
        setSupportActionBar(toolbar as Toolbar?)
        supportActionBar?.run {
            title = getString(R.string.prf_laba_rugi)
            setDisplayHomeAsUpEnabled(true)
        }

        setPreferences()

        if(sharedPreferences.getString("from", "") == "repeat"){

        }

        et_omz.addTextChangedListener(RupiahTextWatcher(et_omz))

        et_gaji_kyw.addTextChangedListener(RupiahTextWatcher(et_gaji_kyw))
        et_sw_tmp_usaha.addTextChangedListener(RupiahTextWatcher(et_sw_tmp_usaha))
        et_lstrik_air_tel.addTextChangedListener(RupiahTextWatcher(et_lstrik_air_tel))
        et_adm_keamanan_ling.addTextChangedListener(RupiahTextWatcher(et_adm_keamanan_ling))
        et_lain_lain.addTextChangedListener(RupiahTextWatcher(et_lain_lain))

        et_jml_biaya_usaha.addTextChangedListener(RupiahTextWatcher(et_jml_biaya_usaha))

        et_gaji_kyw.setOnFocusChangeListener(this)
        et_sw_tmp_usaha.setOnFocusChangeListener(this)
        et_lstrik_air_tel.setOnFocusChangeListener(this)
        et_adm_keamanan_ling.setOnFocusChangeListener(this)
        et_lain_lain.setOnFocusChangeListener(this)

        et_keuntungan_usaha.addTextChangedListener(RupiahTextWatcher(et_keuntungan_usaha))
        et_pndptn_suami_istri.addTextChangedListener(RupiahTextWatcher(et_pndptn_suami_istri))
        et_pndptn_lain.addTextChangedListener(RupiahTextWatcher(et_pndptn_lain))
        et_netto.addTextChangedListener(RupiahTextWatcher(et_netto))

        et_biaya_rt.addTextChangedListener(RupiahTextWatcher(et_biaya_rt))
        et_lstrik_air_tel2.addTextChangedListener(RupiahTextWatcher(et_lstrik_air_tel2))
        et_b_pendidikan.addTextChangedListener(RupiahTextWatcher(et_b_pendidikan))
        et_b_sewa_lainnya.addTextChangedListener(RupiahTextWatcher(et_b_sewa_lainnya))

        et_ttl_b_pribd.addTextChangedListener(RupiahTextWatcher(et_ttl_b_pribd))

        et_biaya_rt.setOnFocusChangeListener(this)
        et_lstrik_air_tel2.setOnFocusChangeListener(this)
        et_b_pendidikan.setOnFocusChangeListener(this)
        et_b_sewa_lainnya.setOnFocusChangeListener(this)

        et_sisa_pnghsln_net.addTextChangedListener(RupiahTextWatcher(et_sisa_pnghsln_net))
        et_angs_bnk_lain.addTextChangedListener(RupiahTextWatcher(et_angs_bnk_lain))
        et_rnc_angs.addTextChangedListener(RupiahTextWatcher(et_rnc_angs))
        et_total_angs.addTextChangedListener(RupiahTextWatcher(et_total_angs))

        btn_next_prf_laba_rugi.setOnClickListener(this)
    }

    private fun setDummy() {
        et_omz.setText("200.000.000")
        et_gaji_kyw.setText("2.000.000")
        et_sw_tmp_usaha.setText("10.000.000")
        et_lstrik_air_tel.setText("2.000.000")
        et_adm_keamanan_ling.setText("500.000")
        et_lain_lain.setText("5.000.000")

        et_jml_biaya_usaha.setText("19.500.000")

        et_keuntungan_usaha.setText("100.000.000")
        et_pndptn_suami_istri.setText("0")
        et_pndptn_lain.setText("75.000.000")
        et_netto.setText("300.000.000")

        et_biaya_rt.setText("5.000.000")
        et_lstrik_air_tel2.setText("2.000.000")
        et_b_pendidikan.setText("20.000.000")
        et_b_sewa_lainnya.setText("15.000.000")

        et_ttl_b_pribd.setText("42.000.000")

        et_sisa_pnghsln_net.setText("5.000.000")
        et_angs_bnk_lain.setText("2.000.000")
        et_rnc_angs.setText("20.000.000")
        et_total_angs.setText("15.000.000")
    }

    private fun setPreferences() {
        sharedPreferences = getSharedPreferences(Config.PREF_NAME,
                Activity.MODE_PRIVATE)
        idKredit = sharedPreferences.getInt(Config.KREDIT_ID, Config.EMPTY_INT)
    }

    override fun onFocusChange(p0: View, p1: Boolean) {
        when(p0.id){
            R.id.et_gaji_kyw -> {
                setSumBiaya()
            }
            R.id.et_sw_tmp_usaha -> {
                setSumBiaya()
            }
            R.id.et_lstrik_air_tel -> {
                setSumBiaya()
            }
            R.id.et_adm_keamanan_ling -> {
                setSumBiaya()
            }
            R.id.et_lain_lain -> {
                setSumBiaya()
            }
            R.id.et_biaya_rt -> {
                setSumBiaya2()
            }
            R.id.et_lstrik_air_tel2 -> {
                setSumBiaya2()
            }
            R.id.et_b_pendidikan -> {
                setSumBiaya2()
            }
            R.id.et_b_sewa_lainnya -> {
                setSumBiaya2()
            }
        }
    }

    private fun setSumBiaya2() {
        if(et_biaya_rt.text.isNotEmpty()){
            etBiayaRt = et_biaya_rt.getText().toString().replace("[$,.]".toRegex(), "").toInt()
        }else{
            etBiayaRt = 0
        }
        if (et_lstrik_air_tel2.text.isNotEmpty()){
            etLstrkAirTelp2 = et_lstrik_air_tel2.getText().toString().replace("[$,.]".toRegex(), "").toInt()
        }else{
            etLstrkAirTelp2 = 0
        }
        if (et_b_pendidikan.text.isNotEmpty()){
            etBPend = et_b_pendidikan.getText().toString().replace("[$,.]".toRegex(), "").toInt()
        }
        else{
            etBPend = 0
        }
        if (et_b_sewa_lainnya.text.isNotEmpty()){
            etBSewaLain = et_b_sewa_lainnya.getText().toString().replace("[$,.]".toRegex(), "").toInt()
        }
        else{
            etBSewaLain = 0
        }
        sumBiaya2 = etBiayaRt + etLstrkAirTelp2 + etBPend + etBSewaLain
        et_ttl_b_pribd.setText(sumBiaya2.toString())
    }

    private fun setSumBiaya() {
        if(et_gaji_kyw.text.isNotEmpty()){
            etGajiKyw = et_gaji_kyw.getText().toString().replace("[$,.]".toRegex(), "").toInt()
        }else{
            etGajiKyw = 0
        }
        if( et_sw_tmp_usaha.text.isNotEmpty()){
            etSwTmpUsaha = et_sw_tmp_usaha.getText().toString().replace("[$,.]".toRegex(), "").toInt()
        }else{
            etSwTmpUsaha = 0
        }
        if (et_lstrik_air_tel.text.isNotEmpty()){
            etLstrkAirTelp = et_lstrik_air_tel.getText().toString().replace("[$,.]".toRegex(), "").toInt()
        }else{
            etLstrkAirTelp = 0
        }
        if (et_adm_keamanan_ling.text.isNotEmpty()){
            etAdmKemLing = et_adm_keamanan_ling.getText().toString().replace("[$,.]".toRegex(), "").toInt()
        }
        else{
            etAdmKemLing = 0
        }
        if (et_lain_lain.text.isNotEmpty()){
            etLainLain = et_lain_lain.getText().toString().replace("[$,.]".toRegex(), "").toInt()
        }
        else{
            etLainLain = 0
        }
        sumBiaya = etGajiKyw + etSwTmpUsaha + etLstrkAirTelp + etAdmKemLing + etLainLain
        et_jml_biaya_usaha.setText(sumBiaya.toString())
    }

    override fun onClick(p0: View) {
        when(p0.id){
            R.id.btn_next_prf_laba_rugi -> {
                next()
            }
        }
    }

    private fun next() {
        pDialog = ProgressDialog.show(this,
                "",
                "Tunggu Sebentar!")
        if(et_omz.text.toString() != "" && et_detail_keterangan.text.toString() != "" &&
                et_hpp.text.toString() != "" && etGajiKyw.toString() != "" &&
                etSwTmpUsaha.toString() != "" && etLstrkAirTelp.toString() != "" && etAdmKemLing.toString() != "" &&
                etLainLain.toString() != "" && sumBiaya.toString() != "" && et_detail_keterangan2.text.toString() != "" &&
                et_keuntungan_usaha.text.toString() != "" && et_grs_prft_mrgn.text.toString() != "" && et_pndptn_suami_istri.text.toString() != "" &&
                et_pndptn_lain.text.toString() != "" && et_detail_keterangan3.text.toString() != "" && et_netto.text.toString() != "" &&
                et_detail_keterangan4.text.toString() != "" && etBiayaRt.toString() != "" && etLstrkAirTelp2.toString() != "" &&
                etBPend.toString() != "" && etBSewaLain.toString() != "" && sumBiaya2.toString() != "" &&
                et_detail_keterangan6.text.toString() != "" && et_sisa_pnghsln_net.text.toString() != "" &&
                et_detail_keterangan7.text.toString() != "" && et_angs_bnk_lain.toString() != "" &&
                et_rnc_angs.text.toString() != "" && et_total_angs.text.toString() != "" &&
                et_detail_keterangan8.text.toString() != ""){
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
        val call = service.sendPerforma(idKredit, et_omz.getText().toString().replace("[$,.]".toRegex(), "").toInt(),
                et_detail_keterangan.text.toString(),
                Integer.parseInt(et_hpp.text.toString()),
                etGajiKyw,
                etSwTmpUsaha,
                etLstrkAirTelp,
                etAdmKemLing,
                etLainLain,
                sumBiaya,
                et_detail_keterangan2.text.toString(),
                et_keuntungan_usaha.getText().toString().replace("[$,.]".toRegex(), "").toInt(),
                Integer.parseInt(et_grs_prft_mrgn.text.toString()),
                et_pndptn_suami_istri.getText().toString().replace("[$,.]".toRegex(), "").toInt(),
                et_pndptn_lain.getText().toString().replace("[$,.]".toRegex(), "").toInt(),
                et_detail_keterangan3.text.toString(),
                et_netto.getText().toString().replace("[$,.]".toRegex(), "").toInt(),
                et_detail_keterangan4.text.toString(),
                etBiayaRt,
                etLstrkAirTelp2,
                etBPend,
                etBSewaLain,
                sumBiaya2,
                et_detail_keterangan6.text.toString(),
                et_sisa_pnghsln_net.getText().toString().replace("[$,.]".toRegex(), "").toInt(),
                et_detail_keterangan7.text.toString(),
                et_angs_bnk_lain.getText().toString().replace("[$,.]".toRegex(), "").toInt(),
                et_rnc_angs.getText().toString().replace("[$,.]".toRegex(), "").toInt(),
                et_total_angs.getText().toString().replace("[$,.]".toRegex(), "").toInt(),
                et_detail_keterangan8.text.toString())
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
                            startActivity(Intent(this@PrfLabaRugiActivity, KesimpulanActivity::class.java))
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

        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return true
    }
}
