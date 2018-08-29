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
import com.layanacomputindo.bprcma.DetailCustomerActivity
import com.layanacomputindo.bprcma.ListKreditActivity
import com.layanacomputindo.bprcma.R
import com.layanacomputindo.bprcma.form.InfoNasabahPart1Activity
import com.layanacomputindo.bprcma.model.Customer
import com.layanacomputindo.bprcma.model.Debitur
import com.layanacomputindo.bprcma.util.Config
import com.squareup.picasso.Picasso


class CustomerListAdapter(private var mList: ArrayList<Debitur>, val context: Context): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var sharedPreferences: SharedPreferences

    inner class ProgressViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var pbLoading: ProgressBar = v.findViewById(R.id.pbLoading)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var ivPicture: ImageView = itemView.findViewById(R.id.img_customer_info_kredit)
        var tvStatus: TextView = itemView.findViewById(R.id.txt_status_kredit)
        var tvName: TextView = itemView.findViewById(R.id.nama)
        var tvAlamat: TextView = itemView.findViewById(R.id.alamat)
        var tvPhone: TextView = itemView.findViewById(R.id.phone)
        var btnHubungi: Button = itemView.findViewById(R.id.btn_hubungi)
    }

    override fun getItemViewType(position: Int): Int {
        return if (mList[position].getId() != 0) 1 else 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder

        vh = if (viewType == 1) {
            val v = LayoutInflater.from(parent.context).inflate(R.layout.list_item_customer_info_kredit, parent, false)
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

    fun filter(queryText: String) {
        val copyList = ArrayList<Debitur>()
        copyList.addAll(mList)
        mList.clear()

        if (queryText.isEmpty()) {
            mList.addAll(copyList)
        } else {
            for (data in copyList) {
                if (data.getNama()!!.toLowerCase().contains(queryText.toLowerCase())) {
                    mList.add(data)
                }
            }

        }
        notifyDataSetChanged()
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder){
            holder.tvStatus.text = mList[position].getKredit()!![0].getStatusKredit()
            when(mList[position].getKredit()!![0].getStatusKredit()){
                "disetujui" -> {
                    holder.tvStatus.setBackgroundResource(R.drawable.rounded_corner_green)
                }
                "cancel" -> {
                    holder.tvStatus.setBackgroundResource(R.drawable.rounded_corner_yellow)
                    holder.tvStatus.text = "draft"
                }
                "ditolak" -> {
                    holder.tvStatus.setBackgroundResource(R.drawable.rounded_corner_red)
                }
            }
            Picasso.with(context).load(mList[position].getFotoDebitur()?.getFotoKtp()).error(android.R.drawable.stat_notify_error).into(holder.ivPicture)
            holder.tvName.text = mList[position].getNama()
            holder.tvAlamat.text = mList[position].getAlamat()
            val listTelp = mList[position].getTeleponDebitur()
            if (listTelp != null) {
                if( listTelp.isEmpty()){
                    holder.tvPhone.text = "Nomor Telepon Kosong"
                    holder.btnHubungi.setOnClickListener {
                        sharedPreferences = context.getSharedPreferences(Config.PREF_NAME,
                                Activity.MODE_PRIVATE)
                        val editor = sharedPreferences.edit()
                        mList[position].getId()?.let { it1 -> editor.putInt(Config.DEBITUR_ID, it1) }
                        editor.apply()
                        context.startActivity(Intent(context, DetailCustomerActivity::class.java))
//                        val phoneNumber = listTelp[0].getNoTelepon()
//                        val dialPhoneIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
//                        context.startActivity(dialPhoneIntent)
                    }
                }else{
                    holder.tvPhone.text = listTelp[0].getNoTelepon()
                    holder.btnHubungi.setOnClickListener {
                        sharedPreferences = context.getSharedPreferences(Config.PREF_NAME,
                                Activity.MODE_PRIVATE)
                        val editor = sharedPreferences.edit()
                        mList[position].getId()?.let { it1 -> editor.putInt(Config.DEBITUR_ID, it1) }
                        editor.apply()
                        context.startActivity(Intent(context, DetailCustomerActivity::class.java))
//                        val phoneNumber = listTelp[0].getNoTelepon()
//                        val dialPhoneIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
//                        context.startActivity(dialPhoneIntent)
                    }
                }
            }
            holder.itemView.setOnClickListener {
//                context.startActivity(Intent(context, DetailCustomerActivity::class.java))
                sharedPreferences = context.getSharedPreferences(Config.PREF_NAME,
                        Activity.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                mList[position].getId()?.let { it1 -> editor.putInt(Config.DEBITUR_ID, it1) }
                editor.apply()
                context.startActivity(Intent(context, DetailCustomerActivity::class.java))
            }
        }else{
            (holder as ProgressViewHolder).pbLoading.isIndeterminate = true
        }
    }
}