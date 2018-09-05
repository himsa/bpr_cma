package com.layanacomputindo.bprcma.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class FotoPekerjaan {
    @SerializedName("id")
    @Expose
    private var id: Int? = null
    @SerializedName("debitur_id")
    @Expose
    private var debiturId: Int? = null
    @SerializedName("latitude")
    @Expose
    private var latitude: String? = null
    @SerializedName("longitude")
    @Expose
    private var longitude: String? = null
    @SerializedName("jenis_utama_pekerjaan")
    @Expose
    private var jenisUtamaPekerjaan: String? = null
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

    fun getDebiturId(): Int? {
        return debiturId
    }

    fun setDebiturId(debiturId: Int?) {
        this.debiturId = debiturId
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

    fun getJenisUtamaPekerjaan(): String? {
        return jenisUtamaPekerjaan
    }

    fun setJenisUtamaPekerjaan(jenisUtamaPekerjaan: String) {
        this.jenisUtamaPekerjaan = jenisUtamaPekerjaan
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