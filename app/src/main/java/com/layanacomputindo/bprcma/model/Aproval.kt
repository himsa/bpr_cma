package com.layanacomputindo.bprcma.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class Aproval {
    @SerializedName("id")
    @Expose
    private var id: Int? = null
    @SerializedName("user_id")
    @Expose
    private var userId: Int? = null
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
    @SerializedName("kolektibilitas")
    @Expose
    private var kolektibilitas: Any? = null
    @SerializedName("user")
    @Expose
    private var user: User? = null
    @SerializedName("kredit")
    @Expose
    private var kredit: List<Kredit>? = null

    fun getId(): Int? {
        return id
    }

    fun setId(id: Int?) {
        this.id = id
    }

    fun getUserId(): Int? {
        return userId
    }

    fun setUserId(userId: Int?) {
        this.userId = userId
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

    fun getKolektibilitas(): Any? {
        return kolektibilitas
    }

    fun setKolektibilitas(kolektibilitas: Any) {
        this.kolektibilitas = kolektibilitas
    }

    fun getUser(): User? {
        return user
    }

    fun setUser(user: User) {
        this.user = user
    }

    fun getKredit(): List<Kredit>? {
        return kredit
    }

    fun setKredit(kredit: List<Kredit>) {
        this.kredit = kredit
    }
}