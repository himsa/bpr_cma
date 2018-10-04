package com.layanacomputindo.bprcma.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class FotoTanah {
    @SerializedName("id")
    @Expose
    private var id: Int? = null
    @SerializedName("jaminan_tanah_bangunan_id")
    @Expose
    private var jaminanTanahBangunanId: Int? = null
    @SerializedName("tampak_muka")
    @Expose
    private var tampakMuka: String? = null
    @SerializedName("tampak_belakang")
    @Expose
    private var tampakBelakang: String? = null
    @SerializedName("tampak_sisi_kiri")
    @Expose
    private var tampakSisiKiri: String? = null
    @SerializedName("tampak_sisi_kanan")
    @Expose
    private var tampakSisiKanan: String? = null
    @SerializedName("tampak_dalam")
    @Expose
    private var tampakDalam: String? = null
    @SerializedName("tampak_lain")
    @Expose
    private var tampakLain: String? = null
    @SerializedName("created_at")
    @Expose
    private var createdAt: String? = null
    @SerializedName("updated_at")
    @Expose
    private var updatedAt: String? = null
    @SerializedName("tampak_sekitar_1")
    @Expose
    private var tampakSekitar1: String? = null
    @SerializedName("tampak_sekitar_2")
    @Expose
    private var tampakSekitar2: String? = null

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

    fun getTampakMuka(): String? {
        return tampakMuka
    }

    fun setTampakMuka(tampakMuka: String) {
        this.tampakMuka = tampakMuka
    }

    fun getTampakBelakang(): String? {
        return tampakBelakang
    }

    fun setTampakBelakang(tampakBelakang: String) {
        this.tampakBelakang = tampakBelakang
    }

    fun getTampakSisiKiri(): String? {
        return tampakSisiKiri
    }

    fun setTampakSisiKiri(tampakSisiKiri: String) {
        this.tampakSisiKiri = tampakSisiKiri
    }

    fun getTampakSisiKanan(): String? {
        return tampakSisiKanan
    }

    fun setTampakSisiKanan(tampakSisiKanan: String) {
        this.tampakSisiKanan = tampakSisiKanan
    }

    fun getTampakDalam(): String? {
        return tampakDalam
    }

    fun setTampakDalam(tampakDalam: String) {
        this.tampakDalam = tampakDalam
    }

    fun getTampakLain(): String? {
        return tampakLain
    }

    fun setTampakLain(tampakLain: String) {
        this.tampakLain = tampakLain
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

    fun getTampakSekitar1(): String? {
        return tampakSekitar1
    }

    fun setTampakSekitar1(tampakSekitar1: String) {
        this.tampakSekitar1 = tampakSekitar1
    }

    fun getTampakSekitar2(): String? {
        return tampakSekitar2
    }

    fun setTampakSekitar2(tampakSekitar2: String) {
        this.tampakSekitar2 = tampakSekitar2
    }

}