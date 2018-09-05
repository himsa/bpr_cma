package com.layanacomputindo.bprcma

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import com.layanacomputindo.bprcma.adapter.MyPagerAdapter
import com.layanacomputindo.bprcma.form.PemeriksaanJaminanActivity
import com.layanacomputindo.bprcma.util.Config
import kotlinx.android.synthetic.main.activity_list_jaminan.*

class ListJaminanActivity : AppCompatActivity(), View.OnClickListener {
    override fun onClick(p0: View) {
        when(p0.id){
            R.id.btn_add_jaminan -> {
                val editor = sharedPreferences.edit()
                editor.putString("from", "main")
                editor.apply()
                startActivity(Intent(applicationContext, PemeriksaanJaminanActivity::class.java))
            }
        }
    }

    private lateinit var rvKredit: RecyclerView
//    private lateinit var rvAdapter: KreditListAdapter
//    private lateinit var kreditArrayList: ArrayList<Kredit>
    private lateinit var lm: RecyclerView.LayoutManager

    private lateinit var sharedPreferences: SharedPreferences

    private var pDialog: ProgressDialog? = null

    var idDebitur = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_jaminan)
        setSupportActionBar(toolbar as Toolbar?)
        supportActionBar?.run {
            title = getString(R.string.action_list_jaminan)
            setDisplayHomeAsUpEnabled(true)
        }

        setPreferences()
        btn_add_jaminan.setOnClickListener(this)

        val fragmentAdapter = MyPagerAdapter(supportFragmentManager)
        viewpager_main.adapter = fragmentAdapter

        tabs_main.setupWithViewPager(viewpager_main)
    }

    private fun setPreferences() {
        sharedPreferences = getSharedPreferences(Config.PREF_NAME,
                Activity.MODE_PRIVATE)
        if(sharedPreferences.getString(Config.ROLE, "") == "komite"||sharedPreferences.getString(Config.ROLE, "") == "supervisor"){
            btn_add_jaminan.visibility = View.GONE
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return true
    }
}
