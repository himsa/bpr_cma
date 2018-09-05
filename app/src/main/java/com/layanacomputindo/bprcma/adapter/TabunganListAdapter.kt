package com.layanacomputindo.bprcma.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import com.layanacomputindo.bprcma.R
import com.layanacomputindo.bprcma.form.TabunganDepositoActivity
import com.layanacomputindo.bprcma.model.Tabungan
import com.layanacomputindo.bprcma.util.Config

class TabunganListAdapter(private var mList: ArrayList<Tabungan>, val context: Context?): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var sharedPreferences: SharedPreferences

    inner class ProgressViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var pbLoading: ProgressBar = v.findViewById(R.id.pbLoading)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvName: TextView = itemView.findViewById(R.id.nama)
        var tvHat: TextView = itemView.findViewById(R.id.hat)
        var btnDetail: Button = itemView.findViewById(R.id.btn_detail)
    }

    override fun getItemViewType(position: Int): Int {
        return if (mList[position].getId() != 0) 1 else 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder

        vh = if (viewType == 1) {
            val v = LayoutInflater.from(parent.context).inflate(R.layout.list_item_cjaminan_tabungan, parent, false)
            ViewHolder(v)
        } else {
            val v = LayoutInflater.from(parent.context).inflate(R.layout.item_loading, parent, false)
            ProgressViewHolder(v)
        }
        return vh
    }

    override fun getItemCount(): Int {
        return mList.size
    }

//    fun filter(queryText: String) {
//        val copyList = ArrayList<Aproval>()
//        copyList.addAll(mList)
//        mList.clear()
//
//        if (queryText.isEmpty()) {
//            mList.addAll(copyList)
//        } else {
//            for (data in copyList) {
//                if (data.getNama()!!.toLowerCase().contains(queryText.toLowerCase())) {
//                    mList.add(data)
//                }
//            }
//
//        }
//        notifyDataSetChanged()
//    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            holder.tvName.text = mList[position].getNama()
            holder.tvHat.text = mList[position].getNomorBilyet()
            holder.btnDetail.setOnClickListener {
                sharedPreferences = context!!.getSharedPreferences(Config.PREF_NAME,
                        Activity.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                mList[position].getId()?.let { it1 -> editor.putInt(Config.JAMINAN_ID, it1) }
                editor.apply()
                context.startActivity(Intent(context, TabunganDepositoActivity::class.java))
            }
        } else {
            (holder as ProgressViewHolder).pbLoading.isIndeterminate = true
        }
    }
}