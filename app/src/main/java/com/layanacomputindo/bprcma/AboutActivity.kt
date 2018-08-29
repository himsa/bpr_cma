package com.layanacomputindo.bprcma

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_about.*

class AboutActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var btnBack: ImageButton
    private lateinit var faq: TextView
    private lateinit var contactUs: TextView
    private lateinit var terms: TextView
    private lateinit var privacyPolicy: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_about)
        btnBack = back_about
        faq = txt_faq
        contactUs = txt_contact_us
        terms = txt_terms
        privacyPolicy = txt_privacy_policy

        btnBack.setOnClickListener(this)
        faq.setOnClickListener(this)
        contactUs.setOnClickListener(this)
        terms.setOnClickListener(this)
        privacyPolicy.setOnClickListener(this)
    }

    override fun onClick(p0: View) {
        when(p0.id) {
            R.id.back_about -> {
                onBackPressed()
            }
            R.id.txt_faq -> {
                Toast.makeText(this, "FAQ clicked", Toast.LENGTH_SHORT).show()
            }
            R.id.txt_contact_us -> {
                Toast.makeText(this, "Contact Us clicked", Toast.LENGTH_SHORT).show()
            }
            R.id.txt_terms -> {
                Toast.makeText(this, "Terms clicked", Toast.LENGTH_SHORT).show()
            }
            R.id.txt_privacy_policy -> {
                Toast.makeText(this, "Privacy Policy clicked", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()

    }
}
