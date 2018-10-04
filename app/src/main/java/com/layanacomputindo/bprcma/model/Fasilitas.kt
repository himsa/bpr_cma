package com.layanacomputindo.bprcma.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class Fasilitas {
    @SerializedName("id")
    @Expose
    private var id: Int? = null
    @SerializedName("jaminan_tanah_bangunan_id")
    @Expose
    private var jaminanTanahBangunanId: Int? = null
    @SerializedName("saluran_listrik_watt")
    @Expose
    private var saluranListrikWatt: Int? = null
    @SerializedName("saluran_listrik_volt")
    @Expose
    private var saluranListrikVolt: Int? = null
    @SerializedName("saluran_telepon")
    @Expose
    private var saluranTelepon: String? = null
    @SerializedName("sumber_air")
    @Expose
    private var sumberAir: String? = null
    @SerializedName("created_at")
    @Expose
    private var createdAt: String? = null
    @SerializedName("updated_at")
    @Expose
    private var updatedAt: String? = null

    fun getId(): Int? {
        return id
    }

    fun setId(id: Int?) {
        this.id = id
    }

    fun getJaminanTanahBangunanId(): Int? {
        return jaminanTanahBangunanId
    }

    fun setJaminanTanahBangunanId(jaminanTanahBangunanId: Int?) {
        this.jaminanTanahBangunanId = jaminanTanahBangunanId
    }

    fun getSaluranListrikWatt(): Int? {
        return saluranListrikWatt
    }

    fun setSaluranListrikWatt(saluranListrikWatt: Int?) {
        this.saluranListrikWatt = saluranListrikWatt
    }

    fun getSaluranListrikVolt(): Int? {
        return saluranListrikVolt
    }

    fun setSaluranListrikVolt(saluranListrikVolt: Int?) {
        this.saluranListrikVolt = saluranListrikVolt
    }

    fun getSaluranTelepon(): String? {
        return saluranTelepon
    }

    fun setSaluranTelepon(saluranTelepon: String) {
        this.saluranTelepon = saluranTelepon
    }

    fun getSumberAir(): String? {
        return sumberAir
    }

    fun setSumberAir(sumberAir: String) {
        this.sumberAir = sumberAir
    }

    fun getCreatedAt(): String? {
        return createdAt
    }

    fun setCreatedAt(createdAt: String) {
        this.createdAt = createdAt
    }

    fun getUpdatedAt(): String? {
        return updatedAt
    }

    fun setUpdatedAt(updatedAt: String) {
        this.updatedAt = updatedAt
    }
}