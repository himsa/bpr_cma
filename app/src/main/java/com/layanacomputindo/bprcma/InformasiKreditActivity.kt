package com.layanacomputindo.bprcma

import android.app.Activity
import android.app.ProgressDialog
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.isapanah.awesomespinner.AwesomeSpinner
import com.layanacomputindo.bprcma.adapter.CustomerListAdapter
import com.layanacomputindo.bprcma.model.CurrentPage
import com.layanacomputindo.bprcma.model.Customer
import com.layanacomputindo.bprcma.model.Debitur
import com.layanacomputindo.bprcma.model.Result
import com.layanacomputindo.bprcma.rest.RestClient
import com.layanacomputindo.bprcma.util.Config
import com.layanacomputindo.bprcma.util.EndlessRecyclerViewScrollListener
import kotlinx.android.synthetic.main.activity_informasi_kredit.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class InformasiKreditActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var rvCustomer: RecyclerView
    private lateinit var rvAdapter: CustomerListAdapter
    private lateinit var customerArrayList: ArrayList<Debitur>
    private lateinit var lm: RecyclerView.LayoutManager

    private lateinit var sharedPreferences: SharedPreferences

    private var pDialog: ProgressDialog? = null

    private var scrollListener: EndlessRecyclerViewScrollListener? = null

    private lateinit var spFilter: AwesomeSpinner
    private lateinit var spSortBy: AwesomeSpinner
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_informasi_kredit)
        setSupportActionBar(toolbar as Toolbar?)
        sharedPreferences = getSharedPreferences(Config.PREF_NAME,
                Activity.MODE_PRIVATE)
        supportActionBar?.run {
            if(sharedPreferences.getString(Config.ROLE, "")== "komite"||sharedPreferences.getString(Config.ROLE, "")== "supervisor"){
                title = getString(R.string.action_informasi_kredit)
                setDisplayHomeAsUpEnabled(false)
            }else{
                title = getString(R.string.action_informasi_kredit)
                setDisplayHomeAsUpEnabled(true)
            }
        }

        pDialog = ProgressDialog.show(this,
                "",
                "Tunggu Sebentar!")

        spFilter = sp_filter
        spSortBy = sp_sort_by

        val filterAdapter = ArrayAdapter.createFromResource(this, R.array.filter, android.R.layout.simple_spinner_item)
        val sortByAdapter = ArrayAdapter.createFromResource(this, R.array.sortBy, android.R.layout.simple_spinner_item)

        spFilter.setAdapter(filterAdapter, 0)
        spSortBy.setAdapter(sortByAdapter, 0)

        spFilter.setOnSpinnerItemClickListener({ position, itemAtPosition ->
            Toast.makeText(this, itemAtPosition, Toast.LENGTH_SHORT).show()
        })
        spSortBy.setOnSpinnerItemClickListener({ position, itemAtPosition ->
            Toast.makeText(this, itemAtPosition, Toast.LENGTH_SHORT).show()
        })


        rvCustomer = rv_customer
        lm = LinearLayoutManager(this@InformasiKreditActivity)
        rvCustomer.setHasFixedSize(true)
        rvCustomer.layoutManager = lm
        addData(sharedPreferences.getString("status", ""))
        scrollListener = object : EndlessRecyclerViewScrollListener(lm as LinearLayoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
//                loadNextDataFromApi(page)
                Log.e("PAGE", page.toString())
                addNextData(sharedPreferences.getString("status", ""), page + 1)
            }
        }
        rvCustomer.addOnScrollListener(scrollListener as EndlessRecyclerViewScrollListener)
        rvCustomer.addOnScrollListener(object : RecyclerView.OnScrollListener() {
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
            addData(sharedPreferences.getString("status", ""))
            srlRefresh.isRefreshing = false
        })
    }

    private fun addNextData(status: String, page: Int) {
        val service by lazy {
            RestClient.getClient(this)
        }
        val call = service.getInfoKreditByStatusNext(status, page)
        call.enqueue(object: Callback<Result<CurrentPage<Debitur>>>{
            override fun onFailure(call: Call<Result<CurrentPage<Debitur>>>?, t: Throwable?) {
                pDialog!!.dismiss()
                Log.e("on Failure", t.toString())
                Toast.makeText(applicationContext, R.string.cekkoneksi, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<Result<CurrentPage<Debitur>>>?, response: Response<Result<CurrentPage<Debitur>>>?) {
                Log.d("debitur", "Status Code = " + response!!.code())
                if(response.isSuccessful){
                    pDialog!!.dismiss()
                    val result = response.body()
                    if (result != null) {
                        if (result.getStatus()!!) {
                            val page = result.getData()
                            if(page != null){
                                if(true){
                                    for (items in page.getData()!!){
                                        customerArrayList.add(items)
                                    }
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu, this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        val myActionMenuItem = menu.findItem(R.id.app_bar_search)
        val searchView = myActionMenuItem.actionView as androidx.appcompat.widget.SearchView
        searchView.setQueryHint(resources.getText(R.string.search_name))
        searchView.setIconified(false)
        searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                // Toast like print
                Toast.makeText(baseContext, query, Toast.LENGTH_SHORT).show()
                if (!searchView.isIconified()) {
                    searchView.setIconified(true)
                }
                myActionMenuItem.collapseActionView()
                return false
            }



            override fun onQueryTextChange(s: String): Boolean {
                if(s.isEmpty()){
                    customerArrayList.clear()
                    addData(sharedPreferences.getString("status", ""))
                }else{
                    rvAdapter.filter(s)
                }
                return false
            }
        })
        return true
    }

//    private fun addData() {
//        customerArrayList = ArrayList()
//        customerArrayList.add(Customer(1, "Disetujui", sharedPreferences.getString("from", ""), "Himsa Yushistira", "Pogung Lor RT 8 No 36, Sinduadi, Mlati, Sleman, Yogyakarta", "+6285658970892"))
//
//        rvAdapter = CustomerListAdapter(customerArrayList, this@InformasiKreditActivity)
//        rvCustomer.adapter = rvAdapter
//        rvAdapter.notifyDataSetChanged()
//    }

    private fun addData(status: String) {
        val service by lazy {
            RestClient.getClient(this)
        }
        val call = service.getInfoKreditByStatus(status)
        call.enqueue(object : Callback<Result<CurrentPage<Debitur>>>{
            override fun onFailure(call: Call<Result<CurrentPage<Debitur>>>?, t: Throwable?) {
                pDialog!!.dismiss()
                Log.e("on Failure", t.toString())
                Toast.makeText(applicationContext, R.string.cekkoneksi, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<Result<CurrentPage<Debitur>>>, response: Response<Result<CurrentPage<Debitur>>>) {
                Log.d("debitur", "Status Code = " + response.code())
                if(response.isSuccessful){
                    pDialog!!.dismiss()
                    val result = response.body()
                    if (result != null) {
                        if (result.getStatus()!!) {
                            val page = result.getData()
                            if(page != null){
                                customerArrayList = page.getData() as ArrayList<Debitur>
                                if(true){
                                    rvAdapter = CustomerListAdapter(customerArrayList, this@InformasiKreditActivity)
                                    rvCustomer.adapter = rvAdapter
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

    override fun onClick(p0: View) {
        when(p0.id){

        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return true
    }
}
