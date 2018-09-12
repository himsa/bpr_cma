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
import android.view.View
import android.widget.Toast
import com.layanacomputindo.bprcma.adapter.CustomerAprovalListAdapter
import com.layanacomputindo.bprcma.model.Aproval
import com.layanacomputindo.bprcma.model.CurrentPage
import com.layanacomputindo.bprcma.model.Result
import com.layanacomputindo.bprcma.rest.RestClient
import com.layanacomputindo.bprcma.util.Config
import kotlinx.android.synthetic.main.activity_aproval_list.*
import kotlinx.android.synthetic.main.toolbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AprovalListActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var rvCustomerAproval: RecyclerView
    private lateinit var rvAdapter: CustomerAprovalListAdapter
    private lateinit var customerArrayList: ArrayList<Aproval>
    private lateinit var lm: RecyclerView.LayoutManager

    private lateinit var sharedPreferences: SharedPreferences

    private var pDialog: ProgressDialog? = null
    override fun onClick(p0: View) {
        when(p0.id){
            R.id.menu_profile -> {
                val i = Intent(this,ProfileActivity::class.java)
                startActivity(i)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aproval_list)
        val myToolbar = findViewById(R.id.toolbar_top) as Toolbar
        myToolbar.title = ""
        setSupportActionBar(myToolbar)

        menu_profile.setOnClickListener(this)

        pDialog = ProgressDialog.show(this,
                "",
                "Tunggu Sebentar!")
        sharedPreferences = getSharedPreferences(Config.PREF_NAME,
                Activity.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("from", "repeat")
        editor.apply()
        rvCustomerAproval = rv_customer_aproval
        lm = LinearLayoutManager(this@AprovalListActivity)
        rvCustomerAproval.setHasFixedSize(true)
        rvCustomerAproval.layoutManager = lm
        addData()
        rvCustomerAproval.addOnScrollListener(object : RecyclerView.OnScrollListener() {
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
            customerArrayList.clear()
            addData()
            srlRefresh.isRefreshing = false
        })
    }

    private fun addData() {
        val service by lazy {
            RestClient.getClient(this)
        }
        val call = service.getKreditAproval()
        call.enqueue(object: Callback<Result<CurrentPage<Aproval>>>{
            override fun onFailure(call: Call<Result<CurrentPage<Aproval>>>?, t: Throwable?) {
                pDialog!!.dismiss()
                Log.e("on Failure", t.toString())
                Toast.makeText(applicationContext, R.string.cekkoneksi, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<Result<CurrentPage<Aproval>>>?, response: Response<Result<CurrentPage<Aproval>>>?) {
                Log.d("debitur", "Status Code = " + response!!.code())
                if(response.isSuccessful){
                    pDialog!!.dismiss()
                    val result = response.body()
                    if (result != null) {
                        if (result.getStatus()!!) {
                            val page = result.getData()
                            if(page != null){
                                customerArrayList = page.getData() as ArrayList<Aproval>
                                if(true){
                                    rvAdapter = CustomerAprovalListAdapter(customerArrayList, this@AprovalListActivity)
                                    rvCustomerAproval.adapter = rvAdapter
                                    rvAdapter.notifyDataSetChanged()
                                }
                                else{
                                    Toast.makeText(baseContext, result.getMessage(), Toast.LENGTH_SHORT).show()
                                }
                            }
                        } else {
                            Log.e("debitur", response.raw().toString())
                            Toast.makeText(baseContext, result.getMessage(), Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

        })
    }
}
