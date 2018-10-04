package com.layanacomputindo.bprcma.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class Pekerjaan {
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
    @SerializedName("foto_1")
    @Expose
    private var foto1: String? = null
    @SerializedName("foto_2")
    @Expose
    private var foto2: String? = null
    @SerializedName("foto_pendukung_1")
    @Expose
    private var fotoPendukung1: String? = null
    @SerializedName("foto_pendukung_2")
    @Expose
    private var fotoPendukung2: String? = null

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

    fun getFoto1(): String? {
        return foto1
    }

    fun setFoto1(foto1: String) {
        this.foto1 = foto1
    }

    fun getFoto2(): String? {
        return foto2
    }

    fun setFoto2(foto2: String) {
        this.foto2 = foto2
    }

    fun getFotoPendukung1(): String? {
        return fotoPendukung1
    }

    fun setFotoPendukung1(fotoPendukung1: String) {
        this.fotoPendukung1 = fotoPendukung1
    }

    fun getFotoPendukung2(): String? {
        return fotoPendukung2
    }

    fun setFotoPendukung2(fotoPendukung2: String) {
        this.fotoPendukung2 = fotoPendukung2
    }

}