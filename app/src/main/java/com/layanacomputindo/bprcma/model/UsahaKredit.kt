package com.layanacomputindo.bprcma.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class UsahaKredit {
    @SerializedName("id")
    @Expose
    private var id: Int? = null
    @SerializedName("latitude")
    @Expose
    private var latitude: String? = null
    @SerializedName("longitude")
    @Expose
    private var longitude: String? = null
    @SerializedName("keterangan")
    @Expose
    private var keterangan: String? = null
    @SerializedName("detail_keterangan")
    @Expose
    private var detailKeterangan: String? = null
    @SerializedName("mulai_sejak")
    @Expose
    private var mulaiSejak: String? = null
    @SerializedName("status")
    @Expose
    private var status: String? = null
    @SerializedName("detail_deskripsi_usaha")
    @Expose
    private var detailDeskripsiUsaha: String? = null
    @SerializedName("deskripsi_penghasilan_sampingan")
    @Expose
    private var deskripsiPenghasilanSampingan: String? = null
    @SerializedName("created_at")
    @Expose
    private var createdAt: String? = null
    @SerializedName("updated_at")
    @Expose
    private var updatedAt: String? = null
    @SerializedName("kredit_id")
    @Expose
    private var kreditId: Int? = null

    fun getId(): Int? {
        return id
    }

    fun setId(id: Int?) {
        this.id = id
    }

    fun getLatitude(): String? {
        return latitude
    }

    fun setLatitude(latitude: String) {
        this.latitude = latitude
    }

    fun getLongitude(): String? {
        return longitude
    }

    fun setLongitude(longitude: String) {
        this.longitude = longitude
    }

    fun getKeterangan(): String? {
        return keterangan
    }

    fun setKeterangan(keterangan: String) {
        this.keterangan = keterangan
    }

    fun getDetailKeterangan(): String? {
        return detailKeterangan
    }

    fun setDetailKeterangan(detailKeterangan: String) {
        this.detailKeterangan = detailKeterangan
    }

    fun getMulaiSejak(): String? {
        return mulaiSejak
    }

    fun setMulaiSejak(mulaiSejak: String) {
        this.mulaiSejak = mulaiSejak
    }

    fun getStatus(): String? {
        return status
    }

    fun setStatus(status: String) {
        this.status = status
    }

    fun getDetailDeskripsiUsaha(): String? {
        return detailDeskripsiUsaha
    }

    fun setDetailDeskripsiUsaha(detailDeskripsiUsaha: String) {
        this.detailDeskripsiUsaha = detailDeskripsiUsaha
    }

    fun getDeskripsiPenghasilanSampingan(): String? {
        return deskripsiPenghasilanSampingan
    }

    fun setDeskripsiPenghasilanSampingan(deskripsiPenghasilanSampingan: String) {
        this.deskripsiPenghasilanSampingan = deskripsiPenghasilanSampingan
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

    fun getKreditId(): Int? {
        return kreditId
    }

    fun setKreditId(kreditId: Int?) {
        this.kreditId = kreditId
    }
}