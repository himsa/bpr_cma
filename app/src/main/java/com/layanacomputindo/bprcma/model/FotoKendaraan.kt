package com.layanacomputindo.bprcma.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class FotoKendaraan {
    @SerializedName("id")
    @Expose
    private var id: Int? = null
    @SerializedName("jaminan_kendaraan_id")
    @Expose
    private var jaminanKendaraanId: Int? = null
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
    @SerializedName("foto_nomor_rangka")
    @Expose
    private var fotoNomorRangka: String? = null
    @SerializedName("foto_nomor_mesin")
    @Expose
    private var fotoNomorMesin: String? = null
    @SerializedName("foto_bpkb")
    @Expose
    private var fotoBpkb: String? = null
    @SerializedName("foto_stnk")
    @Expose
    private var fotoStnk: String? = null
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

    fun getJaminanKendaraanId(): Int? {
        return jaminanKendaraanId
    }

    fun setJaminanKendaraanId(jaminanKendaraanId: Int?) {
        this.jaminanKendaraanId = jaminanKendaraanId
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

    fun getFotoNomorRangka(): String? {
        return fotoNomorRangka
    }

    fun setFotoNomorRangka(fotoNomorRangka: String) {
        this.fotoNomorRangka = fotoNomorRangka
    }

    fun getFotoNomorMesin(): String? {
        return fotoNomorMesin
    }

    fun setFotoNomorMesin(fotoNomorMesin: String) {
        this.fotoNomorMesin = fotoNomorMesin
    }

    fun getFotoBpkb(): String? {
        return fotoBpkb
    }

    fun setFotoBpkb(fotoBpkb: String) {
        this.fotoBpkb = fotoBpkb
    }

    fun getFotoStnk(): String? {
        return fotoStnk
    }

    fun setFotoStnk(fotoStnk: String) {
        this.fotoStnk = fotoStnk
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