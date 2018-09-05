package com.layanacomputindo.bprcma.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.layanacomputindo.bprcma.InformasiKreditActivity
import com.layanacomputindo.bprcma.ListJaminanActivity
import com.layanacomputindo.bprcma.R
import com.layanacomputindo.bprcma.form.InfoNasabahPart1Activity
import com.layanacomputindo.bprcma.model.Kredit
import com.layanacomputindo.bprcma.util.Config
import com.squareup.picasso.Picasso
import java.text.DateFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

class KreditListAdapter(private var mList: ArrayList<Kredit>, val context: Context): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var sharedPreferences: SharedPreferences

    inner class ProgressViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var pbLoading: ProgressBar = v.findViewById(R.id.pbLoading)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvDate: TextView = itemView.findViewById(R.id.tv_kredit_date)
        var tvNominal: TextView = itemView.findViewById(R.id.tv_kredit_nominal)
        var tvTempo: TextView = itemView.findViewById(R.id.tv_kredit_tempo)
        var tvStatusLunas: TextView = itemView.findViewById(R.id.tv_kredit_status_lunas)
        var btnDetail: TextView = itemView.findViewById(R.id.btn_detail_kredit)
        var btnJaminan: TextView = itemView.findViewById(R.id.btn_jaminan_kredit)
    }

    override fun getItemViewType(position: Int): Int {
        return if (mList[position].getId() != 0) 1 else 0
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder

        vh = if (viewType == 1) {
            val v = LayoutInflater.from(parent.context).inflate(R.layout.list_item_kredit, parent, false)
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

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder){
            var formatDate = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val newDate = formatDate.parse(mList[position].getCreatedAt())
            var formatDate2 = SimpleDateFormat("dd MMMM yyyy")
            val date = formatDate2.format(newDate)
            holder.tvDate.text = date

            val format = NumberFormat.getInstance(Locale.GERMANY)
            holder.tvNominal.text = "Rp" + format.format(mList[position].getNominal()) + ",00"

            holder.btnDetail.setOnClickListener{
                sharedPreferences = context.getSharedPreferences(Config.PREF_NAME,
                        Activity.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                mList[position].getId()?.let { it1 -> editor.putInt(Config.KREDIT_ID, it1) }
                editor.apply()
                context.startActivity(Intent(context, InfoNasabahPart1Activity::class.java))
            }
            holder.btnJaminan.setOnClickListener{
                sharedPreferences = context.getSharedPreferences(Config.PREF_NAME,
                        Activity.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                mList[position].getId()?.let { it1 -> editor.putInt(Config.KREDIT_ID, it1) }
                editor.apply()
                context.startActivity(Intent(context, ListJaminanActivity::class.java))
            }
        }else{
            (holder as ProgressViewHolder).pbLoading.isIndeterminate = true
        }
    }
}