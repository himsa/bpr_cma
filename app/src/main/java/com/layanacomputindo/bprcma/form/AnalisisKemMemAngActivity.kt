package com.layanacomputindo.bprcma.form

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.layanacomputindo.bprcma.R
import kotlinx.android.synthetic.main.activity_analisis_kem_mem_ang.*

class AnalisisKemMemAngActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_analisis_kem_mem_ang)
        setSupportActionBar(toolbar as Toolbar?)
        supportActionBar?.run {
            title = getString(R.string.analisis_kem_mem_ang)
            setDisplayHomeAsUpEnabled(true)
        }

        btn_next_an_kem_mem.setOnClickListener(this)
    }

    override fun onClick(p0: View) {
        when(p0.id){
            R.id.btn_next_an_kem_mem -> {
                val intent = Intent(this, KesimpulanActivity::class.java)
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
