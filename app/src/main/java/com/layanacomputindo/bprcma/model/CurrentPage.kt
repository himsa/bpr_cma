package com.layanacomputindo.bprcma.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class CurrentPage {
    @SerializedName("current_page")
    @Expose
    private var currentPage: Int? = null
    @SerializedName("data")
    @Expose
    private var data: ArrayList<Debitur>? = null

    fun getCurrentPage(): Int? {
        return currentPage
    }

    fun setCurrentPage(currentPage: Int?) {
        this.currentPage = currentPage
    }

    fun getData(): ArrayList<Debitur>? {
        return data
    }

    fun setData(data: ArrayList<Debitur>) {
        this.data = data
    }
}