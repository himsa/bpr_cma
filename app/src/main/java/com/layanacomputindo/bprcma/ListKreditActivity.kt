package com.layanacomputindo.bprcma

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.layanacomputindo.bprcma.adapter.KreditListAdapter
import com.layanacomputindo.bprcma.form.InfoNasabahPart1Activity
import com.layanacomputindo.bprcma.model.Kredit
import com.layanacomputindo.bprcma.model.Results
import com.layanacomputindo.bprcma.rest.RestClient
import com.layanacomputindo.bprcma.util.Config
import kotlinx.android.synthetic.main.activity_list_kredit.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListKreditActivity : AppCompatActivity(), View.OnClickListener {
    override fun onClick(p0: View) {
        when(p0.id){
            R.id.btn_add_kredit -> {
                val editor = sharedPreferences.edit()
                editor.putString("from", "main")
                editor.apply()
                startActivity(Intent(applicationContext, InfoNasabahPart1Activity::class.java))
            }
        }
    }

    private lateinit var rvKredit: RecyclerView
    private lateinit var rvAdapter: KreditListAdapter
    private lateinit var kreditArrayList: ArrayList<Kredit>
    private lateinit var lm: RecyclerView.LayoutManager

    private lateinit var sharedPreferences: SharedPreferences

    private var pDialog: ProgressDialog? = null

    var idDebitur = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_kredit)
        setSupportActionBar(toolbar as Toolbar?)
        supportActionBar?.run {
            title = getString(R.string.action_list_kredit)
            setDisplayHomeAsUpEnabled(true)
        }

        setPreferences()
        btn_add_kredit.setOnClickListener(this)

        pDialog = ProgressDialog.show(this,
                "",
                "Tunggu Sebentar!")
        rvKredit = rv_customer
        lm = LinearLayoutManager(this@ListKreditActivity)
        rvKredit.setHasFixedSize(true)
        rvKredit.layoutManager = lm
        addData()
        rvKredit.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val topRowVerticalPosition = if (recyclerView.childCount == 0) 0 else recyclerView.getChildAt(0).top
                srlRefresh.isEnabled = topRowVerticalPosition >= 0
            }
        })
        srlRefresh.setColorSchemeResources(
                R.color.red_soft,
                R.color.yellow_soft,
                R.color.green_soft)

        srlRefresh.setOnRefreshListener({
            kreditArrayList.clear()
            addData()
            srlRefresh.isRefreshing = false
        })
    }

    private fun addData() {
        val service by lazy {
            RestClient.getClient(this)
        }
        val call = service.getKreditByDebitur(idDebitur)
        call.enqueue(object: Callback<Results<Kredit>>{
            override fun onFailure(call: Call<Results<Kredit>>?, t: Throwable?) {
                pDialog!!.dismiss()
                Log.e("on Failure", t.toString())
                Toast.makeText(applicationContext, R.string.cekkoneksi, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<Results<Kredit>>, response: Response<Results<Kredit>>) {
                Log.d("kredit", "Status Code = " + response.code())
                if(response.isSuccessful){
                    pDialog!!.dismiss()
                    val result = response.body()
                    if (result != null) {
                        if (result.getStatus()!!) {
                            kreditArrayList = result.getData() as ArrayList<Kredit>
                            rvAdapter = KreditListAdapter(kreditArrayList, this@ListKreditActivity)
                            rvKredit.adapter = rvAdapter
                            rvAdapter.notifyDataSetChanged()
                        } else {
                            Log.e("kredit", response.raw().toString())
                            Toast.makeText(baseContext, result.getMessage(), Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

        })
    }

    private fun setPreferences() {
        sharedPreferences = getSharedPreferences(Config.PREF_NAME,
                Activity.MODE_PRIVATE)
        idDebitur = sharedPreferences.getInt(Config.DEBITUR_ID, Config.EMPTY_INT)
        if(sharedPreferences.getString(Config.ROLE, "") == "komite"||sharedPreferences.getString(Config.ROLE, "") == "supervisor"){
            btn_add_kredit.visibility = View.GONE
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return true
    }
}
