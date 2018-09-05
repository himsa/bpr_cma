package com.layanacomputindo.bprcma.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class Pasangan {
    @SerializedName("id")
    @Expose
    private var id: Int? = null
    @SerializedName("debitur_id")
    @Expose
    private var debiturId: Int? = null
    @SerializedName("nama")
    @Expose
    private var nama: String? = null
    @SerializedName("alamat")
    @Expose
    private var alamat: String? = null
    @SerializedName("tanggal_lahir")
    @Expose
    private var tanggalLahir: String? = null
    @SerializedName("tempat_lahir")
    @Expose
    private var tempatLahir: String? = null
    @SerializedName("no_ktp")
    @Expose
    private var noKtp: String? = null
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

    fun getNama(): String? {
        return nama
    }

    fun setNama(nama: String) {
        this.nama = nama
    }

    fun getAlamat(): String? {
        return alamat
    }

    fun setAlamat(alamat: String) {
        this.alamat = alamat
    }

    fun getTanggalLahir(): String? {
        return tanggalLahir
    }

    fun setTanggalLahir(tanggalLahir: String) {
        this.tanggalLahir = tanggalLahir
    }

    fun getTempatLahir(): String? {
        return tempatLahir
    }

    fun setTempatLahir(tempatLahir: String) {
        this.tempatLahir = tempatLahir
    }

    fun getNoKtp(): String? {
        return noKtp
    }

    fun setNoKtp(noKtp: String) {
        this.noKtp = noKtp
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