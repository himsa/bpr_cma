package com.layanacomputindo.bprcma.form

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import com.layanacomputindo.bprcma.R
import kotlinx.android.synthetic.main.activity_pemeriksaan_jaminan.*

class PemeriksaanJaminanActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pemeriksaan_jaminan)
        setSupportActionBar(toolbar as Toolbar?)
        supportActionBar?.run {
            title = getString(R.string.pemr_jaminan)
            setDisplayHomeAsUpEnabled(true)
        }

        tabungan_deposito.setOnClickListener(this)
        kendaraan.setOnClickListener(this)
        tanah_bangunan.setOnClickListener(this)
    }

    override fun onClick(p0: View) {
        when(p0.id){
            R.id.tabungan_deposito -> {
                val intent = Intent(this, TabunganDepositoActivity::class.java)
                startActivity(intent)
            }
            R.id.kendaraan -> {
                val intent = Intent(this, KendaraanActivity::class.java)
                startActivity(intent)
            }
            R.id.tanah_bangunan -> {
                val intent = Intent(this, TanahBangunanActivity::class.java)
                startActivity(intent)
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
