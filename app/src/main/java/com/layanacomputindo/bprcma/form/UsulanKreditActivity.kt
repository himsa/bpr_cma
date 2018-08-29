package com.layanacomputindo.bprcma.form

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import com.layanacomputindo.bprcma.R
import kotlinx.android.synthetic.main.activity_usulan_kredit.*

class UsulanKreditActivity : AppCompatActivity(), RadioGroup.OnCheckedChangeListener, View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_usulan_kredit)
        setSupportActionBar(toolbar as Toolbar?)
        supportActionBar?.run {
            title = getString(R.string.usulan_kredit)
            setDisplayHomeAsUpEnabled(true)
        }

        rg_jnis_kredit.setOnCheckedChangeListener(this)
        rg_tata_cara_bayar.setOnCheckedChangeListener(this)

        btn_next_usulan_kredit.setOnClickListener(this)
    }

    override fun onClick(p0: View) {
        when(p0.id){
            R.id.btn_next_usulan_kredit -> {
                val intent = Intent(this, PemeriksaanJaminanActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onCheckedChanged(p0: RadioGroup?, p1: Int) {
        when(p1){
            R.id.rb_baru -> {
                val radioButton = findViewById(R.id.rb_baru) as RadioButton

                Toast.makeText(this,
                        radioButton.getText(), Toast.LENGTH_SHORT).show()
            }
            R.id.rb_tambahan -> {
                val radioButton = findViewById(R.id.rb_tambahan) as RadioButton

                Toast.makeText(this,
                        radioButton.getText(), Toast.LENGTH_SHORT).show()
            }
            R.id.rb_pembaharuan -> {
                val radioButton = findViewById(R.id.rb_pembaharuan) as RadioButton

                Toast.makeText(this,
                        radioButton.getText(), Toast.LENGTH_SHORT).show()
            }
            R.id.rb_angsuran -> {
                val radioButton = findViewById(R.id.rb_angsuran) as RadioButton

                Toast.makeText(this,
                        radioButton.getText(), Toast.LENGTH_SHORT).show()
            }
            R.id.rb_berjangka -> {
                val radioButton = findViewById(R.id.rb_berjangka) as RadioButton

                Toast.makeText(this,
                        radioButton.getText(), Toast.LENGTH_SHORT).show()
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
