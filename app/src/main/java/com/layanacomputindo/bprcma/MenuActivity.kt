package com.layanacomputindo.bprcma

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_menu.*
import kotlinx.android.synthetic.main.toolbar.*
import com.layanacomputindo.bprcma.R.layout.toolbar
import com.layanacomputindo.bprcma.model.Result
import com.layanacomputindo.bprcma.model.UserId
import com.layanacomputindo.bprcma.rest.RestClient
import com.layanacomputindo.bprcma.util.Config
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MenuActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var profile: ImageButton
    private lateinit var pengajuan: ImageButton
    private lateinit var infomasi: ImageButton
    private lateinit var proses: ImageButton

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        val myToolbar = findViewById(R.id.toolbar_top) as Toolbar
        myToolbar.title = ""
        setSupportActionBar(myToolbar)

        sharedPreferences = getSharedPreferences(Config.PREF_NAME,
                Activity.MODE_PRIVATE)

        profile = menu_profile
        pengajuan = ib_pengajuan
        infomasi = ib_informasi
        proses = ib_proses

        profile.setOnClickListener(this)
        pengajuan.setOnClickListener(this)
        infomasi.setOnClickListener(this)
        proses.setOnClickListener(this)
    }

    override fun onClick(p0: View) {
        when(p0.id){
            R.id.menu_profile -> {
                val i = Intent(this,ProfileActivity::class.java)
                startActivity(i)
            }
            R.id.ib_pengajuan -> {
                startActivity(Intent(applicationContext, OpsiPengajuanActivity::class.java))
            }
            R.id.ib_informasi -> {
                val i = Intent(this,InformasiKreditActivity::class.java)
                val editor = sharedPreferences.edit()
                editor.putString("from", "repeat")
                editor.putString("status", "disetujui")
                editor.apply()
                startActivity(i)
            }
            R.id.ib_proses -> {
                val i = Intent(this,InformasiKreditActivity::class.java)
                val editor = sharedPreferences.edit()
                editor.putString("from", "repeat")
                editor.putString("status", "cancel")
                editor.apply()
                startActivity(i)
            }
        }
    }
}
