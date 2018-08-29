package com.layanacomputindo.bprcma

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import com.layanacomputindo.bprcma.util.Config
import kotlinx.android.synthetic.main.activity_detail_customer.*

class DetailCustomerActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var btnBack: ImageButton

    private lateinit var btnSearch: ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_customer)
        setSupportActionBar(toolbar as Toolbar?)
        supportActionBar?.run {
            title = getString(R.string.action_informasi_kredit)
            setDisplayHomeAsUpEnabled(true)
        }

        btn_dftr_kredit.setOnClickListener(this)
        btn_hub.setOnClickListener(this)
    }

    override fun onClick(p0: View) {
        when(p0.id){
            R.id.btn_dftr_kredit -> {
                startActivity(Intent(this, ListKreditActivity::class.java))
            }
            R.id.btn_hub -> {
                val phoneNumber = txt_telp_debitur.text
                val dialPhoneIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
                startActivity(dialPhoneIntent)
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
