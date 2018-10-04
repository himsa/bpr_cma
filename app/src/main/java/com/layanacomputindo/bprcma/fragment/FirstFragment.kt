package com.layanacomputindo.bprcma.fragment

import android.app.Activity
import android.app.ProgressDialog
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.layanacomputindo.bprcma.R
import com.layanacomputindo.bprcma.adapter.TanahListAdapter
import com.layanacomputindo.bprcma.model.Results
import com.layanacomputindo.bprcma.model.TanahBangunan
import com.layanacomputindo.bprcma.rest.RestClient
import com.layanacomputindo.bprcma.util.Config
import kotlinx.android.synthetic.main.fragment_first.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FirstFragment : Fragment() {
    private lateinit var rvAdapter: TanahListAdapter
    private lateinit var tanahArrayList: ArrayList<TanahBangunan>
    private lateinit var lm: RecyclerView.LayoutManager

    private lateinit var sharedPreferences: SharedPreferences

    private var pDialog: ProgressDialog? = null

    var idKredit = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_first, container, false)
        sharedPreferences = activity!!.getSharedPreferences(Config.PREF_NAME,
                Activity.MODE_PRIVATE)
        idKredit = sharedPreferences.getInt(Config.KREDIT_ID, Config.EMPTY_INT)
        // Inflate the layout for this fragment
        return v
    }

    override fun onResume() {
        super.onResume()
        pDialog = ProgressDialog.show(activity,
                "",
                "Tunggu Sebentar!")
        lm = LinearLayoutManager(activity)
        rv_tanah.setHasFixedSize(true)
        rv_tanah.layoutManager = lm
        addData(idKredit)
        rv_tanah.addOnScrollListener(object : RecyclerView.OnScrollListener() {
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
            tanahArrayList.clear()
            addData(idKredit)
            srlRefresh.isRefreshing = false
        })
    }

    private fun addData(idKredit: Int) {
        val service by lazy {
            RestClient.getClient(context)
        }
        val call = service.getListJaminanTanah(idKredit)
        call.enqueue(object: Callback<Results<TanahBangunan>>{
            override fun onFailure(call: Call<Results<TanahBangunan>>?, t: Throwable?) {
                pDialog!!.dismiss()
                Log.e("on Failure", t.toString())
                Toast.makeText(context, R.string.cekkoneksi, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<Results<TanahBangunan>>, response: Response<Results<TanahBangunan>>) {
                Log.d("jaminan", "Status Code = " + response.code())
                if(response.isSuccessful){
                    pDialog!!.dismiss()
                    val result = response.body()
                    if (result != null) {
                        if (result.getStatus()!!) {
                            tanahArrayList = result.getData() as ArrayList<TanahBangunan>
                            rvAdapter = TanahListAdapter(tanahArrayList, context)
                            rv_tanah.adapter = rvAdapter
                            rvAdapter.notifyDataSetChanged()
                        } else {
                            Log.e("kredit", response.raw().toString())
                            Toast.makeText(context, result.getMessage(), Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

        })
    }
}