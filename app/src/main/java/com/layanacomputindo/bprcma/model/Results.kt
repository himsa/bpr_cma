package com.layanacomputindo.bprcma.model

import java.util.ArrayList
import java.util.HashMap
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class Results<D> {
    @SerializedName("status")
    @Expose
    private var status: Boolean? = null
    @SerializedName("message")
    @Expose
    private var message: String? = null
    @SerializedName("data")
    @Expose
    private var data: List<D>? = null

    fun getStatus(): Boolean? {
        return status
    }

    fun setStatus(status: Boolean?) {
        this.status = status
    }

    fun getMessage(): String? {
        return message
    }

    fun setMessage(message: String) {
        this.message = message
    }

    fun getData(): List<D>? {
        return data
    }

    fun setData(data: List<D>) {
        this.data = data
    }
}